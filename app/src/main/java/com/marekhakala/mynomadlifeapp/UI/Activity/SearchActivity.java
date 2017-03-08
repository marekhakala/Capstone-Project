package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter.StateType;
import com.marekhakala.mynomadlifeapp.UI.Adapter.CitiesDSRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerView;
import com.marekhakala.mynomadlifeapp.UI.Component.OnCityItemClickListener;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import io.realm.Realm;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.Subscriptions;

public class SearchActivity extends AbstractBaseActivity implements
        DataSourceRecyclerView.OnActionListener, OnCityItemClickListener {

    @Bind(R.id.search_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.search_list_recycler_view)
    DataSourceRecyclerView mRecyclerView;

    @Bind(R.id.search_view)
    MaterialSearchView mSearchView;

    @Inject
    IMyNomadLifeRepository mRepository;

    public static final String ACTIVITY_TAG = "activity_search";

    protected Boolean mSearchData = false;
    protected String mCurrentSection = ConstantValues.SEARCH_SECTION_CODE;
    protected CitiesDSRecyclerViewAdapter mAdapter;

    protected Subscription mSubscription = Subscriptions.empty();

    protected static final String EXTRA_STATE_SELECTED_POSITION = "selected_position";
    protected static final String EXTRA_SEARCH_RESULTS = "search_results";
    protected static final String EXTRA_RESTORE_SEARCH_DATA = "restore_search_data";
    protected static final String EXTRA_STATE_SEARCH_QUERY = "search_query";
    protected static final String EXTRA_STATE_CURRENT_PAGE = "current_page";
    protected static final String EXTRA_STATE_TOTAL_NUMBER_OF_PAGES = "total_number_of_pages";

    protected int mCurrentPage = 1;
    protected int mTotalNumberOfPages = 1;
    protected int mSelectedPosition = -1;
    protected String mSearchQuery = "";
    protected boolean mRestoreSearchData = false;
    protected List<CityEntity> mSearchResults = new ArrayList<>();
    protected String mReturnSection = ConstantValues.MAIN_SECTION_CODE;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search);
        initSearchView();

        mAdapter = new CitiesDSRecyclerViewAdapter(this, new ArrayList<>());
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);

        if (bundle != null) {
            mRestoreSearchData = bundle.getBoolean(EXTRA_RESTORE_SEARCH_DATA, false);

            mCurrentPage = bundle.getInt(EXTRA_STATE_CURRENT_PAGE, 1);
            mTotalNumberOfPages = bundle.getInt(EXTRA_STATE_TOTAL_NUMBER_OF_PAGES, 1);

            mSelectedPosition = bundle.getInt(EXTRA_STATE_SELECTED_POSITION, -1);
            mSearchQuery = bundle.getString(EXTRA_STATE_SEARCH_QUERY, "");

            CitiesResultEntity citiesResultEntity = bundle.getParcelable(EXTRA_SEARCH_RESULTS);

            if (citiesResultEntity != null)
                mSearchResults = citiesResultEntity.getEntries();
            else
                mSearchResults = new ArrayList<>();

            if(bundle != null)
                mCurrentPage = bundle.getInt(EXTRA_STATE_CURRENT_PAGE, 1);

            mReturnSection = bundle.getString(MainListActivity.EXTRA_SECTION, ConstantValues.MAIN_SECTION_CODE);
            restoreUi();
        }
    }

    protected void restoreUi() {
        if (mRestoreSearchData)
            mSearchView.setQuery(mSearchQuery, false);

        mAdapter.updateData(mSearchResults);

        if (mSelectedPosition != -1)
            mRecyclerView.scrollToPosition(mSelectedPosition);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == MainListActivity.DETAIL_VIEW_RESULT_CODE)
            loadDataFromCache();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(MainListActivity.SEARCH_RESULT_CODE, intent);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Intent returnIntent = new Intent();
        setResult(MainListActivity.SEARCH_RESULT_CODE,returnIntent);
        super.onDestroy();
    }

    protected void initSearchView() {
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearchSubmit(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                onBackPressed();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putBoolean(EXTRA_RESTORE_SEARCH_DATA, true);

        bundle.putInt(EXTRA_STATE_CURRENT_PAGE, mCurrentPage);
        bundle.putInt(EXTRA_STATE_SELECTED_POSITION, mSelectedPosition);
        bundle.putString(EXTRA_STATE_SEARCH_QUERY, mSearchQuery);

        CitiesResultEntity citiesResultEntity = new CitiesResultEntity();
        citiesResultEntity.setEntries(mSearchResults);
        bundle.putParcelable(EXTRA_SEARCH_RESULTS, citiesResultEntity);
    }

    protected void onSearchSubmit(String query) {
        mSearchQuery = query.trim();

        if (mSearchQuery.isEmpty())
            return;

        loadDataFromAPI(false);
    }

    protected void loadDataFromAPI(boolean loadMore) {
        loadDataFromAPI(mCurrentPage, false);
    }

    protected void loadDataFromAPI(Integer page, boolean loadMore) {
        if (!mSearchData) {
            mSearchData = true;
            mAdapter.setCurrentState(StateType.LOADING_STATE);

            if (mSubscription != null)
                mSubscription.unsubscribe();

            Realm realm = mRepository.getRealm();

            mSubscription = mRepository.searchCities(realm, mCurrentPage, mSearchQuery)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cities -> {
                        mSearchResults = cities;
                        mAdapter.updateData(cities);
                        mSearchData = false;
                        UtilityHelper.closeDatabase(realm);
                    }, throwable -> {
                        mSearchResults = new ArrayList<>();
                        mAdapter.setCurrentState(StateType.ERROR_STATE);
                        mSearchData = false;
                        UtilityHelper.closeDatabase(realm);
                    });
        }
    }

    private void loadDataFromCache() {
        mSearchData = true;
        mAdapter.setCurrentState(StateType.LOADING_STATE);

        Realm realm = mRepository.getRealm();

        Observable.zip(mRepository.favouriteCitiesSlugs(realm),
                mRepository.offlineCitiesSlugs(realm), (favouriteCitiesSlugs, offlineCitiesSlugs) -> {
                    for(CityEntity cityEntity : mSearchResults) {
                        cityEntity.setFavourite(favouriteCitiesSlugs.contains(cityEntity.getSlug()));
                        cityEntity.setOffline(offlineCitiesSlugs.contains(cityEntity.getSlug()));
                    }

                    return mSearchResults;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    mSearchResults = cities;
                    mAdapter.updateData(cities);

                    if (mSelectedPosition != -1)
                        mRecyclerView.scrollToPosition(mSelectedPosition);

                    mSearchData = false;
                    UtilityHelper.closeDatabase(realm);
                }, throwable -> {
                    mSearchResults = new ArrayList<>();
                    mAdapter.setCurrentState(StateType.ERROR_STATE);
                    mSearchData = false;
                    UtilityHelper.closeDatabase(realm);
                });
    }

    @Override
    protected void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
    }

    public void refreshData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_search_list, menu);
        MenuItem item = menu.findItem(R.id.menu_main_list_search);
        mSearchView.setMenuItem(item);
        mSearchView.showSearch();
        return true;
    }

    @Override
    public void setContentView(int id) {
        super.setContentView(id);
        setupToolbar();
        setTitle("");
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getCurrentActivity() {
        return ACTIVITY_TAG;
    }

    @Override
    protected String getCurrentFragment() {
        return "";
    }

    @Override
    public String getCurrentSection() {
        return this.mCurrentSection;
    }

    @Override
    protected void setupSectionFragment(String section) {
    }

    protected void setupToolbar() {
        if (mToolbar == null)
            return;

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void setupComponent(AppComponent component) {
        component.inject(this);
    }

    @Override
    public void onClicked(CityEntity entity, View view, int index) {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra(ConstantValues.EXTRA_ITEM_CITY_TYPE, ConstantValues.CITY_ENTITY);
        intent.putExtra(ConstantValues.EXTRA_ITEM_CITY, entity);
        startActivityForResult(intent, MainListActivity.DETAIL_VIEW_REQUEST_CODE);
    }

    @Override
    public void onClicked(CityOfflineEntity entity, View view, int index) {}

    @Override
    public void onFavouriteClicked(String slug, boolean state) {
        if (state)
            mRepository.addFavouriteCitySlugs(slug);
        else
            mRepository.removeFavouriteCitySlugs(slug);
    }

    @Override
    public void onLoadMore() {
        if(!mAdapter.isWaitingForData() && mCurrentPage < mTotalNumberOfPages) {
            mCurrentPage += 1;
            loadDataFromAPI(true);
        }
    }
}
