package nathja.finalproject.asynctask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnStart;

    MyAsyncTask myAsyncTask;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize your process
                //Pass the main Activity, MainActivity, to your process
                myAsyncTask = new MyAsyncTask( MainActivity.this);
                //Call the execute function to trigger the process
                myAsyncTask.execute();
            }
        });

    }
}