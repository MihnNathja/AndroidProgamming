package nathja.finalproject.sliderimages;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ViewFlipperActivity extends AppCompatActivity {


    ViewFlipper viewFlipperMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);
        viewFlipperMain = findViewById(R.id.viewFlipperMain);
        ActionViewFlipperMain();
    }

    //Flipper function
    private void ActionViewFlipperMain() {

        List<String> arrayListFlipper = new ArrayList<>();

        arrayListFlipper.add("https://ocafe.net/wp-content/uploads/2024/10/anh-nen-may-tinh-4k-1.jpg");
        arrayListFlipper.add("https://ocafe.net/wp-content/uploads/2024/10/hinh-nen-may-tinh-dep-4k-9.jpg");
        arrayListFlipper.add("https://ocafe.net/wp-content/uploads/2024/10/hinh-nen-may-tinh-dep-4k-8.jpg");
        arrayListFlipper.add("https://ocafe.net/wp-content/uploads/2024/10/hinh-nen-may-tinh-dep-4k-7.jpg");

        for (int i = 0; i < arrayListFlipper.size(); i++) {

            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(arrayListFlipper.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperMain.addView(imageView);
        }

        viewFlipperMain.setFlipInterval(3000);

        viewFlipperMain.setAutoStart(true);

        //set animation for flipper
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        viewFlipperMain.setInAnimation(slide_in);
        viewFlipperMain.setOutAnimation(slide_out);

    }
}