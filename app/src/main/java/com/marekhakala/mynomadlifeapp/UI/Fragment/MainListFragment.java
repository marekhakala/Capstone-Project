package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.UI.Activity.FilterActivity;
import com.marekhakala.mynomadlifeapp.UI.Activity.MainListActivity;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter.StateType;
import com.marekhakala.mynomadlifeapp.UI.Adapter.CitiesDSRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerView;
import com.marekhakala.mynomadlifeapp.UI.Loader.CitiesLoader;

import java.util.ArrayList;
import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class MainListFragment extends AbstractListFragment implements DataSourceRecyclerView.OnActionListener,
        LoaderManager.LoaderCallbacks<CitiesResultEntity> {

    public static final String FRAGMENT_TAG = "fragment_main_list";
    public static final String EXTRA_STATE_CURRENT_PAGE = "current_page";
    public static final String EXTRA_STATE_TOTAL_NUMBER_OF_PAGES = "total_number_of_pages";
    public static final int MAIN_LIST_ID = 1;

    protected CitiesDSRecyclerViewAdapter mAdapter;
    protected Integer mCurrentPage = 1;
    protected Integer mTotalNumberOfPages = 1;

    @Inject
    CitiesLoader citiesLoader;

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        mAdapter = new CitiesDSRecyclerViewAdapter(getActivity(), new ArrayList<>());
        mAdapter.setListener(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnActionListener(this);

        if(bundle != null) {
            mCurrentPage = bundle.getInt(EXTRA_STATE_CURRENT_PAGE, 1);
            mTotalNumberOfPages = bundle.getInt(EXTRA_STATE_TOTAL_NUMBER_OF_PAGES, 1);
        }

        if (mSelectedPosition != -1)
            mRecyclerView.scrollToPosition(mSelectedPosition);

        getLoaderManager().initLoader(MAIN_LIST_ID, null, this);
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
        getLoaderManager().getLoader(MAIN_LIST_ID).forceLoad();
    }

    @Override
    protected void loadDataFromAPI() {
        loadDataFromAPI(mCurrentPage, false);
    }

    protected void loadDataFromAPI(Integer page, boolean refreshing) {
        if(refreshing)
            mAdapter.setCurrentState(StateType.REFRESHING_STATE);
        else
            mAdapter.setCurrentState(StateType.LOADING_STATE);

        if(mSubscription != null)
            mSubscription.unsubscribe();

        mSubscription = mRepository.cities(true, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    mTotalNumberOfPages = result.getTotalPages();
                    getLoaderManager().getLoader(MAIN_LIST_ID).forceLoad();
                }, throwable -> {
                    mAdapter.setCurrentState(StateType.ERROR_STATE);
                });
    }

    protected void loadMoreDataFromAPI(Integer page) {
        mAdapter.setCurrentState(StateType.LOAD_MORE_STATE);

        if(mSubscription != null)
            mSubscription.unsubscribe();

        mSubscription = mRepository.cities(false, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    mTotalNumberOfPages = result.getTotalPages();
                    mAdapter.addData(result.getEntries());
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
            loadMoreDataFromAPI(mCurrentPage);
        }
    }

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
        mTotalNumberOfPages = 1;
        mCurrentPage = 1;
        mSelectedPosition = -1;
        mAdapter.setCurrentState(StateType.EMPTY_STATE);
    }
}
