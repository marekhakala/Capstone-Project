package com.marekhakala.mynomadlifeapp.UI.Component;

import android.view.View;

import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;

public interface OnCityItemClickListener {

    void onClicked(CityEntity entity, View view, int index);
    void onClicked(CityOfflineEntity entity, View view, int index);
    void onFavouriteClicked(String slug, boolean state);

    OnCityItemClickListener DUMMY = new OnCityItemClickListener() {
        @Override
        public void onClicked(CityEntity entity, View view, int index) {}

        @Override
        public void onClicked(CityOfflineEntity entity, View view, int index) {}

        @Override
        public void onFavouriteClicked(String slug, boolean state) {}
    };
}
