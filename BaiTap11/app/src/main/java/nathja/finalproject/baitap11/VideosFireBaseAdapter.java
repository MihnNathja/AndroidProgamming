package nathja.finalproject.baitap11;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class VideosFireBaseAdapter extends FirebaseRecyclerAdapter<Video1Model, VideosFireBaseAdapter.MyHolder> {

    private Uri photoUrl;

    public VideosFireBaseAdapter(@NonNull FirebaseRecyclerOptions<Video1Model> options, Uri photoUrl) {
        super(options);
        this.photoUrl = photoUrl;
    }

    public VideosFireBaseAdapter(@NonNull FirebaseRecyclerOptions<Video1Model> options) {
        super(options);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull Video1Model model) {
        String videoId = model.getVideoId();
        if (videoId == null || videoId.isEmpty()) {
            Log.e("Adapter", "Video ID is null or empty, skipping item.");
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();
        Uri photoUrl = user.getPhotoUrl();

        // Hiển thị avatar
        if (photoUrl != null) {
            Glide.with(holder.imPerson.getContext())
                    .load(photoUrl)
                    .circleCrop()
                    .into(holder.imPerson);
        } else {
            holder.imPerson.setImageResource(R.drawable.ic_person_pin); // Avatar mặc định
        }


        holder.textVideoTitle.setText(model.getTitle());
        holder.textVideoDescription.setText(model.getDescription());

        // Kiểm tra video URL trước khi set
        String videoUrl = model.getVideoUrl();
        if (videoUrl != null && !videoUrl.isEmpty()) {
            holder.videoView.setVideoURI(Uri.parse(videoUrl));
        } else {
            Log.e("Adapter", "Video URL is null or empty for videoId: " + videoId);
        }

        holder.videoView.setOnPreparedListener(mp -> {
            holder.videoProgressBar.setVisibility(View.GONE);
            mp.start();
            float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
            float screenRatio = holder.videoView.getWidth() / (float) holder.videoView.getHeight();
            float scale = videoRatio / screenRatio;
            if (scale >= 1f) {
                holder.videoView.setScaleX(scale);
            } else {
                holder.videoView.setScaleY(1f / scale);
            }
        });

        holder.videoView.setOnCompletionListener(MediaPlayer::start);

        // Hiển thị icon like/dislike
        updateLikeDislikeIcons(holder, videoId, currentUserId);

        // Xử lý sự kiện Like / Dislike
        holder.like.setOnClickListener(v -> {
            toggleLike(videoId, currentUserId);
            notifyItemChanged(position);
        });

        holder.dislike.setOnClickListener(v -> {
            toggleDislike(videoId, currentUserId);
            notifyItemChanged(position);
        });

        // Hiển thị số lượt thích
        DatabaseReference likeCountRef = FirebaseDatabase.getInstance()
                .getReference("video")
                .child(videoId)
                .child("likes");

        likeCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long likeCount = snapshot.getChildrenCount();
                holder.tvLikeCount.setText(likeCount + " likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // Hiển thị số lượt không thích
        DatabaseReference dislikeCountRef = FirebaseDatabase.getInstance()
                .getReference("video")
                .child(videoId)
                .child("dislikes");

        dislikeCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long dislikeCount = snapshot.getChildrenCount();
                holder.tvDislikeCount.setText(dislikeCount + " dislikes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //Lấy thông tin người đăng
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("user")
                .child(model.getUserId());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String avatarUrl = snapshot.child("avatarUrl").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);

                if (avatarUrl != null) {
                    Glide.with(holder.imPersonUploadVideo.getContext())
                            .load(avatarUrl)
                            .circleCrop()
                            .into(holder.imPersonUploadVideo);
                }

                holder.tvPersonUploadVideo.setText(email); // nếu muốn hiển thị email
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Adapter", "Failed to load user info: " + error.getMessage());
            }
        });



        holder.imPerson.setOnClickListener(v -> {
            Intent intent = new Intent(holder.imPerson.getContext(), ProfileActivity.class);
            holder.imPerson.getContext().startActivity(intent);
        });
    }


    private void updateLikeDislikeIcons(MyHolder holder, String videoId, String userId) {
        DatabaseReference likeRef = FirebaseDatabase.getInstance()
                .getReference("video")
                .child(videoId)
                .child("likes")
                .child(userId);

        likeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.like.setImageResource(snapshot.exists() ? R.drawable.ic_fill_favorite : R.drawable.ic_favorite);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        DatabaseReference dislikeRef = FirebaseDatabase.getInstance()
                .getReference("video")
                .child(videoId)
                .child("dislikes")
                .child(userId);

        dislikeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.dislike.setImageResource(snapshot.exists() ? R.drawable.ic_fill_dislike : R.drawable.ic_dislike);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void toggleLike(String videoId, String userId) {
        DatabaseReference videoRef = FirebaseDatabase.getInstance().getReference("video").child(videoId);
        DatabaseReference likeRef = videoRef.child("likes").child(userId);
        DatabaseReference dislikeRef = videoRef.child("dislikes").child(userId);

        likeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    likeRef.removeValue(); // Bỏ like nếu đã like
                } else {
                    likeRef.setValue(true);         // Like video
                    dislikeRef.removeValue();       // Bỏ dislike nếu có
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    private void toggleDislike(String videoId, String userId) {
        DatabaseReference videoRef = FirebaseDatabase.getInstance().getReference("video").child(videoId);
        DatabaseReference dislikeRef = videoRef.child("dislikes").child(userId);
        DatabaseReference likeRef = videoRef.child("likes").child(userId);

        dislikeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dislikeRef.removeValue(); // Bỏ dislike nếu đã dislike
                } else {
                    dislikeRef.setValue(true);  // Dislike video
                    likeRef.removeValue();      // Bỏ like nếu có
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    public static class MyHolder extends RecyclerView.ViewHolder {

        private final VideoView videoView;
        private final ProgressBar videoProgressBar;
        private final TextView textVideoTitle;
        private final TextView textVideoDescription;
        private final TextView tvPersonUploadVideo;
        TextView tvLikeCount, tvDislikeCount;
        private final ImageView imPerson, imPersonUploadVideo, like, dislike, imShare, imMore;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            tvPersonUploadVideo = itemView.findViewById(R.id.tvPersonUploadVideo);
            imPerson = itemView.findViewById(R.id.imPerson);
            imPersonUploadVideo = itemView.findViewById(R.id.imPersonUploadVideo);
            like = itemView.findViewById(R.id.like);
            dislike = itemView.findViewById(R.id.dislike);
            imShare = itemView.findViewById(R.id.imShare);
            imMore = itemView.findViewById(R.id.imMore);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvDislikeCount = itemView.findViewById(R.id.tvDislikeCount);
        }
    }
}
