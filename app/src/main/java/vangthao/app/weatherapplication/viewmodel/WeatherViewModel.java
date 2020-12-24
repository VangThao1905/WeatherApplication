package vangthao.app.weatherapplication.viewmodel;

import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import vangthao.app.weatherapplication.model.weatherdata.WeatherResponse;
import vangthao.app.weatherapplication.repository.WeatherRepository;

public class WeatherViewModel extends ViewModel {
    private final WeatherRepository weatherRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<WeatherResponse> weatherResponseMutableLiveData = new MutableLiveData<>();

    @Inject
    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public MutableLiveData<WeatherResponse> getWeatherResponseMutableLiveData(String lat, String lon, String units, String appId) {
        loadDataWeather(lat, lon, units, appId);
        return weatherResponseMutableLiveData;
    }

    private void loadDataWeather(String lat, String lon, String units, String appId) {
        disposable.add(weatherRepository.modelSingle(lat, lon, units, appId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherResponse>() {
                    @Override
                    public void onSuccess(WeatherResponse value) {
                        weatherResponseMutableLiveData.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TEST", "name:" + e.getMessage());
                    }
                }));
    }

    public void setWeatherTypeIcon(ImageView imageView, String icon) {
        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
        Picasso.get().load(iconUrl).into(imageView);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
