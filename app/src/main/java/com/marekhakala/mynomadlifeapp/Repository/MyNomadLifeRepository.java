package com.marekhakala.mynomadlifeapp.Repository;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;

import com.marekhakala.mynomadlifeapp.DataModel.CitiesOfflineResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflinePlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlacesToWorkResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ExchangeRateEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ImageResponseBodyEntity;
import com.marekhakala.mynomadlifeapp.DataSource.MyNomadLifeAPI;
import com.marekhakala.mynomadlifeapp.Database.CitiesContract;
import com.marekhakala.mynomadlifeapp.Database.CitiesProjection;
import com.marekhakala.mynomadlifeapp.MyNomadLifeApplication;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.CostPerMonthArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.InternetSpeedArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.OtherFiltersArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.PopulationArguments;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;
import com.squareup.sqlbrite.BriteContentResolver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public class MyNomadLifeRepository implements IMyNomadLifeRepository {

    protected MyNomadLifeAPI mApi = null;
    protected MyNomadLifeApplication mApp = null;
    protected CompositeSubscription mSubscriptions = null;

    protected ContentResolver mContentResolver;
    protected BriteContentResolver mBriteContentResolver;

    protected BehaviorSubject<List<String>> mFavouriteSlugsSubject = null;
    protected BehaviorSubject<List<String>> mOfflineSlugsSubject = null;

    public MyNomadLifeRepository(MyNomadLifeAPI api, ContentResolver contentResolver,
                                 BriteContentResolver briteContentResolver, Application application) {
        if (application instanceof MyNomadLifeApplication)
            mApp = (MyNomadLifeApplication) application;

        mApi = api;
        mContentResolver = contentResolver;
        mBriteContentResolver = briteContentResolver;
    }

    protected Observable<CitiesResultEntity> prepareFiltersForAPI(Integer page) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mApp.getApplicationContext());

        CostPerMonthArguments costPerMonthArguments = RepositoryHelpers.prepareCostPerMonthArguments(mApp.getApplicationContext(), settings);
        PopulationArguments populationArguments = RepositoryHelpers.preparePopulationArguments(mApp.getApplicationContext(), settings);
        InternetSpeedArguments internetSpeedArguments = RepositoryHelpers.prepareInternetSpeedArguments(mApp.getApplicationContext(), settings);
        OtherFiltersArguments otherFiltersArguments = RepositoryHelpers.prepareOtherFiltersArguments(mApp.getApplicationContext(), settings);

        return mApi.getCitiesFilter(page, costPerMonthArguments.getCostPerMonthFrom(), costPerMonthArguments.getCostPerMonthTo(),
                populationArguments.getPopulationFrom(), populationArguments.getPopulationTo(),
                internetSpeedArguments.getInternetSpeedFrom(), internetSpeedArguments.getInternetSpeedTo(),
                otherFiltersArguments.getSafetyFrom(), otherFiltersArguments.getNightlifeFrom(),
                otherFiltersArguments.getPlacesToWorkFrom(), otherFiltersArguments.getFunFrom(),
                otherFiltersArguments.getEnglishSpeakingFrom(), otherFiltersArguments.getStartupScoreFrom(),
                otherFiltersArguments.getFriendlyToForeignersFrom(), otherFiltersArguments.getFemaleFriendlyFrom(),
                otherFiltersArguments.getGayFriendlyFrom());
    }

    @Override
    public Observable<CitiesResultEntity> cities(boolean cleanAdd, Integer page) {
        return prepareFiltersForAPI(page)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .subscribeOn(Schedulers.io())
                .doOnNext(result -> {
                    if(cleanAdd)
                        removeAllCachedCities();

                    addCitiesToCache(result.getEntries());
                });
    }

    protected List<String> getListOfFavouriteSlugs() {
        Cursor cursor = mContentResolver.query(CitiesContract.CitiesFavouriteSlugs.CONTENT_URI,
                CitiesProjection.CITIES_FAVOURITE_SLUGS_PROJECTION, null, null, CitiesContract.Cities.SORT_ORDER);

        List<String> slugs = new ArrayList<>();

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String slug = UtilityHelper.getCursorString(cursor, CitiesContract.CitiesFavouriteSlugs.CITY_FAVOURITE_SLUGS_SLUG);
                    slugs.add(slug);
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return slugs;
    }

    protected List<String> getListOfOfflineSlugs() {
        Cursor cursor = mContentResolver.query(CitiesContract.CitiesOfflineSlugs.CONTENT_URI,
                CitiesProjection.CITIES_OFFLINE_SLUGS_PROJECTION, null, null, CitiesContract.Cities.SORT_ORDER);
        List<String> slugs = new ArrayList<>();

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String slug = UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflineSlugs.CITY_OFFLINE_SLUGS_SLUG);
                    slugs.add(slug);
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return slugs;
    }

    @Override
    public CitiesResultEntity cachedCities() {
        List<String> favouriteSlugs = getListOfFavouriteSlugs();
        List<String> offlineSlugs = getListOfOfflineSlugs();

        Cursor cursor = mContentResolver.query(CitiesContract.Cities.CONTENT_URI, CitiesProjection.CITIES_PROJECTION,
                null, null, CitiesContract.Cities.SORT_ORDER);
        List<CityEntity> citiesList = new ArrayList<>();

        try {
            if(cursor != null) {
                while (cursor.moveToNext()) {
                    CityEntity entity = RepositoryHelpers.cityEntityFavouriteOfflineFactory(RepositoryHelpers.cityEntityFactory(cursor),
                            favouriteSlugs, offlineSlugs);
                    citiesList.add(entity);
                }
            }
        } finally {
            if(cursor != null)
                cursor.close();
        }

        CitiesResultEntity resultEntity = new CitiesResultEntity();
        resultEntity.setEntries(citiesList);
        return resultEntity;
    }

    @Override
    public Observable<List<CityEntity>> searchCities(Integer page, String query) {
        Observable<List<CityEntity>> observableCities = mApi.getCitiesSearch(page, query)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .map(CitiesResultEntity::getEntries)
                .subscribeOn(Schedulers.io());

        return Observable.zip(observableCities, getFavouriteCitiesSlugs(), getOfflineCitiesSlugs(),
                UtilityHelper::setupFavouriteOfflineItems)
                .subscribeOn(Schedulers.io());
    }

    protected Observable<List<CityEntity>> getSearchCitiesFromApi(RequestBody body) {
        return mApi.getCitiesListSearch(body)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .map(CitiesResultEntity::getEntries)
                .subscribeOn(Schedulers.io())
                .doOnNext(cities -> {
                    removeAllCachedCities();
                    addCitiesToCache(cities);
                });
    }

    protected Observable<List<CityOfflineEntity>> getSearchOfflineCitiesFromApi(RequestBody body, boolean cleanAll) {

        Observable<List<CityOfflineEntity>> observableCities = mApi.getCitiesOfflineListSearch(body)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .map(CitiesOfflineResultEntity::getEntries)
                .subscribeOn(Schedulers.io())
                .doOnNext(cities -> {
                    if(cleanAll) {
                        removeAllOfflineCities();
                        removeAllOfflineCitiesImages();
                        removeAllOfflineCitiesPlacesToWork();
                    }

                    addOfflineCities(cities);
                });

        return Observable.zip(observableCities, getFavouriteCitiesSlugs(), getOfflineCitiesSlugs(),
                UtilityHelper::setupOfflineFavouriteOfflineItems)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<String>> favouriteCitiesSlugs() {
        return mBriteContentResolver
                .createQuery(CitiesContract.CitiesFavouriteSlugs.CONTENT_URI, CitiesProjection.CITIES_FAVOURITE_SLUGS_PROJECTION,
                        null, null, CitiesContract.CitiesFavouriteSlugs.SORT_ORDER, true)
                .map(query -> {
                    Cursor cursor = query.run();
                    List<String> slugs = new ArrayList<>();

                    try {
                        if (cursor != null) {
                            while (cursor.moveToNext()) {
                                String slug = UtilityHelper.getCursorString(cursor, CitiesContract.CitiesFavouriteSlugs.CITY_FAVOURITE_SLUGS_SLUG);
                                slugs.add(slug);
                            }
                        }
                    } finally {
                        if (cursor != null)
                            cursor.close();
                    }

                    return slugs;
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<String>> getFavouriteCitiesSlugs() {
        mFavouriteSlugsSubject = BehaviorSubject.create();
        favouriteCitiesSlugs().subscribe(mFavouriteSlugsSubject);
        return mFavouriteSlugsSubject.asObservable();
    }

    @Override
    public Observable<List<CityEntity>> favouriteCities(List<String> citiesSlugs) {
        JSONObject object = new JSONObject();
        String jsonInput;

        try {
            object.put(ConstantValues.API_SEARCH_PARAM, new JSONArray(citiesSlugs));
            jsonInput = object.toString();
        } catch (JSONException e) {
            jsonInput = "{}";
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(ConstantValues.HTTP_DATA_TYPE), jsonInput);
        return getSearchCitiesFromApi(body);
    }

    @Override
    public Observable<List<String>> offlineCitiesSlugs() {
        return mBriteContentResolver
                .createQuery(CitiesContract.CitiesOfflineSlugs.CONTENT_URI, CitiesProjection.CITIES_OFFLINE_SLUGS_PROJECTION,
                        null, null, CitiesContract.CitiesOfflineSlugs.SORT_ORDER, true)
                .map(query -> {
                    Cursor cursor = query.run();
                    List<String> slugs = new ArrayList<>();

                    try {
                        if (cursor != null) {
                            while (cursor.moveToNext()) {
                                String slug = UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflineSlugs.CITY_OFFLINE_SLUGS_SLUG);
                                slugs.add(slug);
                            }
                        }
                    } finally {
                        if (cursor != null)
                            cursor.close();
                    }

                    return slugs;
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<String>> getOfflineCitiesSlugs() {
        mOfflineSlugsSubject = BehaviorSubject.create();
        offlineCitiesSlugs().subscribe(mOfflineSlugsSubject);
        return mOfflineSlugsSubject.asObservable();
    }

    @Override
    public List<CityOfflineEntity> offlineCities() {
        List<String> favouriteSlugs = getListOfFavouriteSlugs();
        List<String> offlineSlugs = getListOfOfflineSlugs();

        Cursor cursor = mContentResolver.query(CitiesContract.CitiesOffline.CONTENT_URI, CitiesProjection.CITIES_OFFLINE_PROJECTION,
                null, null, CitiesContract.CitiesOffline.SORT_ORDER);

        List<CityOfflineEntity> citiesList = new ArrayList<>();

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    CityOfflineEntity cityOfflineEntity = RepositoryHelpers
                            .cityOfflineEntityFavouriteOfflineFactory(RepositoryHelpers.cityOfflineEntityFactory(cursor), favouriteSlugs, offlineSlugs);
                    citiesList.add(cityOfflineEntity);
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return citiesList;
    }

    protected Observable<CityOfflineEntity> getOfflineCitiesFromApiImages(Observable<CityOfflineEntity> observableOfflineCities) {
        return observableOfflineCities
                .flatMap((cityOfflineEntity) -> getCityImageFromApi(cityOfflineEntity.getSlug()),
                        (cityOfflineEntity, imageResponseBodyEntity) -> {
                            if (imageResponseBodyEntity != null && imageResponseBodyEntity.isData()) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResponseBodyEntity.getImageData(), 0, imageResponseBodyEntity.getImageData().length);
                                addOfflineCityImage(imageResponseBodyEntity.getSlug(), bitmap);
                            }
                            return cityOfflineEntity;
                        });
    }

    protected Observable<List<CityOfflineEntity>> getOfflineCitiesFromApi(List<String> citiesSlugs, boolean cleanAll) {
        JSONObject object = new JSONObject();
        String jsonInput;

        try {
            object.put(ConstantValues.API_SEARCH_PARAM, new JSONArray(citiesSlugs));
            jsonInput = object.toString();
        } catch (JSONException e) {
            jsonInput = "{}";
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(ConstantValues.HTTP_DATA_TYPE), jsonInput);
        Observable<CityOfflineEntity> observableOfflineCities = getSearchOfflineCitiesFromApi(body, cleanAll).flatMap(Observable::from);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mApp);
        boolean offlineModeAutoUpdatesImages = settings.getBoolean(ConstantValues.SETTINGS_OFFLINE_MODE_AUTO_UPDATES_IMAGES, true);

        if(offlineModeAutoUpdatesImages)
            observableOfflineCities = getOfflineCitiesFromApiImages(observableOfflineCities);

        observableOfflineCities = observableOfflineCities.flatMap((cityOfflineEntity) -> getCityPlacesToWorkFromApi(cityOfflineEntity.getSlug()),
                        (cityOfflineEntity, citiesPlacesToWork) -> {
                            addOfflineCityPlacesToWork(citiesPlacesToWork);
                            return cityOfflineEntity;
        });

        return observableOfflineCities.toList();
    }

    @Override
    public Observable<List<CityOfflineEntity>> offlineCitiesFromApi(List<String> offlineCitiesSlugs, boolean cleanAll) {
        return getOfflineCitiesFromApi(offlineCitiesSlugs, cleanAll);
    }

    @Override
    public void addCitiesToCache(List<CityEntity> cities) {
        ContentValues[] values = new ContentValues[cities.size()];

        int i = 0;
        for(CityEntity entity : cities)
            values[i++] = entity.getValues();

        if(!cities.isEmpty())
            mContentResolver.bulkInsert(CitiesContract.Cities.CONTENT_URI, values);
    }

    public void removeAllCachedCities() {
        mContentResolver.delete(CitiesContract.Cities.CONTENT_URI, null, null);
    }

    @Override
    public void addOfflineCitySlug(String slug) {
        ContentValues values = new ContentValues();
        values.put(CitiesContract.CitiesOfflineSlugs.CITY_OFFLINE_SLUGS_SLUG, slug);
        mContentResolver.insert(CitiesContract.CitiesOfflineSlugs.CONTENT_URI, values);
    }

    @Override
    public void addOfflineCity(CityOfflineEntity city) {
        mContentResolver.insert(CitiesContract.CitiesOffline.CONTENT_URI, city.getValues());
    }

    private void removeOfflineCitySlugs(String slug) {
        mContentResolver.delete(CitiesContract.CitiesOfflineSlugs.CONTENT_URI,
                CitiesContract.CitiesOfflineSlugs.CITY_OFFLINE_SLUGS_SLUG + " = ?", new String[]{slug});
    }

    @Override
    public void removeOfflineCity(String slug) {
        mContentResolver.delete(CitiesContract.CitiesOffline.CONTENT_URI,
                CitiesContract.CitiesOffline.CITY_SLUG + " = ?", new String[]{slug});

        removeOfflineCitySlugs(slug);
        removeOfflineCityImage(slug);
        removeAllOfflineCitiesPlacesToWork(slug);
    }

    protected void removeAllOfflineCitiesSlugs() {
        mContentResolver.delete(CitiesContract.CitiesOfflineSlugs.CONTENT_URI, null, null);
    }

    @Override
    public void removeAllOfflineCitiesData() {
        removeAllOfflineCitiesSlugs();
        removeAllOfflineCities();
        removeAllOfflineCitiesImages();
        removeAllOfflineCitiesPlacesToWork();
    }

    @Override
    public void addOfflineCities(List<CityOfflineEntity> cities) {
        for(CityOfflineEntity entity : cities) {
            mContentResolver.insert(CitiesContract.CitiesOffline.CONTENT_URI, entity.getValues());
        }
    }

    @Override
    public void removeAllOfflineCities() {
        mContentResolver.delete(CitiesContract.CitiesOffline.CONTENT_URI, null, null);
    }

    @Override
    public void addOfflineCityImage(String citySlug, Bitmap bitmapImage) {
        ContentValues values = new ContentValues();
        values.put(CitiesContract.CitiesOfflineImages.CITY_IMAGE_SLUG, citySlug);
        values.put(CitiesContract.CitiesOfflineImages.CITY_IMAGE_DATA, UtilityHelper.bitmapToByteArray(bitmapImage));
        mContentResolver.insert(CitiesContract.CitiesOfflineImages.CONTENT_URI, values);
    }

    @Override
    public Observable<List<ImageResponseBodyEntity>> offlineCityImage(String citySlug) {
        String selectionWhere;
        String[] selectionArguments = new String[]{citySlug};

        selectionWhere = CitiesContract.CitiesOfflineImages.CITY_IMAGE_SLUG + " = ?";
        selectionArguments[0] = citySlug;

        return mBriteContentResolver
                .createQuery(CitiesContract.CitiesOfflineImages.CONTENT_URI, CitiesProjection.CITIES_OFFLINE_IMAGES_PROJECTION,
                        selectionWhere, selectionArguments, CitiesContract.CitiesOfflineImages.SORT_ORDER, true)
                .map(query -> {
                    Cursor cursor = query.run();
                    List<ImageResponseBodyEntity> images = new ArrayList<>();

                    try {
                        if (cursor != null) {
                            while (cursor.moveToNext()) {
                                ImageResponseBodyEntity imageResponseBodyEntity = new ImageResponseBodyEntity();
                                imageResponseBodyEntity.setSlug(UtilityHelper.getCursorString(cursor, CitiesContract.CitiesOfflineImages.CITY_IMAGE_SLUG));

                                try {
                                    byte[] imageData = UtilityHelper.getCursorBlob(cursor, CitiesContract.CitiesOfflineImages.CITY_IMAGE_DATA);

                                    if (imageData != null && imageData.length > 0) {
                                        imageResponseBodyEntity.setData(true);
                                        imageResponseBodyEntity.setImageData(imageData);
                                    } else {
                                        imageResponseBodyEntity.setData(false);
                                    }
                                } catch (SQLiteException exception) {
                                    imageResponseBodyEntity.setData(false);
                                }

                                images.add(imageResponseBodyEntity);
                            }
                        }
                    } finally {
                        if (cursor != null)
                            cursor.close();
                    }

                    return images;
                })
                .subscribeOn(Schedulers.io());
    }

    protected Observable<ImageResponseBodyEntity> getCityImageFromApi(String slug) {
        return mApi.getImage(slug)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .subscribeOn(Schedulers.io())
                .map(response -> UtilityHelper.processImageFromApi(slug, response));
    }

    @Override
    public Observable<List<Boolean>> updateAllOfflineCitiesImages(List<String> citySlugs) {
        if(mSubscriptions != null)
            mSubscriptions.unsubscribe();

        mSubscriptions = new CompositeSubscription();
        List<Observable<ImageResponseBodyEntity>> responses = new ArrayList<>();

        for(String cityOfflineSlug : citySlugs)
            responses.add(getCityImageFromApi(cityOfflineSlug));

        return Observable.zip(responses, (imageResponses) -> {
            List<Boolean> imageResults = new ArrayList<>();

            if(imageResponses != null) {
                for(Object responseObject : imageResponses) {
                    ImageResponseBodyEntity imageResponseBodyEntity = (ImageResponseBodyEntity) responseObject;

                    if(imageResponseBodyEntity != null && imageResponseBodyEntity.isData()) {
                        addOfflineCityImage(imageResponseBodyEntity.getSlug(),
                                BitmapFactory.decodeByteArray(imageResponseBodyEntity.getImageData(), 0, imageResponseBodyEntity.getImageData().length));
                        imageResults.add(true);
                    }
                    imageResults.add(false);
                }
            }

            return imageResults;
        });
    }

    @Override
    public void removeOfflineCityImage(String slug) {
        String whereString = CitiesContract.CitiesOfflineImages.CITY_IMAGE_SLUG + " = ?";
        mContentResolver.delete(CitiesContract.CitiesOfflineImages.CONTENT_URI, whereString, new String[]{slug});
    }

    @Override
    public void removeAllOfflineCitiesImages() {
        mContentResolver.delete(CitiesContract.CitiesOfflineImages.CONTENT_URI, null, null);
    }

    @Override
    public void addFavouriteCitySlugs(String slug) {
        ContentValues values = new ContentValues();
        values.put(CitiesContract.CitiesFavouriteSlugs.CITY_FAVOURITE_SLUGS_SLUG, slug);
        mContentResolver.insert(CitiesContract.CitiesFavouriteSlugs.CONTENT_URI, values);
    }

    @Override
    public void removeFavouriteCitySlugs(String slug) {
        String whereString = CitiesContract.CitiesFavouriteSlugs.CITY_FAVOURITE_SLUGS_SLUG + " = ?";
        mContentResolver.delete(CitiesContract.CitiesFavouriteSlugs.CONTENT_URI, whereString, new String[]{slug});
    }

    @Override
    public void removeAllFavouriteCitiesSlugs() {
        mContentResolver.delete(CitiesContract.CitiesFavouriteSlugs.CONTENT_URI, null, null);
    }

    protected Observable<List<CityPlaceToWorkEntity>> getCityPlacesToWork(String citySlug) {
        return mApi.getListOfPlacesToWork(citySlug)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .map(CityPlacesToWorkResultEntity::getEntries)
                .subscribeOn(Schedulers.io())
                .map(placesToWork -> {
                    for(CityPlaceToWorkEntity cityPlaceToWorkEntity : placesToWork)
                        cityPlaceToWorkEntity.setCitySlug(citySlug);

                    return placesToWork;
                });
    }

    public Observable<List<CityOfflinePlaceToWorkEntity>> getCityPlacesToWorkFromApi(String citySlug) {
        return getCityPlacesToWork(citySlug)
                .flatMap(results -> Observable.from(results)
                .map(RepositoryHelpers::cityPlaceToWorkEntityToOffline).toList());
    }

    private boolean isInList(CityPlaceToWorkEntity entity, List<String> queryList) {
        String name = entity.getName().toLowerCase();
        String subName = entity.getSubName().toLowerCase();

        for(String queryItem : queryList) {
            queryItem = queryItem.toLowerCase();

            if(name.contains(queryItem))
                return true;

            if(subName.contains(queryItem))
                return true;
        }

        return false;
    }

    @Override
    public Observable<List<CityPlaceToWorkEntity>> cityPlacesToWork(String citySlug, String query) {
        return mApi.getListOfPlacesToWork(citySlug)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .map(CityPlacesToWorkResultEntity::getEntries)
                .subscribeOn(Schedulers.io())
                .map(placesToWork -> {
                    for(CityPlaceToWorkEntity cityPlaceToWorkEntity : placesToWork)
                        cityPlaceToWorkEntity.setCitySlug(citySlug);

                    return placesToWork;
                })
                .doOnNext(placesToWork -> {
                    removeAllCityPlacesToWork();
                    addCityPlacesToWorkToCache(placesToWork);
                })
                .flatMap(results -> Observable.from(results)
                        .filter(placeToWork -> isInList(placeToWork, createQueryList(query))).toList());
    }

    private List<String> createQueryList(String query) {
        return Arrays.asList(query.split(" "));
    }

    @Override
    public List<CityPlaceToWorkEntity> cachedCityPlacesToWork(String citySlug, String sqlQuery) {
        String selectionWhere = "";
        List<String> queryList = createQueryList(sqlQuery);
        String[] selectionArguments = new String[(queryList.size()*2)+1];

        if(sqlQuery != null && !sqlQuery.isEmpty() && !sqlQuery.equals("")) {
            for (int i = 0; i < queryList.size(); i++) {
                selectionWhere += CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_NAME + " LIKE ? OR ";
                selectionWhere += CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_SUBNAME + " LIKE ?";

                if (i != (queryList.size() - 1))
                    selectionWhere += " OR ";
            }

            int i = 0;
            for(String item : queryList) {
                selectionArguments[i] = "%" + item + "%";
                selectionArguments[i+1] = "%" + item + "%";
                i += 2;
            }

            selectionArguments[i] = citySlug;
            selectionWhere = "(" + selectionWhere + ") AND " + CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_CITY_SLUG + " = ?";

        } else {
            selectionArguments = new String[]{citySlug};
            selectionWhere = CitiesContract.CitiesPlacesToWork.CITY_PLACE_TO_WORK_CITY_SLUG + " = ?";
        }

        Cursor cursor = mContentResolver.query(CitiesContract.CitiesPlacesToWork.CONTENT_URI,
                CitiesProjection.CITIES_OFFLINE_PLACES_TO_WORK_PROJECTION, selectionWhere, selectionArguments,
                CitiesContract.CitiesPlacesToWork.SORT_ORDER);
        List<CityPlaceToWorkEntity> placesToWork = new ArrayList<>();

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    CityPlaceToWorkEntity entity = RepositoryHelpers.cityPlaceToWorkEntityFactory(cursor);
                    placesToWork.add(entity);
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return placesToWork;
    }

    @Override
    public void addCityPlacesToWorkToCache(List<CityPlaceToWorkEntity> placesToWork) {
        ContentValues[] values = new ContentValues[placesToWork.size()];

        int i = 0;
        for(CityPlaceToWorkEntity entity : placesToWork)
            values[i++] = entity.getValues();

        mContentResolver.bulkInsert(CitiesContract.CitiesPlacesToWork.CONTENT_URI, values);
    }

    @Override
    public void removeAllCityPlacesToWork() {
        mContentResolver.delete(CitiesContract.CitiesPlacesToWork.CONTENT_URI, null, null);
    }

    @Override
    public List<CityOfflinePlaceToWorkEntity> offlineCityPlacesToWork(String citySlug, String sqlQuery) {
        String selectionWhere = "";
        List<String> queryList = createQueryList(sqlQuery);
        String[] selectionArguments = new String[(queryList.size()*2)+1];

        if(sqlQuery != null && !sqlQuery.isEmpty() && !sqlQuery.equals("")) {
            for (int i = 0; i < queryList.size(); i++) {
                selectionWhere += CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_NAME + " LIKE ? OR ";
                selectionWhere += CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_SUBNAME + " LIKE ?";

                if (i != (queryList.size() - 1))
                    selectionWhere += " OR ";
            }

            int i = 0;
            for(String item : queryList) {
                selectionArguments[i] = "%" + item + "%";
                selectionArguments[i+1] = "%" + item + "%";
                i += 2;
            }

            selectionArguments[i] = citySlug;
            selectionWhere = "(" + selectionWhere + ") AND " + CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_CITY_SLUG + " = ?";
        } else {
            selectionWhere = CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_CITY_SLUG + " = ?";
            selectionArguments = new String[]{citySlug};
        }

        Cursor cursor = mContentResolver.query(CitiesContract.CitiesOfflinePlacesToWork.CONTENT_URI,
                CitiesProjection.CITIES_OFFLINE_PLACES_TO_WORK_PROJECTION, selectionWhere, selectionArguments,
                CitiesContract.CitiesOfflinePlacesToWork.SORT_ORDER);
        List<CityOfflinePlaceToWorkEntity> offlinePlacesToWork = new ArrayList<>();

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    CityOfflinePlaceToWorkEntity entity = RepositoryHelpers.cityOfflinePlaceToWorkEntityFactory(cursor);
                    offlinePlacesToWork.add(entity);
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return offlinePlacesToWork;
    }

    @Override
    public void addOfflineCityPlacesToWork(List<CityOfflinePlaceToWorkEntity> cityOfflinePlacesToWorkEntity) {
        if(cityOfflinePlacesToWorkEntity != null && !cityOfflinePlacesToWorkEntity.isEmpty()) {
            ContentValues[] values = new ContentValues[cityOfflinePlacesToWorkEntity.size()];

            int i = 0;
            for (CityOfflinePlaceToWorkEntity entity : cityOfflinePlacesToWorkEntity)
                values[i++] = entity.getValues();

            mContentResolver.bulkInsert(CitiesContract.CitiesOfflinePlacesToWork.CONTENT_URI, values);
        }
    }

    @Override
    public void addOfflineCityPlacesToWorkFromApi(List<CityPlaceToWorkEntity> cityPlacesToWorkEntity) {
        if(cityPlacesToWorkEntity != null && !cityPlacesToWorkEntity.isEmpty()) {
            ContentValues[] values = new ContentValues[cityPlacesToWorkEntity.size()];

            int i = 0;
            for (CityPlaceToWorkEntity entity : cityPlacesToWorkEntity)
                values[i++] = entity.getValues();

            mContentResolver.bulkInsert(CitiesContract.CitiesOfflinePlacesToWork.CONTENT_URI, values);
        }
    }

    @Override
    public Observable<List<Boolean>> updateAllOfflineCitiesPlacesToWork(List<String> citySlugs) {
        if(mSubscriptions != null)
            mSubscriptions.unsubscribe();

        mSubscriptions = new CompositeSubscription();
        List<Observable<List<CityPlaceToWorkEntity>>> responses = new ArrayList<>();

        for(String cityOfflineSlug : citySlugs)
            responses.add(getCityPlacesToWork(cityOfflineSlug));

        return Observable.zip(responses, (placesToWorkResponses) -> {
            List<Boolean> placesToWorkResults = new ArrayList<>();

            if(placesToWorkResponses != null) {
                removeAllOfflineCitiesPlacesToWork();

                for(Object responseObject : placesToWorkResponses) {
                    List<CityPlaceToWorkEntity> placesToWorkList = (List<CityPlaceToWorkEntity>) responseObject;

                    if(placesToWorkList != null && !placesToWorkList.isEmpty()) {
                        addOfflineCityPlacesToWorkFromApi(placesToWorkList);
                        placesToWorkResults.add(true);
                    }
                    placesToWorkResults.add(false);
                }
            }

            return placesToWorkResults;
        });
    }

    @Override
    public void removeAllOfflineCitiesPlacesToWork(String citySlug) {
        String whereString = CitiesContract.CitiesOfflinePlacesToWork.CITY_PLACE_TO_WORK_CITY_SLUG + " = ?";
        mContentResolver.delete(CitiesContract.CitiesOfflinePlacesToWork.CONTENT_URI, whereString, new String[]{citySlug});
    }

    @Override
    public void removeAllOfflineCitiesPlacesToWork() {
        mContentResolver.delete(CitiesContract.CitiesOfflinePlacesToWork.CONTENT_URI, null, null);
    }

    @Override
    public List<ExchangeRateEntity> exchangeRates() {
        Cursor cursor = mContentResolver.query(CitiesContract.ExchangeRates.CONTENT_URI, CitiesProjection.EXCHANGE_RATES_PROJECTION,
                null, null, CitiesContract.ExchangeRates.SORT_ORDER);
        List<ExchangeRateEntity> exchangeRateEntityList = new ArrayList<>();

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    ExchangeRateEntity exchangeRateEntity = RepositoryHelpers.exchangeRateEntityFactory(cursor);
                    exchangeRateEntityList.add(exchangeRateEntity);
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return exchangeRateEntityList;
    }
}


