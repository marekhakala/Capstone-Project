package com.marekhakala.mynomadlifeapp.UI.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.marekhakala.mynomadlifeapp.DataModel.ExchangeRateEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.util.List;

public class ExchangeRatesRecyclerViewAdapter extends AbstractDataSourceRecyclerViewAdapter<ExchangeRateEntity, ExchangeRateViewViewHolder> {

    public ExchangeRatesRecyclerViewAdapter(Context context, List<ExchangeRateEntity> results) {
        super(context, results);
    }

    public ExchangeRateViewViewHolder onCreateDataSourceViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_today_exchange_rates, viewGroup, false);
        return new ExchangeRateViewViewHolder(view);
    }

    @Override
    public void onBindDataSourceViewHolder(ExchangeRateViewViewHolder holder, int position) {
        final ExchangeRateEntity exchangeRateEntity = results.get(position);

        String c1InC2Text = UtilityHelper.getWidgetExchangeRateTitle(getContext(), exchangeRateEntity);
        holder.c1Inc2Text.setContentDescription(c1InC2Text);
        holder.c1Inc2Text.setText(c1InC2Text);

        String c1InC2Value = UtilityHelper.getWidgetExchangeRateValue(getContext(), exchangeRateEntity);
        holder.c1Inc2Value.setContentDescription(c1InC2Value);
        holder.c1Inc2Value.setText(c1InC2Value);
    }

    @Override
    public void removeItem(ExchangeRateEntity item) {}
}
