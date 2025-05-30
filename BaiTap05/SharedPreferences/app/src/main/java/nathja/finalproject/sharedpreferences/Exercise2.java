package nathja.finalproject.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Exercise2 extends AppCompatActivity {
// Declare global variables.
    private EditText mEmailView;
    private EditText mPasswordView;
    private CheckBox checkBoxRememberMe;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise2);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction (TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBoxRememberMe);
        //Here we will validate saved preferences
        if (!new PrefManager (this).isUserLogedOut()) {
        //user's email and password both are saved in preferences
            startHomeActivity();
        }

    }
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString(); String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        // Check for a valid password, if the user entered one.

        if (!TextUtils.isEmpty(password) && ! isPasswordValid (password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
// Check for a valid email address.

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            // save data in local shared preferences
            if (checkBoxRememberMe.isChecked())
                saveLoginDetails (email, password);
            startHomeActivity();
        }
    }
    private void startHomeActivity() {
        Intent intent = new Intent( this, Exercise2.class);
        startActivity(intent);
        finish();
    }

    private void saveLoginDetails (String email, String password) {
        new PrefManager (this).saveLoginDetails (email, password);
    }

    private boolean isEmailValid (String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid (String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}