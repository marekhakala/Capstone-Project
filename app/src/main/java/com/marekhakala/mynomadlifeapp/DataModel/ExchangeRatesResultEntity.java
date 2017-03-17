package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExchangeRatesResultEntity implements Parcelable {

    @Expose
    @SerializedName("exchange_rates")
    private List<ExchangeRateEntity> exchangeRates;

    public List<ExchangeRateEntity> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRateEntity> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.exchangeRates);
    }

    public ExchangeRatesResultEntity() {
    }

    protected ExchangeRatesResultEntity(Parcel in) {
        this.exchangeRates = in.createTypedArrayList(ExchangeRateEntity.CREATOR);
    }

    public static final Parcelable.Creator<ExchangeRatesResultEntity> CREATOR = new Parcelable.Creator<ExchangeRatesResultEntity>() {
        @Override
        public ExchangeRatesResultEntity createFromParcel(Parcel source) {
            return new ExchangeRatesResultEntity(source);
        }

        @Override
        public ExchangeRatesResultEntity[] newArray(int size) {
            return new ExchangeRatesResultEntity[size];
        }
    };
}
