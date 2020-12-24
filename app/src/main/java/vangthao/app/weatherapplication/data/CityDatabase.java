package vangthao.app.weatherapplication.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import vangthao.app.weatherapplication.R;
import vangthao.app.weatherapplication.model.places.City;
import vangthao.app.weatherapplication.remote.CityDao;

@Database(entities = {City.class}, version = 1,exportSchema = false)
public abstract class CityDatabase extends RoomDatabase {
    private static CityDatabase instance;
    private static Context activity;

    public abstract CityDao cityDao();

    public static synchronized CityDatabase getInstance(Context context) {
        activity = context.getApplicationContext();
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CityDatabase.class, "city_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsynTask(instance).execute();
        }
    };

    public static class PopulateDatabaseAsynTask extends AsyncTask<Void, Void, Void> {

        public PopulateDatabaseAsynTask(CityDatabase cityDatabase) {
            CityDao cityDao = cityDatabase.cityDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fillWithStartingData(activity);
            return null;
        }
    }

    private static void fillWithStartingData(Context context) {
        CityDao cityDao = getInstance(context).cityDao();
        JSONArray cities = loadJSONArray(context);

        try {
            for (int i = 0; i < Objects.requireNonNull(cities).length(); i++) {
                JSONObject city = cities.getJSONObject(i);
                int id = city.getInt("id");
                String lat = city.getString("lat");
                String lon = city.getString("lng");
                String cityName = city.getString("city");
                cityDao.insert(new City(id, lat, lon, cityName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static JSONArray loadJSONArray(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = context.getResources().openRawResource(R.raw.city_list);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            return jsonObject.getJSONArray("cities");
        } catch (IOException | JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return null;
    }
}
