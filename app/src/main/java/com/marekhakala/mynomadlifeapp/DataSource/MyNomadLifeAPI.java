package com.marekhakala.mynomadlifeapp.DataSource;

import com.marekhakala.mynomadlifeapp.DataModel.CitiesOfflineResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlacesToWorkResultEntity;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MyNomadLifeAPI {
    @GET("cities")
    Observable<CitiesResultEntity> getCities(@Query("page") Integer page);

    @GET("cities")
    Observable<CitiesResultEntity> getCitiesFilter(@Query("page") Integer page, @Query("costpermonthfrom") Float costPerMonthFrom, @Query("costpermonthto") Float costPerMonthTo,
                                                   @Query("populationfrom") Long populationFrom, @Query("populationto") Long populationTo,
                                                   @Query("internetspeedfrom") Integer internetspeedFrom, @Query("internetspeedto") Integer internetSpeedTo,
                                                   @Query("safetyfrom") Integer safetyFrom, @Query("nightlifefrom") Integer nightlifeFrom,
                                                   @Query("placestoworkfrom") Integer placesToWorkFrom, @Query("funfrom") Integer funFrom,
                                                   @Query("englishspeakingfrom") Integer englishSpeakingFrom, @Query("startupscorefrom") Float startupScoreFrom,
                                                   @Query("friendlytoforeignersfrom") Integer friendlyToForeignersFrom, @Query("femalefriendlyfrom") Integer femaleFriendlyFrom,
                                                   @Query("gayfriendlyfrom") Integer gayFriendlyFrom);

    @GET("cities")
    Observable<CitiesResultEntity> getCitiesSearch(@Query("page") Integer page, @Query("search") String search);

    @POST("cities/search")
    Observable<CitiesResultEntity> getCitiesListSearch(@Body RequestBody params);

    @POST("cities/search")
    Observable<CitiesOfflineResultEntity> getCitiesOfflineListSearch(@Body RequestBody params);

    @GET("cities/{slug}/image")
    Observable<Response<ResponseBody>> getImage(@Path("slug") String slug);

    @GET("cities/{slug}")
    Observable<CityEntity> getCity(@Path("slug") String slug);

    @GET("cities/{slug}/coworking")
    Observable<CityPlacesToWorkResultEntity> getListOfPlacesToWork(@Path("slug") String slug);
}
