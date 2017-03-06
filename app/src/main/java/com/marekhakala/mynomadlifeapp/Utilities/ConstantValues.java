package com.marekhakala.mynomadlifeapp.Utilities;

public class ConstantValues {

    // REST API
    public static final String BASE_URL = "http://mnl.showmelink.co.uk";
    public static final String ENDPOINT_URL = BASE_URL + "/api/v1/";
    public static final String IMAGE_URL = ENDPOINT_URL + "cities/";
    public static final String IMAGE_URL_PATH = "/image";

    public static final String API_SEARCH_PARAM = "slugs";
    public static final String HTTP_DATA_TYPE = "application/json; charset=utf-8";

    // General
    public static final String USD_CURRENCY = "USD";

    // Realm DB options
    public static String REALM_DB_FILE = "mynomadlife.realm.db";
    public static long DB_SCHEMA_VERSION = 1;

    public static final String NAME_COLUMN_NAME = "name";
    public static final String SUB_NAME_COLUMN_NAME = "subName";
    public static final String CITY_SLUG_COLUMN_NAME = "citySlug";
    public static final String SLUG_COLUMN_NAME = "slug";
    public static final String ID_COLUMN_NAME = "id";

    // UI sections
    public static final String MAIN_SECTION_CODE = "main_list";
    public static final String START_SECTION_CODE = "start_list";
    public static final String FAVOURITES_SECTION_CODE = "favourites_list";
    public static final String OFFLINE_MODE_SECTION_CODE = "offline_mode_list";
    public static final String SEARCH_SECTION_CODE = "search_list";
    public static final String FILTER_SECTION_CODE = "filter";
    public static final String DETAIL_VIEW_SECTION_CODE = "detail_view";
    public static final String SETTINGS_SECTION_CODE = "settings";
    public static final String ABOUT_SECTION_CODE = "about";

    public static final String EXTRA_ITEM_CITY = "city";
    public static final String EXTRA_ITEM_CITY_TYPE = "cityType";
    
    public static final String CITY_ENTITY = "City";
    public static final String CITY_OFFLINE_ENTITY = "CityOffline";

    // Settings
    public static final String SETTINGS_OFFLINE_MODE_AUTO_UPDATES_IMAGES = "offline_mode_auto_updates_images";
}
