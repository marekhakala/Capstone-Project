package com.marekhakala.mynomadlifeapp.UI.Component;

import android.os.Parcel;
import android.os.Parcelable;

public class PriceValueHolder implements Parcelable {
    private String value;
    private String currencyText;
    private String currencySymbol = "";
    private String valuePeriod;

    public static final String DAILY_PERIOD = "daily";
    public static final String MONTHLY_PERIOD = "monthly";
    public static final String UNLIMITED_PERIOD_VALUE = "";

    public PriceValueHolder(String value, String currencyText) {
        this.value = value;
        this.currencyText = currencyText;
        this.valuePeriod = "";
    }

    public PriceValueHolder(String value, String currencyText, String currencySymbol) {
        this.value = value;
        this.currencyText = currencyText;
        this.currencySymbol = currencySymbol;
        this.valuePeriod = "";
    }

    public PriceValueHolder(String value, String currencyText, String currencySymbol, String valuePeriod) {
        this.value = value;
        this.currencyText = currencyText;
        this.currencySymbol = currencySymbol;
        this.valuePeriod = (valuePeriod.toLowerCase().equals(DAILY_PERIOD) || valuePeriod.toLowerCase().equals(MONTHLY_PERIOD)) ? valuePeriod : "";
    }

    protected PriceValueHolder(Parcel in) {
        value = in.readString();
        currencyText = in.readString();
        currencySymbol = in.readString();
        valuePeriod = in.readString();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrencyText() {
        return currencyText;
    }

    public void setCurrencyText(String currencyText) {
        this.currencyText = currencyText;
    }

    public String getCurrencySymbol() {
        return this.currencySymbol;
    }

    public void setCurrencySymbol(String value) {
        this.currencySymbol = value;
    }

    public boolean hasCurrencySymbol() {
        return (this.currencySymbol != null);
    }

    public String getValuePeriod() {
        return valuePeriod;
    }

    public void setValuePeriod(String valuePeriod) {
        this.valuePeriod = valuePeriod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeString(currencyText);
        dest.writeString(currencySymbol);
        dest.writeString(valuePeriod);
    }

    public static final Creator<PriceValueHolder> CREATOR = new Creator<PriceValueHolder>() {
        @Override
        public PriceValueHolder createFromParcel(Parcel in) {
            return new PriceValueHolder(in);
        }

        @Override
        public PriceValueHolder[] newArray(int size) {
            return new PriceValueHolder[size];
        }
    };
}