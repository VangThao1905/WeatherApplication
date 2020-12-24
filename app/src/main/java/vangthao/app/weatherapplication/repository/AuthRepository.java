package vangthao.app.weatherapplication.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AuthRepository {
    private final Application application;
    private final FirebaseAuth firebaseAuth;
    public final DatabaseReference databaseReference;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private static final String KEY_EMAIL = "email";
    private final MutableLiveData<FirebaseUser> userMutableLiveData;

    public AuthRepository(Application application) {
        this.application = application;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = application.getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
        userMutableLiveData = new MutableLiveData<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                    } else {
                        Toast.makeText(application, "Registration Failed:" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                        //Toast.makeText(application, "Login success!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(application, "Login Failed:" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void saveSessionIntoSharedPreferences(String email){
        editor.putString(KEY_EMAIL,email);
        editor.apply();
    }

    public String loadSessionData(){
        return sharedPreferences.getString("email","NO EMAIL");
    }

    public void logoutUser(){
        editor.putString(KEY_EMAIL, "NO EMAIL");
        editor.commit();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
