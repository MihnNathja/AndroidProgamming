package nathja.finalproject.baitap11;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;


public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 101;
    private static final int REQUEST_PERMISSION = 101;  // Mã yêu cầu quyền runtime
    private static final int PICK_VIDEO_REQUEST = 2; // Đặt mã yêu cầu để nhận video
    private Button btnUploadVideo; // Biến cho nút upload video
    private ImageView imPerson;
    private TextView tvUserEmail, tvVideoCount;
    private RecyclerView recyclerView;
    private FirebaseUser user;
    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<Video1Model, VideosFireBaseAdapter.MyHolder> videosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_VIDEO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_VIDEO}, REQUEST_PERMISSION);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }



        imPerson = findViewById(R.id.imPerson);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvVideoCount = findViewById(R.id.tvVideoCount); // Số lượng video
        recyclerView = findViewById(R.id.recyclerViewVideos); // RecyclerView hiển thị video
        // Khởi tạo nút upload video
        btnUploadVideo = findViewById(R.id.btnUploadVideo);

        // Gắn sự kiện click cho nút upload video
        btnUploadVideo.setOnClickListener(v -> openVideoGallery());
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Hiển thị email người dùng
            tvUserEmail.setText("Email: " + email);

            // Hiển thị avatar
            if (photoUrl != null) {
                Glide.with(this)
                        .load(photoUrl)
                        .circleCrop()
                        .into(imPerson);
            } else {
                imPerson.setImageResource(R.drawable.ic_person_pin); // Avatar mặc định
            }
        }
        imPerson.setOnClickListener(v -> openGalleryForAvatar());

        // Lấy danh sách video đã đăng của người dùng
        getUserVideos();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, có thể tiếp tục thao tác
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Quyền không được cấp, thông báo cho người dùng
                Toast.makeText(this, "Permission denied. Cannot upload video", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openGalleryForAvatar() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void openVideoGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*");
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri videoUri = data.getData();

            // Giữ quyền truy cập Uri (cần thiết cho ACTION_OPEN_DOCUMENT)
            final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            getContentResolver().takePersistableUriPermission(videoUri, takeFlags);

            // Kiểm tra quyền tương ứng với Android version
            boolean hasPermission;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                hasPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED;
            } else {
                hasPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            }

            if (hasPermission) {
                new UploadVideoTask().execute(videoUri);
            } else {
                Toast.makeText(this, "Permission denied. Cannot upload video", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            new UploadAvatarTask().execute(imageUri);
        }
    }
    private void updateFirebaseUserProfile(String imageUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(imageUrl))
                    .build();

            user.updateProfile(request).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Lưu avatar + email vào Realtime DB
                    DatabaseReference userRef = FirebaseDatabase.getInstance()
                            .getReference("user")
                            .child(user.getUid());

                    userRef.child("avatarUrl").setValue(imageUrl);
                    userRef.child("email").setValue(user.getEmail());

                    Log.d("ProfileActivity", "User profile updated and saved to DB.");
                }
            });
        }
    }

    private void saveVideoInfoToDatabase(String videoUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String videoId = UUID.randomUUID().toString();

            // Tạo đối tượng Video
            Video1Model video = new Video1Model(videoId, userId, videoUrl, "Video Title", "Video Description");

            // Lưu video vào Firebase Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("video");
            databaseReference.child(videoId).setValue(video)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(ProfileActivity.this, "Video uploaded successfully", Toast.LENGTH_SHORT).show();
                        Log.d("ProfileActivity", "Video saved successfully in Firebase");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ProfileActivity.this, "Failed to save video info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ProfileActivity", "Failed to save video info: " + e.getMessage());
                    });
        }
    }

    private void getUserVideos() {
        mDatabase = FirebaseDatabase.getInstance().getReference("video");

        // Truy vấn các video đã đăng bởi người dùng hiện tại
        Query query = mDatabase.orderByChild("userId").equalTo(user.getUid());

        FirebaseRecyclerOptions<Video1Model> options = new FirebaseRecyclerOptions.Builder<Video1Model>()
                .setQuery(query, Video1Model.class)
                .build();

        videosAdapter = new VideosFireBaseAdapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(videosAdapter);

        // Hiển thị số lượng video người dùng đã đăng
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvVideoCount.setText("Videos: " + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (videosAdapter != null) {
            videosAdapter.startListening();
        }

        // Cập nhật avatar nếu user có avatar mới
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Uri photoUrl = user.getPhotoUrl();
            if (photoUrl != null) {
                Glide.with(this)
                        .load(photoUrl)
                        .circleCrop()
                        .into(imPerson);
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (videosAdapter != null) {
            videosAdapter.stopListening();
        }
    }
    private class UploadVideoTask extends AsyncTask<Uri, Void, String> {

        @Override
        protected String doInBackground(Uri... uris) {
            Uri videoUri = uris[0];
            Cloudinary cloudinary = CloudinaryConfig.getCloudinary();

            try (InputStream inputStream = getContentResolver().openInputStream(videoUri)) {
                if (inputStream == null) {
                    return "Error: Cannot open video stream.";
                }

                // Upload video to Cloudinary
                Map uploadResult = cloudinary.uploader().upload(
                        inputStream,
                        ObjectUtils.asMap("resource_type", "video")
                );

                return (String) uploadResult.get("secure_url"); // Trả về URL video
            } catch (IOException e) {
                e.printStackTrace();
                return "Error uploading video: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.startsWith("Error")) {
                Toast.makeText(ProfileActivity.this, result, Toast.LENGTH_SHORT).show();
            } else {
                saveVideoInfoToDatabase(result);
                Toast.makeText(ProfileActivity.this, "Video uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class UploadAvatarTask extends AsyncTask<Uri, Void, String> {

        @Override
        protected String doInBackground(Uri... uris) {
            Uri imageUri = uris[0];
            try (InputStream inputStream = getContentResolver().openInputStream(imageUri)) {
                Map uploadResult = CloudinaryConfig.getCloudinary().uploader()
                        .upload(inputStream, ObjectUtils.asMap("resource_type", "image"));

                return (String) uploadResult.get("secure_url");

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("UploadAvatar", "Exception: ", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String imageUrl) {
            if (imageUrl != null) {
                updateFirebaseUserProfile(imageUrl);

                Glide.with(ProfileActivity.this)
                        .load(imageUrl)
                        .circleCrop()
                        .into(imPerson);

                Toast.makeText(ProfileActivity.this, "Avatar updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Upload avatar failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
