package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;

import butterknife.Bind;

public class SettingsActivity extends AbstractBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    protected MyPreferenceFragment myPreferenceFragment;
    protected int mRequestCode = MainListActivity.SETTINGS_REQUEST_CODE;
    protected String mReturnSection = ConstantValues.MAIN_SECTION_CODE;
    public static final String ACTIVITY_TAG = "activity_settings";

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

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
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

    public static class MyPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
