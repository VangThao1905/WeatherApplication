package vangthao.app.weatherapplication.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ContextModule {

    public ContextModule(Context context) {
    }

    @Binds
    abstract Context bindContext(Application application);
}
