package vangthao.app.weatherapplication.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vangthao.app.weatherapplication.remote.WeatherService;

@Module(includes = ViewModelModule.class)
public class NetworkModule {
    private static final String BASE_URL = "http://api.openweathermap.org/";

    @Provides
    @Singleton
    public static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public static WeatherService provideUserService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }
}
