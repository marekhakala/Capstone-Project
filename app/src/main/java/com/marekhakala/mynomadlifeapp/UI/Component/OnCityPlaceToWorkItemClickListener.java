package com.marekhakala.mynomadlifeapp.UI.Component;

import android.view.View;

import com.marekhakala.mynomadlifeapp.DataModel.CityOfflinePlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlaceToWorkEntity;

public interface OnCityPlaceToWorkItemClickListener {
    void onClicked(CityPlaceToWorkEntity entity, View view, int index);
    void onClicked(CityOfflinePlaceToWorkEntity entity, View view, int index);

    OnCityPlaceToWorkItemClickListener DUMMY = new OnCityPlaceToWorkItemClickListener() {
        @Override
        public void onClicked(CityPlaceToWorkEntity entity, View view, int index) {}

        @Override
        public void onClicked(CityOfflinePlaceToWorkEntity entity, View view, int index) {}
    };
}
