package vangthao.app.weatherapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import vangthao.app.weatherapplication.R;
import vangthao.app.weatherapplication.adapter.CitySavedAdapter;
import vangthao.app.weatherapplication.remote.CityLoadListener;
import vangthao.app.weatherapplication.viewmodel.CitySaveViewModel;

public class PlaceManagement extends AppCompatActivity implements CityLoadListener {

    private CitySavedAdapter cityAdapterSaved;
    private CitySaveViewModel citySaveViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_management);
        setTitle("Place Management");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String email = intent.getStringExtra("useremail");

        RecyclerView rvCitiesSaved = findViewById(R.id.rvCitiesSaved);
        rvCitiesSaved.setLayoutManager(new LinearLayoutManager(this));
        rvCitiesSaved.setHasFixedSize(true);

        citySaveViewModel = new ViewModelProvider(this).get(CitySaveViewModel.class);
        citySaveViewModel.init(PlaceManagement.this, email);
        cityAdapterSaved = new CitySavedAdapter(citySaveViewModel.getAllCitiesSaved().getValue());
        rvCitiesSaved.setAdapter(cityAdapterSaved);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCityLoaded() {
        citySaveViewModel.getAllCitiesSaved().observe(this, citySaves -> cityAdapterSaved.notifyDataSetChanged());
    }
}