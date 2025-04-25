package nathja.finalproject.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Exercise1 extends AppCompatActivity {
    Button buttonTxt;
    EditText usernameTxt, passwordTxt;
    CheckBox cbRememberMe;
    SharedPreferences sharedPreferences;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1);

        AnhXa();

        buttonTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();
                if (username.equals("admin") && password.equals("admin")) {
                    Toast.makeText(Exercise1.this,
                    "Login successful", Toast.LENGTH_SHORT).show();
                    //if there is a check
                    if (cbRememberMe.isChecked()) {
                        //Edit the contents of the archive file
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("taikhoan", username);
                        editor.putString("matkhau", password);
                        editor.putBoolean("trangthai", true);
                        editor.commit(); //confirm saving
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("taikhoan");
                        editor.remove("password");
                        editor.remove("page");
                        editor.commit();
                    }
                }
                else{
                    Toast.makeText(Exercise1.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //no need to create shared preference
        sharedPreferences = getSharedPreferences ("dataLogin", MODE_PRIVATE);
        //get sharedPreferences value
        usernameTxt.setText(sharedPreferences.getString( "taikhoan", ""));
        passwordTxt.setText(sharedPreferences.getString( "matkhau", ""));
        cbRememberMe.setChecked(sharedPreferences.getBoolean( "trangthai",  false));
    }
    private void AnhXa(){
        buttonTxt = (Button) findViewById(R.id.buttonTxt);
        usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        cbRememberMe = (CheckBox) findViewById(R.id.cbmemberme);
    }
}