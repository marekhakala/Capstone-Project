package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.UI.Activity.MainListActivity;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter.StateType;
import com.marekhakala.mynomadlifeapp.UI.Adapter.OfflineCitiesDSRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Loader.OfflineModeCitiesLoader;

import rx.Subscription;
import rx.subscriptions.Subscriptions;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;

public class OfflineModeListFragment extends AbstractListFragment implements LoaderManager.LoaderCallbacks<List<CityOfflineEntity>> {

    public static final String FRAGMENT_TAG = "fragment_offline_list";
    public static final int OFFLINE_MODE_LIST_ID = 1;

    protected OfflineCitiesDSRecyclerViewAdapter mAdapter = null;
    protected Subscription mSubscriptionApi = Subscriptions.empty();

    @Inject
    OfflineModeCitiesLoader offlineModeLoader;

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        mAdapter = new OfflineCitiesDSRecyclerViewAdapter(getActivity(), new ArrayList<>());
        mAdapter.setListener(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnActionListener(this);
        mRecyclerView.setRefreshEnabled(false);

        getLoaderManager().initLoader(OFFLINE_MODE_LIST_ID, null, this);
        refreshData();
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
                refreshData();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_offline_mode, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.menu_offline_mode_update_data) {
            reloadDataFromApi();
            return true;
        } else if(item.getItemId() == R.id.menu_offline_mode_update_images) {
            reloadImages();
            return true;
        } else if(item.getItemId() == R.id.menu_offline_mode_places_to_work) {
            reloadPlacesToWork();
            return true;
        } else if(item.getItemId() == R.id.menu_offline_delete_all_data) {
            deleteAllData();
            return true;
        } else if(item.getItemId() == R.id.menu_offline_delete_images) {
            deleteAllImages();
            return true;
        }

        return false;
    }

    @Override
    protected void loadDataFromDB() {
        mAdapter.setCurrentState(StateType.LOADING_STATE);
        getLoaderManager().getLoader(OFFLINE_MODE_LIST_ID).forceLoad();
    }

    @Override
    protected void loadDataFromAPI() {
        mAdapter.setCurrentState(StateType.LOADING_STATE);

        if(mSubscription != null)
            mSubscription.unsubscribe();

        mSubscription = mRepository.getOfflineCitiesSlugs()
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

        mSubscriptionApi = mRepository.offlineCitiesFromApi(citiesSlugs, true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    reloadUiFromDB();
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
        if(!mAdapter.isWaitingForData()) {
            mSelectedPosition = mRecyclerView.getCurrentPosition();
            loadDataFromDB();
        }
    }

    public void reloadDataFromApi() {
        if (!mAdapter.isWaitingForData()) {
            mSelectedPosition = 0;
            loadDataFromAPI();
        }
    }

    protected void deleteAllData() {
        if(!mAdapter.isWaitingForData()) {
            mRepository.removeAllOfflineCitiesData();
            mSelectedPosition = 0;
            reloadUiFromDB();
        }
    }

    protected void deleteAllImages() {
        if(!mAdapter.isWaitingForData()) {
            mRepository.removeAllOfflineCitiesImages();
            mSelectedPosition = mRecyclerView.getCurrentPosition();
            reloadUiFromDB();
        }
    }

    protected void reloadPlacesToWork() {
        if(!mAdapter.isWaitingForData()) {
            mSelectedPosition = mRecyclerView.getCurrentPosition();
            mAdapter.setCurrentState(StateType.LOADING_STATE);

            if(mSubscription != null)
                mSubscription.unsubscribe();

            mSubscription = mRepository.getOfflineCitiesSlugs()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(citiesSlugs -> {
                        if (citiesSlugs.size() > 0) {
                            getPlacesToWorkFromAPI(citiesSlugs);
                        } else {
                            mAdapter.setCurrentState(StateType.EMPTY_STATE);
                        }
                    }, throwable -> {
                        mAdapter.setCurrentState(StateType.ERROR_STATE);
                    });
        }
    }

    protected void getPlacesToWorkFromAPI(List<String> citySlugs) {
        mAdapter.setCurrentState(StateType.LOADING_STATE);

        if(mSubscriptionApi != null)
            mSubscriptionApi.unsubscribe();

        mSubscriptionApi = mRepository.updateAllOfflineCitiesPlacesToWork(citySlugs)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    reloadUiFromDB();
                }, throwable -> {
                    mAdapter.setCurrentState(StateType.ERROR_STATE);
                });
    }

    private void reloadUiFromDB() {
        getActivity().runOnUiThread(this::loadDataFromDB);
    }

    protected void reloadImages() {
        if(!mAdapter.isWaitingForData()) {
            mRepository.removeAllOfflineCitiesImages();

            mAdapter.setCurrentState(StateType.LOADING_STATE);

            if(mSubscription != null)
                mSubscription.unsubscribe();

            mSubscription = mRepository.getOfflineCitiesSlugs()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(citiesSlugs -> {
                        if (citiesSlugs.size() > 0) {
                            getImagesFromAPI(citiesSlugs);
                        } else {
                            mAdapter.setCurrentState(StateType.EMPTY_STATE);
                        }
                    }, throwable -> {
                        mAdapter.setCurrentState(StateType.ERROR_STATE);
                    });
        }
    }

    protected void getImagesFromAPI(List<String> citySlugs) {
        mAdapter.setCurrentState(StateType.LOADING_STATE);

        if(mSubscriptionApi != null)
            mSubscriptionApi.unsubscribe();

        mSubscriptionApi = mRepository.updateAllOfflineCitiesImages(citySlugs)
                .subscribe(results -> {
                    reloadUiFromDB();
                }, throwable -> {
                    mAdapter.setCurrentState(StateType.ERROR_STATE);
                });
    }

    @Override
    public void onLoadMore() {}

    @Override
    public Loader<List<CityOfflineEntity>> onCreateLoader(int id, Bundle args) {
        return offlineModeLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<CityOfflineEntity>> loader, List<CityOfflineEntity> data) {
        mAdapter.updateData(data);

        if (mSelectedPosition != -1)
            mRecyclerView.scrollToPosition(mSelectedPosition);
    }

    @Override
    public void onLoaderReset(Loader<List<CityOfflineEntity>> loader) {
        mSelectedPosition = -1;
        mAdapter.setCurrentState(StateType.EMPTY_STATE);
    }
}

