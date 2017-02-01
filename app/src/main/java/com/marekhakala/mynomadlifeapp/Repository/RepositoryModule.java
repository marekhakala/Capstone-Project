package com.marekhakala.mynomadlifeapp.Repository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    public IMyNomadLifeRepository providesRepository() {
        return new MyNomadLifeRepository();
    }
}
