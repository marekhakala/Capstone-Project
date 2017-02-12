package com.marekhakala.mynomadlifeapp.UI.Adapter;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CityPlacesToWorkViewViewHolder extends DataSourceRecyclerViewHolder {

    @Bind(R.id.item_city_place_to_work_image)
    ImageView image;

    @Bind(R.id.item_city_place_to_work_name_text)
    TextView nameText;

    @Bind(R.id.item_city_sub_name_text)
    TextView subNameText;

    @Bind(R.id.item_city_place_to_work_web_button)
    Button webButton;

    @Bind(R.id.item_city_place_to_work_distance)
    TextView distanceText;

    @Bind(R.id.item_city_place_to_work_navigation_fab)
    FloatingActionButton navigationFab;

    public CityPlacesToWorkViewViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
