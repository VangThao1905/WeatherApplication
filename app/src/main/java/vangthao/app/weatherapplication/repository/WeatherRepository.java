package vangthao.app.weatherapplication.repository;

import javax.inject.Inject;

import io.reactivex.Single;
import vangthao.app.weatherapplication.model.weatherdata.WeatherResponse;
import vangthao.app.weatherapplication.remote.WeatherService;

public class WeatherRepository {
    private final WeatherService weatherService;

    @Inject
    public WeatherRepository(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public Single<WeatherResponse> modelSingle(String lat, String lon, String units, String appId) {
        return weatherService.getCurrentWeatherData(lat, lon, units, appId);
    }
}
