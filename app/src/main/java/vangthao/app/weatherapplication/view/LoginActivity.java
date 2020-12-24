package vangthao.app.weatherapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import vangthao.app.weatherapplication.R;
import vangthao.app.weatherapplication.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailLogin;
    private EditText edtPasswordLogin;
    private TextView txtRegister;
    private Button btnLogin;
    private LoginViewModel loginViewModel;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadViews();
        loadEventList();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                String email = firebaseUser.getEmail();
                loginViewModel.saveSessionIntoSharedPreferences(email);
                Intent intent = new Intent();
                intent.putExtra("email",email);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    private void loadEventList() {
        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            String email = edtEmailLogin.getText().toString();
            String password = edtPasswordLogin.getText().toString();
            if (email.length() > 0 && password.length() > 0) {
                loginViewModel.login(email, password);
            }
        });
    }

    private void loadViews() {
        txtRegister = findViewById(R.id.txtRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
    }
}