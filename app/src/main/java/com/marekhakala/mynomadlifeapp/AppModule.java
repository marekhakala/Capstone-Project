package com.marekhakala.mynomadlifeapp;

import android.app.Application;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    protected MyNomadLifeApplication application;

    public AppModule(MyNomadLifeApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return this.application;
    }
}
