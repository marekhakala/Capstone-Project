package com.marekhakala.mynomadlifeapp.Database;

public class CitiesProjection {
    private CitiesProjection() {}

    public static final String[] CITIES_PROJECTION = {
        CitiesContract.Cities._ID,
        CitiesContract.Cities.CITY_SLUG,
        CitiesContract.Cities.CITY_REGION,
        CitiesContract.Cities.CITY_COUNTRY,
        CitiesContract.Cities.CITY_TEMPERATURE_C,
        CitiesContract.Cities.CITY_TEMPERATURE_F,
        CitiesContract.Cities.CITY_HUMIDITY,
        CitiesContract.Cities.CITY_RANK,
        CitiesContract.Cities.CITY_COST_PER_MONTH,
        CitiesContract.Cities.CITY_INTERNET_SPEED,
        CitiesContract.Cities.CITY_POPULATION,
        CitiesContract.Cities.CITY_GENDER_RATIO,
        CitiesContract.Cities.CITY_RELIGIOUS,
        CitiesContract.Cities.CITY_CURRENCY,
        CitiesContract.Cities.CITY_CURRENCY_RATE,
        CitiesContract.Cities.CITY_SCORE_NOMAD_SCORE,
        CitiesContract.Cities.CITY_SCORE_LIFE_SCORE,
        CitiesContract.Cities.CITY_SCORE_COST,
        CitiesContract.Cities.CITY_SCORE_INTERNET,
        CitiesContract.Cities.CITY_SCORE_FUN,
        CitiesContract.Cities.CITY_SCORE_SAFETY,
        CitiesContract.Cities.CITY_SCORE_PEACE,
        CitiesContract.Cities.CITY_SCORE_NIGHTLIFE,
        CitiesContract.Cities.CITY_SCORE_FREE_WIFI_IN_CITY,
        CitiesContract.Cities.CITY_SCORE_PLACES_TO_WORK,
        CitiesContract.Cities.CITY_SCORE_AC_OR_HEATING,
        CitiesContract.Cities.CITY_SCORE_FRIENDLY_TO_FOREIGNERS,
        CitiesContract.Cities.CITY_SCORE_FEMALE_FRIENDLY,
        CitiesContract.Cities.CITY_SCORE_GAY_FRIENDLY,
        CitiesContract.Cities.CITY_SCORE_STARTUP_SCORE,
        CitiesContract.Cities.CITY_SCORE_ENGLISH_SPEAKING,
        CitiesContract.Cities.CITY_COST_OF_LIVING_NOMAD_COST,
        CitiesContract.Cities.CITY_COST_OF_LIVING_EXPAT_COST_OF_LIVING,
        CitiesContract.Cities.CITY_COST_OF_LIVING_LOCAL_COST_OF_LIVING,
        CitiesContract.Cities.CITY_COST_OF_LIVING_ONE_BEDROOM_APARTMENT,
        CitiesContract.Cities.CITY_COST_OF_LIVING_HOTEL_ROOM,
        CitiesContract.Cities.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_MONTH,
        CitiesContract.Cities.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_DAY,
        CitiesContract.Cities.CITY_COST_OF_LIVING_COWORKING_SPACE,
        CitiesContract.Cities.CITY_COST_OF_LIVING_COCA_COLA_IN_CAFE,
        CitiesContract.Cities.CITY_COST_OF_LIVING_PINT_OF_BEER_IN_BAR,
        CitiesContract.Cities.CITY_COST_OF_LIVING_CAPPUCCINO_IN_CAFE
    };

    public static String generateCityOfflineSqlQuery() {
        String columns = "";
        String citySlug = CitiesDatabase.Tables.CITIES_OFFLINE + "." + CitiesContract.Cities.CITY_SLUG;
        String imageSlug = CitiesDatabase.Tables.CITIES_OFFLINE_IMAGES + "." + CitiesContract.CitiesOfflineImagesColumns.CITY_IMAGE_SLUG;

        for(int i = 0; i < CITIES_OFFLINE_PROJECTION.length; i++) {
            columns += CitiesDatabase.Tables.CITIES_OFFLINE + "." + CITIES_OFFLINE_PROJECTION[i] + " AS " + CITIES_OFFLINE_PROJECTION[i];

            if(i != (CITIES_OFFLINE_PROJECTION.length-1))
                columns += ",";
        }

        String imageDataColumn =  CitiesDatabase.Tables.CITIES_OFFLINE_IMAGES + "." + CitiesContract.CitiesOfflineImagesColumns.CITY_IMAGE_DATA;
        columns += ", " + imageSlug + ", " + imageDataColumn + " AS " + CitiesContract.CitiesOfflineImagesColumns.CITY_IMAGE_DATA;
        return "SELECT " + columns + " FROM " + CitiesDatabase.Tables.CITIES_OFFLINE + " LEFT JOIN " + CitiesDatabase.Tables.CITIES_OFFLINE_IMAGES + " ON " + citySlug + " = " + imageSlug;
    }

