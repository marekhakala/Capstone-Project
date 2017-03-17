package com.marekhakala.mynomadlifeapp.Repository;

import android.app.Application;
import android.content.ContentResolver;

import com.marekhakala.mynomadlifeapp.DataSource.MyNomadLifeAPI;
import com.squareup.sqlbrite.BriteContentResolver;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    public IMyNomadLifeRepository providesMyNomadLifeRepository(MyNomadLifeAPI api, ContentResolver contentResolver,
                                                                BriteContentResolver briteContentResolver, Application application) {

        return new MyNomadLifeRepository(api, contentResolver, briteContentResolver, application);
    }
}
