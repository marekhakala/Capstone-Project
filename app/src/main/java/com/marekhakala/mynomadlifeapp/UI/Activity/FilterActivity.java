package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import butterknife.Bind;

public class FilterActivity extends AbstractBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static final String ACTIVITY_TAG = "activity_filter";

    protected String mCurrentSection = ConstantValues.FILTER_SECTION_CODE;
    protected int mRequestCode = MainListActivity.FILTER_REQUEST_CODE;
    protected String mReturnSection = ConstantValues.MAIN_SECTION_CODE;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_filter);

        if(getIntent().hasExtra(ACTIVITY_REQUEST_CODE))
            mRequestCode = getIntent().getIntExtra(ACTIVITY_REQUEST_CODE, MainListActivity.FILTER_REQUEST_CODE);

        if(getIntent().hasExtra(MainListActivity.EXTRA_SECTION))
            mReturnSection = getIntent().getStringExtra(MainListActivity.EXTRA_SECTION);
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
