package com.marekhakala.mynomadlifeapp.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ExchangeRateEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ImageResponseBodyEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Component.PriceValueHolder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

public class UtilityHelper {
    public static String getCursorString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndexOrThrow(column));
    }

    public static boolean getCursorBoolean(Cursor cursor, String column) {
        return getCursorInt(cursor, column) == 1;
    }

    public static long getCursorLong(Cursor cursor, String column) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(column));
    }

    public static int getCursorInt(Cursor cursor, String column) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(column));
    }

    public static Float getCursorFloat(Cursor cursor, String column) {
        return cursor.getFloat(cursor.getColumnIndexOrThrow(column));
    }

    public static byte[] getCursorBlob(Cursor cursor, String column) {
        return cursor.getBlob(cursor.getColumnIndexOrThrow(column));
    }

    public static String getCityImageUri(String baseUrl, CityEntity city) {
        return Uri.parse(baseUrl).buildUpon().appendEncodedPath(city.getSlug() + ConstantValues.IMAGE_URL_PATH).build().toString();
    }

    public static String getInternetSpeed(long internetDownload, Context context) {
        return Long.toString(internetDownload) + " " + context.getResources().getString(R.string.internet_speed_label);
    }

    public static String getTemperature(CityEntity city, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String prefsTemperatureUnit = prefs.getString(context.getString(R.string.settings_key_temperature_unit_type), "1");

        if(prefsTemperatureUnit.equals(context.getString(R.string.settings_temperature_celsius_unit_value)))
            return Float.toString(city.getTemperatureC())
                    + " " + context.getResources().getString(R.string.temperature_celsius_label);

        return Float.toString(city.getTemperatureF())
                + " " + context.getResources().getString(R.string.temperature_fahrenheit_label);
    }

    public static String getTemperature(CityOfflineEntity city, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String prefsTemperatureUnit = prefs.getString(context.getString(R.string.settings_key_temperature_unit_type), "1");

        if(prefsTemperatureUnit.equals(context.getString(R.string.settings_temperature_celsius_unit_value)))
            return Float.toString(city.getTemperatureC())
                    + " " + context.getResources().getString(R.string.temperature_celsius_label);

        return Float.toString(city.getTemperatureF())
                + " " + context.getResources().getString(R.string.temperature_fahrenheit_label);
    }

    public static String getHumidity(CityEntity city, Context context) {
        return Float.toString(city.getHumidity()) + context.getResources().getString(R.string.humidity_label);
    }

    public static String getHumidity(CityOfflineEntity city, Context context) {
        return Float.toString(city.getHumidity()) + context.getResources().getString(R.string.humidity_label);
    }

    public static String getMonthlyPrice(CityEntity city, Context context) {
        String preferredCurrency = ConstantValues.USD_CURRENCY;
        String currencySymbol = UtilityHelper.getCurrencies(context).get(preferredCurrency);

        PriceValueHolder priceValueHolder = new PriceValueHolder(city.getCostPerMonth().toString(),
                preferredCurrency, currencySymbol != null ? currencySymbol : "", PriceValueHolder.MONTHLY_PERIOD);
        return UtilityHelper.getPriceValueNormal(priceValueHolder, context);
    }

    public static String getMonthlyPrice(CityOfflineEntity city, Context context) {
        String preferredCurrency = ConstantValues.USD_CURRENCY;
        String currencySymbol = UtilityHelper.getCurrencies(context).get(preferredCurrency);

        PriceValueHolder priceValueHolder = new PriceValueHolder(city.getCostPerMonth().toString(),
                preferredCurrency, currencySymbol != null ? currencySymbol : "", PriceValueHolder.MONTHLY_PERIOD);
        return UtilityHelper.getPriceValueNormal(priceValueHolder, context);
    }

    public static int getIntegerFromDoublePercentage(Float value) {
        return Math.round(value*10);
    }

    public static Map<String, String> getCurrencies(Context context) {
        Map<String, String> CURRENCIES = new HashMap<>();

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

        return CURRENCIES;
    }

    public static String getPriceValueNormal(PriceValueHolder priceValueHolder, Context context) {
        DecimalFormat formatter = new DecimalFormat(context.getResources().getString(R.string.currency_format_value));
        return getPriceValue(priceValueHolder, formatter, context);
    }

    public static String getPriceValueExchange(PriceValueHolder priceValueHolder, Context context) {
        DecimalFormat formatter = new DecimalFormat(context.getResources().getString(R.string.currency_format_exchange_value_second));
        return getPriceValue(priceValueHolder, formatter, context);
    }

    private static String getPriceValue(PriceValueHolder priceValueHolder, DecimalFormat formatter, Context context) {
        String formattedString;
        String periodString;

        try {
            formattedString = formatter.format(Float.parseFloat(priceValueHolder.getValue()));
            periodString = setupPeriod(priceValueHolder.getValuePeriod(), context);
        } catch (NumberFormatException exception) {
            return "";
        }

        if(priceValueHolder.hasCurrencySymbol()) {
            return String.format(context.getResources().getString(R.string.currency_format),
                    priceValueHolder.getCurrencySymbol(), formattedString, periodString);
        } else {
            return String.format(context.getResources().getString(R.string.currency_format),
                    formattedString, " " + priceValueHolder.getCurrencyText(), periodString);
        }
    }

    public static String setupPeriod(String valuePeriod, Context context) {
        switch(valuePeriod) {
            case PriceValueHolder.DAILY_PERIOD:
                return context.getResources().getString(R.string.currency_format_daily);
            case PriceValueHolder.MONTHLY_PERIOD:
                return context.getResources().getString(R.string.currency_format_monthly);
            default:
                return PriceValueHolder.UNLIMITED_PERIOD_VALUE;
        }
    }

    public static CitiesResultEntity setupFavouriteOfflineItems(CitiesResultEntity result,
                                                                List<String> favouriteSlugs, List<String> offlineSlugs) {
        for(CityEntity city : result.getEntries()) {
            city.setFavourite(favouriteSlugs.contains(city.getSlug()));
            city.setOffline(offlineSlugs.contains(city.getSlug()));
        }

        return result;
    }

    public static List<CityEntity> setupFavouriteOfflineItems(List<CityEntity> cityEntities,
                                                              List<String> favouriteSlugs, List<String> offlineSlugs) {
         for(CityEntity city : cityEntities) {
             city.setFavourite(favouriteSlugs.contains(city.getSlug()));
            city.setOffline(offlineSlugs.contains(city.getSlug()));
        }

        return cityEntities;
    }

    public static List<CityOfflineEntity> setupOfflineFavouriteOfflineItems(List<CityOfflineEntity> cityEntities,
                                                                            List<String> favouriteSlugs, List<String> offlineSlugs) {
        for(CityOfflineEntity city : cityEntities) {
            city.setFavourite(favouriteSlugs.contains(city.getSlug()));
            city.setOffline(offlineSlugs.contains(city.getSlug()));
        }

        return cityEntities;
    }

    public static byte[] bitmapToByteArray(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 90, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    public static String getPopulation(Float population, Context context) {
        DecimalFormat formatter = new DecimalFormat(context.getString(R.string.currency_format_value));
        return formatter.format(population);
    }

    public static ImageResponseBodyEntity processImageFromApi(String slug, Response<ResponseBody> response) {
        ImageResponseBodyEntity imageResponseBodyEntity = new ImageResponseBodyEntity();

        imageResponseBodyEntity.setData(response.isSuccessful());
        imageResponseBodyEntity.setSlug(slug);

        if(response.isSuccessful()) {
            try {
                imageResponseBodyEntity.setImageData(response.body().bytes());
            } catch (IOException e) {
                imageResponseBodyEntity.setData(false);
                Timber.d("Error");
            }
        }

        return imageResponseBodyEntity;
    }

    public static int settingsStringToInt(SharedPreferences settings, String settingsKey, int defaultValue) {
        if(settings == null || settingsKey == null)
            return defaultValue;

        String value = settings.getString(settingsKey, String.valueOf(defaultValue));

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }

    public static Float settingsStringToFloat(SharedPreferences settings, String settingsKey, Float defaultValue) {
        if(settings == null || settingsKey == null)
            return defaultValue;

        String value = settings.getString(settingsKey, String.valueOf(defaultValue));

        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }

    public static Long settingsStringToLong(SharedPreferences settings, String settingsKey, Long defaultValue) {
        if(settings == null || settingsKey == null)
            return defaultValue;

        String value = settings.getString(settingsKey, String.valueOf(defaultValue));

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }

    public static String getWidgetExchangeRateTitle(Context context, ExchangeRateEntity exchangeRateItemObject) {
        return String.format(context.getString(R.string.exchange_rates_row_format_title),
                exchangeRateItemObject.getBaseCurrencyCode().toUpperCase(),
                exchangeRateItemObject.getCurrencyCode().toUpperCase());
    }

    public static String getWidgetExchangeRateValue(Context context, ExchangeRateEntity exchangeRateItemObject) {
        DecimalFormat formatter = new DecimalFormat(context.getString(R.string.currency_format_exchange_value_second));
        String formatedCurrencyRate = formatter.format(exchangeRateItemObject.getCurrencyRate());

        return String.format(context.getString(R.string.exchange_rates_row_format_value),
                formatedCurrencyRate, exchangeRateItemObject.getCurrencyCode().toUpperCase());
    }

    public static String getScreenNameForAnalytics(String section) {
        return ConstantValues.ACTIVITY_SECTION_TEXT + section;
    }
}
