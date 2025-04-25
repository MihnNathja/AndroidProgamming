package nathja.finalproject.baitap09;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageActivity extends AppCompatActivity {
    Button btnChoose, btnUpload;
    ImageView imageViewChoose;
    EditText editTextUserName, editTextUserId;
    private Uri mUri;
    private ProgressDialog mProgressDialog;

    private void AnhXa() {
        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        editTextUserName = findViewById(R.id.editUserName);
        editTextUserId = findViewById(R.id.editUserId);
        imageViewChoose = findViewById(R.id.imgChoose);
    }

    public static final int MY_REQUEST_CODE = 100;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };

    public static String[] storge_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) return;
                        mUri = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUri);
                            imageViewChoose.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        AnhXa();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait upload....");

        btnChoose.setOnClickListener(v -> CheckPermission());
        btnUpload.setOnClickListener(v -> {
            if (mUri != null) UploadImage1();
        });
    }

    public void UploadImage1() {
        mProgressDialog.show();
        String username = editTextUserName.getText().toString().trim();
        String userId = editTextUserId.getText().toString().trim();
        RequestBody requestUsername = RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody requestId = RequestBody.create(MediaType.parse("multipart/form-data"), userId);

        try {
            InputStream inputStream = getContentResolver().openInputStream(mUri);
            if (inputStream == null) {
                Log.e("DEBUG_UPLOAD", "Không đọc được InputStream từ Uri!");
                Toast.makeText(this, "Không thể đọc ảnh!", Toast.LENGTH_SHORT).show();
                return;
            }
            byte[] fileBytes = new byte[inputStream.available()];
            inputStream.read(fileBytes);
            Log.d("DEBUG_UPLOAD", "Kích thước ảnh (bytes): " + fileBytes.length);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), fileBytes);
            MultipartBody.Part partbodyavatar = MultipartBody.Part.createFormData("images", "upload.jpg", requestFile);
            Log.d("DEBUG_UPLOAD", "Đã tạo MultipartBody.Part thành công");

            Call<ResponseBody> call = ServiceAPI.serviceapi.upload(requestId, partbodyavatar);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    mProgressDialog.dismiss();
                    try {
                        String raw = response.body().string();
                        Log.d("RAW_RESPONSE", raw);
                        Toast.makeText(UploadImageActivity.this, "Phản hồi: " + raw, Toast.LENGTH_LONG).show();

                        // Nếu raw là JSON object, có thể dùng JSONObject để parse thủ công
                        if (raw.startsWith("{")) {
                            JSONObject json = new JSONObject(raw);
                            boolean success = json.getBoolean("success");
                            if (success && json.has("result")) {
                                JSONArray result = json.getJSONArray("result");
                                if (result.length() > 0) {
                                    String avatarUrl = result.getJSONObject(0).getString("images");
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("avatarUrl", avatarUrl);
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                }
                            }
                        }

                    } catch (Exception e) {
                        Log.e("READ_ERROR", "Không đọc được body hoặc JSON lỗi", e);
                        Toast.makeText(UploadImageActivity.this, "Lỗi đọc phản hồi", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Log.e("API_ERROR", "Upload failed: " + t.getMessage(), t);
                    Toast.makeText(UploadImageActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            mProgressDialog.dismiss();
            e.printStackTrace();
            Toast.makeText(this, "Không thể xử lý file ảnh!", Toast.LENGTH_SHORT).show();
        }
    }

    public static String[] permissions() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ? storge_permissions_33 : storge_permissions;
    }

    private void CheckPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissions(permissions(), MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }
}