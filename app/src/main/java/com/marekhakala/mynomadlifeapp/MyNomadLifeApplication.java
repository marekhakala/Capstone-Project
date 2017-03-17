package com.marekhakala.mynomadlifeapp;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.marekhakala.mynomadlifeapp.DataSource.DataSourceModule;
import com.marekhakala.mynomadlifeapp.Repository.RepositoryModule;
import com.marekhakala.mynomadlifeapp.UI.UiModule;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.Iconics;

import timber.log.Timber;

public class MyNomadLifeApplication extends Application {
    protected Tracker mTracker;
    protected AppComponent component;

    public static AppComponent appComponent(Context context) {
        return get(context).getComponent();
    }

    public String getPath() {
        return this.getFilesDir().getAbsolutePath();
    }

    public AppComponent getComponent() {
        return component;
    }

    public static MyNomadLifeApplication get(Context context) {
        return (MyNomadLifeApplication) context.getApplicationContext();
    }

    @Override public void onCreate() {
        super.onCreate();

        // Icons
        Iconics.registerFont(new FontAwesome());

        setupComponent();
        Timber.plant(new Timber.DebugTree());
    }

    protected void setupComponent() {
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataSourceModule(new DataSourceModule())
                .repositoryModule(new RepositoryModule())
                .uiModule(new UiModule())
                .build();
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }

        return mTracker;
    }
}
