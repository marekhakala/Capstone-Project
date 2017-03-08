package com.marekhakala.mynomadlifeapp.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.marekhakala.mynomadlifeapp.DataModel.CityCostOfLivingEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflinePlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityScoresEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.RealmDataModel.City;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityCostOfLiving;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOffline;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOfflineImage;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOfflinePlaceToWork;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityPlaceToWork;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityScores;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.CostPerMonthArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.InternetSpeedArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.OtherFiltersArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.PopulationArguments;
import com.marekhakala.mynomadlifeapp.UI.Activity.FilterActivity;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import io.realm.Realm;
import io.realm.RealmResults;

public class RepositoryHelpers {

    public static CityEntity cityEntityFactory(City result) {
        CityEntity cityEntity = new CityEntity();

        cityEntity.setSlug(result.getSlug());
        cityEntity.setRegion(result.getRegion());
        cityEntity.setCountry(result.getCountry());
        cityEntity.setTemperatureC(result.getTemperatureC());
        cityEntity.setTemperatureF(result.getTemperatureF());
        cityEntity.setHumidity(result.getHumidity());
        cityEntity.setRank(result.getRank());
        cityEntity.setCostPerMonth(result.getCostPerMonth());
        cityEntity.setInternetSpeed(result.getInternetSpeed());
        cityEntity.setPopulation(result.getPopulation());
        cityEntity.setGenderRatio(result.getGenderRatio());
        cityEntity.setReligious(result.getReligious());
        cityEntity.setCityCurrency(result.getCityCurrency());
        cityEntity.setCityCurrencyRate(result.getCityCurrencyRate());
        cityEntity.setScores(scoresEntityFactory(result.getScores()));
        cityEntity.setCostOfLiving(costOfLivingEntityFactory(result.getCostOfLiving()));
        cityEntity.setFavourite(result.getFavourite());

        return cityEntity;
    }

    public static CityOfflineEntity cityOfflineEntityFactory(CityOffline result) {
        CityOfflineEntity cityEntity = new CityOfflineEntity();

        cityEntity.setSlug(result.getSlug());
        cityEntity.setRegion(result.getRegion());
        cityEntity.setCountry(result.getCountry());
        cityEntity.setTemperatureC(result.getTemperatureC());
        cityEntity.setTemperatureF(result.getTemperatureF());
        cityEntity.setHumidity(result.getHumidity());
        cityEntity.setRank(result.getRank());
        cityEntity.setCostPerMonth(result.getCostPerMonth());
        cityEntity.setInternetSpeed(result.getInternetSpeed());
        cityEntity.setPopulation(result.getPopulation());
        cityEntity.setGenderRatio(result.getGenderRatio());
        cityEntity.setReligious(result.getReligious());
        cityEntity.setCityCurrency(result.getCityCurrency());
        cityEntity.setCityCurrencyRate(result.getCityCurrencyRate());
        cityEntity.setScores(scoresEntityFactory(result.getScores()));
        cityEntity.setCostOfLiving(costOfLivingEntityFactory(result.getCostOfLiving()));
        cityEntity.setFavourite(result.getFavourite());

        return cityEntity;
    }

    public static CityScoresEntity scoresEntityFactory(CityScores scores) {
        CityScoresEntity scoresEntity = new CityScoresEntity();

        scoresEntity.setNomadScore(scores.getNomadScore());
        scoresEntity.setLifeScore(scores.getLifeScore());
        scoresEntity.setCost(scores.getCost());
        scoresEntity.setInternet(scores.getInternet());
        scoresEntity.setFun(scores.getFun());
        scoresEntity.setSafety(scores.getSafety());
        scoresEntity.setPeace(scores.getPeace());
        scoresEntity.setNightlife(scores.getNightlife());
        scoresEntity.setFreeWifiInCity(scores.getFreeWifiInCity());
        scoresEntity.setPlacesToWork(scores.getPlacesToWork());
        scoresEntity.setAcOrHeating(scores.getAcOrHeating());
        scoresEntity.setFriendlyToForeigners(scores.getFriendlyToForeigners());
        scoresEntity.setFemaleFriendly(scores.getFemaleFriendly());
        scoresEntity.setGayFriendly(scores.getGayFriendly());
        scoresEntity.setStartupScore(scores.getStartupScore());
        scoresEntity.setEnglishSpeaking(scores.getEnglishSpeaking());

        return scoresEntity;
    }

