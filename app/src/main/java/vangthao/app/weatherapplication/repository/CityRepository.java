package vangthao.app.weatherapplication.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import java.util.List;

import vangthao.app.weatherapplication.data.CityDatabase;
import vangthao.app.weatherapplication.model.places.City;
import vangthao.app.weatherapplication.remote.CityDao;

public class CityRepository {
    private final CityDao cityDao;
    private final LiveData<List<City>> allCities;

    public CityRepository(Application application){
        CityDatabase cityDatabase = CityDatabase.getInstance(application);
        cityDao = cityDatabase.cityDao();
        allCities = cityDao.getAllCity();
    }

    public void insert(City city){
        new InsertCityAsynTask(cityDao).execute(city);
    }

    public void delete(City city){
        new DeleteCityAsynTask(cityDao).execute(city);
    }

    public LiveData<List<City>> getAllCities(){
        return allCities;
    }

    private static class InsertCityAsynTask extends AsyncTask<City,Void, Void>{
        private final CityDao cityDao;
        private InsertCityAsynTask(CityDao cityDao){
            this.cityDao = cityDao;
        }
        @Override
        protected Void doInBackground(City... cities) {
            cityDao.insert(cities[0]);
            return null;
        }
    }

    private static class DeleteCityAsynTask extends AsyncTask<City,Void, Void>{

        private final CityDao cityDao;
        private DeleteCityAsynTask(CityDao cityDao){
            this.cityDao = cityDao;
        }
        @Override
        protected Void doInBackground(City... cities) {
            cityDao.delete(cities[0]);
            return null;
        }
    }
}
