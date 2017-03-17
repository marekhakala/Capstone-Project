package com.marekhakala.mynomadlifeapp.UI.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Component.OnCityItemClickListener;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class OfflineCitiesDSRecyclerViewAdapter extends AbstractDataSourceRecyclerViewAdapter<CityOfflineEntity, CityViewViewHolder> {

    private OnCityItemClickListener listener = null;

    public OfflineCitiesDSRecyclerViewAdapter(Context context, List<CityOfflineEntity> results) {
        super(context, results);
    }

    public void setListener(OnCityItemClickListener listener) {
        this.listener = listener;
    }

    public CityViewViewHolder onCreateDataSourceViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_city, viewGroup, false);
        return new CityViewViewHolder(view);
    }

    public void onBindDataSourceViewHolder(CityViewViewHolder holder, int position) {
        final CityOfflineEntity cityEntity = results.get(position);

        if (cityEntity.getBitmapImage() != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            cityEntity.getBitmapImage().compress(Bitmap.CompressFormat.PNG, 100, stream);

            Glide.with(context)
                    .load(stream.toByteArray())
                    .placeholder(R.drawable.placeholder_loading)
                    .into(holder.image);
        } else {
            holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder_loading));
        }

        holder.image.setColorFilter(Color.argb(90, 0, 0, 0), PorterDuff.Mode.DARKEN);
        holder.title.setText(cityEntity.getRegion());
        holder.country.setText(cityEntity.getCountry());
        holder.speed.setText(UtilityHelper.getInternetSpeed(cityEntity.getInternetSpeed(), context));
        holder.temperature.setText(UtilityHelper.getTemperature(cityEntity, context));
        holder.monthPrice.setText(UtilityHelper.getMonthlyPrice(cityEntity, context));
        holder.favouriteButton.setState(cityEntity.isFavourite());

        holder.container.setOnClickListener(view -> {
            if(listener != null)
                listener.onClicked(cityEntity, view, holder.getAdapterPosition());
        });

        holder.favouriteButton.setOnClickListener(view -> {
            if (listener != null) {
                boolean state = !holder.favouriteButton.getState();
                holder.favouriteButton.setState(state);
                cityEntity.setFavourite(state);

                listener.onFavouriteClicked(cityEntity.getSlug(), state);
            }
        });
    }

    @Override
    public void removeItem(CityOfflineEntity item) {}
}
