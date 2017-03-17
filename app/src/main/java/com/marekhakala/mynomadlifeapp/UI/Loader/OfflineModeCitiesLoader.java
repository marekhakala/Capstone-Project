package com.marekhakala.mynomadlifeapp.UI.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;

import java.util.List;

public class OfflineModeCitiesLoader extends AsyncTaskLoader<List<CityOfflineEntity>> {
    protected IMyNomadLifeRepository mRepository;

    public OfflineModeCitiesLoader(Context context, IMyNomadLifeRepository repository) {
        super(context);
        mRepository = repository;
    }

    @Override
    public List<CityOfflineEntity> loadInBackground() {
        return mRepository.offlineCities();
    }
}
