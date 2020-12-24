package vangthao.app.weatherapplication.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import vangthao.app.weatherapplication.model.places.CitySave;
import vangthao.app.weatherapplication.repository.CitySavedRepository;

public class CitySaveViewModel extends AndroidViewModel {
    private final CitySavedRepository citySavedRepository;
    private MutableLiveData<ArrayList<CitySave>> allCities;

    public CitySaveViewModel(@NonNull Application application) {
        super(application);
        citySavedRepository = new CitySavedRepository(application);
    }

    public void init(Context context, String email) {
        if (allCities != null) {
            return;
        }
        allCities = CitySavedRepository.getInstance(context).getCitiesSaved(email);
    }

    public void saveCity(CitySave citySave) {
        citySavedRepository.saveCity(citySave);
    }

    public void deleteCity(CitySave citySave) {
        citySavedRepository.deleteCity(citySave);
    }


    public LiveData<ArrayList<CitySave>> getAllCitiesSaved() {
        return allCities;
    }

}
