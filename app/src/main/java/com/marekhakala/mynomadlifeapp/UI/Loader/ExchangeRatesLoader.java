package com.marekhakala.mynomadlifeapp.UI.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.marekhakala.mynomadlifeapp.DataModel.ExchangeRatesResultEntity;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;

public class ExchangeRatesLoader extends AsyncTaskLoader<ExchangeRatesResultEntity> {
    protected IMyNomadLifeRepository mRepository;

    public ExchangeRatesLoader(Context context, IMyNomadLifeRepository repository) {
        super(context);
        mRepository = repository;
    }

    @Override
    public ExchangeRatesResultEntity loadInBackground() {
        ExchangeRatesResultEntity result = new ExchangeRatesResultEntity();
        result.setExchangeRates(mRepository.exchangeRates());
        return result;
    }
}

