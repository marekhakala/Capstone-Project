package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Component.FilterSingleButton;
import com.marekhakala.mynomadlifeapp.UI.Component.FilterSingleSelectionButtons;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import javax.inject.Inject;
import butterknife.Bind;

public class FilterActivity extends AbstractBaseActivity {

    public static final String FILTER_CHEAP_AFFORDABLE_EXPENSIVE_STATE = "cheap_affordable_expensive_state";
    public static final String FILTER_TOWN_BIG_CITY_MEGA_CITY_STATE = "town_big_city_mega_city_state";
    public static final String FILTER_INTERNET_SLOW_GOOD_FAST_CITY_STATE = "internet_slow_good_fast_city_state";
    public static final int FILTER_BUTTONS_NONE_STATE = -1;

    public static final String FILTER_SAFE_STATE = "safe_state";
    public static final String FILTER_NIGHTLIFE_STATE = "nightlife_state";
    public static final String FILTER_FUN_STATE = "fun_state";
    public static final String FILTER_PLACES_TO_WORK_STATE = "places_to_work_state";
    public static final String FILTER_STARTUP_SCORE_STATE = "startup_score_state";
    public static final String FILTER_ENGLISH_SPEAKING_STATE = "english_speaking_state";
    public static final String FILTER_FRIENDLY_TO_FOREIGNERS_STATE = "friendly_to_foreigners_state";
    public static final String FILTER_FEMALE_FRIENDLY_STATE = "female_friendly_state";
    public static final String FILTER_GAY_FRIENDLY_STATE = "gay_friendly_state";
    public static final String FILTER_CHANGES_APPLIED = "changes_applied";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.filter_cheap_affordable_expensive_buttons)
    FilterSingleSelectionButtons filterCheapAffordableExpensiveButtons;

    @Bind(R.id.filter_town_big_city_mega_city_buttons)
    FilterSingleSelectionButtons filterTownBigCityMegaCityButtons;

    @Bind(R.id.filter_internet_slow_good_fast_city_buttons)
    FilterSingleSelectionButtons filterInternetSlowGoodFastCityButtons;

    @Bind(R.id.filter_safe_button)
    FilterSingleButton filterSafeButton;

    @Bind(R.id.filter_nightlife_button)
    FilterSingleButton filterNightlifeButton;

    @Bind(R.id.filter_fun_button)
    FilterSingleButton filterFunButton;

    @Bind(R.id.filter_places_to_work_button)
    FilterSingleButton filterPlacesToWorkButton;

    @Bind(R.id.filter_startup_score_button)
    FilterSingleButton filterStartupScoreButton;

    @Bind(R.id.filter_english_speaking_button)
    FilterSingleButton filterEnglishSpeakingButton;

    @Bind(R.id.filter_friendly_to_foreigners_button)
    FilterSingleButton filterFriendlyToForeignersButton;

    @Bind(R.id.filter_female_friendly_button)
    FilterSingleButton filterFemaleFriendlyButton;

    @Bind(R.id.filter_gay_friendly_button)
    FilterSingleButton filterGayFriendlyButton;

    @Inject
    Tracker mTracker;

    public static final String ACTIVITY_TAG = "activity_filter";

    protected String mCurrentSection = ConstantValues.FILTER_SECTION_CODE;
    protected int mRequestCode = MainListActivity.FILTER_REQUEST_CODE;
    protected String mReturnSection = ConstantValues.MAIN_SECTION_CODE;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_filter);

        restoreFilterStates();

        if(getIntent().hasExtra(ACTIVITY_REQUEST_CODE))
            mRequestCode = getIntent().getIntExtra(ACTIVITY_REQUEST_CODE, MainListActivity.FILTER_REQUEST_CODE);

        if(getIntent().hasExtra(MainListActivity.EXTRA_SECTION))
            mReturnSection = getIntent().getStringExtra(MainListActivity.EXTRA_SECTION);

        // Analytics
        mTracker.setScreenName(UtilityHelper.getScreenNameForAnalytics(ConstantValues.FILTER_SECTION_CODE));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    protected void restoreFilterStates() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        filterCheapAffordableExpensiveButtons.clickButton(settings.getInt(FILTER_CHEAP_AFFORDABLE_EXPENSIVE_STATE, FILTER_BUTTONS_NONE_STATE));
        filterTownBigCityMegaCityButtons.clickButton(settings.getInt(FILTER_TOWN_BIG_CITY_MEGA_CITY_STATE, FILTER_BUTTONS_NONE_STATE));
        filterInternetSlowGoodFastCityButtons.clickButton(settings.getInt(FILTER_INTERNET_SLOW_GOOD_FAST_CITY_STATE, FILTER_BUTTONS_NONE_STATE));

        filterSafeButton.setEnabled(settings.getBoolean(FILTER_SAFE_STATE, false));
        filterNightlifeButton.setEnabled(settings.getBoolean(FILTER_NIGHTLIFE_STATE, false));
        filterFunButton.setEnabled(settings.getBoolean(FILTER_FUN_STATE, false));
        filterPlacesToWorkButton.setEnabled(settings.getBoolean(FILTER_PLACES_TO_WORK_STATE, false));
        filterStartupScoreButton.setEnabled(settings.getBoolean(FILTER_STARTUP_SCORE_STATE, false));
        filterEnglishSpeakingButton.setEnabled(settings.getBoolean(FILTER_ENGLISH_SPEAKING_STATE, false));
        filterFriendlyToForeignersButton.setEnabled(settings.getBoolean(FILTER_FRIENDLY_TO_FOREIGNERS_STATE, false));
        filterFemaleFriendlyButton.setEnabled(settings.getBoolean(FILTER_FEMALE_FRIENDLY_STATE, false));
        filterGayFriendlyButton.setEnabled(settings.getBoolean(FILTER_GAY_FRIENDLY_STATE, false));
    }

    protected void saveFilterStates() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt(FILTER_CHEAP_AFFORDABLE_EXPENSIVE_STATE, filterCheapAffordableExpensiveButtons.getCurrentButtonId());
        editor.putInt(FILTER_TOWN_BIG_CITY_MEGA_CITY_STATE, filterTownBigCityMegaCityButtons.getCurrentButtonId());
        editor.putInt(FILTER_INTERNET_SLOW_GOOD_FAST_CITY_STATE, filterInternetSlowGoodFastCityButtons.getCurrentButtonId());

        editor.putBoolean(FILTER_SAFE_STATE, filterSafeButton.getEnabled());
        editor.putBoolean(FILTER_NIGHTLIFE_STATE, filterNightlifeButton.getEnabled());
        editor.putBoolean(FILTER_FUN_STATE, filterFunButton.getEnabled());
        editor.putBoolean(FILTER_PLACES_TO_WORK_STATE, filterPlacesToWorkButton.getEnabled());
        editor.putBoolean(FILTER_STARTUP_SCORE_STATE, filterStartupScoreButton.getEnabled());
        editor.putBoolean(FILTER_ENGLISH_SPEAKING_STATE, filterEnglishSpeakingButton.getEnabled());
        editor.putBoolean(FILTER_FRIENDLY_TO_FOREIGNERS_STATE, filterFriendlyToForeignersButton.getEnabled());
        editor.putBoolean(FILTER_FEMALE_FRIENDLY_STATE, filterFemaleFriendlyButton.getEnabled());
        editor.putBoolean(FILTER_GAY_FRIENDLY_STATE, filterGayFriendlyButton.getEnabled());
        editor.apply();
    }

    protected void resetFilterStates() {
        filterCheapAffordableExpensiveButtons.clickButton(FILTER_BUTTONS_NONE_STATE);
        filterTownBigCityMegaCityButtons.clickButton(FILTER_BUTTONS_NONE_STATE);
        filterInternetSlowGoodFastCityButtons.clickButton(FILTER_BUTTONS_NONE_STATE);

        filterSafeButton.setEnabled(false);
        filterNightlifeButton.setEnabled(false);
        filterFunButton.setEnabled(false);
        filterPlacesToWorkButton.setEnabled(false);
        filterStartupScoreButton.setEnabled(false);
        filterEnglishSpeakingButton.setEnabled(false);
        filterFriendlyToForeignersButton.setEnabled(false);
        filterFemaleFriendlyButton.setEnabled(false);
        filterGayFriendlyButton.setEnabled(false);
    }

    protected void saveAndClose() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainListActivity.EXTRA_SECTION, mReturnSection);
        returnIntent.putExtra(FILTER_CHANGES_APPLIED, true);
        setResult(MainListActivity.FILTER_RESULT_CODE, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();

        returnIntent.putExtra(MainListActivity.EXTRA_SECTION, mReturnSection);
        returnIntent.putExtra(FILTER_CHANGES_APPLIED, false);
        setResult(MainListActivity.FILTER_RESULT_CODE, returnIntent);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.menu_filter_reset) {
            resetFilterStates();
            return true;
        } else if(item.getItemId() == R.id.menu_filter_done) {
            saveFilterStates();
            saveAndClose();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
