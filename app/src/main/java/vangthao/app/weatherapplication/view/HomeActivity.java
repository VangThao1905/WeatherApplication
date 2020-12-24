package vangthao.app.weatherapplication.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import vangthao.app.weatherapplication.R;
import vangthao.app.weatherapplication.databinding.ActivityHomeBinding;
import vangthao.app.weatherapplication.model.places.City;
import vangthao.app.weatherapplication.model.places.CitySave;
import vangthao.app.weatherapplication.viewmodel.CitySaveViewModel;
import vangthao.app.weatherapplication.viewmodel.LoginViewModel;
import vangthao.app.weatherapplication.viewmodel.WeatherViewModel;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding activityMainBinding;
    private WeatherViewModel weatherViewModel;
    private LoginViewModel loginViewModel;
    private CitySaveViewModel citySaveViewModel;

    private City cityShowing;
    private String userEmail;

    private ImageView imgViewSavePlace;

    private static final String units = "metric";
    private static final String appId = "107b3310315dd2200e5af401ab8c6b70";

    private static final int REQUEST_CODE_SEE_WEATHER_BY_PLACE = 1;
    private static final int REQUEST_CODE_LOGIN = 2;
    private static final int REQUST_CODE_PLACE_MANAGEMENT = 3;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ((BaseApplication) getApplication()).getAppComponent().inject(this);

        weatherViewModel = new ViewModelProvider(this, viewModelFactory).get(WeatherViewModel.class);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        citySaveViewModel = new ViewModelProvider(this).get(CitySaveViewModel.class);

        userEmail = loginViewModel.loadSessionData();

        //Default city
        String lat = "21.0245";
        String lon = "105.8412";
        cityShowing = new City(2, lat, lon, "Hanoi");
        loadDataWeather(lat, lon, units, appId);
        loadViews();
        loadEventList();
    }

    private void loadViews() {
        imgViewSavePlace = findViewById(R.id.imgViewSavePlace);
    }

    private void loadEventList() {
        imgViewSavePlace.setOnClickListener(v -> {
            if (cityShowing != null && !userEmail.equals("NO EMAIL")) {
                CitySave citySave = new CitySave(cityShowing, userEmail);
                citySaveViewModel.saveCity(citySave);
            } else {
                showActivityLogin();
            }
        });
    }


    public void loadDataWeather(String lat, String lon, String units, String appId) {
        weatherViewModel.getWeatherResponseMutableLiveData(lat, lon, units, appId).observe(this, weatherResponse -> {
            activityMainBinding.setWeather(weatherResponse);
            weatherViewModel.setWeatherTypeIcon(activityMainBinding.imagWeatherType, weatherResponse.getWeather().get(0).getIcon());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuMyAccount) {
            if (!userEmail.equals("NO EMAIL")) {
                showDialogMyAccount();
            } else {
                showActivityLogin();
            }
        } else if (id == R.id.menuSeeWeatherByPlace) {
            Intent intentSeeWeatherByPlace = new Intent(HomeActivity.this, SeeWeatherByPlaceActivity.class);
            startActivityForResult(intentSeeWeatherByPlace, REQUEST_CODE_SEE_WEATHER_BY_PLACE);
        } else if (id == R.id.menuPlacesManagement) {
            if (!userEmail.equals("NO EMAIL")) {
                Intent intentPlaceManagement = new Intent(HomeActivity.this, PlaceManagement.class);
                intentPlaceManagement.putExtra("useremail", userEmail);
                startActivityForResult(intentPlaceManagement, REQUST_CODE_PLACE_MANAGEMENT);
            } else {
                showActivityLogin();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void showActivityLogin() {
        Intent intentLogin = new Intent(HomeActivity.this, LoginActivity.class);
        startActivityForResult(intentLogin, REQUEST_CODE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((requestCode == REQUEST_CODE_SEE_WEATHER_BY_PLACE || requestCode == REQUST_CODE_PLACE_MANAGEMENT) && resultCode == RESULT_OK && data != null) {
            loadDataWeatherByCity(data);
        }

        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK && data != null) {
            userEmail = data.getStringExtra("email");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void loadDataWeatherByCity(Intent data) {
        cityShowing = (City) data.getSerializableExtra("city");
        String resultLat = cityShowing.getLat();
        String resultLon = cityShowing.getLon();
        loadDataWeather(resultLat, resultLon, units, appId);
    }

    private void showDialogMyAccount() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_my_account);
        TextView txtEmail = dialog.findViewById(R.id.txtEmail);
        Button btnLogout = dialog.findViewById(R.id.btnLogout);
        txtEmail.setText(userEmail);

        btnLogout.setOnClickListener(v -> {
            loginViewModel.logoutUser();
            userEmail = "NO EMAIL";
            Toast.makeText(HomeActivity.this, "Logout success!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }
}