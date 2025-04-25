package nathja.finalproject.sliderimages;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewPagerCircleIndicatorExcercise extends AppCompatActivity {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private List<ImagesSlider> imagesList;
    private SliderAdapter sliderAdapter;
    APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_circle_indicator_excercise);

        viewPager = findViewById(R.id.viewpage);
        circleIndicator = findViewById(R.id.circle_indicator);

        loadImages(1111);
        setupViewPager(imagesList);
    }

    private void loadImages(int position) {

        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.LoadImageSlider(position).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, retrofit2.Response<MessageModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ImagesSlider> images = response.body().getResult();
                    setupViewPager(images);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to load images", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewPager(List<ImagesSlider> images) {
        sliderAdapter = new SliderAdapter(this, images);
        viewPager.setAdapter(sliderAdapter);
        circleIndicator.setViewPager(viewPager);
    }
}