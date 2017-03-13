package com.marekhakala.mynomadlifeapp.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Arrays;
import timber.log.Timber;

import com.marekhakala.mynomadlifeapp.Utilities.DBQueryBuilder;
import static com.marekhakala.mynomadlifeapp.Database.CitiesProjection.generateCityOfflineSqlQuery;

public class CitiesProvider extends ContentProvider {
    protected static final String TAG_LOG = CitiesProvider.class.getSimpleName();

    protected static final UriMatcher sUriMatcher = buildUriMatcher();
    protected SQLiteOpenHelper mDBHelper = null;

    public static final int CITIES = 200;
    public static final int CITIES_PLACES_TO_WORK = 201;
    public static final int CITIES_OFFLINE = 202;
    public static final int CITIES_OFFLINE_IMAGES = 203;
    public static final int CITIES_OFFLINE_PLACES_TO_WORK = 204;
    public static final int CITIES_FAVOURITE_SLUGS = 205;
    public static final int CITIES_OFFLINE_SLUGS = 206;
    public static final int EXCHANGE_RATES = 207;

    @Override
    public boolean onCreate() {
        mDBHelper = new CitiesDatabase(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CITIES:
                return CitiesContract.Cities.CONTENT_TYPE;
            case CITIES_PLACES_TO_WORK:
                return CitiesContract.CitiesPlacesToWork.CONTENT_TYPE;
            case CITIES_OFFLINE:
                return CitiesContract.CitiesOffline.CONTENT_TYPE;
            case CITIES_OFFLINE_IMAGES:
                return CitiesContract.CitiesOfflineImages.CONTENT_TYPE;
            case CITIES_OFFLINE_PLACES_TO_WORK:
                return CitiesContract.CitiesOfflinePlacesToWork.CONTENT_TYPE;
            case CITIES_FAVOURITE_SLUGS:
                return CitiesContract.CitiesFavouriteSlugs.CONTENT_TYPE;
            case CITIES_OFFLINE_SLUGS:
                return CitiesContract.CitiesOfflineSlugs.CONTENT_TYPE;
            case EXCHANGE_RATES:
                return CitiesContract.ExchangeRates.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String order) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String log_message = "[query] match: " + match + ", uri: " + uri;
        if(projection != null) log_message += ", projection: " + Arrays.toString(projection);
        if(selection != null) log_message += ", selection: " + selection;
        if(selectionArgs != null) log_message += ", selectionArgs: " + Arrays.toString(selectionArgs);

        Timber.tag(TAG_LOG).d(log_message);

        String distictString = uri.getQueryParameter("distict");
        boolean distinct = (distictString != null && !distictString.isEmpty());

        DBQueryBuilder queryBuilder = buildQuery(uri);
        Context context = getContext();

        Cursor cursor;

        if(match == CITIES_OFFLINE) {
            cursor = queryBuilder
                    .setWhere(selection, selectionArgs)
                    .rawQuery(db, generateCityOfflineSqlQuery());
        } else {
            cursor = queryBuilder
                    .setWhere(selection, selectionArgs)
                    .query(db, distinct, projection, order, null);
        }

        if (context != null)
            cursor.setNotificationUri(context.getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        String log_message = "[insert][query] uri: " + uri;
        if(values != null) log_message += ", values: " + values.toString();
        Timber.tag(TAG_LOG).d(log_message);

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CITIES: {
                db.insertOrThrow(CitiesDatabase.Tables.CITIES, null, values);
                notifyChange(uri);
                return CitiesContract.Cities.buildUri(values.getAsString(CitiesContract.Cities.CITY_SLUG));
            }
            case CITIES_PLACES_TO_WORK: {
                db.insertOrThrow(CitiesDatabase.Tables.CITIES_PLACES_TO_WORK, null, values);
                notifyChange(uri);
                return CitiesContract.CitiesPlacesToWork.buildUri(values.getAsString(CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_SLUG));
            }
            case CITIES_OFFLINE: {
                db.insertOrThrow(CitiesDatabase.Tables.CITIES_OFFLINE, null, values);
                notifyChange(uri);
                return CitiesContract.CitiesOffline.buildUri(values.getAsString(CitiesContract.CitiesOffline.CITY_SLUG));
            }
            case CITIES_OFFLINE_IMAGES: {
                db.insertOrThrow(CitiesDatabase.Tables.CITIES_OFFLINE_IMAGES, null, values);
                notifyChange(uri);
                return CitiesContract.CitiesOfflineImages.buildUri(values.getAsString(CitiesContract.CitiesOfflineImages.CITY_IMAGE_SLUG));
            }
            case CITIES_OFFLINE_PLACES_TO_WORK: {
                db.insertOrThrow(CitiesDatabase.Tables.CITIES_OFFLINE_PLACES_TO_WORK, null, values);
                notifyChange(uri);
                return CitiesContract.CitiesOfflinePlacesToWork.buildUri(values.getAsString(CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_SLUG));
            }
            case CITIES_FAVOURITE_SLUGS: {
                db.insertOrThrow(CitiesDatabase.Tables.CITIES_FAVOURITE_SLUGS, null, values);
                notifyChange(uri);
                return CitiesContract.CitiesFavouriteSlugs.buildUri(values.getAsString(CitiesContract.CitiesFavouriteSlugs.CITY_FAVOURITE_SLUGS_SLUG));
            }
            case CITIES_OFFLINE_SLUGS: {
                db.insertOrThrow(CitiesDatabase.Tables.CITIES_OFFLINE_SLUGS, null, values);
                notifyChange(uri);
                return CitiesContract.CitiesOfflineSlugs.buildUri(values.getAsString(CitiesContract.CitiesOfflineSlugs.CITY_OFFLINE_SLUGS_SLUG));
            }
            case EXCHANGE_RATES: {
                db.insertOrThrow(CitiesDatabase.Tables.EXCHANGE_RATES, null, values);
                notifyChange(uri);
                return CitiesContract.ExchangeRates.buildUri(values.getAsString(CitiesContract.ExchangeRates.EXCHANGE_RATES_CURRENCY_CODE));
            }
            default:
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String log_message = "[update][query] uri: " + uri;
        if(values != null) log_message += ", values: " + values.toString();
        if(selection != null) log_message += ", selection: " + selection;
        if(selectionArgs != null) log_message += ", selectionArgs: " + selectionArgs.toString();
        Timber.tag(TAG_LOG).d(log_message);

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int returnValue = buildQuery(uri).setWhere(selection, selectionArgs).update(db, values);
        notifyChange(uri);

        return returnValue;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        String log_message = "[delete][query] uri: " + uri + ", selection: " + selection
                + ", selectionArgs: " + Arrays.toString(selectionArgs);
        Timber.tag(TAG_LOG).d(log_message);

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        if (uri.equals(CitiesContract.BASE_CONTENT_URI)) {
            deleteDatabase();
            notifyChange(uri);
            return 1;
        }

        int returnValue = buildQuery(uri).setWhere(selection, selectionArgs).delete(db);
        notifyChange(uri);

        return returnValue;
    }

    protected void deleteDatabase() {
        mDBHelper.close();
        Context context = getContext();
        CitiesDatabase.deleteDatabase(context);
        mDBHelper = new CitiesDatabase(getContext());
    }

    protected void notifyChange(Uri uri) {
        Context context = getContext();

        if(context != null && context.getContentResolver() != null)
            context.getContentResolver().notifyChange(uri, null);
    }

    protected static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(CitiesContract.CONTENT_AUTHORITY, "cities", CITIES);
        matcher.addURI(CitiesContract.CONTENT_AUTHORITY, "cities_places_to_work", CITIES_PLACES_TO_WORK);
        matcher.addURI(CitiesContract.CONTENT_AUTHORITY, "cities_offline", CITIES_OFFLINE);
        matcher.addURI(CitiesContract.CONTENT_AUTHORITY, "cities_offline_images", CITIES_OFFLINE_IMAGES);
        matcher.addURI(CitiesContract.CONTENT_AUTHORITY, "cities_offline_places_to_work", CITIES_OFFLINE_PLACES_TO_WORK);
        matcher.addURI(CitiesContract.CONTENT_AUTHORITY, "cities_favourite_slugs", CITIES_FAVOURITE_SLUGS);
        matcher.addURI(CitiesContract.CONTENT_AUTHORITY, "cities_offline_slugs", CITIES_OFFLINE_SLUGS);
        matcher.addURI(CitiesContract.CONTENT_AUTHORITY, "exchange_rates", EXCHANGE_RATES);

        return matcher;
    }

