package com.marekhakala.mynomadlifeapp.UI.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;

public class CitiesLoader extends AsyncTaskLoader<CitiesResultEntity> {
    protected IMyNomadLifeRepository mRepository;

    public CitiesLoader(Context context, IMyNomadLifeRepository repository) {
        super(context);
        mRepository = repository;
    }

    @Override
    public CitiesResultEntity loadInBackground() {
        return mRepository.cachedCities();
    }
}
