package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Activity.MainListActivity;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter.StateType;
import com.marekhakala.mynomadlifeapp.UI.Adapter.CitiesDSRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.Subscriptions;

public class FavouritesListFragment extends AbstractListFragment {

    public static final String FRAGMENT_TAG = "fragment_favourites";
    protected CitiesDSRecyclerViewAdapter mAdapter;
    protected Subscription mSubscriptionApi = Subscriptions.empty();

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        mAdapter = new CitiesDSRecyclerViewAdapter(getContext(), new ArrayList<>());
        mAdapter.setListener(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnActionListener(this);

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

        if(mSubscription != null)
            mSubscription.unsubscribe();

        Realm realm = mRepository.getRealm();

        mSubscription = mRepository.cachedFavouriteCities(realm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    mAdapter.updateData(cities);

                    if (mSelectedPosition != -1)
                        mRecyclerView.scrollToPosition(mSelectedPosition);

                    UtilityHelper.closeDatabase(realm);
                }, throwable -> {
                    mAdapter.setCurrentState(StateType.ERROR_STATE);
                    UtilityHelper.closeDatabase(realm);
                });
    }

    @Override
    protected void loadDataFromAPI() {
        mAdapter.setCurrentState(StateType.LOADING_STATE);

        if(mSubscription != null)
            mSubscription.unsubscribe();

        Realm realm = mRepository.getRealm();
        mSubscription = mRepository.getFavouriteCitiesSlugs(realm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(citiesSlugs -> {
                    if (citiesSlugs.size() > 0) {
                        getDataFromAPI(realm, citiesSlugs);
                    } else {
                        mAdapter.setCurrentState(StateType.EMPTY_STATE);
                        UtilityHelper.closeDatabase(realm);
                    }
                }, throwable -> {
                    mAdapter.setCurrentState(StateType.ERROR_STATE);
                    UtilityHelper.closeDatabase(realm);
                });
    }

    protected void getDataFromAPI(Realm realm, List<String> citiesSlugs) {
        mAdapter.setCurrentState(StateType.LOADING_STATE);

        if(mSubscriptionApi != null)
            mSubscriptionApi.unsubscribe();

        mSubscriptionApi = mRepository.favouriteCities(realm, citiesSlugs)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    mAdapter.updateData(cities);

                    UtilityHelper.closeDatabase(realm);
                }, throwable -> {
                    mAdapter.setCurrentState(StateType.ERROR_STATE);
                    UtilityHelper.closeDatabase(realm);
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
}
