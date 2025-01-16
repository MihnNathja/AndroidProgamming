package nathja.excercises.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ManHinh03 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide  the title not the title bar
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_man_hinh03);

        ArrayList<Integer> numbers = new ArrayList<>();
        Button btnClick = (Button) findViewById(R.id.btnInput);
        EditText inputArray = (EditText) findViewById(R.id.inputArray);
        TextView primeNumbers = (TextView) findViewById(R.id.primeNumbers);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                String inputText = inputArray.getText().toString().trim();
                int number = Integer.parseInt(inputText);

                if(isPrime(number)){
                    numbers.add(number);
                }
                /* primeNumbers.setText(numbers.toString());
                Mới đầu em tưởng là in ra giao diện, hihi
                */
                Log.d("PrimeNumbers", "Các số nguyên tố trong mảng là:");
                for (int num : numbers) {
                    if (isPrime(num)) {
                        Log.d("PrimeNumbers", String.valueOf(num));
                    }
                }
                } catch (NumberFormatException e) {
                    primeNumbers.setText("Vui lòng nhập số!");
                }
            }
            private boolean isPrime(int number) {
                if (number < 2) return false;
                for (int i = 2; i <= Math.sqrt(number); i++) {
                    if (number % i == 0) return false;
                }
                return true;
            }
        });


    }
}