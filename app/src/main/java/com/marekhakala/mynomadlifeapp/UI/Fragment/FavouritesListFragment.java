package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Activity.MainListActivity;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter.StateType;
import com.marekhakala.mynomadlifeapp.UI.Adapter.CitiesDSRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Loader.CitiesLoader;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.Subscriptions;

public class FavouritesListFragment extends AbstractListFragment implements LoaderManager.LoaderCallbacks<CitiesResultEntity> {

    public static final String FRAGMENT_TAG = "fragment_favourites";
    public static final int FAVOURITES_LIST_ID = 1;

    protected CitiesDSRecyclerViewAdapter mAdapter;
    protected Subscription mSubscriptionApi = Subscriptions.empty();

    @Inject
    CitiesLoader citiesLoader;

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        mAdapter = new CitiesDSRecyclerViewAdapter(getActivity(), new ArrayList<>());
        mAdapter.setListener(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnActionListener(this);

        getLoaderManager().initLoader(FAVOURITES_LIST_ID, null, this);
        loadData();
    }

    @Override
    protected void setupActivityListener() {
        MainListActivity activity = (MainListActivity) getActivity();
        mListener = activity;

        activity.setListener(new MainListActivity.OnFragmentActionsListener() {
            @Override
            public void onGoToTop() {
                mRecyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onReloadData() {
                refreshData();
            }

            @Override
            public void onReloadDataFromCache() {
                reloadDataFromCache();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_favourites, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onFavouriteClicked(String slug, boolean state) {
        super.onFavouriteClicked(slug, state);

        CityEntity removeItemCityEntity = null;

        for(int i = 0; i < mAdapter.getItemCount(); i++) {
            CityEntity cityEntity = (CityEntity) mAdapter.getItem(i);
            if(cityEntity.getSlug().equals(slug))
                removeItemCityEntity = cityEntity;
        }

        if(removeItemCityEntity != null)
            mAdapter.removeItem(removeItemCityEntity);
    }

    @Override
    protected void loadDataFromDB() {
        mAdapter.setCurrentState(StateType.LOADING_STATE);
        getLoaderManager().getLoader(FAVOURITES_LIST_ID).forceLoad();
    }

    @Override
    protected void loadDataFromAPI() {
        mAdapter.setCurrentState(StateType.LOADING_STATE);

        if(mSubscription != null)
            mSubscription.unsubscribe();

        mSubscription = mRepository.getFavouriteCitiesSlugs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(citiesSlugs -> {
                    if (citiesSlugs.size() > 0) {
                        getDataFromAPI(citiesSlugs);
                    } else {
                        mAdapter.setCurrentState(StateType.EMPTY_STATE);
                    }
                }, throwable -> {
                    mAdapter.setCurrentState(StateType.ERROR_STATE);
                });
    }

    protected void getDataFromAPI(List<String> citiesSlugs) {
        mAdapter.setCurrentState(StateType.LOADING_STATE);

        if(mSubscriptionApi != null)
            mSubscriptionApi.unsubscribe();

        mSubscriptionApi = mRepository.favouriteCities(citiesSlugs)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    getLoaderManager().getLoader(FAVOURITES_LIST_ID).forceLoad();
                }, throwable -> {
                    mAdapter.setCurrentState(StateType.ERROR_STATE);
                });
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_TAG;
    }

    @Override
    protected void setupComponent(AppComponent component) {
        component.inject(this);
    }

    @Override
    public void refreshData() {
        if(!mAdapter.isWaitingForData())
            loadDataFromAPI();
    }

    public void reloadDataFromCache() {
        if(!mAdapter.isWaitingForData())
            loadDataFromDB();
    }

    @Override
    public void onLoadMore() {}

    @Override
    public Loader<CitiesResultEntity> onCreateLoader(int id, Bundle args) {
        return citiesLoader;
    }

    @Override
    public void onLoadFinished(Loader<CitiesResultEntity> loader, CitiesResultEntity data) {
        mAdapter.updateData(data.getEntries());

        if (mSelectedPosition != -1)
            mRecyclerView.scrollToPosition(mSelectedPosition);
    }

    @Override
    public void onLoaderReset(Loader<CitiesResultEntity> loader) {
        mSelectedPosition = -1;
        mAdapter.setCurrentState(StateType.EMPTY_STATE);
    }
}