    public static CityCostOfLivingEntity costOfLivingEntityFactory(CityCostOfLiving costOfLiving) {
        CityCostOfLivingEntity costOfLivingEntity = new CityCostOfLivingEntity();

        costOfLivingEntity.setNomadCost(costOfLiving.getNomadCost());
        costOfLivingEntity.setExpatCostOfLiving(costOfLiving.getExpatCostOfLiving());
        costOfLivingEntity.setLocalCostOfLiving(costOfLiving.getLocalCostOfLiving());
        costOfLivingEntity.setOneBedroomApartment(costOfLiving.getOneBedroomApartment());
        costOfLivingEntity.setHotelRoom(costOfLiving.getHotelRoom());
        costOfLivingEntity.setAirbnbApartmentMonth(costOfLiving.getAirbnbApartmentMonth());
        costOfLivingEntity.setAirbnbApartmentDay(costOfLiving.getAirbnbApartmentDay());
        costOfLivingEntity.setCoworkingSpace(costOfLiving.getCoworkingSpace());
        costOfLivingEntity.setCocaColaInCafe(costOfLiving.getCocaColaInCafe());
        costOfLivingEntity.setPintOfBeerInBar(costOfLiving.getPintOfBeerInBar());
        costOfLivingEntity.setCappucinoInCafe(costOfLiving.getCappucinoInCafe());

        return costOfLivingEntity;
    }

    public static City setupCity(Realm realm, CityEntity entity) {

        City city = realm.createObject(City.class);
        int nextID = (realm.where(City.class).max("id").intValue() + 1);
        city.setId(nextID);

        city.setSlug(entity.getSlug());
        city.setRegion(entity.getRegion());
        city.setCountry(entity.getCountry());
        city.setTemperatureC(entity.getTemperatureC());
        city.setTemperatureF(entity.getTemperatureF());
        city.setHumidity(entity.getHumidity());
        city.setRank(entity.getRank());
        city.setCostPerMonth(entity.getCostPerMonth());
        city.setInternetSpeed(entity.getInternetSpeed());
        city.setPopulation(entity.getPopulation());
        city.setGenderRatio(entity.getGenderRatio());
        city.setReligious(entity.getReligious());
        city.setCityCurrency(entity.getCityCurrency());
        city.setCityCurrencyRate(entity.getCityCurrencyRate());
        city.setScores(setupScores(realm, entity.getScores()));
        city.setCostOfLiving(setupCostOfLiving(realm, entity.getCostOfLiving()));
        city.setFavourite(entity.isFavourite());

        return city;
    }

    public static CityOffline setupOfflineCity(Realm realm, CityOfflineEntity entity) {

        CityOffline city = realm.createObject(CityOffline.class);
        int nextID = (realm.where(CityOffline.class).max("id").intValue() + 1);
        city.setId(nextID);

        city.setSlug(entity.getSlug());
        city.setRegion(entity.getRegion());
        city.setCountry(entity.getCountry());
        city.setTemperatureC(entity.getTemperatureC());
        city.setTemperatureF(entity.getTemperatureF());
        city.setHumidity(entity.getHumidity());
        city.setRank(entity.getRank());
        city.setCostPerMonth(entity.getCostPerMonth());
        city.setInternetSpeed(entity.getInternetSpeed());
        city.setPopulation(entity.getPopulation());
        city.setGenderRatio(entity.getGenderRatio());
        city.setReligious(entity.getReligious());
        city.setCityCurrency(entity.getCityCurrency());
        city.setCityCurrencyRate(entity.getCityCurrencyRate());
        city.setScores(setupScores(realm, entity.getScores()));
        city.setCostOfLiving(setupCostOfLiving(realm, entity.getCostOfLiving()));
        city.setFavourite(entity.isFavourite());

        return city;
    }

    public static CityScores setupScores(Realm realm, CityScoresEntity entity) {

        CityScores scores = realm.createObject(CityScores.class);
        int nextID = (realm.where(CityScores.class).max("id").intValue() + 1);
        scores.setId(nextID);

        scores.setNomadScore(entity.getNomadScore());
        scores.setLifeScore(entity.getLifeScore());
        scores.setCost(entity.getCost());
        scores.setInternet(entity.getInternet());
        scores.setFun(entity.getFun());
        scores.setSafety(entity.getSafety());
        scores.setPeace(entity.getPeace());
        scores.setNightlife(entity.getNightlife());
        scores.setFreeWifiInCity(entity.getFreeWifiInCity());
        scores.setPlacesToWork(entity.getPlacesToWork());
        scores.setAcOrHeating(entity.getAcOrHeating());
        scores.setFriendlyToForeigners(entity.getFriendlyToForeigners());
        scores.setFemaleFriendly(entity.getFemaleFriendly());
        scores.setGayFriendly(entity.getGayFriendly());
        scores.setStartupScore(entity.getStartupScore());
        scores.setEnglishSpeaking(entity.getEnglishSpeaking());

        return scores;
    }

