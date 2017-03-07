package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Activity.MainListActivity;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Adapter.OfflineCitiesDSRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.Subscriptions;

public class OfflineModeListFragment extends AbstractListFragment {

    public static final String FRAGMENT_TAG = "fragment_offline_list";
    protected OfflineCitiesDSRecyclerViewAdapter mAdapter = null;
    protected Subscription mSubscriptionApi = Subscriptions.empty();

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        mAdapter = new OfflineCitiesDSRecyclerViewAdapter(getContext(), new ArrayList<>());
        mAdapter.setListener(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnActionListener(this);
        mRecyclerView.setRefreshEnabled(false);

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
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);

        if(mSubscription != null)
            mSubscription.unsubscribe();

        Realm realm = mRepository.getRealm();

        mSubscription = mRepository.offlineCities(realm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    mAdapter.updateData(cities);

                    if (mSelectedPosition != -1)
                        mRecyclerView.scrollToPosition(mSelectedPosition);

                    UtilityHelper.closeDatabase(realm);
                }, throwable -> {
                    mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.ERROR_STATE);
                    UtilityHelper.closeDatabase(realm);
                });
    }

    @Override
    protected void loadDataFromAPI() {
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);

        if(mSubscription != null)
            mSubscription.unsubscribe();

        Realm realm = mRepository.getRealm();
        mSubscription = mRepository.getOfflineCitiesSlugs(realm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(citiesSlugs -> {
                    if (citiesSlugs.size() > 0) {
                        getDataFromAPI(realm, citiesSlugs);
                    } else {
                        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.EMPTY_STATE);
                        UtilityHelper.closeDatabase(realm);
                    }
                }, throwable -> {
                    mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.ERROR_STATE);
                    UtilityHelper.closeDatabase(realm);
                });
    }

    protected void getDataFromAPI(Realm realm, List<String> citiesSlugs) {
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);

        if(mSubscriptionApi != null)
            mSubscriptionApi.unsubscribe();

        mSubscriptionApi = mRepository.offlineCitiesFromApi(realm, citiesSlugs, true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    reloadUiFromDB();
                    UtilityHelper.closeDatabase(realm);
                }, throwable -> {
                    mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.ERROR_STATE);
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
            mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);

            if(mSubscription != null)
                mSubscription.unsubscribe();

            Realm realm = mRepository.getRealm();
            mSubscription = mRepository.getOfflineCitiesSlugs(realm)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(citiesSlugs -> {
                        if (citiesSlugs.size() > 0) {
                            getPlacesToWorkFromAPI(citiesSlugs);
                        } else {
                            mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.EMPTY_STATE);
                            UtilityHelper.closeDatabase(realm);
                        }
                    }, throwable -> {
                        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.ERROR_STATE);
                        UtilityHelper.closeDatabase(realm);
                    });
        }
    }

    protected void getPlacesToWorkFromAPI(List<String> citySlugs) {
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);

        if(mSubscriptionApi != null)
            mSubscriptionApi.unsubscribe();

        mSubscriptionApi = mRepository.updateAllOfflineCitiesPlacesToWork(citySlugs)
                .subscribe(results -> {
                    reloadUiFromDB();
                }, throwable -> {
                    mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.ERROR_STATE);
                });
    }

    private void reloadUiFromDB() {
        getActivity().runOnUiThread(this::loadDataFromDB);
    }

    protected void reloadImages() {
        if(!mAdapter.isWaitingForData()) {
            mRepository.removeAllOfflineCitiesImages();

            mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);

            if(mSubscription != null)
                mSubscription.unsubscribe();

            Realm realm = mRepository.getRealm();
            mSubscription = mRepository.getOfflineCitiesSlugs(realm)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(citiesSlugs -> {
                        if (citiesSlugs.size() > 0) {
                            getImagesFromAPI(citiesSlugs);
                        } else {
                            mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.EMPTY_STATE);
                            UtilityHelper.closeDatabase(realm);
                        }
                    }, throwable -> {
                        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.ERROR_STATE);
                        UtilityHelper.closeDatabase(realm);
                    });
        }
    }

    protected void getImagesFromAPI(List<String> citySlugs) {
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);

        if(mSubscriptionApi != null)
            mSubscriptionApi.unsubscribe();

        mSubscriptionApi = mRepository.updateAllOfflineCitiesImages(citySlugs)
                .subscribe(results -> {
                    reloadUiFromDB();
                }, throwable -> {
                    mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.ERROR_STATE);
                });
    }

    @Override
    public void onLoadMore() {}
}

