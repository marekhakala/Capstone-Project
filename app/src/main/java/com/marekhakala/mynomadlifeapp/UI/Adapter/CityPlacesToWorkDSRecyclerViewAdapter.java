package com.marekhakala.mynomadlifeapp.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Component.OnCityPlaceToWorkItemClickListener;

import java.util.List;

public class CityPlacesToWorkDSRecyclerViewAdapter extends AbstractDataSourceRecyclerViewAdapter<CityPlaceToWorkEntity, CityPlacesToWorkViewViewHolder> {

    private OnCityPlaceToWorkItemClickListener listener = null;

    public CityPlacesToWorkDSRecyclerViewAdapter(Context context, List<CityPlaceToWorkEntity> results) {
        super(context, results);
    }

    public void setListener(OnCityPlaceToWorkItemClickListener listener) {
        this.listener = listener;
    }

    public CityPlacesToWorkViewViewHolder onCreateDataSourceViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_city_place_to_work, viewGroup, false);
        return new CityPlacesToWorkViewViewHolder(view);
    }

    @Override
    public void onBindDataSourceViewHolder(CityPlacesToWorkViewViewHolder holder, int position) {
        final CityPlaceToWorkEntity cityPlaceToWorkEntity = results.get(position);

        Glide.with(context)
                .load(cityPlaceToWorkEntity.getImageUrl())
                .placeholder(R.drawable.placeholder_loading)
                .centerCrop()
                .crossFade()
                .into(holder.image);

        holder.image.setColorFilter(Color.argb(90, 0, 0, 0), PorterDuff.Mode.DARKEN);
        holder.nameText.setText(cityPlaceToWorkEntity.getName());
        holder.subNameText.setText(cityPlaceToWorkEntity.getSubName());

        holder.webButton.setOnClickListener(view -> {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cityPlaceToWorkEntity.getDataUrl())));
        });

        holder.distanceText.setText(cityPlaceToWorkEntity.getDistance());

        holder.navigationFab.setOnClickListener(view -> {
            if(listener != null)
                listener.onClicked(cityPlaceToWorkEntity, view, position);
        });
    }

    @Override
    public void removeItem(CityPlaceToWorkEntity item) {}
}
