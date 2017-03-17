package com.marekhakala.mynomadlifeapp.DataModel;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marekhakala.mynomadlifeapp.Database.CitiesContract;

public class ExchangeRateEntity implements Parcelable {

    private int id;

    @Expose
    @SerializedName("currency_code")
    private String currencyCode;

    @Expose
    @SerializedName("update_date")
    private String updateDate;

    @Expose
    @SerializedName("base")
    private String baseCurrencyCode;

    @Expose
    @SerializedName("currency_rate")
    private Float currencyRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public Float getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(Float currencyRate) {
        this.currencyRate = currencyRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.currencyCode);
        dest.writeString(this.updateDate);
        dest.writeString(this.baseCurrencyCode);
        dest.writeValue(this.currencyRate);
    }

    public ExchangeRateEntity() {
    }

    protected ExchangeRateEntity(Parcel in) {
        this.id = in.readInt();
        this.currencyCode = in.readString();
        this.updateDate = in.readString();
        this.baseCurrencyCode = in.readString();
        this.currencyRate = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Creator<ExchangeRateEntity> CREATOR = new Creator<ExchangeRateEntity>() {
        @Override
        public ExchangeRateEntity createFromParcel(Parcel source) {
            return new ExchangeRateEntity(source);
        }

        @Override
        public ExchangeRateEntity[] newArray(int size) {
            return new ExchangeRateEntity[size];
        }
    };

    public ContentValues getValues() {
        ContentValues values = new ContentValues();

        values.put(CitiesContract.ExchangeRates.EXCHANGE_RATES_CURRENCY_CODE, this.currencyCode);
        values.put(CitiesContract.ExchangeRates.EXCHANGE_RATES_UPDATE_DATE, this.updateDate);
        values.put(CitiesContract.ExchangeRates.EXCHANGE_RATES_BASE_CURRENCY_CODE, this.baseCurrencyCode);
        values.put(CitiesContract.ExchangeRates.EXCHANGE_RATES_CURRENCY_RATE, this.currencyRate);

        return values;
    }
}

