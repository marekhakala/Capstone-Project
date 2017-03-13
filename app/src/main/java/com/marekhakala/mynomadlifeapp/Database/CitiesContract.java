package com.marekhakala.mynomadlifeapp.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class CitiesContract {
    public interface CitiesColumns {
        // General
        String CITY_SLUG = "slug";
        String CITY_REGION = "region";
        String CITY_COUNTRY = "country";
        String CITY_TEMPERATURE_C = "temperature_c";
        String CITY_TEMPERATURE_F = "temperature_f";
        String CITY_HUMIDITY = "humidity";
        String CITY_RANK = "rank";
        String CITY_COST_PER_MONTH = "cost_per_month";
        String CITY_INTERNET_SPEED = "internet_speed";
        String CITY_POPULATION = "population";
        String CITY_GENDER_RATIO = "gender_ratio";
        String CITY_RELIGIOUS = "religious";
        String CITY_CURRENCY = "currency";
        String CITY_CURRENCY_RATE = "currency_rate";

        // Scores
        String CITY_SCORE_NOMAD_SCORE = "score_nomad_score";
        String CITY_SCORE_LIFE_SCORE = "score_life_score";
        String CITY_SCORE_COST = "score_cost";
        String CITY_SCORE_INTERNET = "score_internet";
        String CITY_SCORE_FUN = "score_fun";
        String CITY_SCORE_SAFETY = "score_safety";
        String CITY_SCORE_PEACE = "score_peace";
        String CITY_SCORE_NIGHTLIFE = "score_nightlife";
        String CITY_SCORE_FREE_WIFI_IN_CITY = "score_free_wifi_in_city";
        String CITY_SCORE_PLACES_TO_WORK = "score_places_to_work";
        String CITY_SCORE_AC_OR_HEATING = "score_ac_or_heating";
        String CITY_SCORE_FRIENDLY_TO_FOREIGNERS = "score_friendly_to_foreigners";
        String CITY_SCORE_FEMALE_FRIENDLY = "score_female_friendly";
        String CITY_SCORE_GAY_FRIENDLY = "score_gay_friendly";
        String CITY_SCORE_STARTUP_SCORE = "score_startup_score";
        String CITY_SCORE_ENGLISH_SPEAKING = "score_english_speaking";

        // Cost of living
        String CITY_COST_OF_LIVING_NOMAD_COST = "cof_nomad_cost";
        String CITY_COST_OF_LIVING_EXPAT_COST_OF_LIVING = "cof_expat_cost_of_living";
        String CITY_COST_OF_LIVING_LOCAL_COST_OF_LIVING = "cof_local_cost_of_living";
        String CITY_COST_OF_LIVING_ONE_BEDROOM_APARTMENT = "cof_one_bedroom_apartment";
        String CITY_COST_OF_LIVING_HOTEL_ROOM = "cof_hotel_room";
        String CITY_COST_OF_LIVING_AIRBNB_APARTMENT_MONTH = "cof_airbnb_apartment_month";
        String CITY_COST_OF_LIVING_AIRBNB_APARTMENT_DAY = "cof_airbnb_apartment_day";
        String CITY_COST_OF_LIVING_COWORKING_SPACE = "cof_coworking_space";
        String CITY_COST_OF_LIVING_COCA_COLA_IN_CAFE = "cof_coca_cola_in_cafe";
        String CITY_COST_OF_LIVING_PINT_OF_BEER_IN_BAR = "cof_pint_of_beer_in_bar";
        String CITY_COST_OF_LIVING_CAPPUCCINO_IN_CAFE = "cof_cappuccino_in_cafe";
    }

    public interface CitiesPlacesToWorkColumns {
        String CITY_PLACE_TO_WORK_CITY_SLUG = "place_to_work_city_slug";
        String CITY_PLACE_TO_WORK_SLUG = "place_to_work_slug";
        String CITY_PLACE_TO_WORK_NAME = "place_to_work_name";
        String CITY_PLACE_TO_WORK_SUBNAME = "place_to_work_subname";
        String CITY_PLACE_TO_WORK_COWORKING_TYPE = "place_to_work_coworking_type";
        String CITY_PLACE_TO_WORK_DISTANCE = "place_to_work_distance";
        String CITY_PLACE_TO_WORK_LAT = "place_to_work_lat";
        String CITY_PLACE_TO_WORK_LNG = "place_to_work_lng";
        String CITY_PLACE_TO_WORK_DATA_URL = "place_to_work_data_url";
        String CITY_PLACE_TO_WORK_IMAGE_URL = "place_to_work_image_url";
    }

    public interface CitiesOfflineColumns {
        // General
        String CITY_SLUG = "slug";
        String CITY_REGION = "region";
        String CITY_COUNTRY = "country";
        String CITY_TEMPERATURE_C = "temperature_c";
        String CITY_TEMPERATURE_F = "temperature_f";
        String CITY_HUMIDITY = "humidity";
        String CITY_RANK = "rank";
        String CITY_COST_PER_MONTH = "cost_per_month";
        String CITY_INTERNET_SPEED = "internet_speed";
        String CITY_POPULATION = "population";
        String CITY_GENDER_RATIO = "gender_ratio";
        String CITY_RELIGIOUS = "religious";
        String CITY_CURRENCY = "currency";
        String CITY_CURRENCY_RATE = "currency_rate";

        // Scores
        String CITY_SCORE_NOMAD_SCORE = "score_nomad_score";
        String CITY_SCORE_LIFE_SCORE = "score_life_score";
        String CITY_SCORE_COST = "score_cost";
        String CITY_SCORE_INTERNET = "score_internet";
        String CITY_SCORE_FUN = "score_fun";
        String CITY_SCORE_SAFETY = "score_safety";
        String CITY_SCORE_PEACE = "score_peace";
        String CITY_SCORE_NIGHTLIFE = "score_nightlife";
        String CITY_SCORE_FREE_WIFI_IN_CITY = "score_free_wifi_in_city";
        String CITY_SCORE_PLACES_TO_WORK = "score_places_to_work";
        String CITY_SCORE_AC_OR_HEATING = "score_ac_or_heating";
        String CITY_SCORE_FRIENDLY_TO_FOREIGNERS = "score_friendly_to_foreigners";
        String CITY_SCORE_FEMALE_FRIENDLY = "score_female_friendly";
        String CITY_SCORE_GAY_FRIENDLY = "score_gay_friendly";
        String CITY_SCORE_STARTUP_SCORE = "score_startup_score";
        String CITY_SCORE_ENGLISH_SPEAKING = "score_english_speaking";

        // Cost of living
        String CITY_COST_OF_LIVING_NOMAD_COST = "cof_nomad_cost";
        String CITY_COST_OF_LIVING_EXPAT_COST_OF_LIVING = "cof_expat_cost_of_living";
        String CITY_COST_OF_LIVING_LOCAL_COST_OF_LIVING = "cof_local_cost_of_living";
        String CITY_COST_OF_LIVING_ONE_BEDROOM_APARTMENT = "cof_one_bedroom_apartment";
        String CITY_COST_OF_LIVING_HOTEL_ROOM = "cof_hotel_room";
        String CITY_COST_OF_LIVING_AIRBNB_APARTMENT_MONTH = "cof_airbnb_apartment_month";
        String CITY_COST_OF_LIVING_AIRBNB_APARTMENT_DAY = "cof_airbnb_apartment_day";
        String CITY_COST_OF_LIVING_COWORKING_SPACE = "cof_coworking_space";
        String CITY_COST_OF_LIVING_COCA_COLA_IN_CAFE = "cof_coca_cola_in_cafe";
        String CITY_COST_OF_LIVING_PINT_OF_BEER_IN_BAR = "cof_pint_of_beer_in_bar";
        String CITY_COST_OF_LIVING_CAPPUCCINO_IN_CAFE = "cof_cappuccino_in_cafe";
    }

    public interface CitiesOfflineImagesColumns {
        String CITY_IMAGE_SLUG = "slug";
        String CITY_IMAGE_DATA = "image_data";
    }

    public interface CitiesOfflinePlacesToWorkColumns {
        String CITY_PLACE_TO_WORK_CITY_SLUG = "place_to_work_city_slug";
        String CITY_PLACE_TO_WORK_SLUG = "place_to_work_slug";
        String CITY_PLACE_TO_WORK_NAME = "place_to_work_name";
        String CITY_PLACE_TO_WORK_SUBNAME = "place_to_work_subname";
        String CITY_PLACE_TO_WORK_COWORKING_TYPE = "place_to_work_coworking_type";
        String CITY_PLACE_TO_WORK_DISTANCE = "place_to_work_distance";
        String CITY_PLACE_TO_WORK_LAT = "place_to_work_lat";
        String CITY_PLACE_TO_WORK_LNG = "place_to_work_lng";
        String CITY_PLACE_TO_WORK_DATA_URL = "place_to_work_data_url";
        String CITY_PLACE_TO_WORK_IMAGE_URL = "place_to_work_image_url";
    }

    public interface CitiesFavouriteSlugsColumns {
        String CITY_FAVOURITE_SLUGS_SLUG = "city_favourite_slugs_slug";
    }

    public interface CitiesOfflineSlugsColumns {
        String CITY_OFFLINE_SLUGS_SLUG = "city_offline_slugs_slug";
    }

    public interface ExchangeRatesColumns {
        String EXCHANGE_RATES_CURRENCY_CODE = "exchange_rates_currency_code";
        String EXCHANGE_RATES_BASE_CURRENCY_CODE = "exchange_rates_base_currency_code";
        String EXCHANGE_RATES_UPDATE_DATE = "exchange_rates_update_date";
        String EXCHANGE_RATES_CURRENCY_RATE = "exchange_rates_currency_rate";
    }

    public static final String CONTENT_AUTHORITY = "com.marekhakala.mynomadlifeapp.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CITIES = "cities";
    public static final String PATH_CITIES_PLACES_TO_WORK = "cities_places_to_work";
    public static final String PATH_CITIES_OFFLINE = "cities_offline";
    public static final String PATH_CITIES_OFFLINE_IMAGES = "cities_offline_images";
    public static final String PATH_CITIES_OFFLINE_PLACES_TO_WORK = "cities_places_to_work";
    public static final String PATH_CITIES_FAVOURITE_SLUGS = "cities_favourite_slugs";
    public static final String PATH_CITIES_OFFLINE_SLUGS = "cities_offline_slugs";
    public static final String PATH_EXCHANGE_RATES = "exchange_rates";

    public static class Cities implements CitiesColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CITIES).build();

        public static final String SORT_ORDER = BaseColumns._ID + " ASC";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITIES;

        public static Uri buildUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class CitiesPlacesToWork implements CitiesPlacesToWorkColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CITIES_PLACES_TO_WORK).build();

        public static final String SORT_ORDER = BaseColumns._ID + " ASC";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITIES_PLACES_TO_WORK;

        public static Uri buildUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class CitiesOffline implements CitiesOfflineColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CITIES_OFFLINE).build();

        public static final String SORT_ORDER = BaseColumns._ID + " ASC";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITIES_OFFLINE;

        public static Uri buildUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class CitiesOfflineImages implements CitiesOfflineImagesColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CITIES_OFFLINE_IMAGES).build();

        public static final String SORT_ORDER = BaseColumns._ID + " ASC";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITIES_OFFLINE_IMAGES;

        public static Uri buildUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class CitiesOfflinePlacesToWork implements CitiesOfflinePlacesToWorkColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CITIES_OFFLINE_PLACES_TO_WORK).build();

        public static final String SORT_ORDER = BaseColumns._ID + " ASC";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITIES_OFFLINE_PLACES_TO_WORK;

        public static Uri buildUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class CitiesFavouriteSlugs implements CitiesFavouriteSlugsColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CITIES_FAVOURITE_SLUGS).build();

        public static final String SORT_ORDER = BaseColumns._ID + " ASC";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITIES_FAVOURITE_SLUGS;

        public static Uri buildUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class CitiesOfflineSlugs implements CitiesOfflineSlugsColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CITIES_OFFLINE_SLUGS).build();

        public static final String SORT_ORDER = BaseColumns._ID + " ASC";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITIES_OFFLINE_SLUGS;

        public static Uri buildUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class ExchangeRates implements ExchangeRatesColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_EXCHANGE_RATES).build();

        public static final String SORT_ORDER = BaseColumns._ID + " ASC";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXCHANGE_RATES;

        public static Uri buildUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    private CitiesContract() {}
}
