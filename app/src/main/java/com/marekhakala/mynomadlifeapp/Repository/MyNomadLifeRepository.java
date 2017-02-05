package com.marekhakala.mynomadlifeapp.Repository;

import android.app.Application;
import android.content.SharedPreferences;
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
import com.marekhakala.mynomadlifeapp.DataModel.ImageResponseBodyEntity;
import com.marekhakala.mynomadlifeapp.DataSource.MyNomadLifeAPI;
import com.marekhakala.mynomadlifeapp.RealmDataModel.City;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityFavouriteSlug;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOffline;
import com.marekhakala.mynomadlifeapp.MyNomadLifeApplication;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOfflineImage;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOfflinePlaceToWork;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOfflineSlug;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityPlaceToWork;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.CostPerMonthArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.InternetSpeedArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.OtherFiltersArguments;
import com.marekhakala.mynomadlifeapp.Repository.Arguments.PopulationArguments;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public class MyNomadLifeRepository implements IMyNomadLifeRepository {

    protected MyNomadLifeAPI mApi = null;
    protected MyNomadLifeApplication mApp = null;
    protected CompositeSubscription mSubscriptions = null;

    protected BehaviorSubject<List<String>> mFavouriteSlugsSubject = null;
    protected BehaviorSubject<List<String>> mOfflineSlugsSubject = null;

    public MyNomadLifeRepository(MyNomadLifeAPI api, Application application) {
        if (application instanceof MyNomadLifeApplication)
            mApp = (MyNomadLifeApplication) application;
        mApi = api;
    }

    @Override
    public Realm getRealm() {
        return MyNomadLifeApplication.getDatabaseInstance(mApp);
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
    public Observable<CitiesResultEntity> cities(Realm realm, boolean cleanAdd, Integer page) {

        Observable<CitiesResultEntity> observableCities = prepareFiltersForAPI(page)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .subscribeOn(Schedulers.io())
                .doOnNext(result -> {
                    if(cleanAdd) {
                        removeAllCachedCities();
                    }

                    addCitiesToCache(result.getEntries());
                });

        return Observable.zip(observableCities, getFavouriteCitiesSlugs(realm), getOfflineCitiesSlugs(realm),
                UtilityHelper::setupFavouriteOfflineItems)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<CityEntity>> cachedCities(Realm realm) {
        Observable<List<CityEntity>> observableCities = realm.where(City.class)
                .findAll()
                .asObservable()
                .flatMap(results -> Observable.from(results)
                        .map(RepositoryHelpers::cityEntityFactory).toList());

        return Observable.zip(observableCities, getFavouriteCitiesSlugs(realm), getOfflineCitiesSlugs(realm),
                UtilityHelper::setupFavouriteOfflineItems);
    }

    @Override
    public Observable<List<CityEntity>> searchCities(Realm realm, Integer page, String query) {

        Observable<List<CityEntity>> observableCities = mApi.getCitiesSearch(page, query)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .map(CitiesResultEntity::getEntries)
                .subscribeOn(Schedulers.io());

        return Observable.zip(observableCities, getFavouriteCitiesSlugs(realm), getOfflineCitiesSlugs(realm),
                UtilityHelper::setupFavouriteOfflineItems)
                .subscribeOn(Schedulers.io());
    }

    protected Observable<List<CityEntity>> getSearchCitiesFromApi(Realm realm, RequestBody body) {

        Observable<List<CityEntity>> observableCities = mApi.getCitiesListSearch(body)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .map(CitiesResultEntity::getEntries)
                .subscribeOn(Schedulers.io())
                .doOnNext(cities -> {
                    removeAllCachedCities();
                    addCitiesToCache(cities);
                });

        return Observable.zip(observableCities, getFavouriteCitiesSlugs(realm), getOfflineCitiesSlugs(realm),
                UtilityHelper::setupFavouriteOfflineItems)
                .subscribeOn(Schedulers.io());
    }

    protected Observable<List<CityOfflineEntity>> getSearchOfflineCitiesFromApi(Realm realm, RequestBody body, boolean cleanAll) {

        Observable<List<CityOfflineEntity>> observableCities = mApi.getCitiesOfflineListSearch(body)
                .timeout(6, TimeUnit.SECONDS)
                .retry(3)
                .map(CitiesOfflineResultEntity::getEntries)
                .subscribeOn(Schedulers.io())
                .doOnNext(cities -> {
                    if(cleanAll) {
                        removeAllOfflineCities();
                        removeAllOfflineCitiesImages();
                        removeAllOfflineCityPlacesToWork();
                    }

                    addOfflineCities(cities);
                });

        return Observable.zip(observableCities, getFavouriteCitiesSlugs(realm), getOfflineCitiesSlugs(realm),
                UtilityHelper::setupOfflineFavouriteOfflineItems)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<String>> favouriteCitiesSlugs(Realm realm) {

        return realm.where(CityFavouriteSlug.class)
                .findAll()
                .asObservable()
                .flatMap(results -> Observable.from(results)
                        .map(UtilityHelper::cityIdFactory).toList());
    }

    @Override
    public Observable<List<String>> getFavouriteCitiesSlugs(Realm realm) {

        mFavouriteSlugsSubject = BehaviorSubject.create();
        favouriteCitiesSlugs(realm).subscribe(mFavouriteSlugsSubject);
        return mFavouriteSlugsSubject.asObservable();
    }

    @Override
    public Observable<List<CityEntity>> favouriteCities(Realm realm, List<String> citiesSlugs) {
        JSONObject object = new JSONObject();
        String jsonInput;

        try {
            object.put(ConstantValues.API_SEARCH_PARAM, new JSONArray(citiesSlugs));
            jsonInput = object.toString();
        } catch (JSONException e) {
            jsonInput = "{}";
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(ConstantValues.HTTP_DATA_TYPE), jsonInput);
        return getSearchCitiesFromApi(realm, body);
    }

    @Override
    public Observable<List<CityEntity>> cachedFavouriteCities(Realm realm) {

        return cachedCities(realm)
                .flatMap(cities -> Observable.from(cities)
                        .filter(CityEntity::isFavourite).toList());
    }

    @Override
    public Observable<List<String>> offlineCitiesSlugs(Realm realm) {

        return realm.where(CityOfflineSlug.class)
                .findAll()
                .asObservable()
                .flatMap(results -> Observable.from(results)
                        .map(UtilityHelper::cityIdFactory).toList());
    }

    @Override
    public Observable<List<String>> getOfflineCitiesSlugs(Realm realm) {

        mOfflineSlugsSubject = BehaviorSubject.create();
        offlineCitiesSlugs(realm).subscribe(mOfflineSlugsSubject);
        return mOfflineSlugsSubject.asObservable();
    }

    @Override
    public Observable<List<CityOfflineEntity>> offlineCities(Realm realm) {

        Observable<List<CityOfflineEntity>> observableCities = realm.where(CityOffline.class)
                .findAll()
                .asObservable()
                .flatMap(results -> Observable.from(results)
                        .map(RepositoryHelpers::cityOfflineEntityFactory).toList())
                .map(cities -> {
                    Realm mapRealm = getRealm();
                    for(CityOfflineEntity cityOfflineEntity : cities) {
                        RealmResults<CityOfflineImage> citiesImages = mapRealm.where(CityOfflineImage.class).equalTo(ConstantValues.SLUG_COLUMN_NAME, cityOfflineEntity.getSlug()).findAll();

                        if(citiesImages.size() > 0)
                            UtilityHelper.setupImageFromDatabase(citiesImages.first(), cityOfflineEntity);
                    }

                    UtilityHelper.closeDatabase(mapRealm);
                    return cities;
                });

        return Observable.zip(observableCities, getFavouriteCitiesSlugs(realm), getOfflineCitiesSlugs(realm),
                UtilityHelper::setupOfflineFavouriteOfflineItems);
    }

    protected Observable<CityOfflineEntity> getOfflineCitiesFromApiImages(Observable<CityOfflineEntity> observableOfflineCities) {
        return observableOfflineCities
                .flatMap((cityOfflineEntity) -> getCityImageFromApi(cityOfflineEntity.getSlug()),
                        (cityOfflineEntity, imageResponseBodyEntity) -> {
                            if (imageResponseBodyEntity != null && imageResponseBodyEntity.isData()) {
                                Realm innerRealm = getRealm();

                                RepositoryHelpers.saveImageToDatabase(innerRealm, imageResponseBodyEntity.getSlug(),
                                        BitmapFactory.decodeByteArray(imageResponseBodyEntity.getImageData(), 0,
                                                imageResponseBodyEntity.getImageData().length));

                                UtilityHelper.closeDatabase(innerRealm);
                            }
                            return cityOfflineEntity;
                        });
    }

    protected Observable<List<CityOfflineEntity>> getOfflineCitiesFromApi(Realm realm, List<String> citiesSlugs, boolean cleanAll) {
        JSONObject object = new JSONObject();
        String jsonInput;

        try {
            object.put(ConstantValues.API_SEARCH_PARAM, new JSONArray(citiesSlugs));
            jsonInput = object.toString();
        } catch (JSONException e) {
            jsonInput = "{}";
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(ConstantValues.HTTP_DATA_TYPE), jsonInput);
        Observable<CityOfflineEntity> observableOfflineCities = getSearchOfflineCitiesFromApi(realm, body, cleanAll).flatMap(Observable::from);

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
    public Observable<List<CityOfflineEntity>> offlineCitiesFromApi(Realm realm, List<String> offlineCitiesSlugs, boolean cleanAll) {
        return getOfflineCitiesFromApi(realm, offlineCitiesSlugs, cleanAll);
    }

    @Override
    public void addCitiesToCache(List<CityEntity> cities) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if (realm != null) {
            realm.beginTransaction();

            for(CityEntity entity : cities)
                RepositoryHelpers.setupCity(realm, entity);

            realm.commitTransaction();
            UtilityHelper.closeDatabase(realm);
        }
    }

    public void removeAllCachedCities() {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if (realm != null) {
            realm.beginTransaction();
            realm.delete(City.class);
            realm.commitTransaction();
            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void addOfflineCitySlug(String slug) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if(realm != null) {
            RealmResults<CityFavouriteSlug> results = realm.where(CityFavouriteSlug.class).equalTo(ConstantValues.SLUG_COLUMN_NAME, slug).findAll();

            if (results.size() < 1) {
                realm.beginTransaction();

                CityOfflineSlug city = realm.createObject(CityOfflineSlug.class);
                int nextID = (realm.where(CityOfflineSlug.class).max(ConstantValues.ID_COLUMN_NAME).intValue() + 1);
                city.setId(nextID);
                city.setSlug(slug);

                realm.commitTransaction();
            }

            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void addOfflineCity(CityOfflineEntity city) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if (realm != null) {
            realm.beginTransaction();
            RepositoryHelpers.setupOfflineCity(realm, city);
            realm.commitTransaction();

            UtilityHelper.closeDatabase(realm);
        }
    }

    private void removeOfflineCitySlugs(Realm realm, String slug) {
        if(realm != null) {
            RealmResults<CityOfflineSlug> slugsResults = realm.where(CityOfflineSlug.class).equalTo(ConstantValues.SLUG_COLUMN_NAME, slug).findAll();

            if (slugsResults.size() > 0) {
                realm.beginTransaction();
                slugsResults.deleteAllFromRealm();
                realm.commitTransaction();
            }
        }
    }

    @Override
    public void removeOfflineCity(String slug) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if (realm != null) {
            RealmResults<CityOffline> results = realm.where(CityOffline.class).equalTo(ConstantValues.SLUG_COLUMN_NAME, slug).findAll();

            if(results.size() > 0) {
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.commitTransaction();
            }

            removeOfflineCitySlugs(realm, slug);
            removeOfflineCityImage(realm, slug);
            removeAllOfflineCityPlacesToWork(realm, slug);

            UtilityHelper.closeDatabase(realm);
        }
    }

    private void removeAllOfflineCitiesSlugs(Realm realm) {
        realm.beginTransaction();
        realm.delete(CityOfflineSlug.class);
        realm.commitTransaction();
    }

    private void removeAllOfflineCities(Realm realm) {
        realm.beginTransaction();
        realm.delete(CityOffline.class);
        realm.commitTransaction();
    }

    private void removeAllOfflineCityImages(Realm realm) {
        realm.beginTransaction();
        realm.delete(CityOfflineImage.class);
        realm.commitTransaction();
    }

    private void removeAllOfflineCityPlacesToWork(Realm realm) {
        realm.beginTransaction();
        realm.delete(CityOfflinePlaceToWork.class);
        realm.commitTransaction();
    }

    @Override
    public void removeAllOfflineCitiesData() {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if(realm != null) {
            removeAllOfflineCitiesSlugs(realm);
            removeAllOfflineCities(realm);
            removeAllOfflineCityImages(realm);
            removeAllOfflineCityPlacesToWork(realm);
            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void addOfflineCities(List<CityOfflineEntity> cities) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if (realm != null) {
            realm.beginTransaction();

            for(CityOfflineEntity entity : cities) {
                RepositoryHelpers.setupOfflineCity(realm, entity);
                RepositoryHelpers.setupImage(realm, entity);
            }

            realm.commitTransaction();
            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void removeAllOfflineCities() {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if(realm != null) {
            removeAllOfflineCities(realm);

            if(!realm.isClosed())
                realm.close();
        }
    }

    public void addOfflineCityImage(String citySlug, Bitmap bitmapImage) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if(realm != null) {
            realm.beginTransaction();

            CityOfflineImage cityOfflineImage = realm.createObject(CityOfflineImage.class);
            int nextID = (realm.where(CityOfflineImage.class).max(ConstantValues.ID_COLUMN_NAME).intValue() + 1);
            cityOfflineImage.setId(nextID);
            cityOfflineImage.setSlug(citySlug);
            cityOfflineImage.setImageData(UtilityHelper.bitmapToByteArray(bitmapImage));

            realm.commitTransaction();

            if(!realm.isClosed())
                realm.close();
        }
    }

    public Observable<List<ImageResponseBodyEntity>> offlineCityImage(Realm realm, String citySlug) {
        return realm.where(CityOfflineImage.class)
                .equalTo(ConstantValues.SLUG_COLUMN_NAME, citySlug)
                .findAll()
                .asObservable()
                .map(results -> UtilityHelper.processImageFromDatabase(citySlug, results));
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

        return Observable.zip(responses, (imageResponses) -> UtilityHelper.processImagesFromApi(getRealm(), imageResponses));
    }

    @Override
    public void removeOfflineCityImage(Realm realm, String slug) {
        if(realm != null) {
            RealmResults<CityOfflineImage> results = realm.where(CityOfflineImage.class)
                    .equalTo(ConstantValues.SLUG_COLUMN_NAME, slug, Case.INSENSITIVE).findAll();

            if(results.size() > 0) {
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.commitTransaction();
            }

            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void removeAllOfflineCitiesImages() {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if(realm != null) {
            removeAllOfflineCityImages(realm);
            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void addFavouriteCitySlugs(String slug) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if(realm != null) {
            RealmResults<CityFavouriteSlug> results = realm.where(CityFavouriteSlug.class).equalTo(ConstantValues.SLUG_COLUMN_NAME, slug).findAll();

            if (results.size() < 1) {
                realm.beginTransaction();

                CityFavouriteSlug city = realm.createObject(CityFavouriteSlug.class);
                int nextID = (realm.where(CityFavouriteSlug.class).max(ConstantValues.ID_COLUMN_NAME).intValue() + 1);
                city.setId(nextID);
                city.setSlug(slug);

                realm.commitTransaction();
            }

            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void removeFavouriteCitySlugs(String slug) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if(realm != null) {
            RealmResults<CityFavouriteSlug> results = realm.where(CityFavouriteSlug.class).equalTo(ConstantValues.SLUG_COLUMN_NAME, slug).findAll();

            if(results.size() > 0) {
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.commitTransaction();
            }

            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void removeAllFavouriteCitiesSlugs() {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if(realm != null) {
            realm.beginTransaction();
            realm.delete(CityFavouriteSlug.class);
            realm.commitTransaction();
            UtilityHelper.closeDatabase(realm);
        }
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

    @Override
    public Observable<List<CityPlaceToWorkEntity>> cityPlacesToWork(String citySlug) {
        return getCityPlacesToWork(citySlug).doOnNext(placesToWork -> {
                    removeAllCityPlacesToWork();
                    addCityPlacesToWorkToCache(placesToWork);
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
    public Observable<List<CityPlaceToWorkEntity>> cachedCityPlacesToWork(Realm realm, String citySlug) {
        return realm.where(CityPlaceToWork.class)
                .equalTo(ConstantValues.CITY_SLUG_COLUMN_NAME, citySlug)
                .findAll()
                .asObservable()
                .flatMap(results -> Observable.from(results)
                        .map(RepositoryHelpers::cityPlaceToWorkEntityFactory).toList());
    }

    @Override
    public Observable<List<CityPlaceToWorkEntity>> cachedCityPlacesToWork(Realm realm, String citySlug, String query) {
        List<String> queryList = createQueryList(query);
        RealmQuery<CityPlaceToWork> realmQuery = realm.where(CityPlaceToWork.class).beginGroup();

        for(int i = 0; i < queryList.size(); i++) {
            realmQuery = realmQuery
                    .contains(ConstantValues.NAME_COLUMN_NAME, queryList.get(i), Case.INSENSITIVE)
                    .or()
                    .contains(ConstantValues.SUB_NAME_COLUMN_NAME, queryList.get(i), Case.INSENSITIVE);

            if(i != (queryList.size() - 1))
                realmQuery = realmQuery.or();
        }

        return realmQuery.endGroup()
                .findAll()
                .asObservable()
                .flatMap(results -> Observable.from(results)
                        .map(RepositoryHelpers::cityPlaceToWorkEntityFactory).toList());
    }

    @Override
    public void addCityPlacesToWorkToCache(List<CityPlaceToWorkEntity> placesToWork) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if (realm != null) {
            realm.beginTransaction();

            for(CityPlaceToWorkEntity entity : placesToWork)
                RepositoryHelpers.setupCityPlaceToWork(realm, entity);

            realm.commitTransaction();
            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void removeAllCityPlacesToWork() {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if(realm != null) {
            realm.beginTransaction();
            realm.delete(CityPlaceToWork.class);
            realm.commitTransaction();
            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public Observable<List<CityOfflinePlaceToWorkEntity>> offlineCityPlacesToWork(Realm realm, String citySlug) {
        return realm.where(CityOfflinePlaceToWork.class)
                .equalTo(ConstantValues.CITY_SLUG_COLUMN_NAME, citySlug)
                .findAll()
                .asObservable()
                .flatMap(results -> Observable.from(results)
                        .map(RepositoryHelpers::cityOfflinePlaceToWorkEntityFactory).toList());
    }

    @Override
    public Observable<List<CityOfflinePlaceToWorkEntity>> offlineCityPlacesToWork(Realm realm, String citySlug, String query) {
        List<String> queryList = createQueryList(query);
        RealmQuery<CityOfflinePlaceToWork> realmQuery = realm.where(CityOfflinePlaceToWork.class).beginGroup();

        for(int i = 0; i < queryList.size(); i++) {
            realmQuery = realmQuery
                    .contains(ConstantValues.NAME_COLUMN_NAME, queryList.get(i), Case.INSENSITIVE)
                    .or()
                    .contains(ConstantValues.SUB_NAME_COLUMN_NAME, queryList.get(i), Case.INSENSITIVE);

            if(i != (queryList.size() - 1))
                realmQuery = realmQuery.or();
        }

        return realmQuery.endGroup()
                .equalTo(ConstantValues.CITY_SLUG_COLUMN_NAME, citySlug)
                .findAll()
                .asObservable()
                .flatMap(results -> Observable.from(results)
                        .map(RepositoryHelpers::cityOfflinePlaceToWorkEntityFactory).toList());
    }

    @Override
    public void addOfflineCityPlacesToWork(List<CityOfflinePlaceToWorkEntity> cityPlacesToWorkEntity) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if (realm != null) {
            realm.beginTransaction();

            for(CityOfflinePlaceToWorkEntity entity : cityPlacesToWorkEntity)
                RepositoryHelpers.setupCityOfflinePlaceToWork(realm, entity);

            realm.commitTransaction();
            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void addOfflineCityPlacesToWorkFromApi(List<CityPlaceToWorkEntity> cityPlacesToWorkEntity) {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if (realm != null) {
            realm.beginTransaction();

            for(CityPlaceToWorkEntity entity : cityPlacesToWorkEntity)
                RepositoryHelpers.setupCityOfflinePlaceToWork(realm, entity);

            realm.commitTransaction();
            UtilityHelper.closeDatabase(realm);
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
            Realm realm = getRealm();
            List<Boolean> placesToWorkResults = new ArrayList<>();

            if(placesToWorkResponses != null) {
                removeAllOfflineCityPlacesToWork();

                for(Object responseObject : placesToWorkResponses) {
                    List<CityPlaceToWorkEntity> placesToWorkList = (List<CityPlaceToWorkEntity>) responseObject;

                    if(placesToWorkList != null && !placesToWorkList.isEmpty()) {
                        addOfflineCityPlacesToWorkFromApi(placesToWorkList);
                        placesToWorkResults.add(true);
                    }
                    placesToWorkResults.add(false);
                }
            }

            UtilityHelper.closeDatabase(realm);
            return placesToWorkResults;
        });
    }

    @Override
    public void removeAllOfflineCityPlacesToWork(Realm realm, String citySlug) {
        if(realm != null) {
            RealmResults<CityOfflinePlaceToWork> results = realm.where(CityOfflinePlaceToWork.class)
                    .equalTo(ConstantValues.CITY_SLUG_COLUMN_NAME, citySlug, Case.INSENSITIVE).findAll();

            if(results.size() > 0) {
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.commitTransaction();
            }

            UtilityHelper.closeDatabase(realm);
        }
    }

    @Override
    public void removeAllOfflineCityPlacesToWork() {
        Realm realm = MyNomadLifeApplication.getDatabaseInstance(mApp);

        if(realm != null) {
            realm.beginTransaction();
            realm.delete(CityOfflinePlaceToWork.class);
            realm.commitTransaction();
            UtilityHelper.closeDatabase(realm);
        }
    }
}


