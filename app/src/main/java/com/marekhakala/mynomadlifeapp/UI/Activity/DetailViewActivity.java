package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;
import com.marekhakala.mynomadlifeapp.UI.Component.FavouriteActionButton;
import com.marekhakala.mynomadlifeapp.UI.Adapter.DetailViewFragmentPagerAdapter;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import io.realm.Realm;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.Subscriptions;

public class DetailViewActivity extends AbstractBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.city_detail_view_image)
    ImageView backdropImage;

    @Bind(R.id.city_detail_view_country_text)
    TextView countryText;

    @Bind(R.id.city_detail_view_internet_speed_text)
    TextView internetSpeedText;

    @Bind(R.id.city_detail_view_temperature_text)
    TextView temperatureText;

    @Bind(R.id.city_detail_view_month_price_text)
    TextView monthPriceText;

    @Bind(R.id.city_detail_view_favourite_fab)
    FavouriteActionButton favouriteButton;

    @Bind(R.id.city_detail_view_collapsing_toolbar)
    CollapsingToolbarLayout mToolbarLayout;

    @Bind(R.id.city_detail_view_viewpager)
    ViewPager viewPager;

    @Bind(R.id.city_detail_view_sliding_tabs)
    TabLayout tabLayout;

    @Bind(R.id.city_datail_view_nested_scroll_view)
    NestedScrollView nestedScrollView;

    @Inject
    IMyNomadLifeRepository mRepository;

    protected boolean mOfflineModeLoading = false;
    protected Subscription mSubscriptionApi = Subscriptions.empty();

    protected boolean mOfflineModeState = false;
    protected CityEntity cityEntity = null;

    protected String mCurrentSection = ConstantValues.DETAIL_VIEW_SECTION_CODE;

    protected int mRequestCode = MainListActivity.DETAIL_VIEW_REQUEST_CODE;
    protected String mReturnSection = ConstantValues.MAIN_SECTION_CODE;

    protected DetailViewFragmentPagerAdapter pagerAdapter = null;
    public static final String ACTIVITY_TAG = "activity_detail_view";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_detail_view);

        if (mToolbar != null) {
            ViewCompat.setElevation(mToolbar, getResources().getDimension(R.dimen.toolbar_elevation));
            ActionBar ab = getSupportActionBar();

            if (ab != null) {
                ab.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
                ab.setDisplayShowHomeEnabled(true);
                ab.setDisplayHomeAsUpEnabled(true);
                mToolbar.setNavigationOnClickListener(arrow -> onBackPressed());
            }
        }

        if(getIntent().hasExtra(ACTIVITY_REQUEST_CODE))
            mRequestCode = getIntent().getIntExtra(ACTIVITY_REQUEST_CODE, MainListActivity.DETAIL_VIEW_REQUEST_CODE);

        if(getIntent().hasExtra(MainListActivity.EXTRA_SECTION))
            mReturnSection = getIntent().getStringExtra(MainListActivity.EXTRA_SECTION);

        pagerAdapter = new DetailViewFragmentPagerAdapter(getSupportFragmentManager(), this);
        CityEntity city = getIntent().getParcelableExtra(ConstantValues.EXTRA_ITEM_CITY);
        setupCityEntity(city);
        this.cityEntity = city;
        pagerAdapter.setCityEntity(city);

        initView();
        invalidateOptionsMenu();
    }

    protected void initView() {
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        nestedScrollView.setFillViewport(true);
        favouriteButton.setOnClickListener(view -> {
            if(cityEntity != null) {
                setupFavouriteState();
            }
        });
    }

    private void setupCityEntity(CityEntity cityEntity) {

        if(cityEntity != null) {
            Glide.with(this)
                    .load(UtilityHelper.getCityImageUri(ConstantValues.IMAGE_URL, cityEntity))
                    .placeholder(R.drawable.placeholder_loading)
                    .centerCrop()
                    .crossFade()
                    .into(backdropImage);
            backdropImage.setColorFilter(Color.argb(90, 0, 0, 0), PorterDuff.Mode.DARKEN);

            mToolbarLayout.setTitle(cityEntity.getRegion());
            countryText.setText(cityEntity.getCountry());
            internetSpeedText.setText(
                    UtilityHelper.getInternetSpeed(cityEntity.getInternetSpeed(), this));
            temperatureText.setText(UtilityHelper.getTemperature(cityEntity, this));
            monthPriceText.setText(UtilityHelper.getMonthlyPrice(cityEntity, this));
            favouriteButton.setState(cityEntity.isFavourite());
            mOfflineModeState = cityEntity.isOffline();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_detail_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail_view_favourite_button:
                setupFavouriteState();
                return true;
            case R.id.menu_detail_view_offline_mode_button:
                setupOfflineModeState();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem favouriteMenuItem = menu.findItem(R.id.menu_detail_view_favourite_button);
        MenuItem offlineModeMenuItem = menu.findItem(R.id.menu_detail_view_offline_mode_button);

        favouriteMenuItem.setTitle(favouriteButton.getState() ? getString(R.string.menu_detail_view_remove_from_favourites)
                : getString(R.string.menu_detail_view_add_to_favourites));

        offlineModeMenuItem.setTitle(mOfflineModeState ? getString(R.string.menu_detail_view_remove_from_offline_mode)
                : getString(R.string.menu_detail_view_add_to_offline_mode));

        return super.onPrepareOptionsMenu(menu);
    }

    protected void setupResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainListActivity.EXTRA_SECTION, mReturnSection);
        setResult(MainListActivity.DETAIL_VIEW_RESULT_CODE, returnIntent);
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

    protected void setupFavouriteState() {
        favouriteButton.setState(!favouriteButton.getState());
        invalidateOptionsMenu();

        onFavouriteClicked(this.cityEntity.getSlug(), favouriteButton.getState());
    }

    protected void setupOfflineModeState() {
        onOfflineModeClicked(this.cityEntity.getSlug(), mOfflineModeState);
    }

    @Override
    public void setContentView(int id) {
        super.setContentView(id);
        setupToolbar();
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
    protected void setupSectionFragment(String section) {}

    protected void setupToolbar() {
        if (mToolbar == null)
            return;

        ViewCompat.setElevation(mToolbar, getResources().getDimension(R.dimen.toolbar_elevation));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(arrow -> onBackPressed());
        }
    }

    @Override
    protected void setupComponent(AppComponent component) {
        component.inject(this);
    }

    protected void onFavouriteClicked(String slug, boolean state) {
        if(state)
            mRepository.addFavouriteCitySlugs(slug);
        else
            mRepository.removeFavouriteCitySlugs(slug);
    }

    protected void setOfflineModeState(boolean value) {
        this.runOnUiThread(() -> {
            mOfflineModeState = value;
            invalidateOptionsMenu();
        });
    }

    protected void onOfflineModeClicked(String slug, boolean state) {
        if(state) {
            mRepository.removeOfflineCity(slug);
            setOfflineModeState(false);
            Toast.makeText(this, getString(R.string.city_item_detail_view_offline_mode_remove), Toast.LENGTH_LONG).show();
        } else {
            addCurrentCityEntityToOfflineMode();
        }
    }

    protected void addCurrentCityEntityToOfflineMode() {
        List<String> citiesSlugs = new ArrayList<>();
        citiesSlugs.add(cityEntity.getSlug());

        if(mOfflineModeLoading) {
            this.runOnUiThread(() -> {
                Toast.makeText(this, getString(R.string.city_item_detail_view_offline_mode_please_wait), Toast.LENGTH_SHORT).show();
            });
            return;
        }

        mOfflineModeLoading = true;

        if(mSubscriptionApi != null)
            mSubscriptionApi.unsubscribe();

        Realm realm = mRepository.getRealm();
        mSubscriptionApi = mRepository.offlineCitiesFromApi(realm, citiesSlugs, false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    mRepository.addOfflineCitySlug(cityEntity.getSlug());
                    setOfflineModeState(true);
                    UtilityHelper.closeDatabase(realm);
                    this.runOnUiThread(() -> {
                        Toast.makeText(this, getString(R.string.city_item_detail_view_offline_mode_add), Toast.LENGTH_SHORT).show();
                        mOfflineModeLoading = false;
                    });
                }, throwable -> {
                    UtilityHelper.closeDatabase(realm);
                    this.runOnUiThread(() -> {
                        Toast.makeText(this, getString(R.string.city_item_detail_view_offline_mode_add_error), Toast.LENGTH_LONG).show();
                        mOfflineModeLoading = false;
                    });
                });
    }
}
