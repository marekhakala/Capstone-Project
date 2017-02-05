package com.marekhakala.mynomadlifeapp.Repository;

import android.app.Application;

import com.marekhakala.mynomadlifeapp.DataSource.MyNomadLifeAPI;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    public IMyNomadLifeRepository providesMoviesRepository(MyNomadLifeAPI api, Application application) {
        return new MyNomadLifeRepository(api, application);
    }
}
