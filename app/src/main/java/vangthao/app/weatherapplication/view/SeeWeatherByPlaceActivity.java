package vangthao.app.weatherapplication.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import vangthao.app.weatherapplication.R;
import vangthao.app.weatherapplication.adapter.CityAdapter;
import vangthao.app.weatherapplication.viewmodel.CityViewModel;

public class SeeWeatherByPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_weather_by_place);
        setTitle("See Weather By Place");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        RecyclerView rvCities = findViewById(R.id.rvCities);
        rvCities.setLayoutManager(new LinearLayoutManager(this));
        rvCities.setHasFixedSize(true);

        CityAdapter cityAdapter = new CityAdapter();
        rvCities.setAdapter(cityAdapter);

        CityViewModel cityViewModel = new ViewModelProvider(this).get(CityViewModel.class);
        cityViewModel.getAllCitys().observe(this, cityAdapter::setCities);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}