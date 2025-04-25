package nathja.finalproject.sliderimages;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class ViewPager2Activity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Images> imagesList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);
        // render viewpager2
        viewPager2 = findViewById(R.id.viewpage2);
        circleIndicator3 = findViewById(R.id.circle_indicator3);
        imagesList1= getListImages();
        ImagesViewPager2Adapter adapter1 = new ImagesViewPager2Adapter(imagesList1);
        viewPager2.setAdapter(adapter1);

        //link viewpager2 and indicator3

        circleIndicator3.setViewPager (viewPager2);

        handler.postDelayed(runnable, 3000);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable); // Xóa các tác vụ đang chờ
                handler.postDelayed(runnable, 3000); // Đặt lại tác vụ sau 3 giây
            }
        });
        viewPager2.setPageTransformer(new DepthPageTransformer());
    }

    public class DepthPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.75f;
        public void transformPage (View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.

                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left pa
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setTranslationZ(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);
            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(position);
                // Counteract the default slide transition.
                view.setTranslationX(pageWidth - position);
                // Move it behind the left page
                view.setTranslationZ(-1f);
                // Scale the page down (between MIN_SCALE and 1).
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)*(1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else { // (1, +Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == imagesList1.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    @Override
    protected void onPause(){
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume(){
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
    private List<Images> getListImages(){

        List<Images> list = new ArrayList<>();
        list.add(new Images (R.drawable.bg1));
        list.add(new Images (R.drawable.bg2));
        return list;

    }
}