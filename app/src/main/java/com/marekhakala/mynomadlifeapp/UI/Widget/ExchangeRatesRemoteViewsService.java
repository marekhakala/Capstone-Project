package com.marekhakala.mynomadlifeapp.UI.Widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.marekhakala.mynomadlifeapp.DataModel.ExchangeRateEntity;
import com.marekhakala.mynomadlifeapp.Database.CitiesContract;
import com.marekhakala.mynomadlifeapp.Database.CitiesProjection;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Repository.RepositoryHelpers;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class ExchangeRatesRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RemoteViewsFactory() {
            protected List<ExchangeRateEntity> exchangeRatesList = null;

            @Override
            public void onCreate() {
                Timber.d("onCreate");
            }

            @Override
            public void onDataSetChanged() {
                Timber.d("onDataSetChanged");
                final long identityToken = Binder.clearCallingIdentity();

                Cursor cursor = getContentResolver().query(CitiesContract.ExchangeRates.CONTENT_URI,
                        CitiesProjection.EXCHANGE_RATES_PROJECTION, null, null, CitiesContract.ExchangeRates.SORT_ORDER);

                if(cursor != null) {
                    if(exchangeRatesList != null)
                        exchangeRatesList.clear();
                    else
                        exchangeRatesList = new ArrayList<>();

                    try {
                        int i = 0;
                        while (cursor.moveToNext()) {
                            ExchangeRateEntity exchangeRateEntity = RepositoryHelpers.exchangeRateEntityFactory(cursor);
                            exchangeRateEntity.setId(i++);
                            exchangeRatesList.add(exchangeRateEntity);
                        }
                    } finally {
                        cursor.close();
                    }
                }
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {}

            @Override
            public int getCount() {
                return exchangeRatesList.size();
            }

            @Override
            public RemoteViews getViewAt(int position) {

                if (position == AdapterView.INVALID_POSITION ||
                        exchangeRatesList == null || position < 0 ||
                        position >= exchangeRatesList.size())
                    return null;

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.item_widget_today_exchange_rates);
                ExchangeRateEntity exchangeRateItemObject = exchangeRatesList.get(position);
                return getRemoteView(views, exchangeRateItemObject);
            }

            public RemoteViews getRemoteView(RemoteViews views, ExchangeRateEntity exchangeRateEntity) {

                String c1InC2Text = UtilityHelper.getWidgetExchangeRateTitle(getApplicationContext(), exchangeRateEntity);
                views.setTextViewText(R.id.widget_item_today_exchange_rates_c1_in_c2_text, c1InC2Text);
                views.setContentDescription(R.id.widget_item_today_exchange_rates_c1_in_c2_text, c1InC2Text);

                String c1InC2Value = UtilityHelper.getWidgetExchangeRateValue(getApplicationContext(), exchangeRateEntity);
                views.setTextViewText(R.id.widget_item_today_exchange_rates_c1_in_c2_value, c1InC2Value);
                views.setContentDescription(R.id.widget_item_today_exchange_rates_c1_in_c2_value, c1InC2Value);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.item_widget_today_exchange_rates);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if(position >= 0 && position < exchangeRatesList.size())
                    return exchangeRatesList.get(position).getId();
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
