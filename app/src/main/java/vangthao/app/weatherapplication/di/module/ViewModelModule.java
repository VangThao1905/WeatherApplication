package vangthao.app.weatherapplication.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import vangthao.app.weatherapplication.di.ViewModelKey;
import vangthao.app.weatherapplication.viewmodel.ViewModelFactory;
import vangthao.app.weatherapplication.viewmodel.WeatherViewModel;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    abstract ViewModel bindViewModel(WeatherViewModel userViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindFactory(ViewModelFactory factory);
}
