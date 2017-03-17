package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.ExchangeRateEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ExchangeRatesResultEntity;
import com.marekhakala.mynomadlifeapp.DataSource.DataUpdatedReceiver;
import com.marekhakala.mynomadlifeapp.DataSource.ExchangeRatesFetchService;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Adapter.ExchangeRatesRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerView;
import com.marekhakala.mynomadlifeapp.UI.Loader.ExchangeRatesLoader;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import butterknife.Bind;

import static com.marekhakala.mynomadlifeapp.DataSource.ExchangeRatesFetchService.EXTRA_EXCHANGE_RATES_RECEIVER;

public class ExchangeRatesActivity extends AbstractBaseActivity
        implements LoaderManager.LoaderCallbacks<ExchangeRatesResultEntity>, DataSourceRecyclerView.OnActionListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.exchange_rates_list_recycler_view)
    DataSourceRecyclerView mRecyclerView;

    @Inject
    Tracker mTracker;

    @Inject
    ExchangeRatesLoader exchangeRatesLoader;

    public static final String ACTIVITY_TAG = "activity_exchange_rates";
    public static final int EXCHANGE_RATES_ID = 1;

    public static final int RESULT_OK = 100;
    public DataUpdatedReceiver mDataUpdatedReceiver;
    protected ExchangeRatesRecyclerViewAdapter mAdapter;

    protected String mCurrentSection = ConstantValues.EXCHANGE_RATES_SECTION_CODE;
    protected int mRequestCode = MainListActivity.EXCHANGE_RATES_REQUEST_CODE;
    protected String mReturnSection = ConstantValues.MAIN_SECTION_CODE;

    protected int mSelectedPosition = -1;
    protected boolean mUseCacheData = true;
    protected static final String EXTRA_STATE_USE_CACHE_DATA = "use_cache_data";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_exchange_rates);

        if(getIntent().hasExtra(ACTIVITY_REQUEST_CODE))
            mRequestCode = getIntent().getIntExtra(ACTIVITY_REQUEST_CODE, MainListActivity.EXCHANGE_RATES_REQUEST_CODE);

        if(getIntent().hasExtra(MainListActivity.EXTRA_SECTION))
            mReturnSection = getIntent().getStringExtra(MainListActivity.EXTRA_SECTION);

        if(bundle != null)
            mUseCacheData = bundle.getBoolean(EXTRA_STATE_USE_CACHE_DATA, true);

        mAdapter = new ExchangeRatesRecyclerViewAdapter(this, new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnActionListener(this);

        // Analytics
        mTracker.setScreenName(UtilityHelper.getScreenNameForAnalytics(ConstantValues.EXCHANGE_RATES_SECTION_CODE));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        setupServiceReceiver();
        getLoaderManager().initLoader(EXCHANGE_RATES_ID, null, this);
        loadData();
    }

    private void loadData() {
        if(mUseCacheData)
            loadDataFromDB();
        else
            loadDataFromApi();
    }

    protected void loadDataFromDB() {
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);
        getLoaderManager().getLoader(EXCHANGE_RATES_ID).forceLoad();
    }

    private void loadDataFromApi() {
        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_exchange_rates, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_exchange_rates_refresh) {
            refreshData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void updateExchangeRates() {
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);
        Intent intent = new Intent(this, ExchangeRatesFetchService.class);
        intent.putExtra(EXTRA_EXCHANGE_RATES_RECEIVER, mDataUpdatedReceiver);
        startService(intent);
    }

    public void setupServiceReceiver() {
        mDataUpdatedReceiver = new DataUpdatedReceiver(new Handler());
        mDataUpdatedReceiver.setReceiver((resultCode, resultData) -> {
            if (resultCode == RESULT_OK) {
                Parcelable[] parcelableArray = resultData.getParcelableArray(ExchangeRatesFetchService.EXTRA_EXCHANGE_RATES_DATA);

                if (parcelableArray != null) {
                    ExchangeRateEntity[] resultArray = Arrays.copyOf(parcelableArray, parcelableArray.length, ExchangeRateEntity[].class);
                    mAdapter.updateData(Arrays.asList(resultArray));
                }
            }
        });
    }

    protected void setupResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainListActivity.EXTRA_SECTION, mReturnSection);
        setResult(MainListActivity.EXCHANGE_RATES_RESULT_CODE, returnIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean(EXTRA_STATE_USE_CACHE_DATA, true);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void finish() {
        setupResult();
        super.finish();
    }

    @Override
    public void setContentView(int id) {
        super.setContentView(id);
        setupToolbar();
        setTitle(getResources().getString(R.string.section_exchange_rates_title));
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

    @Override
    protected void setupComponent(AppComponent component) {
        component.inject(this);
    }

    protected void setupToolbar() {
        if (mToolbar == null)
            return;

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            mToolbar.setNavigationOnClickListener(arrow -> onBackPressed());
        }
    }

    @Override
    public Loader<ExchangeRatesResultEntity> onCreateLoader(int id, Bundle args) {
        return exchangeRatesLoader;
    }

    @Override
    public void onLoadFinished(Loader<ExchangeRatesResultEntity> loader, ExchangeRatesResultEntity data) {
        mAdapter.updateData(data.getExchangeRates());

        if (mSelectedPosition != -1)
            mRecyclerView.scrollToPosition(mSelectedPosition);
    }

    @Override
    public void onLoaderReset(Loader<ExchangeRatesResultEntity> loader) {
        mSelectedPosition = -1;
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.EMPTY_STATE);
    }

    @Override
    public void refreshData() {
        if(!mAdapter.isWaitingForData())
            updateExchangeRates();
    }

    @Override
    public void onLoadMore() {}
}
