package vangthao.app.weatherapplication.view;

import android.app.Application;

import vangthao.app.weatherapplication.di.components.AppComponent;
import vangthao.app.weatherapplication.di.components.DaggerAppComponent;

public class BaseApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public BaseApplication() {}
    public BaseApplication(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
