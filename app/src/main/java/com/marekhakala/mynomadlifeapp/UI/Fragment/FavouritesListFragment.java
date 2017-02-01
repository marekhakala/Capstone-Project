package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.os.Bundle;
import android.view.View;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.UI.Activity.MainListActivity;

public class FavouritesListFragment extends AbstractListFragment {

    public static final String FRAGMENT_TAG = "fragment_favourites";

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override
    protected void setupActivityListener() {
        MainListActivity activity = (MainListActivity) getActivity();

        activity.setListener(new MainListActivity.OnFragmentActionsListener() {
            @Override
            public void onGoToTop() {}

            @Override
            public void onReloadData() {}

            @Override
            public void onReloadDataFromCache() {}
        });
    }
    
    @Override
    protected void loadDataFromDB() {}

    @Override
    protected void loadDataFromAPI() {}

    @Override
    public String getFragmentName() {
        return FRAGMENT_TAG;
    }

    @Override
    protected void setupComponent(AppComponent component) {
        component.inject(this);
    }
}
