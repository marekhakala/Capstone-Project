package com.marekhakala.mynomadlifeapp.UI.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.marekhakala.mynomadlifeapp.DataSource.ExchangeRatesFetchService;
import com.marekhakala.mynomadlifeapp.R;

public class ExchangeRatesWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        for(int id : ids) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_today_exchange_rates);

            views.setRemoteAdapter(R.id.widget_today_exchange_rates_list, new Intent(context, ExchangeRatesRemoteViewsService.class));
            views.setEmptyView(R.id.widget_today_exchange_rates_list, R.id.widget_today_exchange_rates_empty_text);

            manager.updateAppWidget(id, views);
        }
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction().equals(ExchangeRatesFetchService.DATA_UPDATED_CODE)) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            int[] ids = manager.getAppWidgetIds(new ComponentName(context, getClass()));
            manager.notifyAppWidgetViewDataChanged(ids, R.id.widget_today_exchange_rates_list);
        }
    }
}
