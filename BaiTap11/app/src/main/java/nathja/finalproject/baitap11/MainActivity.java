package nathja.finalproject.baitap11;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;

    private VideosFireBaseAdapter videosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.vpager);
        getVideos();
    }
    private void getVideos() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("video");
        FirebaseRecyclerOptions<Video1Model> options = new FirebaseRecyclerOptions.Builder<Video1Model>()
                .setQuery(mDatabase, Video1Model.class).build();

        // Lấy thông tin người dùng từ Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uri photoUrl = (user != null) ? user.getPhotoUrl() : null;

        videosAdapter = new VideosFireBaseAdapter(options, photoUrl);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(videosAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        videosAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        videosAdapter.stopListening();
    }
    @Override
    protected void onResume () {
        super.onResume();
        videosAdapter.notifyDataSetChanged();
    }
}