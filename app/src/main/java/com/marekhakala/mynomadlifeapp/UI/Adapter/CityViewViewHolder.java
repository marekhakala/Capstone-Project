package com.marekhakala.mynomadlifeapp.UI.Adapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerViewHolder;
import com.marekhakala.mynomadlifeapp.UI.Component.FavouriteActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CityViewViewHolder extends DataSourceRecyclerViewHolder {

    @Bind(R.id.item_city_content_container) CardView container;
    @Bind(R.id.item_city_image) ImageView image;
    @Bind(R.id.item_city_title_text) TextView title;
    @Bind(R.id.item_city_country_text) TextView country;
    @Bind(R.id.item_city_speed_text) TextView speed;
    @Bind(R.id.item_city_temperature) TextView temperature;
    @Bind(R.id.item_city_month_price) TextView monthPrice;
    @Bind(R.id.item_city_favourite_fab) FavouriteActionButton favouriteButton;

    public CityViewViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
