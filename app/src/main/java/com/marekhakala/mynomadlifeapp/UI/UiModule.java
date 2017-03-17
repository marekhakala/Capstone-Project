package com.marekhakala.mynomadlifeapp.UI;

import android.app.Application;
import android.content.Context;

import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;
import com.marekhakala.mynomadlifeapp.UI.Loader.CitiesLoader;
import com.marekhakala.mynomadlifeapp.UI.Loader.ExchangeRatesLoader;
import com.marekhakala.mynomadlifeapp.UI.Loader.OfflineModeCitiesLoader;
import com.marekhakala.mynomadlifeapp.UI.Loader.OfflinePlacesToWorkLoader;
import com.marekhakala.mynomadlifeapp.UI.Loader.PlacesToWorkLoader;

import dagger.Module;
import dagger.Provides;

@Module
public class UiModule {
    @Provides
    public Context providesApplicationContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    public CitiesLoader providesCitiesLoader(Context context, IMyNomadLifeRepository repository) {
        return new CitiesLoader(context, repository);
    }

    @Provides
    public OfflineModeCitiesLoader providesOfflineModeCitiesLoader(Context context, IMyNomadLifeRepository repository) {
        return new OfflineModeCitiesLoader(context, repository);
    }

    @Provides
    public PlacesToWorkLoader providesPlacesToWorkLoader(Context context, IMyNomadLifeRepository repository) {
        return new PlacesToWorkLoader(context, repository);
    }

    @Provides
    public OfflinePlacesToWorkLoader providesOfflinePlacesToWorkLoader(Context context, IMyNomadLifeRepository repository) {
        return new OfflinePlacesToWorkLoader(context, repository);
    }

    @Provides
    public ExchangeRatesLoader providesExchangeRatesLoader(Context context, IMyNomadLifeRepository repository) {
        return new ExchangeRatesLoader(context, repository);
    }
}
