package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Fragment.AbstractBaseFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.AbstractListFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.FavouritesListFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.MainListFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.OfflineModeListFragment;
import com.marekhakala.mynomadlifeapp.UI.PrimaryDrawerInfoItem;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import javax.inject.Inject;

import butterknife.Bind;

import static com.marekhakala.mynomadlifeapp.Utilities.ConstantValues.FAVOURITES_SECTION_CODE;
import static com.marekhakala.mynomadlifeapp.Utilities.ConstantValues.MAIN_SECTION_CODE;
import static com.marekhakala.mynomadlifeapp.Utilities.ConstantValues.OFFLINE_MODE_SECTION_CODE;

public class MainListActivity extends AbstractBaseActivity implements AbstractListFragment.Listener {

    public interface OnFragmentActionsListener {
        void onGoToTop();
        void onReloadData();
        void onReloadDataFromCache();
    }

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.main_list_adview)
    AdView mAdView;

    @Inject
    Tracker mTracker;

    @Inject
    AdRequest mAdRequest;

    public static final String ACTIVITY_TAG = "activity_main_list";
    public static final String EXTRA_SECTION = "currentSection";

    // Sections
    protected Drawer drawer = null;
    protected PrimaryDrawerItem mainListItem;
    protected PrimaryDrawerItem favouriteListItem;
    protected PrimaryDrawerItem offlineModeListItem;
    protected PrimaryDrawerItem exchangeRatesItem;

    // Sections - Request codes
    public static final int LIST_VIEW_REQUEST_CODE = 101;
    public static final int SEARCH_REQUEST_CODE = 102;
    public static final int FILTER_REQUEST_CODE = 103;
    public static final int SETTINGS_REQUEST_CODE = 104;
    public static final int DETAIL_VIEW_REQUEST_CODE = 105;
    public static final int EXCHANGE_RATES_REQUEST_CODE = 106;
    public static final int ABOUT_APP_REQUEST_CODE = 107;

    // Sections - Result codes
    public static final int LIST_VIEW_RESULT_CODE = 301;
    public static final int SEARCH_RESULT_CODE = 302;
    public static final int FILTER_RESULT_CODE = 303;
    public static final int SETTINGS_RESULT_CODE = 304;
    public static final int DETAIL_VIEW_RESULT_CODE = 305;
    public static final int EXCHANGE_RATES_RESULT_CODE = 306;
    public static final int ABOUT_APP_RESULT_CODE = 307;

    protected String mCurrentSection = null;
    protected AbstractListFragment mCurrentFragment = null;
    protected boolean mSectionClickEnabled = true;
    protected OnFragmentActionsListener mListener = null;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_list);
        setupDrawerMenu(this, bundle, mToolbar);

        if(bundle != null) {
            setupSectionFromBundle(bundle.getString(EXTRA_SECTION));
        } else if(getIntent().hasExtra(EXTRA_SECTION)) {
            setupSectionFromBundle(getIntent().getStringExtra(EXTRA_SECTION));
        } else {
            if (mCurrentFragment == null)
                setupSectionFragment(MAIN_SECTION_CODE);
        }

        // Analytics
        mTracker.setScreenName(UtilityHelper.getScreenNameForAnalytics(mCurrentSection));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        // AdMob
        mAdView.loadAd(mAdRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(resultCode) {
            case DETAIL_VIEW_RESULT_CODE:
                mListener.onReloadDataFromCache();
                break;

            case SEARCH_RESULT_CODE:
                mListener.onReloadDataFromCache();
                break;

            case FILTER_RESULT_CODE:
                if(data.hasExtra(FilterActivity.FILTER_CHANGES_APPLIED)
                        && data.getBooleanExtra(FilterActivity.FILTER_CHANGES_APPLIED, false)) {
                    mListener.onReloadData();
                }

                break;

            case SETTINGS_RESULT_CODE:
                if(data.hasExtra(SettingsActivity.EXTRA_TEMPERATURE_UNIT_TYPE_CHANGED)) {
                    if(data.hasExtra(SettingsActivity.EXTRA_FILTER_VALUES_CHANGED))
                        mListener.onReloadData();
                    else
                        mListener.onReloadDataFromCache();
                }
                break;

            case EXCHANGE_RATES_RESULT_CODE:
                if(data.hasExtra(MainListActivity.EXTRA_SECTION)) {
                    mSectionClickEnabled = false;
                    setupSectionFromBundle(data.getStringExtra(MainListActivity.EXTRA_SECTION));
                    mSectionClickEnabled = true;
                }
                break;

            case ABOUT_APP_RESULT_CODE:
                if(data.hasExtra(MainListActivity.EXTRA_SECTION)) {
                    mSectionClickEnabled = false;
                    setupSectionFromBundle(data.getStringExtra(MainListActivity.EXTRA_SECTION));
                    mSectionClickEnabled = true;
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bandle) {
        super.onSaveInstanceState(bandle);

        if(mCurrentSection.equals(ConstantValues.START_SECTION_CODE))
            mCurrentSection = MAIN_SECTION_CODE;

        bandle.putString(EXTRA_SECTION, mCurrentSection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_main_list_go_to_top:
                if(mListener != null)
                    mListener.onGoToTop();
                return true;
            case R.id.menu_main_list_refresh:
                if(mListener != null)
                    mListener.onReloadData();
                return true;
            case R.id.menu_main_list_search:
                startActivityForSection(new Intent(this, SearchActivity.class), SEARCH_REQUEST_CODE);
                return true;
            case R.id.menu_favourites_refresh:
                if(mListener != null)
                    mListener.onReloadData();
                return true;
            case R.id.menu_offline_mode_refresh:
                if(mListener != null)
                    mListener.onReloadData();
                return true;
            case R.id.menu_settings:
                startActivityForSection(new Intent(this, SettingsActivity.class), SETTINGS_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setupSection(String section) {
        Intent intent;

        switch(section) {
            case MAIN_SECTION_CODE:
            case FAVOURITES_SECTION_CODE:
            case OFFLINE_MODE_SECTION_CODE:
                if (getCurrentSection().equals(MAIN_SECTION_CODE)
                        || getCurrentSection().equals(FAVOURITES_SECTION_CODE)
                        || getCurrentSection().equals(OFFLINE_MODE_SECTION_CODE)) {
                    setupSectionFragment(section);
                } else {
                    intent = new Intent(this, MainListActivity.class);
                    startActivityForSection(intent, LIST_VIEW_REQUEST_CODE);
                }
                break;
            case ConstantValues.EXCHANGE_RATES_SECTION_CODE:
                intent = new Intent(this, ExchangeRatesActivity.class);
                intent.putExtra(EXTRA_SECTION, getCurrentSection());
                startActivityForSection(intent, EXCHANGE_RATES_REQUEST_CODE);
                break;
            case ConstantValues.ABOUT_SECTION_CODE:
                intent = new Intent(this, AboutActivity.class);
                intent.putExtra(EXTRA_SECTION, getCurrentSection());
                startActivityForSection(intent, ABOUT_APP_REQUEST_CODE);
                break;
            default:
                intent = new Intent(this, this.getClass());
                startActivity(intent);
                break;
        }
    }

    protected void startActivityForSection(Intent intent, int requestCode) {
        intent.putExtra(EXTRA_SECTION, mCurrentSection);
        startActivityForResult(intent, requestCode);
    }

    public void setListener(OnFragmentActionsListener listener) {
        this.mListener = listener;
    }

    protected IDrawerItem[] setupMenuItems() {
        mainListItem = new PrimaryDrawerInfoItem().withCode(MAIN_SECTION_CODE)
                .withName(getResources().getString(R.string.section_main_list_title))
                .withIcon(FontAwesome.Icon.faw_home).withEnabled(true);

        favouriteListItem = new PrimaryDrawerInfoItem().withCode(FAVOURITES_SECTION_CODE)
                .withName(getResources().getString(R.string.section_favourites_list_title))
                .withIcon(FontAwesome.Icon.faw_star);

        offlineModeListItem = new PrimaryDrawerInfoItem().withCode(OFFLINE_MODE_SECTION_CODE)
                .withName(getResources().getString(R.string.section_offline_mode_list_title))
                .withIcon(FontAwesome.Icon.faw_plane);

        exchangeRatesItem = new PrimaryDrawerInfoItem().withCode(ConstantValues.EXCHANGE_RATES_SECTION_CODE)
                .withName(getResources().getString(R.string.section_exchange_rates_title))
                .withIcon(FontAwesome.Icon.faw_exchange);

        return new IDrawerItem[] {mainListItem, favouriteListItem, offlineModeListItem, exchangeRatesItem,
                new PrimaryDrawerInfoItem().withCode(ConstantValues.ABOUT_SECTION_CODE)
                        .withName(getResources().getString(R.string.section_about_title))
                        .withIcon(FontAwesome.Icon.faw_info_circle)};
    }

    protected void setupDrawerMenu(Activity context, Bundle savedInstanceState, Toolbar toolbar) {
        drawer = new DrawerBuilder()
                .withActivity(context)
                .withToolbar(toolbar)
                .withFullscreen(true)
                .addDrawerItems(setupMenuItems())
                .withOnDrawerItemClickListener(new DrawerMenuItemClickListener(context))
                .withSavedInstance(savedInstanceState)
                .build();
    }

    public class DrawerMenuItemClickListener implements Drawer.OnDrawerItemClickListener {
        protected Context mContext;

        public DrawerMenuItemClickListener(Context context) {
            mContext = context;
        }

        @Override
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            if(drawerItem instanceof PrimaryDrawerInfoItem) {
                PrimaryDrawerInfoItem item = (PrimaryDrawerInfoItem) drawerItem;

                if(mSectionClickEnabled)
                    setupSection(item.getCode());
            }

            return false;
        }
    }

    protected void setupSectionFromBundle(String section) {
        if (section != null) {
            switch (section) {
                case MAIN_SECTION_CODE:
                    setupSectionDrawer(mainListItem);
                    setTitle(getResources().getString(R.string.section_main_list_title));
                    mCurrentFragment = (AbstractListFragment) getFragmentManager().findFragmentByTag(MainListFragment.FRAGMENT_TAG);

                    if(mCurrentFragment == null)
                        replaceFragment(new MainListFragment());
                    break;
                case FAVOURITES_SECTION_CODE:
                    setupSectionDrawer(favouriteListItem);
                    setTitle(getResources().getString(R.string.section_favourites_list_title));
                    mCurrentFragment = (AbstractListFragment) getFragmentManager().findFragmentByTag(FavouritesListFragment.FRAGMENT_TAG);

                    if(mCurrentFragment == null)
                        replaceFragment(new FavouritesListFragment());
                    break;
                case OFFLINE_MODE_SECTION_CODE:
                    setupSectionDrawer(offlineModeListItem);
                    setTitle(getResources().getString(R.string.section_offline_mode_list_title));
                    mCurrentFragment = (AbstractListFragment) getFragmentManager().findFragmentByTag(OfflineModeListFragment.FRAGMENT_TAG);

                    if(mCurrentFragment == null)
                        replaceFragment(new OfflineModeListFragment());
                    break;
            }

            mCurrentSection = section;

            if(section.equals(OFFLINE_MODE_SECTION_CODE)) {
                mAdView.setVisibility(View.GONE);
            } else {
                mAdView.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void setupSectionDrawer(PrimaryDrawerItem section) {
        mSectionClickEnabled = false;
        drawer.setSelection(section);
        mSectionClickEnabled = true;
    }

    @Override
    protected void setupSectionFragment(String section) {

        String title = "";
        AbstractListFragment fragment = null;

        switch (section) {
            case MAIN_SECTION_CODE:
                title = getResources().getString(R.string.section_main_list_title);
                fragment = new MainListFragment();
                break;
            case FAVOURITES_SECTION_CODE:
                title = getResources().getString(R.string.section_favourites_list_title);
                fragment = new FavouritesListFragment();
                break;
            case OFFLINE_MODE_SECTION_CODE:
                title = getResources().getString(R.string.section_offline_mode_list_title);
                fragment = new OfflineModeListFragment();
                break;
        }

        if(section.equals(OFFLINE_MODE_SECTION_CODE)) {
            mAdView.setVisibility(View.GONE);
        } else {
            mAdView.setVisibility(View.VISIBLE);
        }

        setTitle(title);
        replaceFragment(fragment);
        mCurrentSection = section;
    }

    protected void replaceFragment(AbstractBaseFragment fragment) {

        if(fragment instanceof AbstractListFragment) {
            mCurrentFragment = (AbstractListFragment) fragment;

            if(fragment instanceof MainListFragment) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_list_content_container, fragment, MainListFragment.FRAGMENT_TAG).commit();
            } else if(fragment instanceof FavouritesListFragment) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_list_content_container, fragment, FavouritesListFragment.FRAGMENT_TAG).commit();
            } else if(fragment instanceof OfflineModeListFragment) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_list_content_container, fragment, OfflineModeListFragment.FRAGMENT_TAG).commit();
            }
        }
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
        if(mCurrentFragment != null)
            return mCurrentFragment.getFragmentName();

        return "";
    }

    @Override
    public String getCurrentSection() {
        return this.mCurrentSection;
    }

    protected void setupToolbar() {
        if (mToolbar == null)
            return;

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected void setupComponent(AppComponent component) {
        component.inject(this);
    }

    @Override
    public void onCitySelected(CityEntity entity, View view) {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra(ConstantValues.EXTRA_ITEM_CITY_TYPE, ConstantValues.CITY_ENTITY);
        intent.putExtra(ConstantValues.EXTRA_ITEM_CITY, entity);
        intent.putExtra(EXTRA_SECTION, mCurrentSection);
        startActivityForResult(intent, DETAIL_VIEW_REQUEST_CODE);
    }

    @Override
    public void onCitySelected(CityOfflineEntity entity, View view) {
        Intent intent = new Intent(this, OfflineDetailViewActivity.class);
        intent.putExtra(ConstantValues.EXTRA_ITEM_CITY_TYPE, ConstantValues.CITY_ENTITY);
        intent.putExtra(ConstantValues.EXTRA_ITEM_CITY, entity);
        intent.putExtra(EXTRA_SECTION, mCurrentSection);
        startActivityForResult(intent, DETAIL_VIEW_REQUEST_CODE);
    }
}
