package com.marekhakala.mynomadlifeapp.UI.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;

import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Component.OnCityItemClickListener;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.util.List;

public class CitiesDSRecyclerViewAdapter extends AbstractDataSourceRecyclerViewAdapter<CityEntity, CityViewViewHolder> {

    private OnCityItemClickListener listener = null;

    public CitiesDSRecyclerViewAdapter(Context context, List<CityEntity> results) {
        super(context, results);
    }

    public void setListener(OnCityItemClickListener listener) {
        this.listener = listener;
    }

    public CityViewViewHolder onCreateDataSourceViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_city, viewGroup, false);
        return new CityViewViewHolder(view);
    }

    @Override
    public void onBindDataSourceViewHolder(CityViewViewHolder holder, int position) {
        final CityEntity cityEntity = results.get(position);

        Glide.with(context)
                .load(UtilityHelper.getCityImageUri(ConstantValues.IMAGE_URL, cityEntity))
                .placeholder(R.drawable.placeholder_loading)
                .centerCrop()
                .crossFade()
                .into(holder.image);

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
    public void removeItem(CityEntity item) {

        for(int i = 0; i < results.size(); i++) {
            CityEntity cityEntity = results.get(i);
            if(cityEntity.getSlug().equals(item.getSlug())) {
                results.remove(item);
                notifyItemRemoved(i);

                if(results.isEmpty())
                    setCurrentState(StateType.EMPTY_STATE);

                return;
            }
        }
    }
}
