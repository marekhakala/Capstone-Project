package com.marekhakala.mynomadlifeapp.UI.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.marekhakala.mynomadlifeapp.DataModel.CityOfflinePlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;

import java.util.ArrayList;
import java.util.List;

public class OfflinePlacesToWorkLoader extends AsyncTaskLoader<List<CityOfflinePlaceToWorkEntity>> {
    protected String mCitySlug;
    protected String mSearchQuery = "";
    protected IMyNomadLifeRepository mRepository;

    public OfflinePlacesToWorkLoader(Context context, IMyNomadLifeRepository repository) {
        super(context);
        mRepository = repository;
    }

    public String getCitySlug() {
        return mCitySlug;
    }

    public void setCitySlug(String mCitySlug) {
        this.mCitySlug = mCitySlug;
    }

    public String getSearchQuery() {
        return mSearchQuery;
    }

    public void setSearchQuery(String mSearchQuery) {
        this.mSearchQuery = mSearchQuery;
    }

    @Override
    public List<CityOfflinePlaceToWorkEntity> loadInBackground() {
        if(mCitySlug.isEmpty())
            return new ArrayList<>();

        return mRepository.offlineCityPlacesToWork(mCitySlug, mSearchQuery);
    }
}
