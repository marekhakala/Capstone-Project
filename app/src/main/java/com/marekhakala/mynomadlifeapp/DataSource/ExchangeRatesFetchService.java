package com.marekhakala.mynomadlifeapp.DataSource;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.ExchangeRateEntity;
import com.marekhakala.mynomadlifeapp.Database.CitiesContract;
import com.marekhakala.mynomadlifeapp.MyNomadLifeApplication;
import com.marekhakala.mynomadlifeapp.UI.Activity.ExchangeRatesActivity;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import rx.schedulers.Schedulers;

public class ExchangeRatesFetchService extends IntentService {

    public static String DATA_UPDATED_CODE = "com.marekhakala.mynomadlifeapp.DATA_UPDATED_CODE";

    public static final String EXTRA_EXCHANGE_RATES_RECEIVER = "exchange_rates_receiver";
    public static final String EXTRA_EXCHANGE_RATES_SIZE = "exchange_rates_size";
    public static final String EXTRA_EXCHANGE_RATES_DATA = "exchange_rates_data";

    @Inject
    MyNomadLifeAPI mApi;

    public ExchangeRatesFetchService() {
        super("ExchangeRatesFetchService");
    }

    @Override
    public void onCreate() {
        Context context = getApplicationContext();
        setupComponent(MyNomadLifeApplication.get(context).getComponent());
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mApi.getExchangeRates()
                .observeOn(Schedulers.io())
                .subscribe(result -> {
                    processData(intent, result.getExchangeRates());
                    addExchangeRatesToCache(result.getExchangeRates());
                }, throwable -> {
                    processData(intent, new ArrayList<>());
                });
    }

    protected void processData(Intent intent, List<ExchangeRateEntity> data) {
        ExchangeRateEntity[] dataArray = new ExchangeRateEntity[data.size()];
        ResultReceiver rec = intent.getParcelableExtra(EXTRA_EXCHANGE_RATES_RECEIVER);
        Bundle bundle = new Bundle();

        bundle.putInt(EXTRA_EXCHANGE_RATES_SIZE, data.size());

        for(int i = 0; i < data.size(); i++)
            dataArray[i] = data.get(i);

        bundle.putParcelableArray(EXTRA_EXCHANGE_RATES_DATA, dataArray);
        rec.send(ExchangeRatesActivity.RESULT_OK, bundle);
    }

    private long addExchangeRatesToCache(List<ExchangeRateEntity> exchangeRates) {
        ContentValues[] contentValuesList = new ContentValues[exchangeRates.size()];

        int i = 0;
        for(ExchangeRateEntity exchangeRateEntity : exchangeRates) {
            ContentValues values = exchangeRateEntity.getValues();
            contentValuesList[i++] = values;
        }

        if(i > 0) {
            getContentResolver().delete(CitiesContract.ExchangeRates.CONTENT_URI, null, null);
            getContentResolver().bulkInsert(CitiesContract.ExchangeRates.CONTENT_URI, contentValuesList);
        }

        sendBroadcast(new Intent(DATA_UPDATED_CODE).setPackage(getPackageName()));
        return contentValuesList.length;
    }

    protected void setupComponent(AppComponent component) {
        component.inject(this);
    }
}
