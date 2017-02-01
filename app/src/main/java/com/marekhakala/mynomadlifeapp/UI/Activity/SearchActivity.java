package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.Bind;

public class SearchActivity extends AbstractBaseActivity {

    @Bind(R.id.search_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.search_view)
    MaterialSearchView mSearchView;

    public static final String ACTIVITY_TAG = "activity_search";
    protected String mCurrentSection = ConstantValues.SEARCH_SECTION_CODE;

    protected String mSearchQuery = "";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search);
        initSearchView();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(MainListActivity.SEARCH_RESULT_CODE, intent);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Intent returnIntent = new Intent();
        setResult(MainListActivity.SEARCH_RESULT_CODE,returnIntent);
        super.onDestroy();
    }

    protected void initSearchView() {
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearchSubmit(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {}

            @Override
            public void onSearchViewClosed() {
                onBackPressed();
            }
        });
    }

    protected void onSearchSubmit(String query) {
        mSearchQuery = query.trim();
    }

    @Override
    public void setContentView(int id) {
        super.setContentView(id);
        setupToolbar();
        setTitle("");
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
