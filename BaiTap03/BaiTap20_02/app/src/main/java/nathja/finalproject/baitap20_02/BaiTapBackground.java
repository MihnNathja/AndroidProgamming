package nathja.finalproject.baitap20_02;

import android.os.Bundle;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class BaiTapBackground extends AppCompatActivity {
    private ConstraintLayout layout;
    private ArrayList<Integer> backgrounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_tap_background);

        layout = findViewById(R.id.main);
        Switch switchControl = findViewById(R.id.switch1);

        backgrounds = new ArrayList<>();
        backgrounds.add(R.drawable.bg1);
        backgrounds.add(R.drawable.bg2);
        backgrounds.add(R.drawable.bg3);
        backgrounds.add(R.drawable.bg4);

        changeBackground();

        switchControl.setOnCheckedChangeListener((buttonView, isChecked) -> {
            changeBackground();
        });
    }

    private void changeBackground() {
        Random random = new Random();
        int position = random.nextInt(backgrounds.size());
        layout.setBackgroundResource(backgrounds.get(position));
    }
}