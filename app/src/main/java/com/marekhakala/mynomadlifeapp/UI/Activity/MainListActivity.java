package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Fragment.AbstractBaseFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.AbstractListFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.FavouritesListFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.MainListFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.OfflineModeListFragment;
import com.marekhakala.mynomadlifeapp.UI.PrimaryDrawerInfoItem;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.Bind;

import static com.marekhakala.mynomadlifeapp.Utilities.ConstantValues.FAVOURITES_SECTION_CODE;
import static com.marekhakala.mynomadlifeapp.Utilities.ConstantValues.MAIN_SECTION_CODE;
import static com.marekhakala.mynomadlifeapp.Utilities.ConstantValues.OFFLINE_MODE_SECTION_CODE;

public class MainListActivity extends AbstractBaseActivity {

    public interface OnFragmentActionsListener {
        void onGoToTop();
        void onReloadData();
        void onReloadDataFromCache();
    }

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static final String ACTIVITY_TAG = "activity_main_list";
    public static final String EXTRA_SECTION = "current_section";

    // Sections
    protected Drawer drawer = null;
    protected PrimaryDrawerItem mainListItem;
    protected PrimaryDrawerItem favouriteListItem;
    protected PrimaryDrawerItem offlineModeListItem;

    // Sections - Request codes
    public static final int LIST_VIEW_REQUEST_CODE = 101;
    public static final int SEARCH_REQUEST_CODE = 102;
    public static final int FILTER_REQUEST_CODE = 103;
    public static final int SETTINGS_REQUEST_CODE = 104;
    public static final int DETAIL_VIEW_REQUEST_CODE = 105;
    public static final int ABOUT_APP_REQUEST_CODE = 106;

    // Sections - Result codes
    public static final int LIST_VIEW_RESULT_CODE = 301;
    public static final int SEARCH_RESULT_CODE = 302;
    public static final int FILTER_RESULT_CODE = 303;
    public static final int SETTINGS_RESULT_CODE = 304;
    public static final int DETAIL_VIEW_RESULT_CODE = 305;
    public static final int ABOUT_APP_RESULT_CODE = 306;

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
    }

    @Override
    public void onSaveInstanceState(Bundle bandle) {
        super.onSaveInstanceState(bandle);

        if(mCurrentSection.equals(ConstantValues.START_SECTION_CODE))
            mCurrentSection = MAIN_SECTION_CODE;

        bandle.putString(EXTRA_SECTION, mCurrentSection);
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
                    startActivityForSection(intent, MAIN_SECTION_CODE, LIST_VIEW_REQUEST_CODE);
                }
                break;
            case ConstantValues.ABOUT_SECTION_CODE:
                intent = new Intent(this, AboutActivity.class);
                intent.putExtra(EXTRA_SECTION, getCurrentSection());
                startActivityForSection(intent,
                        ConstantValues.ABOUT_SECTION_CODE, ABOUT_APP_REQUEST_CODE);
                break;
            default:
                intent = new Intent(this, this.getClass());
                startActivity(intent);
                break;
        }
    }

    protected void startActivityForSection(Intent intent, String section, int requestCode) {
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

        return new IDrawerItem[] {mainListItem, favouriteListItem, offlineModeListItem,
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
                    mCurrentFragment = (AbstractListFragment) getSupportFragmentManager().findFragmentByTag(MainListFragment.FRAGMENT_TAG);

                    if(mCurrentFragment == null)
                        replaceFragment(new MainListFragment());
                    break;
                case FAVOURITES_SECTION_CODE:
                    setupSectionDrawer(favouriteListItem);
                    setTitle(getResources().getString(R.string.section_favourites_list_title));
                    mCurrentFragment = (AbstractListFragment) getSupportFragmentManager().findFragmentByTag(FavouritesListFragment.FRAGMENT_TAG);

                    if(mCurrentFragment == null)
                        replaceFragment(new FavouritesListFragment());
                    break;
                case OFFLINE_MODE_SECTION_CODE:
                    setupSectionDrawer(offlineModeListItem);
                    setTitle(getResources().getString(R.string.section_offline_mode_list_title));
                    mCurrentFragment = (AbstractListFragment) getSupportFragmentManager().findFragmentByTag(OfflineModeListFragment.FRAGMENT_TAG);

                    if(mCurrentFragment == null)
                        replaceFragment(new OfflineModeListFragment());
                    break;
            }
            mCurrentSection = section;
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

        setTitle(title);
        replaceFragment(fragment);
        mCurrentSection = section;
    }

    protected void replaceFragment(AbstractBaseFragment fragment) {

        if(fragment instanceof AbstractListFragment) {
            mCurrentFragment = (AbstractListFragment) fragment;

            if(fragment instanceof MainListFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_list_content_container, fragment, MainListFragment.FRAGMENT_TAG).commit();
            } else if(fragment instanceof FavouritesListFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_list_content_container, fragment, FavouritesListFragment.FRAGMENT_TAG).commit();
            } else if(fragment instanceof OfflineModeListFragment) {
                getSupportFragmentManager().beginTransaction()
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
}
