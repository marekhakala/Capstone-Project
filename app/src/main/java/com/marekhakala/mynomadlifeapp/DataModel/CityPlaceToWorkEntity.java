package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityPlaceToWorkEntity implements Parcelable {

    private String citySlug;

    @Expose
    private String slug;

    @Expose
    private String name;

    @Expose
    @SerializedName("sub_name")
    private String subName;

    @Expose
    @SerializedName("coworking_type")
    private String coworkingType;

    @Expose
    @SerializedName("distance")
    private String distance;

    @Expose
    private String lat;

    @Expose
    private String lng;

    @Expose
    @SerializedName("data_url")
    private String dataUrl;

    @Expose
    @SerializedName("image_url")
    private String imageUrl;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.citySlug);
        dest.writeString(this.slug);
        dest.writeString(this.name);
        dest.writeString(this.subName);
        dest.writeString(this.coworkingType);
        dest.writeString(this.distance);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeString(this.dataUrl);
        dest.writeString(this.imageUrl);
    }

    public CityPlaceToWorkEntity() {
    }

    protected CityPlaceToWorkEntity(Parcel in) {
        this.citySlug = in.readString();
        this.slug = in.readString();
        this.name = in.readString();
        this.subName = in.readString();
        this.coworkingType = in.readString();
        this.distance = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
        this.dataUrl = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<CityPlaceToWorkEntity> CREATOR = new Creator<CityPlaceToWorkEntity>() {
        @Override
        public CityPlaceToWorkEntity createFromParcel(Parcel source) {
            return new CityPlaceToWorkEntity(source);
        }

        @Override
        public CityPlaceToWorkEntity[] newArray(int size) {
            return new CityPlaceToWorkEntity[size];
        }
    };
}
