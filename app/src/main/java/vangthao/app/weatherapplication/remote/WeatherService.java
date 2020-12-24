package vangthao.app.weatherapplication.remote;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vangthao.app.weatherapplication.model.weatherdata.WeatherResponse;

public interface WeatherService {
    @GET("data/2.5/weather?")
    Single<WeatherResponse> getCurrentWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("units") String units, @Query("APPID") String app_id);
}
