package vangthao.app.weatherapplication.remote;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vangthao.app.weatherapplication.model.places.City;

@Dao
public interface CityDao {
    @Insert
    void insert(City city);

    @Delete
    void delete(City city);

    @Query("SELECT * FROM city_table ORDER BY name ASC")
    LiveData<List<City>> getAllCity();
}
