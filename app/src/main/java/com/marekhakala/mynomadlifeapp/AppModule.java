package com.marekhakala.mynomadlifeapp;

import android.app.Application;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.analytics.Tracker;
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
    public Tracker providesTracker(Application applicationInstance) {
        if(applicationInstance instanceof MyNomadLifeApplication) {
            MyNomadLifeApplication myNomadLifeApplication = (MyNomadLifeApplication) applicationInstance;
            return myNomadLifeApplication.getDefaultTracker();
        } else {
            return null;
        }
    }

    @Provides
    public AdRequest providesAdRequest() {
        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return this.application;
    }
}
