package com.marekhakala.mynomadlifeapp.Repository;

import android.graphics.Bitmap;

import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflinePlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ImageResponseBodyEntity;

import java.util.List;

import io.realm.Realm;
import rx.Observable;

public interface IMyNomadLifeRepository {

    Realm getRealm();

    // Cities
    Observable<CitiesResultEntity> cities(Realm realm, boolean cleanAdd, Integer page);
    Observable<List<CityEntity>> cachedCities(Realm realm);
    Observable<List<CityEntity>> searchCities(Realm realm, Integer page, String query);

    // Cities - Favourites
    Observable<List<String>> favouriteCitiesSlugs(Realm realm);
    Observable<List<String>> getFavouriteCitiesSlugs(Realm realm);
    Observable<List<CityEntity>> favouriteCities(Realm realm, List<String> citiesSlugs);
    Observable<List<CityEntity>> cachedFavouriteCities(Realm realm);

    // Cities - Offline
    Observable<List<String>> offlineCitiesSlugs(Realm realm);
    Observable<List<String>> getOfflineCitiesSlugs(Realm realm);
    Observable<List<CityOfflineEntity>> offlineCities(Realm realm);
    Observable<List<CityOfflineEntity>> offlineCitiesFromApi(Realm realm, List<String> citiesSlugs, boolean cleanAll);

    // Cities - Cache
    void addCitiesToCache(List<CityEntity> cities);
    void removeAllCachedCities();

    // Cities - Offline
    void addOfflineCitySlug(String slug);
    void addOfflineCity(CityOfflineEntity city);
    void removeOfflineCity(String slug);
    void removeAllOfflineCitiesData();
    void addOfflineCities(List<CityOfflineEntity> cities);
    void removeAllOfflineCities();

    void addOfflineCityImage(String citySlug, Bitmap bitmapImage);
    Observable<List<ImageResponseBodyEntity>> offlineCityImage(Realm realm, String citySlug);
    Observable<List<Boolean>> updateAllOfflineCitiesImages(List<String> citySlugs);
    void removeOfflineCityImage(Realm realm, String slug);
    void removeAllOfflineCitiesImages();

    // Cities - Favourite
    void addFavouriteCitySlugs(String slug);
    void removeFavouriteCitySlugs(String slug);
    void removeAllFavouriteCitiesSlugs();

    // Cities - Places to work
    Observable<List<CityPlaceToWorkEntity>> cityPlacesToWork(String citySlug);
    Observable<List<CityPlaceToWorkEntity>> cityPlacesToWork(String citySlug, String query);
    Observable<List<CityPlaceToWorkEntity>> cachedCityPlacesToWork(Realm realm, String citySlug);
    Observable<List<CityPlaceToWorkEntity>> cachedCityPlacesToWork(Realm realm, String citySlug, String query);
    void addCityPlacesToWorkToCache(List<CityPlaceToWorkEntity> placesToWork);
    void removeAllCityPlacesToWork();

    // Cities - Offline - Places to Work
    Observable<List<CityOfflinePlaceToWorkEntity>> offlineCityPlacesToWork(Realm realm, String citySlug);
    Observable<List<CityOfflinePlaceToWorkEntity>> offlineCityPlacesToWork(Realm realm, String citySlug, String query);
    void addOfflineCityPlacesToWork(List<CityOfflinePlaceToWorkEntity> cityOfflinePlaceToWorkEntity);
    void addOfflineCityPlacesToWorkFromApi(List<CityPlaceToWorkEntity> cityPlacesToWorkEntity);
    Observable<List<Boolean>> updateAllOfflineCitiesPlacesToWork(List<String> citySlugs);
    void removeAllOfflineCityPlacesToWork(Realm realm, String slug);
    void removeAllOfflineCityPlacesToWork();
}
