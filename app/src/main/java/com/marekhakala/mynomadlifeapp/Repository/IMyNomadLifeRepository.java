package com.marekhakala.mynomadlifeapp.Repository;

import android.graphics.Bitmap;

import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflinePlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ExchangeRateEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ImageResponseBodyEntity;

import java.util.List;
import rx.Observable;

public interface IMyNomadLifeRepository {

    // Cities
    Observable<CitiesResultEntity> cities(boolean cleanAdd, Integer page);
    CitiesResultEntity cachedCities();
    Observable<List<CityEntity>> searchCities(Integer page, String query);

    // Cities - Favourites
    Observable<List<String>> favouriteCitiesSlugs();
    Observable<List<String>> getFavouriteCitiesSlugs();
    Observable<List<CityEntity>> favouriteCities(List<String> citiesSlugs);

    // Cities - Offline
    Observable<List<String>> offlineCitiesSlugs();
    Observable<List<String>> getOfflineCitiesSlugs();
    List<CityOfflineEntity> offlineCities();
    Observable<List<CityOfflineEntity>> offlineCitiesFromApi(List<String> citiesSlugs, boolean cleanAll);

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
    Observable<List<ImageResponseBodyEntity>> offlineCityImage(String citySlug);
    Observable<List<Boolean>> updateAllOfflineCitiesImages(List<String> citySlugs);
    void removeOfflineCityImage(String slug);
    void removeAllOfflineCitiesImages();

    // Cities - Favourite
    void addFavouriteCitySlugs(String slug);
    void removeFavouriteCitySlugs(String slug);
    void removeAllFavouriteCitiesSlugs();

    // Cities - Places to work
    Observable<List<CityPlaceToWorkEntity>> cityPlacesToWork(String citySlug, String query);
    List<CityPlaceToWorkEntity> cachedCityPlacesToWork(String citySlug, String query);
    void addCityPlacesToWorkToCache(List<CityPlaceToWorkEntity> placesToWork);
    void removeAllCityPlacesToWork();

    // Cities - Offline - Places to Work
    List<CityOfflinePlaceToWorkEntity> offlineCityPlacesToWork(String citySlug, String query);
    void addOfflineCityPlacesToWork(List<CityOfflinePlaceToWorkEntity> cityOfflinePlaceToWorkEntity);
    void addOfflineCityPlacesToWorkFromApi(List<CityPlaceToWorkEntity> cityPlacesToWorkEntity);
    Observable<List<Boolean>> updateAllOfflineCitiesPlacesToWork(List<String> citySlugs);
    void removeAllOfflineCitiesPlacesToWork(String slug);
    void removeAllOfflineCitiesPlacesToWork();

    // Exchange rates
    List<ExchangeRateEntity> exchangeRates();
}
