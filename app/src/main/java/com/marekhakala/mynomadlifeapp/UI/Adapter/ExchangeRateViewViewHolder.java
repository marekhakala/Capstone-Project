package com.marekhakala.mynomadlifeapp.UI.Adapter;

import android.view.View;
import android.widget.TextView;

import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExchangeRateViewViewHolder extends DataSourceRecyclerViewHolder {

    @Bind(R.id.widget_item_today_exchange_rates_c1_in_c2_text)
    TextView c1Inc2Text;

    @Bind(R.id.widget_item_today_exchange_rates_c1_in_c2_value)
    TextView c1Inc2Value;

    public ExchangeRateViewViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
