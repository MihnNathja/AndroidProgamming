package nathja.excercises.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class ManHinh04 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide  the title not the title bar
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_man_hinh04);


        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Integer> squareNumbers = new ArrayList<>();
        Button btnClick = (Button) findViewById(R.id.btnInput);
        EditText inputArray = (EditText) findViewById(R.id.inputArray);
        TextView soChinhPhuong = (TextView) findViewById(R.id.soChinhPhuong);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String inputText = inputArray.getText().toString().trim();
                    int number = Integer.parseInt(inputText);
                    int tempNum;
                    for (int i = 1; i <= number; i++) {
                        tempNum = createRandomNumber();
                        numbers.add(tempNum);
                        if(laSoChinhPhuong(tempNum)){
                            squareNumbers.add(tempNum);
                        }
                    }

                    soChinhPhuong.setText(numbers.toString());
                    if (!squareNumbers.isEmpty()) {
                        Toast.makeText(ManHinh04.this, "Số chính phương: " + squareNumbers, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ManHinh04.this, "Không có số chính phương!", Toast.LENGTH_LONG).show();
                    }
                } catch (NumberFormatException e) {
                    soChinhPhuong.setText("Vui lòng nhập số!");
                }
            }

            private boolean laSoChinhPhuong(int number) {
                if (number < 0) return false;
                int sqrt = (int) Math.sqrt(number);
                return sqrt * sqrt == number;
            }

            private int createRandomNumber() {
                Random random = new Random();
                int number = random.nextInt(1000);
                return number;
            }
        });
    }
}