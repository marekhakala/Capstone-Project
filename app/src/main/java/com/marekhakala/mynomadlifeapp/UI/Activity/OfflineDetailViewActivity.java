package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;

import butterknife.Bind;

public class OfflineDetailViewActivity extends AbstractBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    protected String mCurrentSection = ConstantValues.DETAIL_VIEW_SECTION_CODE;

    protected int mRequestCode = MainListActivity.DETAIL_VIEW_REQUEST_CODE;
    protected String mReturnSection = ConstantValues.MAIN_SECTION_CODE;

    public static final String ACTIVITY_TAG = "activity_offline_detail_view";

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
}
