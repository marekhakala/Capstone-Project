package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.UI.Activity.FilterActivity;
import com.marekhakala.mynomadlifeapp.UI.Activity.MainListActivity;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter.StateType;
import com.marekhakala.mynomadlifeapp.UI.Adapter.CitiesDSRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerView;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import io.realm.Realm;
import java.util.ArrayList;
import rx.android.schedulers.AndroidSchedulers;

public class MainListFragment extends AbstractListFragment implements DataSourceRecyclerView.OnActionListener {

    public static final String FRAGMENT_TAG = "fragment_main_list";
    public static final String EXTRA_STATE_CURRENT_PAGE = "current_page";
    public static final String EXTRA_STATE_TOTAL_NUMBER_OF_PAGES = "total_number_of_pages";

    protected CitiesDSRecyclerViewAdapter mAdapter;
    protected Integer mCurrentPage = 1;
    protected Integer mTotalNumberOfPages = 1;

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        mAdapter = new CitiesDSRecyclerViewAdapter(getContext(), new ArrayList<>());
        mAdapter.setListener(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnActionListener(this);

        if(bundle != null) {
            mCurrentPage = bundle.getInt(EXTRA_STATE_CURRENT_PAGE, 1);
            mTotalNumberOfPages = bundle.getInt(EXTRA_STATE_TOTAL_NUMBER_OF_PAGES, 1);
        }

        if (mSelectedPosition != -1)
            mRecyclerView.scrollToPosition(mSelectedPosition);

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
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_main_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.menu_main_list_filter) {
            Intent intent = new Intent(getActivity(), FilterActivity.class);

            MainListActivity activity = (MainListActivity) getActivity();
            intent.putExtra(MainListActivity.EXTRA_SECTION, activity.getCurrentSection());
            activity.startActivityForResult(intent, MainListActivity.FILTER_REQUEST_CODE);
            return true;
        }

        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(EXTRA_STATE_CURRENT_PAGE, mCurrentPage);
        bundle.getInt(EXTRA_STATE_TOTAL_NUMBER_OF_PAGES, mTotalNumberOfPages);
    }

    @Override
    public void onDestroyView() {
        if(mSubscription != null)
            mSubscription.unsubscribe();
        super.onDestroyView();
    }

    @Override
    protected void loadDataFromDB() {
        mAdapter.setCurrentState(StateType.LOADING_STATE);

        if(mSubscription != null)
            mSubscription.unsubscribe();

        Realm realm = mRepository.getRealm();

        mSubscription = mRepository.cachedCities(realm)
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
        loadDataFromAPI(false, false);
    }

    protected void loadDataFromAPI(boolean refreshing, boolean loadMore) {
        loadDataFromAPI(mCurrentPage, refreshing, loadMore);
    }

    protected void loadDataFromAPI(Integer page, boolean refreshing, boolean loadMore) {
        if (loadMore) {
            mAdapter.setCurrentState(StateType.LOAD_MORE_STATE);
        } else {
            if(refreshing) {
                mAdapter.setCurrentState(StateType.REFRESHING_STATE);
            } else {
                mAdapter.setCurrentState(StateType.LOADING_STATE);
            }
        }

        if(mSubscription != null)
            mSubscription.unsubscribe();

        boolean cleanAdd = !loadMore;
        Realm realm = mRepository.getRealm();

        mSubscription = mRepository.cities(realm, cleanAdd, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    mTotalNumberOfPages = result.getTotalPages();

                    if(loadMore)
                        mAdapter.addData(result.getEntries());
                    else
                        mAdapter.updateData(result.getEntries());

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
        if(!mAdapter.isWaitingForData()) {
            mCurrentPage = 1;
            loadDataFromAPI();
        }
    }

    public void reloadDataFromCache() {
        if(!mAdapter.isWaitingForData())
            loadDataFromDB();
    }

    @Override
    public void onLoadMore() {
        if(!mAdapter.isWaitingForData() && mCurrentPage < mTotalNumberOfPages) {
            mCurrentPage += 1;
            loadDataFromAPI(false, true);
        }
    }
}
