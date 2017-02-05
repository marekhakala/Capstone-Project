package com.marekhakala.mynomadlifeapp.RealmDataModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CityOfflinePlaceToWork extends RealmObject {

    @PrimaryKey
    private long id;

    private String citySlug;
    private String slug;
    private String name;
    private String subName;
    private String coworkingType;
    private String distance;
    private String lat;
    private String lng;
    private String dataUrl;
    private String imageUrl;

    public CityOfflinePlaceToWork() {}

    public CityOfflinePlaceToWork(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCitySlug() {
        return citySlug;
    }

    public void setCitySlug(String citySlug) {
        this.citySlug = citySlug;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getCoworkingType() {
        return coworkingType;
    }

    public void setCoworkingType(String coworkingType) {
        this.coworkingType = coworkingType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
