package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;

public class SplashActivity extends AppCompatActivity {
    protected String settingsStartupScreenKey;
    protected String settingsStartupScreenDefault;
    protected String settingsStartupScreenValue;

    protected String settingsStartupScreenFavourites;
    protected String settingsStartupScreenOfflineMode;

    protected void setupValues() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        settingsStartupScreenKey = getResources().getString(R.string.settings_key_startup_screen);
        settingsStartupScreenDefault = getResources().getString(R.string.settings_startup_screen_value);
        settingsStartupScreenValue = settings.getString(settingsStartupScreenKey, settingsStartupScreenDefault);

        settingsStartupScreenFavourites = getResources().getString(R.string.settings_startup_screen_favourites_value);
        settingsStartupScreenOfflineMode = getResources().getString(R.string.settings_startup_screen_offline_mode_value);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setupValues();
        String extraSectionValue;
        Intent intent = new Intent(this, MainListActivity.class);

        if(settingsStartupScreenValue.equals(settingsStartupScreenFavourites))
            extraSectionValue = ConstantValues.FAVOURITES_SECTION_CODE;
        else if(settingsStartupScreenValue.equals(settingsStartupScreenOfflineMode))
            extraSectionValue = ConstantValues.OFFLINE_MODE_SECTION_CODE;
        else
            extraSectionValue = ConstantValues.MAIN_SECTION_CODE;

        intent.putExtra(MainListActivity.EXTRA_SECTION, extraSectionValue);
        startActivity(intent);
        finish();
    }
}