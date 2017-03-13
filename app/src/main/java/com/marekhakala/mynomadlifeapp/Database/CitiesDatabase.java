package com.marekhakala.mynomadlifeapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class CitiesDatabase extends SQLiteOpenHelper {
    protected static final int DB_VERSION = 1;
    protected static final String DB_NAME = "mynomadlife.app.db";

    public interface Tables {
        String CITIES = "cities";
        String CITIES_PLACES_TO_WORK = "cities_places_to_work";
        String CITIES_OFFLINE = "cities_offline";
        String CITIES_OFFLINE_IMAGES = "cities_offline_images";
        String CITIES_OFFLINE_PLACES_TO_WORK = "cities_offline_places_to_work";
        String CITIES_FAVOURITE_SLUGS = "cities_favourite_slugs";
        String CITIES_OFFLINE_SLUGS = "cities_offline_slugs";
        String EXCHANGE_RATES = "exchange_rates";
    }

    public CitiesDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    protected String createCities() {
        return "CREATE TABLE " + Tables.CITIES + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + CitiesContract.CitiesColumns.CITY_SLUG + " TEXT NOT NULL,"
                + CitiesContract.CitiesColumns.CITY_REGION + " TEXT,"
                + CitiesContract.CitiesColumns.CITY_COUNTRY + " TEXT,"
                + CitiesContract.CitiesColumns.CITY_TEMPERATURE_C + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_TEMPERATURE_F + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_HUMIDITY + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_RANK + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_COST_PER_MONTH + " REAL,"
                + CitiesContract.CitiesColumns.CITY_INTERNET_SPEED + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_POPULATION + " REAL,"
                + CitiesContract.CitiesColumns.CITY_GENDER_RATIO + " TEXT,"
                + CitiesContract.CitiesColumns.CITY_RELIGIOUS + " TEXT,"
                + CitiesContract.CitiesColumns.CITY_CURRENCY + " TEXT,"
                + CitiesContract.CitiesColumns.CITY_CURRENCY_RATE + " REAL,"
                + CitiesContract.CitiesColumns.CITY_SCORE_NOMAD_SCORE + " REAL,"
                + CitiesContract.CitiesColumns.CITY_SCORE_LIFE_SCORE + " REAL,"
                + CitiesContract.CitiesColumns.CITY_SCORE_COST + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_INTERNET + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_FUN + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_SAFETY + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_PEACE + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_NIGHTLIFE + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_FREE_WIFI_IN_CITY + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_PLACES_TO_WORK + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_AC_OR_HEATING + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_FRIENDLY_TO_FOREIGNERS + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_FEMALE_FRIENDLY + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_GAY_FRIENDLY + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_SCORE_STARTUP_SCORE + " REAL,"
                + CitiesContract.CitiesColumns.CITY_SCORE_ENGLISH_SPEAKING + " INTEGER,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_NOMAD_COST + " REAL,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_EXPAT_COST_OF_LIVING + " REAL,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_LOCAL_COST_OF_LIVING + " REAL,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_ONE_BEDROOM_APARTMENT + " REAL,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_HOTEL_ROOM + " REAL,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_MONTH + " REAL,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_DAY + " REAL,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_COWORKING_SPACE + " REAL,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_COCA_COLA_IN_CAFE + " REAL,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_PINT_OF_BEER_IN_BAR + " REAL,"
                + CitiesContract.CitiesColumns.CITY_COST_OF_LIVING_CAPPUCCINO_IN_CAFE + " REAL,"
                + "UNIQUE (" + CitiesContract.CitiesColumns.CITY_SLUG + ") ON CONFLICT REPLACE)";
    }

    protected String createCitiesPlacesToWork() {
        return "CREATE TABLE " + Tables.CITIES_PLACES_TO_WORK + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_SLUG + " TEXT NOT NULL,"
                + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_CITY_SLUG + " TEXT,"
                + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_NAME + " TEXT,"
                + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_SUBNAME + " TEXT,"
                + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_COWORKING_TYPE + " TEXT,"
                + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_DISTANCE + " TEXT,"
                + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_LAT + " TEXT,"
                + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_LNG + " TEXT,"
                + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_DATA_URL + " TEXT,"
                + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_IMAGE_URL + " TEXT,"
                + "UNIQUE (" + CitiesContract.CitiesPlacesToWorkColumns.CITY_PLACE_TO_WORK_SLUG + ") ON CONFLICT REPLACE)";
    }

    protected String createCitiesOffline() {
        return "CREATE TABLE " + Tables.CITIES_OFFLINE + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + CitiesContract.CitiesOfflineColumns.CITY_SLUG + " TEXT NOT NULL,"
                + CitiesContract.CitiesOfflineColumns.CITY_REGION + " TEXT,"
                + CitiesContract.CitiesOfflineColumns.CITY_COUNTRY + " TEXT,"
                + CitiesContract.CitiesOfflineColumns.CITY_TEMPERATURE_C + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_TEMPERATURE_F + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_HUMIDITY + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_RANK + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_PER_MONTH + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_INTERNET_SPEED + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_POPULATION + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_GENDER_RATIO + " TEXT,"
                + CitiesContract.CitiesOfflineColumns.CITY_RELIGIOUS + " TEXT,"
                + CitiesContract.CitiesOfflineColumns.CITY_CURRENCY + " TEXT,"
                + CitiesContract.CitiesOfflineColumns.CITY_CURRENCY_RATE + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_NOMAD_SCORE + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_LIFE_SCORE + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_COST + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_INTERNET + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_FUN + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_SAFETY + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_PEACE + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_NIGHTLIFE + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_FREE_WIFI_IN_CITY + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_PLACES_TO_WORK + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_AC_OR_HEATING + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_FRIENDLY_TO_FOREIGNERS + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_FEMALE_FRIENDLY + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_GAY_FRIENDLY + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_STARTUP_SCORE + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_SCORE_ENGLISH_SPEAKING + " INTEGER,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_NOMAD_COST + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_EXPAT_COST_OF_LIVING + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_LOCAL_COST_OF_LIVING + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_ONE_BEDROOM_APARTMENT + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_HOTEL_ROOM + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_MONTH + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_DAY + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_COWORKING_SPACE + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_COCA_COLA_IN_CAFE + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_PINT_OF_BEER_IN_BAR + " REAL,"
                + CitiesContract.CitiesOfflineColumns.CITY_COST_OF_LIVING_CAPPUCCINO_IN_CAFE + " REAL,"
                + "UNIQUE (" + CitiesContract.CitiesOfflineColumns.CITY_SLUG + ") ON CONFLICT REPLACE)";
    }

    protected String createCitiesOfflineImages() {
        return "CREATE TABLE " + Tables.CITIES_OFFLINE_IMAGES + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + CitiesContract.CitiesOfflineImagesColumns.CITY_IMAGE_SLUG + " TEXT NOT NULL,"
                + CitiesContract.CitiesOfflineImagesColumns.CITY_IMAGE_DATA + " BLOB,"
                + "UNIQUE (" + CitiesContract.CitiesOfflineImagesColumns.CITY_IMAGE_SLUG + ") ON CONFLICT REPLACE)";
    }

    protected String createCitiesOfflinePlacesToWork() {
        return "CREATE TABLE " + Tables.CITIES_OFFLINE_PLACES_TO_WORK + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_SLUG + " TEXT NOT NULL,"
                + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_CITY_SLUG + " TEXT,"
                + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_NAME + " TEXT,"
                + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_SUBNAME + " TEXT,"
                + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_COWORKING_TYPE + " TEXT,"
                + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_DISTANCE + " TEXT,"
                + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_LAT + " TEXT,"
                + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_LNG + " TEXT,"
                + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_DATA_URL + " TEXT,"
                + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_IMAGE_URL + " TEXT,"
                + "UNIQUE (" + CitiesContract.CitiesOfflinePlacesToWorkColumns.CITY_PLACE_TO_WORK_SLUG + ") ON CONFLICT REPLACE)";
    }

    protected String createCitiesFravouriteSlugs() {
        return "CREATE TABLE " + Tables.CITIES_FAVOURITE_SLUGS + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + CitiesContract.CitiesFavouriteSlugsColumns.CITY_FAVOURITE_SLUGS_SLUG + " TEXT NOT NULL,"
                + "UNIQUE (" + CitiesContract.CitiesFavouriteSlugsColumns.CITY_FAVOURITE_SLUGS_SLUG + ") ON CONFLICT REPLACE)";
    }

    protected String createCitiesOfflineSlugs() {
        return "CREATE TABLE " + Tables.CITIES_OFFLINE_SLUGS + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + CitiesContract.CitiesOfflineSlugsColumns.CITY_OFFLINE_SLUGS_SLUG + " TEXT NOT NULL,"
                + "UNIQUE (" + CitiesContract.CitiesOfflineSlugsColumns.CITY_OFFLINE_SLUGS_SLUG + ") ON CONFLICT REPLACE)";
    }

    protected String createExchangeRates() {
        return "CREATE TABLE " + Tables.EXCHANGE_RATES + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + CitiesContract.ExchangeRatesColumns.EXCHANGE_RATES_CURRENCY_CODE + " TEXT NOT NULL,"
                + CitiesContract.ExchangeRatesColumns.EXCHANGE_RATES_BASE_CURRENCY_CODE + " TEXT NOT NULL,"
                + CitiesContract.ExchangeRatesColumns.EXCHANGE_RATES_UPDATE_DATE + " TEXT NOT NULL,"
                + CitiesContract.ExchangeRatesColumns.EXCHANGE_RATES_CURRENCY_RATE + " REAL,"
                + "UNIQUE (" + CitiesContract.ExchangeRatesColumns.EXCHANGE_RATES_CURRENCY_CODE + ") ON CONFLICT REPLACE)";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CITIES_TABLE = createCities();
        db.execSQL(SQL_CREATE_CITIES_TABLE);

        final String SQL_CREATE_CITIES_PLACES_TO_WORK_TABLE = createCitiesPlacesToWork();
        db.execSQL(SQL_CREATE_CITIES_PLACES_TO_WORK_TABLE);

        final String SQL_CREATE_CITIES_OFFLINE_TABLE = createCitiesOffline();
        db.execSQL(SQL_CREATE_CITIES_OFFLINE_TABLE);

        final String SQL_CREATE_CITIES_IMAGES_TABLE = createCitiesOfflineImages();
        db.execSQL(SQL_CREATE_CITIES_IMAGES_TABLE);

        final String SQL_CREATE_CITIES_OFFLINE_PLACES_TO_WORK_TABLE = createCitiesOfflinePlacesToWork();
        db.execSQL(SQL_CREATE_CITIES_OFFLINE_PLACES_TO_WORK_TABLE);

        final String SQL_CITIES_FAVOURITE_SLUGS_TABLE = createCitiesFravouriteSlugs();
        db.execSQL(SQL_CITIES_FAVOURITE_SLUGS_TABLE);

        final String SQL_CITIES_OFFLINE_SLUGS_TABLE = createCitiesOfflineSlugs();
        db.execSQL(SQL_CITIES_OFFLINE_SLUGS_TABLE);

        final String SQL_EXCHANGE_RATES_TABLE = createExchangeRates();
        db.execSQL(SQL_EXCHANGE_RATES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CITIES);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CITIES_PLACES_TO_WORK);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CITIES_OFFLINE);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CITIES_OFFLINE_IMAGES);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CITIES_OFFLINE_PLACES_TO_WORK);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CITIES_FAVOURITE_SLUGS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CITIES_OFFLINE_SLUGS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.EXCHANGE_RATES);
        onCreate(db);
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DB_NAME);
    }
}
