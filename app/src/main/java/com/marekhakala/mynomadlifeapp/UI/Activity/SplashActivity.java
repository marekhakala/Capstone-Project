package com.marekhakala.mynomadlifeapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = new Intent(this, MainListActivity.class);
        intent.putExtra(MainListActivity.EXTRA_SECTION, ConstantValues.MAIN_SECTION_CODE);
        startActivity(intent);
        finish();
    }
}