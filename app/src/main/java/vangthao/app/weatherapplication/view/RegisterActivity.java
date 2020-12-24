package vangthao.app.weatherapplication.view;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import vangthao.app.weatherapplication.R;
import vangthao.app.weatherapplication.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsernameRegister;
    private EditText edtEmailRegister;
    private EditText edtPasswordRegister;
    private EditText edtRetypePasswordRegister;
    private TextView txtHaveAccont;
    private Button btnRegister;

    private RegisterViewModel registerViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loadViews();
        loadEventList();

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if(firebaseUser != null){
                Toast.makeText(RegisterActivity.this, "User created!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void loadEventList() {
        btnRegister.setOnClickListener(v -> {
            String username = edtUsernameRegister.getText().toString();
            String email = edtEmailRegister.getText().toString();
            String password = edtPasswordRegister.getText().toString();
            String retyePassword = edtRetypePasswordRegister.getText().toString();
            if(username.length() > 0 && email.length() > 0 && password.length()  > 0 && retyePassword.length() > 0){
                if(retyePassword.equals(password)){
                    registerViewModel.register(email,password);
                    resetInputFields();
                }else{
                    Toast.makeText(getApplicationContext(), "Password and RetypePassword Mismatched!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtHaveAccont.setOnClickListener(v -> finish());
    }

    private void resetInputFields(){
        edtUsernameRegister.setText("");
        edtEmailRegister.setText("");
        edtPasswordRegister.setText("");
        edtRetypePasswordRegister.setText("");
        edtUsernameRegister.requestFocus();
    }

    private void loadViews() {
        edtUsernameRegister = findViewById(R.id.edtUsernameRegister);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        edtRetypePasswordRegister = findViewById(R.id.edtRetypePasswordRegister);
        txtHaveAccont = findViewById(R.id.txtHaveAccount);
        btnRegister = findViewById(R.id.btnRegister);
    }
}