    protected DBQueryBuilder buildQuery(Uri uri) {
        DBQueryBuilder queryBuilder = new DBQueryBuilder();

        switch (sUriMatcher.match(uri)) {
            case CITIES:
                return queryBuilder.setTable(CitiesDatabase.Tables.CITIES);
            case CITIES_PLACES_TO_WORK:
                return queryBuilder.setTable(CitiesDatabase.Tables.CITIES_PLACES_TO_WORK);
            case CITIES_OFFLINE:
                return queryBuilder.setTable(CitiesDatabase.Tables.CITIES_OFFLINE);
            case CITIES_OFFLINE_IMAGES:
                return queryBuilder.setTable(CitiesDatabase.Tables.CITIES_OFFLINE_IMAGES);
            case CITIES_OFFLINE_PLACES_TO_WORK:
                return queryBuilder.setTable(CitiesDatabase.Tables.CITIES_OFFLINE_PLACES_TO_WORK);
            case CITIES_FAVOURITE_SLUGS:
                return queryBuilder.setTable(CitiesDatabase.Tables.CITIES_FAVOURITE_SLUGS);
            case CITIES_OFFLINE_SLUGS:
                return queryBuilder.setTable(CitiesDatabase.Tables.CITIES_OFFLINE_SLUGS);
            case EXCHANGE_RATES:
                return queryBuilder.setTable(CitiesDatabase.Tables.EXCHANGE_RATES);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }
}