    public static final String[] CITIES_PLACES_TO_WORK_PROJECTION = {
        CitiesContract.CitiesPlacesToWork._ID,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_SLUG,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_CITY_SLUG,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_NAME,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_SUBNAME,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_COWORKING_TYPE,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_DISTANCE,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_LAT,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_LNG,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_DATA_URL,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_IMAGE_URL
    };

    public static final String[] CITIES_OFFLINE_PROJECTION = {
        CitiesContract.CitiesOffline._ID,
        CitiesContract.CitiesOffline.CITY_SLUG,
        CitiesContract.CitiesOffline.CITY_REGION,
        CitiesContract.CitiesOffline.CITY_COUNTRY,
        CitiesContract.CitiesOffline.CITY_TEMPERATURE_C,
        CitiesContract.CitiesOffline.CITY_TEMPERATURE_F,
        CitiesContract.CitiesOffline.CITY_HUMIDITY,
        CitiesContract.CitiesOffline.CITY_RANK,
        CitiesContract.CitiesOffline.CITY_COST_PER_MONTH,
        CitiesContract.CitiesOffline.CITY_INTERNET_SPEED,
        CitiesContract.CitiesOffline.CITY_POPULATION,
        CitiesContract.CitiesOffline.CITY_GENDER_RATIO,
        CitiesContract.CitiesOffline.CITY_RELIGIOUS,
        CitiesContract.CitiesOffline.CITY_CURRENCY,
        CitiesContract.CitiesOffline.CITY_CURRENCY_RATE,
        CitiesContract.CitiesOffline.CITY_SCORE_NOMAD_SCORE,
        CitiesContract.CitiesOffline.CITY_SCORE_LIFE_SCORE,
        CitiesContract.CitiesOffline.CITY_SCORE_COST,
        CitiesContract.CitiesOffline.CITY_SCORE_INTERNET,
        CitiesContract.CitiesOffline.CITY_SCORE_FUN,
        CitiesContract.CitiesOffline.CITY_SCORE_SAFETY,
        CitiesContract.CitiesOffline.CITY_SCORE_PEACE,
        CitiesContract.CitiesOffline.CITY_SCORE_NIGHTLIFE,
        CitiesContract.CitiesOffline.CITY_SCORE_FREE_WIFI_IN_CITY,
        CitiesContract.CitiesOffline.CITY_SCORE_PLACES_TO_WORK,
        CitiesContract.CitiesOffline.CITY_SCORE_AC_OR_HEATING,
        CitiesContract.CitiesOffline.CITY_SCORE_FRIENDLY_TO_FOREIGNERS,
        CitiesContract.CitiesOffline.CITY_SCORE_FEMALE_FRIENDLY,
        CitiesContract.CitiesOffline.CITY_SCORE_GAY_FRIENDLY,
        CitiesContract.CitiesOffline.CITY_SCORE_STARTUP_SCORE,
        CitiesContract.CitiesOffline.CITY_SCORE_ENGLISH_SPEAKING,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_NOMAD_COST,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_EXPAT_COST_OF_LIVING,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_LOCAL_COST_OF_LIVING,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_ONE_BEDROOM_APARTMENT,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_HOTEL_ROOM,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_MONTH,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_DAY,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_COWORKING_SPACE,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_COCA_COLA_IN_CAFE,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_PINT_OF_BEER_IN_BAR,
        CitiesContract.CitiesOffline.CITY_COST_OF_LIVING_CAPPUCCINO_IN_CAFE
    };

    public static final String[] CITIES_OFFLINE_IMAGES_PROJECTION = {
        CitiesContract.CitiesOfflineImages._ID,
        CitiesContract.CitiesOfflineImages.CITY_IMAGE_SLUG,
        CitiesContract.CitiesOfflineImages.CITY_IMAGE_DATA
    };

    public static final String[] CITIES_OFFLINE_PLACES_TO_WORK_PROJECTION = {
        CitiesContract.CitiesPlacesToWork._ID,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_SLUG,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_CITY_SLUG,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_NAME,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_SUBNAME,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_COWORKING_TYPE,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_DISTANCE,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_LAT,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_LNG,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_DATA_URL,
        CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_IMAGE_URL
    };

    public static final String[] CITIES_FAVOURITE_SLUGS_PROJECTION = {
        CitiesContract.CitiesFavouriteSlugs._ID,
        CitiesContract.CitiesFavouriteSlugs.CITY_FAVOURITE_SLUGS_SLUG
    };

    public static final String[] CITIES_OFFLINE_SLUGS_PROJECTION = {
        CitiesContract.CitiesOfflineSlugs._ID,
        CitiesContract.CitiesOfflineSlugs.CITY_OFFLINE_SLUGS_SLUG
    };

    public static final String[] EXCHANGE_RATES_PROJECTION = {
        CitiesContract.ExchangeRates._ID,
        CitiesContract.ExchangeRates.EXCHANGE_RATES_CURRENCY_CODE,
        CitiesContract.ExchangeRates.EXCHANGE_RATES_BASE_CURRENCY_CODE,
        CitiesContract.ExchangeRates.EXCHANGE_RATES_UPDATE_DATE,
        CitiesContract.ExchangeRates.EXCHANGE_RATES_CURRENCY_RATE
    };
}
