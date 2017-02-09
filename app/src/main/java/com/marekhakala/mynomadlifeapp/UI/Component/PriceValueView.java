package com.marekhakala.mynomadlifeapp.UI.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.support.percent.PercentRelativeLayout;
import android.widget.TextView;

import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.util.HashMap;
import java.util.Map;

public class PriceValueView extends PercentRelativeLayout {

    private TextView firstTextView;
    private TextView secondTextView;

    private PriceValueHolder firstCurrencyPrice;
    private PriceValueHolder secondCurrencyPrice;

    private boolean isExchangeRate = false;

    public Map<String, String> CURRENCIES = new HashMap<>();

    public PriceValueView(Context context) {
        super(context);
        init(context, null);
    }

    public PriceValueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PriceValueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.price_value_view, this);
        this.firstTextView = (TextView) findViewById(R.id.price_value_first_text);
        this.secondTextView = (TextView) findViewById(R.id.price_value_second_text);

        CURRENCIES.put(context.getString(R.string.currency_usd_code),
                context.getString(R.string.currency_usd_symbol));
        CURRENCIES.put(context.getString(R.string.currency_eur_code),
                context.getString(R.string.currency_eur_symbol));
        CURRENCIES.put(context.getString(R.string.currency_gbp_code),
                context.getString(R.string.currency_gbp_symbol));
        CURRENCIES.put(context.getString(R.string.currency_cad_code),
                context.getString(R.string.currency_cad_symbol));
        CURRENCIES.put(context.getString(R.string.currency_aud_code),
                context.getString(R.string.currency_aud_symbol));
        CURRENCIES.put(context.getString(R.string.currency_cny_code),
                context.getString(R.string.currency_cny_symbol));

        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PriceValueView);

        this.firstCurrencyPrice = setupPriceValueHolder(typedArray.getString(R.styleable.PriceValueView_firstCurrencyText),
                                                    typedArray.getString(R.styleable.PriceValueView_firstCurrencyValue),
                                                    typedArray.getString(R.styleable.PriceValueView_firstCurrencyPeriod));

        this.secondCurrencyPrice = setupPriceValueHolder(typedArray.getString(R.styleable.PriceValueView_secondCurrencyText),
                                                    typedArray.getString(R.styleable.PriceValueView_secondCurrencyValue),
                                                    typedArray.getString(R.styleable.PriceValueView_secondCurrencyPeriod));

        setupFirstPriceHolder(this.firstCurrencyPrice);
        setupSecondPriceHolder(this.secondCurrencyPrice);
        typedArray.recycle();
    }


    public boolean isExchangeRate() {
        return isExchangeRate;
    }

    public void setExchangeRate(boolean exchangeRate) {
        isExchangeRate = exchangeRate;
    }

    private PriceValueHolder setupPriceValueHolder(String currencyText, String currencyValue, String currencyPeriod) {
        if(currencyPeriod == null)
            currencyPeriod = "";

        String currencySymbol = CURRENCIES.get(currencyText);
        return new PriceValueHolder(currencyValue, currencyText, currencySymbol, currencyPeriod);
    }

    public void setupFirstPriceHolder(PriceValueHolder priceValueHolder) {
        setPriceText(priceValueHolder, this.firstTextView);
    }

    public void setupSecondPriceHolder(PriceValueHolder priceValueHolder) {
        setPriceText(priceValueHolder, this.secondTextView);
    }

    private void setPriceText(PriceValueHolder priceValueHolder, TextView view) {

        if(priceValueHolder.getValue() == null || priceValueHolder.getValue().isEmpty()) {
            view.setText("");
            return;
        }

        if(isExchangeRate)
            view.setText(UtilityHelper.getPriceValueExchange(priceValueHolder, getContext()));
        else
            view.setText(UtilityHelper.getPriceValueNormal(priceValueHolder, getContext()));
    }

    public TextView getFirstTextView() {
        return this.firstTextView;
    }

    public String getFirstValue() {
        return this.firstCurrencyPrice.getValue();
    }

    public void setFirstValue(String value) {
        this.firstCurrencyPrice.setValue(value);
        setPriceText(this.firstCurrencyPrice, this.firstTextView);
    }

    public String getFirstCurrencyText() {
        return this.firstCurrencyPrice.getCurrencyText();
    }

    public void setFirstCurrencyText(String currencyText) {
        String currencySymbol = CURRENCIES.get(currencyText);

        if(currencySymbol != null)
            this.firstCurrencyPrice.setCurrencySymbol(currencySymbol);
        else
            this.firstCurrencyPrice.setCurrencySymbol(null);

        this.firstCurrencyPrice.setCurrencyText(currencyText);
        setPriceText(this.firstCurrencyPrice, this.firstTextView);
    }

    public String getFirstCurrencySymbol() {
        return this.firstCurrencyPrice.getCurrencySymbol();
    }

    public void setFirstCurrencySymbol(String value) {
        this.firstCurrencyPrice.setCurrencySymbol(value);
        setPriceText(this.firstCurrencyPrice, this.firstTextView);
    }

    public TextView getSecondTextView() {
        return this.secondTextView;
    }

    public String getSecondValue() {
        return this.secondCurrencyPrice.getValue();
    }

    public void setSecondValue(String value) {
        this.secondCurrencyPrice.setValue(value);
        setPriceText(this.secondCurrencyPrice, this.secondTextView);
    }

    public String getSecondCurrencyText() {
        return this.secondCurrencyPrice.getCurrencyText();
    }

    public void setSecondCurrencyText(String currencyText) {
        String currencySymbol = CURRENCIES.get(currencyText);

        if(currencySymbol != null)
            this.secondCurrencyPrice.setCurrencySymbol(currencySymbol);
        else
            this.secondCurrencyPrice.setCurrencySymbol(null);

        this.secondCurrencyPrice.setCurrencyText(currencyText);
        setPriceText(this.secondCurrencyPrice, this.secondTextView);
    }

    public String getSecondCurrencySymbol() {
        return this.secondCurrencyPrice.getCurrencySymbol();
    }

    public void setSecondCurrencySymbol(String value) {
        this.secondCurrencyPrice.setCurrencySymbol(value);
        setPriceText(this.secondCurrencyPrice, this.secondTextView);
    }
}