package vangthao.app.weatherapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import vangthao.app.weatherapplication.model.places.City;
import vangthao.app.weatherapplication.repository.CityRepository;

public class CityViewModel extends AndroidViewModel {
    private final CityRepository cityRepository;
    private final LiveData<List<City>> allCities;

    public CityViewModel(@NonNull Application application) {
        super(application);
        cityRepository = new CityRepository(application);
        allCities = cityRepository.getAllCities();
    }

    public void insert(City city){
        cityRepository.insert(city);
    }

    public void delete(City city){
        cityRepository.delete(city);
    }

    public LiveData<List<City>> getAllCitys(){
        return allCities;
    }
}
