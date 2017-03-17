package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;

import butterknife.Bind;

public class SettingsActivity extends AbstractBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static final String ACTIVITY_TAG = "activity_settings";
    public static final String EXTRA_TEMPERATURE_UNIT_TYPE_CHANGED = "temperature_unit_type_changed";
    public static final String EXTRA_FILTER_VALUES_CHANGED = "filter_values_changed";

    protected MyPreferenceFragment myPreferenceFragment;
    protected int mRequestCode = MainListActivity.SETTINGS_REQUEST_CODE;
    protected String mReturnSection = ConstantValues.MAIN_SECTION_CODE;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_settings);

        loadSettingsFragment();

        if(getIntent().hasExtra(ACTIVITY_REQUEST_CODE))
            mRequestCode = getIntent().getIntExtra(ACTIVITY_REQUEST_CODE, MainListActivity.SETTINGS_REQUEST_CODE);

        if(getIntent().hasExtra(MainListActivity.EXTRA_SECTION))
            mReturnSection = getIntent().getStringExtra(MainListActivity.EXTRA_SECTION);
    }

    protected void loadSettingsFragment() {
        myPreferenceFragment = new MyPreferenceFragment();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_content_container, myPreferenceFragment)
                .commit();
    }

    protected void setupResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainListActivity.EXTRA_SECTION, mReturnSection);
        returnIntent.putExtra(EXTRA_TEMPERATURE_UNIT_TYPE_CHANGED, myPreferenceFragment.temperatureUnitTypeChanged());
        returnIntent.putExtra(EXTRA_FILTER_VALUES_CHANGED, myPreferenceFragment.filterValuesChanged());
        setResult(MainListActivity.SETTINGS_RESULT_CODE, returnIntent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_settings_reset) {
            resetToDefault();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int id) {
        super.setContentView(id);
        setupToolbar();
        setTitle(getResources().getString(R.string.section_settings_title));
    }

    @Override
    public Toolbar getToolbar() {
        return this.mToolbar;
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
        return ConstantValues.SETTINGS_SECTION_CODE;
    }

    @Override
    protected void setupSectionFragment(String section) {
    }

    @Override
    protected void setupComponent(AppComponent component) {
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

    protected void resetToDefault() {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().apply();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
        loadSettingsFragment();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        protected boolean mTemperatureUnitTypeChanged = true;
        protected boolean mFilterValuesChanged = true;

        public boolean temperatureUnitTypeChanged() {
            return mTemperatureUnitTypeChanged;
        }

        public boolean filterValuesChanged() {
            return mFilterValuesChanged;
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            //
            // Settings : General
            //
            Preference prefTemperatureUnitType = findPreference(getString(R.string.settings_key_temperature_unit_type));
            prefTemperatureUnitType.setOnPreferenceChangeListener((preference, newValue) -> {
                mTemperatureUnitTypeChanged = true;
                return true;
            });

            Preference.OnPreferenceChangeListener filtersListener = (preference, newValue) -> {
                mFilterValuesChanged = true;
                return true;
            };

            Preference prefCheapCostOfLivingFrom = findPreference(getString(R.string.settings_key_cheap_cost_of_living_from));
            prefCheapCostOfLivingFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefAffordableCostOfLivingFrom = findPreference(getString(R.string.settings_key_affordable_cost_of_living_from));
            prefAffordableCostOfLivingFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefExpensiveCostOfLivingFrom = findPreference(getString(R.string.settings_key_expensive_cost_of_living_from));
            prefExpensiveCostOfLivingFrom.setOnPreferenceChangeListener(filtersListener);

            //
            // Settings : Population
            //
            Preference prefTownPopulationFrom = findPreference(getString(R.string.settings_key_town_population_from));
            prefTownPopulationFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefBigCityPopulationFrom = findPreference(getString(R.string.settings_key_big_city_population_from));
            prefBigCityPopulationFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefMegaCityFrom = findPreference(getString(R.string.settings_key_mega_city_from));
            prefMegaCityFrom.setOnPreferenceChangeListener(filtersListener);

            //
            // Settings : Internet speed
            //
            Preference prefSlowInternetFrom = findPreference(getString(R.string.settings_key_slow_internet_from));
            prefSlowInternetFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefGoodInternetFrom = findPreference(getString(R.string.settings_key_good_internet_from));
            prefGoodInternetFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefFastIternetFrom = findPreference(getString(R.string.settings_key_fast_internet_from));
            prefFastIternetFrom.setOnPreferenceChangeListener(filtersListener);

            //
            // Settings : Other Filters
            //
            Preference prefOtherFiltersSafeFrom = findPreference(getString(R.string.settings_key_other_filters_safe_from));
            prefOtherFiltersSafeFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefOtherFiltersNightlifeFrom = findPreference(getString(R.string.settings_key_other_filters_nightlife_from));
            prefOtherFiltersNightlifeFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefOtherFiltersFunFrom = findPreference(getString(R.string.settings_key_other_filters_fun_from));
            prefOtherFiltersFunFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefOtherFiltersPlacesToWorkFrom = findPreference(getString(R.string.settings_key_other_filters_places_to_work_from));
            prefOtherFiltersPlacesToWorkFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefOtherFiltersStartupScoreFrom = findPreference(getString(R.string.settings_key_other_filters_startup_score_from));
            prefOtherFiltersStartupScoreFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefOtherFiltersEnglishSpeakingFrom = findPreference(getString(R.string.settings_key_other_filters_english_speaking_from));
            prefOtherFiltersEnglishSpeakingFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefOtherFiltersFriendlyToForeignersFrom = findPreference(getString(R.string.settings_key_other_filters_friendly_to_foreigners_from));
            prefOtherFiltersFriendlyToForeignersFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefOtherFiltersFemaleFriendlyFrom = findPreference(getString(R.string.settings_key_other_filters_female_friendly_from));
            prefOtherFiltersFemaleFriendlyFrom.setOnPreferenceChangeListener(filtersListener);

            Preference prefOtherFiltersGayFriendlyFrom = findPreference(getString(R.string.settings_key_other_filters_gay_friendly_from));
            prefOtherFiltersGayFriendlyFrom.setOnPreferenceChangeListener(filtersListener);
        }
    }
}
