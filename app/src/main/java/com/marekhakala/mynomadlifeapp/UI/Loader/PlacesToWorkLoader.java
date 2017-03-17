package com.marekhakala.mynomadlifeapp.UI.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.marekhakala.mynomadlifeapp.DataModel.CityPlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;

import java.util.ArrayList;
import java.util.List;

public class PlacesToWorkLoader extends AsyncTaskLoader<List<CityPlaceToWorkEntity>> {
    protected String mCitySlug;
    protected String mSearchQuery = "";
    protected IMyNomadLifeRepository mRepository;

    public PlacesToWorkLoader(Context context, IMyNomadLifeRepository repository) {
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
    public List<CityPlaceToWorkEntity> loadInBackground() {
        if(mCitySlug.isEmpty())
            return new ArrayList<>();

        return mRepository.cachedCityPlacesToWork(mCitySlug, mSearchQuery);
    }
}
