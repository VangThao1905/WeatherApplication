package vangthao.app.weatherapplication.di.components;

import javax.inject.Singleton;

import dagger.Component;
import vangthao.app.weatherapplication.di.module.ContextModule;
import vangthao.app.weatherapplication.di.module.NetworkModule;
import vangthao.app.weatherapplication.di.module.ViewModelModule;
import vangthao.app.weatherapplication.view.HomeActivity;

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class,ViewModelModule.class})
public interface AppComponent {

    void inject(HomeActivity homeActivity);
}
