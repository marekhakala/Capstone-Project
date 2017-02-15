package com.marekhakala.mynomadlifeapp.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;

import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ImageResponseBodyEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityFavouriteSlug;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOfflineImage;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOfflineSlug;
import com.marekhakala.mynomadlifeapp.Repository.RepositoryHelpers;
import com.marekhakala.mynomadlifeapp.UI.Component.PriceValueHolder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

public class UtilityHelper {

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
        String formattedString = formatter.format(Float.parseFloat(priceValueHolder.getValue()));
        String periodString = setupPeriod(priceValueHolder.getValuePeriod(), context);

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

    public static byte[] bitmapToByteArray(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 90, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    public static String getPopulation(Double population, Context context) {
        DecimalFormat formatter = new DecimalFormat(context.getString(R.string.currency_format_value));
        return formatter.format(population);
    }

    public static void setupImageFromDatabase(CityOfflineImage cityOfflineImage, CityOfflineEntity cityOfflineEntity) {
        Bitmap cityImage = UtilityHelper.byteArrayToBitmap(cityOfflineImage.getImageData());
        cityOfflineEntity.setBitmapImage(cityImage);
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

    public static List<Boolean> processImagesFromApi(Realm innerRealm, Object[] imageResponses) {
        List<Boolean> imageResults = new ArrayList<>();

        if(imageResponses != null) {
            for(Object responseObject : imageResponses) {
                ImageResponseBodyEntity imageResponseBodyEntity = (ImageResponseBodyEntity) responseObject;

                if(imageResponseBodyEntity != null && imageResponseBodyEntity.isData()) {
                    RepositoryHelpers.saveImageToDatabase(innerRealm, imageResponseBodyEntity.getSlug(),
                            BitmapFactory.decodeByteArray(imageResponseBodyEntity.getImageData(), 0,
                                    imageResponseBodyEntity.getImageData().length));
                    imageResults.add(true);
                }
                imageResults.add(false);
            }
        }

        UtilityHelper.closeDatabase(innerRealm);
        return imageResults;
    }

    public static List<ImageResponseBodyEntity> processImageFromDatabase(String citySlug,
                                                                         RealmResults<CityOfflineImage> cityOfflineImages) {
        List<ImageResponseBodyEntity> images = new ArrayList<>();

        for(CityOfflineImage image : cityOfflineImages) {
            ImageResponseBodyEntity imageResponseBodyEntity = new ImageResponseBodyEntity();
            imageResponseBodyEntity.setSlug(citySlug);

            if(image.getImageData().length > 0) {
                imageResponseBodyEntity.setData(true);
                imageResponseBodyEntity.setImageData(image.getImageData());
            } else
                imageResponseBodyEntity.setData(false);

            images.add(imageResponseBodyEntity);
        }

        return images;
    }


    public static String cityIdFactory(CityFavouriteSlug result) {
        return result.getSlug();
    }

    public static String cityIdFactory(CityOfflineSlug result) {
        return result.getSlug();
    }

    public static int getIntegerFromDoublePercentage(Double value) {
        return (int) Math.round(value*10);
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

    public static void closeDatabase(Realm realm) {
        if(realm != null && !realm.isClosed())
            realm.close();
    }
}
