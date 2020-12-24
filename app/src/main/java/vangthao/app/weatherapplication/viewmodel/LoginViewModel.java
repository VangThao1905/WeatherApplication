package vangthao.app.weatherapplication.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import vangthao.app.weatherapplication.repository.AuthRepository;

public class LoginViewModel extends AndroidViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        userMutableLiveData = authRepository.getUserMutableLiveData();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
        authRepository.login(email, password);
    }

    public void saveSessionIntoSharedPreferences(String email) {
        authRepository.saveSessionIntoSharedPreferences(email);
    }

    public String loadSessionData() {
        return authRepository.loadSessionData();
    }

    public void logoutUser(){
        authRepository.logoutUser();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
