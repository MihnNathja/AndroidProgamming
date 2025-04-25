package nathja.finalproject.sliderimages;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class SliderViewActivity extends AppCompatActivity {

    private SliderView sliderView;
    private ArrayList<Integer> arrayList;
    private SliderAdapter2 sliderAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_view);

        sliderView = findViewById(R.id.imageSlider);
        arrayList = new ArrayList<>();
        arrayList.add(R.drawable.bg1);
        arrayList.add(R.drawable.bg2);

        sliderAdapter2 = new SliderAdapter2(getApplicationContext(), arrayList);
        sliderView.setSliderAdapter(sliderAdapter2);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(getResources().getColor(R.color.black));
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInSec(5);
    }
}