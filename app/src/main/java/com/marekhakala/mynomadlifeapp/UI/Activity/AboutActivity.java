package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;

import butterknife.Bind;

public class AboutActivity extends AbstractBaseActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    public static final String ACTIVITY_TAG = "activity_about";

    protected String mCurrentSection = ConstantValues.ABOUT_SECTION_CODE;
    protected int mRequestCode = MainListActivity.ABOUT_APP_REQUEST_CODE;
    protected String mReturnSection = ConstantValues.MAIN_SECTION_CODE;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_about);

        if(getIntent().hasExtra(ACTIVITY_REQUEST_CODE))
            mRequestCode = getIntent().getIntExtra(ACTIVITY_REQUEST_CODE, MainListActivity.ABOUT_APP_REQUEST_CODE);

        if(getIntent().hasExtra(MainListActivity.EXTRA_SECTION))
            mReturnSection = getIntent().getStringExtra(MainListActivity.EXTRA_SECTION);
    }

    protected void setupResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainListActivity.EXTRA_SECTION, mReturnSection);
        setResult(MainListActivity.ABOUT_APP_RESULT_CODE, returnIntent);
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
        setTitle(getResources().getString(R.string.section_about_title));
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