    public static CityCostOfLiving setupCostOfLiving(Realm realm, CityCostOfLivingEntity entity) {
        CityCostOfLiving cost = realm.createObject(CityCostOfLiving.class);
        int nextID = (realm.where(CityCostOfLiving.class).max("id").intValue() + 1);
        cost.setId(nextID);

        cost.setNomadCost(entity.getNomadCost());
        cost.setExpatCostOfLiving(entity.getExpatCostOfLiving());
        cost.setLocalCostOfLiving(entity.getLocalCostOfLiving());
        cost.setOneBedroomApartment(entity.getOneBedroomApartment());
        cost.setHotelRoom(entity.getHotelRoom());
        cost.setAirbnbApartmentMonth(entity.getAirbnbApartmentMonth());
        cost.setAirbnbApartmentDay(entity.getAirbnbApartmentDay());
        cost.setCoworkingSpace(entity.getCoworkingSpace());
        cost.setCocaColaInCafe(entity.getCocaColaInCafe());
        cost.setPintOfBeerInBar(entity.getPintOfBeerInBar());
        cost.setCappucinoInCafe(entity.getCappucinoInCafe());

        return cost;
    }

    public static CityPlaceToWorkEntity cityPlaceToWorkEntityFactory(CityPlaceToWork cityPlaceToWork) {
        CityPlaceToWorkEntity cityPlaceToWorkEntity = new CityPlaceToWorkEntity();

        cityPlaceToWorkEntity.setCitySlug(cityPlaceToWork.getCitySlug());
        cityPlaceToWorkEntity.setSlug(cityPlaceToWork.getSlug());
        cityPlaceToWorkEntity.setName(cityPlaceToWork.getName());
        cityPlaceToWorkEntity.setSubName(cityPlaceToWork.getSubName());
        cityPlaceToWorkEntity.setCoworkingType(cityPlaceToWork.getCoworkingType());
        cityPlaceToWorkEntity.setDistance(cityPlaceToWork.getDistance());
        cityPlaceToWorkEntity.setLat(cityPlaceToWork.getLat());
        cityPlaceToWorkEntity.setLng(cityPlaceToWork.getLng());
        cityPlaceToWorkEntity.setDataUrl(cityPlaceToWork.getDataUrl());
        cityPlaceToWorkEntity.setImageUrl(cityPlaceToWork.getImageUrl());

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

    public static CityOfflinePlaceToWorkEntity cityOfflinePlaceToWorkEntityFactory(CityOfflinePlaceToWork cityOfflinePlaceToWork) {

        CityOfflinePlaceToWorkEntity cityOfflinePlaceToWorkEntity = new CityOfflinePlaceToWorkEntity();

        cityOfflinePlaceToWorkEntity.setCitySlug(cityOfflinePlaceToWork.getCitySlug());
        cityOfflinePlaceToWorkEntity.setSlug(cityOfflinePlaceToWork.getSlug());
        cityOfflinePlaceToWorkEntity.setName(cityOfflinePlaceToWork.getName());
        cityOfflinePlaceToWorkEntity.setSubName(cityOfflinePlaceToWork.getSubName());
        cityOfflinePlaceToWorkEntity.setCoworkingType(cityOfflinePlaceToWork.getCoworkingType());
        cityOfflinePlaceToWorkEntity.setDistance(cityOfflinePlaceToWork.getDistance());
        cityOfflinePlaceToWorkEntity.setLat(cityOfflinePlaceToWork.getLat());
        cityOfflinePlaceToWorkEntity.setLng(cityOfflinePlaceToWork.getLng());
        cityOfflinePlaceToWorkEntity.setDataUrl(cityOfflinePlaceToWork.getDataUrl());
        cityOfflinePlaceToWorkEntity.setImageUrl(cityOfflinePlaceToWork.getImageUrl());

        return cityOfflinePlaceToWorkEntity;
    }

    public static CityPlaceToWork setupCityPlaceToWork(Realm realm, CityPlaceToWorkEntity cityPlaceToWorkEntity) {
        CityPlaceToWork cityOfflinePlaceToWork = realm.createObject(CityPlaceToWork.class);
        int nextID = (realm.where(CityPlaceToWork.class).max("id").intValue() + 1);
        cityOfflinePlaceToWork.setId(nextID);

        cityOfflinePlaceToWork.setCitySlug(cityPlaceToWorkEntity.getCitySlug());
        cityOfflinePlaceToWork.setSlug(cityPlaceToWorkEntity.getSlug());
        cityOfflinePlaceToWork.setName(cityPlaceToWorkEntity.getName());
        cityOfflinePlaceToWork.setSubName(cityPlaceToWorkEntity.getSubName());
        cityOfflinePlaceToWork.setCoworkingType(cityPlaceToWorkEntity.getCoworkingType());
        cityOfflinePlaceToWork.setDistance(cityPlaceToWorkEntity.getDistance());
        cityOfflinePlaceToWork.setLat(cityPlaceToWorkEntity.getLat());
        cityOfflinePlaceToWork.setLng(cityPlaceToWorkEntity.getLng());
        cityOfflinePlaceToWork.setDataUrl(cityPlaceToWorkEntity.getDataUrl());
        cityOfflinePlaceToWork.setImageUrl(cityPlaceToWorkEntity.getImageUrl());

        return cityOfflinePlaceToWork;
    }

    public static CityOfflinePlaceToWork setupCityOfflinePlaceToWork(Realm realm, CityOfflinePlaceToWorkEntity cityOfflinePlaceToWorkEntity) {
        CityOfflinePlaceToWork cityOfflinePlaceToWork = realm.createObject(CityOfflinePlaceToWork.class);
        int nextID = (realm.where(CityOfflinePlaceToWork.class).max("id").intValue() + 1);
        cityOfflinePlaceToWork.setId(nextID);

        cityOfflinePlaceToWork.setCitySlug(cityOfflinePlaceToWorkEntity.getCitySlug());
        cityOfflinePlaceToWork.setSlug(cityOfflinePlaceToWorkEntity.getSlug());
        cityOfflinePlaceToWork.setName(cityOfflinePlaceToWorkEntity.getName());
        cityOfflinePlaceToWork.setSubName(cityOfflinePlaceToWorkEntity.getSubName());
        cityOfflinePlaceToWork.setCoworkingType(cityOfflinePlaceToWorkEntity.getCoworkingType());
        cityOfflinePlaceToWork.setDistance(cityOfflinePlaceToWorkEntity.getDistance());
        cityOfflinePlaceToWork.setLat(cityOfflinePlaceToWorkEntity.getLat());
        cityOfflinePlaceToWork.setLng(cityOfflinePlaceToWorkEntity.getLng());
        cityOfflinePlaceToWork.setDataUrl(cityOfflinePlaceToWorkEntity.getDataUrl());
        cityOfflinePlaceToWork.setImageUrl(cityOfflinePlaceToWorkEntity.getImageUrl());

        return cityOfflinePlaceToWork;
    }

    public static CityOfflinePlaceToWork setupCityOfflinePlaceToWork(Realm realm, CityPlaceToWorkEntity cityPlaceToWorkEntity) {
        CityOfflinePlaceToWork cityOfflinePlaceToWork = realm.createObject(CityOfflinePlaceToWork.class);
        int nextID = (realm.where(CityOfflinePlaceToWork.class).max("id").intValue() + 1);
        cityOfflinePlaceToWork.setId(nextID);

        cityOfflinePlaceToWork.setCitySlug(cityPlaceToWorkEntity.getCitySlug());
        cityOfflinePlaceToWork.setSlug(cityPlaceToWorkEntity.getSlug());
        cityOfflinePlaceToWork.setName(cityPlaceToWorkEntity.getName());
        cityOfflinePlaceToWork.setSubName(cityPlaceToWorkEntity.getSubName());
        cityOfflinePlaceToWork.setCoworkingType(cityPlaceToWorkEntity.getCoworkingType());
        cityOfflinePlaceToWork.setDistance(cityPlaceToWorkEntity.getDistance());
        cityOfflinePlaceToWork.setLat(cityPlaceToWorkEntity.getLat());
        cityOfflinePlaceToWork.setLng(cityPlaceToWorkEntity.getLng());
        cityOfflinePlaceToWork.setDataUrl(cityPlaceToWorkEntity.getDataUrl());
        cityOfflinePlaceToWork.setImageUrl(cityPlaceToWorkEntity.getImageUrl());

        return cityOfflinePlaceToWork;
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

    public static void setupImage(Realm realm, CityOfflineEntity entity) {
        if(entity == null)
            return;

        RealmResults<CityOfflineImage> citiesImages = realm.where(CityOfflineImage.class).equalTo(ConstantValues.SLUG_COLUMN_NAME, entity.getSlug()).findAll();

        if(citiesImages.size() > 0) {
            CityOfflineImage cityOfflineImage = citiesImages.first();
            Bitmap cityBitmapImage = UtilityHelper.byteArrayToBitmap(cityOfflineImage.getImageData());
            entity.setBitmapImage(cityBitmapImage);
        }
    }

    public static void saveImageToDatabase(Realm realm, String slug, Bitmap bitmap) {
        realm.beginTransaction();

        CityOfflineImage cityOfflineImage = realm.createObject(CityOfflineImage.class);
        int nextID = (realm.where(CityOfflineImage.class).max(ConstantValues.ID_COLUMN_NAME).intValue() + 1);
        cityOfflineImage.setId(nextID);
        cityOfflineImage.setSlug(slug);
        cityOfflineImage.setImageData(UtilityHelper.bitmapToByteArray(bitmap));

        realm.commitTransaction();
    }
}
