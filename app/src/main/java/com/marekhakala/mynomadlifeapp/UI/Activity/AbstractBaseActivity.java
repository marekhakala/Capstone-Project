package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.MyNomadLifeApplication;
import butterknife.ButterKnife;

public abstract class AbstractBaseActivity extends AppCompatActivity {

    protected MyNomadLifeApplication application;
    public static final String ACTIVITY_REQUEST_CODE = "request_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        application = MyNomadLifeApplication.get(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setupComponent(MyNomadLifeApplication.get(this).getComponent());
    }

    @Override
    public void setTitle(int id) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
            actionBar.setTitle(id);
        else
            super.setTitle(id);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra(ACTIVITY_REQUEST_CODE, requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    public abstract Toolbar getToolbar();
    protected abstract String getCurrentActivity();
    protected abstract String getCurrentFragment();
    public abstract String getCurrentSection();

    protected abstract void setupSectionFragment(String section);
    protected abstract void setupComponent(AppComponent component);
}