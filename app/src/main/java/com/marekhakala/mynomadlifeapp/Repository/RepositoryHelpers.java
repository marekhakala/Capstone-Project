package com.marekhakala.mynomadlifeapp.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.marekhakala.mynomadlifeapp.DataModel.CityCostOfLivingEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflinePlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityScoresEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ExchangeRateEntity;
import com.marekhakala.mynomadlifeapp.Database.CitiesContract;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.CostPerMonthArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.InternetSpeedArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.OtherFiltersArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.PopulationArguments;
import com.marekhakala.mynomadlifeapp.UI.Activity.FilterActivity;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.util.List;

import timber.log.Timber;

public class RepositoryHelpers {

    public static CityEntity cityEntityFactory(Cursor cursor) {
        CityEntity cityEntity = new CityEntity();

        cityEntity.setSlug(UtilityHelper.getCursorString(cursor, CitiesContract.Cities.CITY_SLUG));
        cityEntity.setRegion(UtilityHelper.getCursorString(cursor, CitiesContract.Cities.CITY_REGION));
        cityEntity.setCountry(UtilityHelper.getCursorString(cursor, CitiesContract.Cities.CITY_COUNTRY));
        cityEntity.setTemperatureC(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_TEMPERATURE_C));
        cityEntity.setTemperatureF(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_TEMPERATURE_F));
        cityEntity.setHumidity(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_HUMIDITY));
        cityEntity.setRank(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_RANK));
        cityEntity.setCostPerMonth(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_PER_MONTH));
        cityEntity.setInternetSpeed(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_INTERNET_SPEED));
        cityEntity.setPopulation(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_POPULATION));
        cityEntity.setGenderRatio(UtilityHelper.getCursorString(cursor, CitiesContract.Cities.CITY_GENDER_RATIO));
        cityEntity.setReligious(UtilityHelper.getCursorString(cursor, CitiesContract.Cities.CITY_RELIGIOUS));
        cityEntity.setCityCurrency(UtilityHelper.getCursorString(cursor, CitiesContract.Cities.CITY_CURRENCY));
        cityEntity.setCityCurrencyRate(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_CURRENCY_RATE));
        cityEntity.setScores(scoresEntityFactory(cursor));
        cityEntity.setCostOfLiving(costOfLivingEntityFactory(cursor));
        cityEntity.setFavourite(false);

        return cityEntity;
    }

    public static CityEntity cityEntityFavouriteOfflineFactory(CityEntity cityEntity, List<String> favouriteSlugs, List<String> offlineSlugs) {
        if(favouriteSlugs.contains(cityEntity.getSlug()))
            cityEntity.setFavourite(true);
        else
            cityEntity.setFavourite(false);

        if(offlineSlugs.contains(cityEntity.getSlug()))
            cityEntity.setOffline(true);
        else
            cityEntity.setOffline(false);

        return cityEntity;
    }

    public static CityOfflineEntity cityOfflineEntityFactory(Cursor cursor) {
        CityOfflineEntity cityOfflineEntity = new CityOfflineEntity();

        cityOfflineEntity.setSlug(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOffline.CITY_SLUG));
        cityOfflineEntity.setRegion(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOffline.CITY_REGION));
        cityOfflineEntity.setCountry(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOffline.CITY_COUNTRY));
        cityOfflineEntity.setTemperatureC(UtilityHelper.getCursorInt(cursor, CitiesContract.CitiesOffline.CITY_TEMPERATURE_C));
        cityOfflineEntity.setTemperatureF(UtilityHelper.getCursorInt(cursor, CitiesContract.CitiesOffline.CITY_TEMPERATURE_F));
        cityOfflineEntity.setHumidity(UtilityHelper.getCursorInt(cursor, CitiesContract.CitiesOffline.CITY_HUMIDITY));
        cityOfflineEntity.setRank(UtilityHelper.getCursorInt(cursor, CitiesContract.CitiesOffline.CITY_RANK));
        cityOfflineEntity.setCostPerMonth(UtilityHelper.getCursorFloat(cursor, CitiesContract.CitiesOffline.CITY_COST_PER_MONTH));
        cityOfflineEntity.setInternetSpeed(UtilityHelper.getCursorInt(cursor, CitiesContract.CitiesOffline.CITY_INTERNET_SPEED));
        cityOfflineEntity.setPopulation(UtilityHelper.getCursorFloat(cursor, CitiesContract.CitiesOffline.CITY_POPULATION));
        cityOfflineEntity.setGenderRatio(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOffline.CITY_GENDER_RATIO));
        cityOfflineEntity.setReligious(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOffline.CITY_RELIGIOUS));
        cityOfflineEntity.setCityCurrency(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOffline.CITY_CURRENCY));
        cityOfflineEntity.setCityCurrencyRate(UtilityHelper.getCursorFloat(cursor, CitiesContract.CitiesOffline.CITY_CURRENCY_RATE));
        cityOfflineEntity.setScores(scoresEntityFactory(cursor));
        cityOfflineEntity.setCostOfLiving(costOfLivingEntityFactory(cursor));
        cityOfflineEntity.setFavourite(false);

        try {
            byte[] imageData = UtilityHelper.getCursorBlob(cursor, CitiesContract.CitiesOfflineImages.CITY_IMAGE_DATA);
            if (imageData != null && imageData.length > 0) {
                cityOfflineEntity.setBitmapImage(UtilityHelper.byteArrayToBitmap(imageData));
            }
        } catch (SQLiteException exception) {
            Timber.e("Data image error");
        }

        return cityOfflineEntity;
    }

    public static CityOfflineEntity cityOfflineEntityFavouriteOfflineFactory(CityOfflineEntity cityOfflineEntity,
                                                                             List<String> favouriteSlugs, List<String> offlineSlugs) {
        if(favouriteSlugs.contains(cityOfflineEntity.getSlug()))
            cityOfflineEntity.setFavourite(true);
        else
            cityOfflineEntity.setFavourite(false);

        if(offlineSlugs.contains(cityOfflineEntity.getSlug()))
            cityOfflineEntity.setOffline(true);
        else
            cityOfflineEntity.setOffline(false);

        return cityOfflineEntity;
    }

    public static CityScoresEntity scoresEntityFactory(Cursor cursor) {
        CityScoresEntity scoresEntity = new CityScoresEntity();

        scoresEntity.setNomadScore(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_SCORE_NOMAD_SCORE));
        scoresEntity.setLifeScore(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_SCORE_LIFE_SCORE));
        scoresEntity.setCost(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_COST));
        scoresEntity.setInternet(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_INTERNET));
        scoresEntity.setFun(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_FUN));
        scoresEntity.setSafety(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_SAFETY));
        scoresEntity.setPeace(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_PEACE));
        scoresEntity.setNightlife(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_NIGHTLIFE));
        scoresEntity.setFreeWifiInCity(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_FREE_WIFI_IN_CITY));
        scoresEntity.setPlacesToWork(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_PLACES_TO_WORK));
        scoresEntity.setAcOrHeating(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_AC_OR_HEATING));
        scoresEntity.setFriendlyToForeigners(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_FRIENDLY_TO_FOREIGNERS));
        scoresEntity.setFemaleFriendly(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_FEMALE_FRIENDLY));
        scoresEntity.setGayFriendly(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_GAY_FRIENDLY));
        scoresEntity.setStartupScore(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_SCORE_STARTUP_SCORE));
        scoresEntity.setEnglishSpeaking(UtilityHelper.getCursorInt(cursor, CitiesContract.Cities.CITY_SCORE_ENGLISH_SPEAKING));

        return scoresEntity;
    }

    public static CityCostOfLivingEntity costOfLivingEntityFactory(Cursor cursor) {
        CityCostOfLivingEntity costOfLivingEntity = new CityCostOfLivingEntity();

        costOfLivingEntity.setNomadCost(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_NOMAD_COST));
        costOfLivingEntity.setExpatCostOfLiving(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_EXPAT_COST_OF_LIVING));
        costOfLivingEntity.setLocalCostOfLiving(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_LOCAL_COST_OF_LIVING));
        costOfLivingEntity.setOneBedroomApartment(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_ONE_BEDROOM_APARTMENT));
        costOfLivingEntity.setHotelRoom(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_HOTEL_ROOM));
        costOfLivingEntity.setAirbnbApartmentMonth(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_MONTH));
        costOfLivingEntity.setAirbnbApartmentDay(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_DAY));
        costOfLivingEntity.setCoworkingSpace(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_COWORKING_SPACE));
        costOfLivingEntity.setCocaColaInCafe(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_COCA_COLA_IN_CAFE));
        costOfLivingEntity.setPintOfBeerInBar(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_PINT_OF_BEER_IN_BAR));
        costOfLivingEntity.setCappucinoInCafe(UtilityHelper.getCursorFloat(cursor, CitiesContract.Cities.CITY_COST_OF_LIVING_CAPPUCCINO_IN_CAFE));

        return costOfLivingEntity;
    }

    public static CityPlaceToWorkEntity cityPlaceToWorkEntityFactory(Cursor cursor) {
        CityPlaceToWorkEntity cityPlaceToWorkEntity = new CityPlaceToWorkEntity();

        cityPlaceToWorkEntity.setCitySlug(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_CITY_SLUG));
        cityPlaceToWorkEntity.setSlug(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_SLUG));
        cityPlaceToWorkEntity.setName(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_NAME));
        cityPlaceToWorkEntity.setSubName(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_SUBNAME));
        cityPlaceToWorkEntity.setCoworkingType(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_COWORKING_TYPE));
        cityPlaceToWorkEntity.setDistance(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_DISTANCE));
        cityPlaceToWorkEntity.setLat(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_LAT));
        cityPlaceToWorkEntity.setLng(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_LNG));
        cityPlaceToWorkEntity.setDataUrl(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_DATA_URL));
        cityPlaceToWorkEntity.setImageUrl(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_IMAGE_URL));

        return cityPlaceToWorkEntity;
    }

    public static CityOfflinePlaceToWorkEntity cityPlaceToWorkEntityToOffline(CityPlaceToWorkEntity cityPlaceToWorkEntity) {
        CityOfflinePlaceToWorkEntity cityOfflinePlaceToWorkEntity = new CityOfflinePlaceToWorkEntity();

        cityOfflinePlaceToWorkEntity.setCitySlug(cityPlaceToWorkEntity.getCitySlug());
        cityOfflinePlaceToWorkEntity.setSlug(cityPlaceToWorkEntity.getSlug());
        cityOfflinePlaceToWorkEntity.setName(cityPlaceToWorkEntity.getName());
        cityOfflinePlaceToWorkEntity.setSubName(cityPlaceToWorkEntity.getSubName());
        cityOfflinePlaceToWorkEntity.setCoworkingType(cityPlaceToWorkEntity.getCoworkingType());
        cityOfflinePlaceToWorkEntity.setDistance(cityPlaceToWorkEntity.getDistance());
        cityOfflinePlaceToWorkEntity.setLat(cityPlaceToWorkEntity.getLat());
        cityOfflinePlaceToWorkEntity.setLng(cityPlaceToWorkEntity.getLng());
        cityOfflinePlaceToWorkEntity.setDataUrl(cityPlaceToWorkEntity.getDataUrl());
        cityOfflinePlaceToWorkEntity.setImageUrl(cityPlaceToWorkEntity.getImageUrl());

        return cityOfflinePlaceToWorkEntity;
    }

    public static CityOfflinePlaceToWorkEntity cityOfflinePlaceToWorkEntityFactory(Cursor cursor) {
        CityOfflinePlaceToWorkEntity cityOfflinePlaceToWorkEntity = new CityOfflinePlaceToWorkEntity();

        cityOfflinePlaceToWorkEntity.setCitySlug(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_CITY_SLUG));
        cityOfflinePlaceToWorkEntity.setSlug(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_SLUG));
        cityOfflinePlaceToWorkEntity.setName(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_NAME));
        cityOfflinePlaceToWorkEntity.setSubName(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_SUBNAME));
        cityOfflinePlaceToWorkEntity.setCoworkingType(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_COWORKING_TYPE));
        cityOfflinePlaceToWorkEntity.setDistance(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_DISTANCE));
        cityOfflinePlaceToWorkEntity.setLat(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_LAT));
        cityOfflinePlaceToWorkEntity.setLng(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_LNG));
        cityOfflinePlaceToWorkEntity.setDataUrl(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_DATA_URL));
        cityOfflinePlaceToWorkEntity.setImageUrl(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_IMAGE_URL));

        return cityOfflinePlaceToWorkEntity;
    }

    public static ExchangeRateEntity exchangeRateEntityFactory(Cursor cursor) {
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();

        exchangeRateEntity.setCurrencyCode(UtilityHelper.getCursorString(cursor, CitiesContract.ExchangeRates.EXCHANGE_RATES_CURRENCY_CODE));
        exchangeRateEntity.setUpdateDate(UtilityHelper.getCursorString(cursor, CitiesContract.ExchangeRates.EXCHANGE_RATES_UPDATE_DATE));
        exchangeRateEntity.setBaseCurrencyCode(UtilityHelper.getCursorString(cursor, CitiesContract.ExchangeRates.EXCHANGE_RATES_BASE_CURRENCY_CODE));
        exchangeRateEntity.setCurrencyRate(UtilityHelper.getCursorFloat(cursor, CitiesContract.ExchangeRates.EXCHANGE_RATES_CURRENCY_RATE));

        return exchangeRateEntity;
    }

    public static CostPerMonthArguments prepareCostPerMonthArguments(Context context, SharedPreferences settings) {
        CostPerMonthArguments arguments = new CostPerMonthArguments();
        Integer cheapAffordableExpensiveState = settings.getInt(FilterActivity.FILTER_CHEAP_AFFORDABLE_EXPENSIVE_STATE, FilterActivity.FILTER_BUTTONS_NONE_STATE);

        if(cheapAffordableExpensiveState != -1) {
            String cheapCostOfLiving = context.getString(R.string.settings_key_cheap_cost_of_living_from);
            String affordableCostOfLiving = context.getApplicationContext().getString(R.string.settings_key_affordable_cost_of_living_from);
            String expensiveCostOfLiving = context.getApplicationContext().getString(R.string.settings_key_expensive_cost_of_living_from);

            if(cheapAffordableExpensiveState == 0) {
                arguments.setCostPerMonthTo(UtilityHelper.settingsStringToFloat(settings, cheapCostOfLiving,
                        Float.valueOf(context.getString(R.string.settings_cheap_cost_of_living_value))));
            } else if(cheapAffordableExpensiveState == 1) {
                arguments.setCostPerMonthTo(UtilityHelper.settingsStringToFloat(settings, affordableCostOfLiving,
                        Float.valueOf(context.getString(R.string.settings_affordable_cost_of_living_value))));
            } else if(cheapAffordableExpensiveState == 2) {
                arguments.setCostPerMonthFrom(UtilityHelper.settingsStringToFloat(settings, expensiveCostOfLiving,
                        Float.valueOf(context.getString(R.string.settings_expensive_cost_of_living_value))));
            }
        }
        return arguments;
    }

    public static PopulationArguments preparePopulationArguments(Context context, SharedPreferences settings) {
        PopulationArguments arguments = new PopulationArguments();
        Integer townBigCityMegaCityState = settings.getInt(FilterActivity.FILTER_TOWN_BIG_CITY_MEGA_CITY_STATE, FilterActivity.FILTER_BUTTONS_NONE_STATE);

        if(townBigCityMegaCityState != -1) {
            String townPopulation = context.getString(R.string.settings_key_town_population_from);
            String bigCityPopulation = context.getString(R.string.settings_key_big_city_population_from);
            String megaCityPopulation = context.getString(R.string.settings_key_mega_city_from);

            if(townBigCityMegaCityState == 0) {
                arguments.setPopulationTo(UtilityHelper.settingsStringToLong(settings, townPopulation,
                        Long.valueOf(context.getString(R.string.settings_town_population_value))));
            } else if(townBigCityMegaCityState == 1) {
                arguments.setPopulationTo(UtilityHelper.settingsStringToLong(settings, bigCityPopulation,
                        Long.valueOf(context.getString(R.string.settings_mega_city_population_value))));
            } else if(townBigCityMegaCityState == 2) {
                arguments.setPopulationFrom(UtilityHelper.settingsStringToLong(settings, megaCityPopulation,
                        Long.valueOf(context.getString(R.string.settings_big_city_population_value))));
            }
        }

        return arguments;
    }

    public static InternetSpeedArguments prepareInternetSpeedArguments(Context context, SharedPreferences settings) {
        InternetSpeedArguments arguments = new InternetSpeedArguments();
        Integer internetSlowGoodFastState = settings.getInt(FilterActivity.FILTER_INTERNET_SLOW_GOOD_FAST_CITY_STATE, FilterActivity.FILTER_BUTTONS_NONE_STATE);

        if(internetSlowGoodFastState != -1) {
            String slowInternetSpeed = context.getString(R.string.settings_key_slow_internet_from);
            String goodInternetSpeed = context.getString(R.string.settings_key_good_internet_from);
            String fastInternetSpeed = context.getString(R.string.settings_key_fast_internet_from);

            if(internetSlowGoodFastState == 0) {
                arguments.setInternetSpeedFrom(UtilityHelper.settingsStringToInt(settings, slowInternetSpeed,
                        Integer.valueOf(context.getString(R.string.settings_slow_internet_value))));
            } else if(internetSlowGoodFastState == 1) {
                arguments.setInternetSpeedFrom(UtilityHelper.settingsStringToInt(settings, goodInternetSpeed,
                        Integer.valueOf(context.getString(R.string.settings_good_internet_value))));
            } else if(internetSlowGoodFastState == 2) {
                arguments.setInternetSpeedFrom(UtilityHelper.settingsStringToInt(settings, fastInternetSpeed,
                        Integer.valueOf(context.getString(R.string.settings_fast_internet_value))));
            }
        }

        return arguments;
    }

    public static OtherFiltersArguments prepareOtherFiltersArguments(Context context, SharedPreferences settings) {
        OtherFiltersArguments arguments = new OtherFiltersArguments();

        if(settings.getBoolean(FilterActivity.FILTER_SAFE_STATE, false)) {
            String safeFrom = context.getString(R.string.settings_key_other_filters_safe_from);
            arguments.setSafetyFrom(UtilityHelper.settingsStringToInt(settings, safeFrom,
                    Integer.valueOf(context.getString(R.string.settings_safe_value))));
        }

        if(settings.getBoolean(FilterActivity.FILTER_NIGHTLIFE_STATE, false)) {
            String nightlifeFrom = context.getString(R.string.settings_key_other_filters_nightlife_from);
            arguments.setNightlifeFrom(UtilityHelper.settingsStringToInt(settings, nightlifeFrom,
                    Integer.valueOf(context.getString(R.string.settings_nightlife_value))));
        }

        if(settings.getBoolean(FilterActivity.FILTER_PLACES_TO_WORK_STATE, false)) {
            String placesToWorkFrom = context.getString(R.string.settings_key_other_filters_places_to_work_from);
            arguments.setPlacesToWorkFrom(UtilityHelper.settingsStringToInt(settings, placesToWorkFrom,
                    Integer.valueOf(context.getString(R.string.settings_places_to_work_value))));
        }

        if(settings.getBoolean(FilterActivity.FILTER_FUN_STATE, false)) {
            String funFrom = context.getString(R.string.settings_key_other_filters_fun_from);
            arguments.setFunFrom(UtilityHelper.settingsStringToInt(settings, funFrom,
                    Integer.valueOf(context.getString(R.string.settings_fun_value))));
        }

        if(settings.getBoolean(FilterActivity.FILTER_ENGLISH_SPEAKING_STATE, false)) {
            String englishSpeakingFrom = context.getString(R.string.settings_key_other_filters_english_speaking_from);
            arguments.setEnglishSpeakingFrom(UtilityHelper.settingsStringToInt(settings, englishSpeakingFrom,
                    Integer.valueOf(context.getString(R.string.settings_english_speaking_value))));
        }

        if(settings.getBoolean(FilterActivity.FILTER_STARTUP_SCORE_STATE, false)) {
            String startupScoreFrom = context.getString(R.string.settings_key_other_filters_startup_score_from);
            arguments.setStartupScoreFrom(UtilityHelper.settingsStringToFloat(settings, startupScoreFrom,
                    Float.valueOf(context.getString(R.string.settings_startup_score_value))));
        }

        if(settings.getBoolean(FilterActivity.FILTER_FRIENDLY_TO_FOREIGNERS_STATE, false)) {
            String friendlyToForeignersFrom = context.getString(R.string.settings_key_other_filters_friendly_to_foreigners_from);
            arguments.setFriendlyToForeignersFrom(UtilityHelper.settingsStringToInt(settings, friendlyToForeignersFrom,
                    Integer.valueOf(context.getString(R.string.settings_friendly_to_foreigners_value))));
        }

        if(settings.getBoolean(FilterActivity.FILTER_FEMALE_FRIENDLY_STATE, false)) {
            String femaleFriendlyFrom = context.getString(R.string.settings_key_other_filters_female_friendly_from);
            arguments.setFemaleFriendlyFrom(UtilityHelper.settingsStringToInt(settings, femaleFriendlyFrom,
                    Integer.valueOf(context.getString(R.string.settings_female_friendly_value))));
        }

        if(settings.getBoolean(FilterActivity.FILTER_GAY_FRIENDLY_STATE, false)) {
            String gayFriendlyFrom = context.getString(R.string.settings_key_other_filters_gay_friendly_from);
            arguments.setGayFriendlyFrom(UtilityHelper.settingsStringToInt(settings, gayFriendlyFrom,
                    Integer.valueOf(context.getString(R.string.settings_gay_friendly_value))));
        }

        return arguments;
    }
}
