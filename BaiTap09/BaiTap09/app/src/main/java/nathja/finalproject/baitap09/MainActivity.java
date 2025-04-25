package nathja.finalproject.baitap09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {
    ImageView imgAvatar;

    private void AnhXa() {
        imgAvatar = findViewById(R.id.imgAvatar);
    }

    // Khai báo launcher
    private ActivityResultLauncher<Intent> uploadLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();

        // Khởi tạo launcher
        uploadLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String avatarUrl = result.getData().getStringExtra("avatarUrl");
                        if (avatarUrl != null) {
                            Glide.with(this)
                                    .load(avatarUrl)
                                    .into(imgAvatar);
                        }
                    }
                });

        imgAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UploadImageActivity.class);
            uploadLauncher.launch(intent);
        });
    }
}