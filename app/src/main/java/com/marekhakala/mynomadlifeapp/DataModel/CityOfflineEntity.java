package com.marekhakala.mynomadlifeapp.DataModel;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityOfflineEntity implements Parcelable {

    @Expose
    private String slug;

    @Expose
    private String region;

    @Expose
    private String country;

    @Expose
    @SerializedName("temperature_c")
    private Integer temperatureC;

    @Expose
    @SerializedName("temperature_f")
    private Integer temperatureF;

    @Expose
    @SerializedName("humidity")
    private Integer humidity;

    @Expose
    private Integer rank;

    @Expose
    @SerializedName("cost_per_month")
    private Double costPerMonth;

    @Expose
    @SerializedName("internet_speed")
    private Integer internetSpeed;

    @Expose
    @SerializedName("population")
    private Double population;

    @Expose
    @SerializedName("gender_ratio")
    private String genderRatio;

    @Expose
    private String religious;

    @Expose
    @SerializedName("city_currency")
    private String cityCurrency;

    @Expose
    @SerializedName("city_currency_rate")
    private Double cityCurrencyRate;

    @Expose
    private CityScoresEntity scores;

    @Expose
    @SerializedName("cost_of_living")
    private CityCostOfLivingEntity costOfLiving;

    private Bitmap bitmapImage;

    private boolean favourite;

    private boolean offline;

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(Integer temperatureC) {
        this.temperatureC = temperatureC;
    }

    public Integer getTemperatureF() {
        return temperatureF;
    }

    public void setTemperatureF(Integer temperatureF) {
        this.temperatureF = temperatureF;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Double getCostPerMonth() {
        return costPerMonth;
    }

    public void setCostPerMonth(Double costPerMonth) {
        this.costPerMonth = costPerMonth;
    }

    public Integer getInternetSpeed() {
        return internetSpeed;
    }

    public void setInternetSpeed(Integer internetSpeed) {
        this.internetSpeed = internetSpeed;
    }

    public Double getPopulation() {
        return population;
    }

    public void setPopulation(Double population) {
        this.population = population;
    }

    public String getGenderRatio() {
        return genderRatio;
    }

    public void setGenderRatio(String genderRatio) {
        this.genderRatio = genderRatio;
    }

    public String getReligious() {
        return religious;
    }

    public void setReligious(String religious) {
        this.religious = religious;
    }

    public String getCityCurrency() {
        return cityCurrency;
    }

    public void setCityCurrency(String cityCurrency) {
        this.cityCurrency = cityCurrency;
    }

    public Double getCityCurrencyRate() {
        return cityCurrencyRate;
    }

    public void setCityCurrencyRate(Double cityCurrencyRate) {
        this.cityCurrencyRate = cityCurrencyRate;
    }

    public CityScoresEntity getScores() {
        return scores;
    }

    public void setScores(CityScoresEntity scores) {
        this.scores = scores;
    }

    public CityCostOfLivingEntity getCostOfLiving() {
        return costOfLiving;
    }

    public void setCostOfLiving(CityCostOfLivingEntity costOfLiving) {
        this.costOfLiving = costOfLiving;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.slug);
        dest.writeString(this.region);
        dest.writeString(this.country);
        dest.writeValue(this.temperatureC);
        dest.writeValue(this.temperatureF);
        dest.writeValue(this.humidity);
        dest.writeValue(this.rank);
        dest.writeValue(this.costPerMonth);
        dest.writeValue(this.internetSpeed);
        dest.writeValue(this.population);
        dest.writeString(this.genderRatio);
        dest.writeString(this.religious);
        dest.writeString(this.cityCurrency);
        dest.writeValue(this.cityCurrencyRate);
        dest.writeParcelable(this.scores, flags);
        dest.writeParcelable(this.costOfLiving, flags);
        dest.writeByte(this.favourite ? (byte) 1 : (byte) 0);
        dest.writeByte(this.offline ? (byte) 1 : (byte) 0);
    }

    public CityOfflineEntity() {
    }

    protected CityOfflineEntity(Parcel in) {
        this.slug = in.readString();
        this.region = in.readString();
        this.country = in.readString();
        this.temperatureC = (Integer) in.readValue(Integer.class.getClassLoader());
        this.temperatureF = (Integer) in.readValue(Integer.class.getClassLoader());
        this.humidity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.rank = (Integer) in.readValue(Integer.class.getClassLoader());
        this.costPerMonth = (Double) in.readValue(Double.class.getClassLoader());
        this.internetSpeed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.population = (Double) in.readValue(Double.class.getClassLoader());
        this.genderRatio = in.readString();
        this.religious = in.readString();
        this.cityCurrency = in.readString();
        this.cityCurrencyRate = (Double) in.readValue(Double.class.getClassLoader());
        this.scores = in.readParcelable(CityScoresEntity.class.getClassLoader());
        this.costOfLiving = in.readParcelable(CityCostOfLivingEntity.class.getClassLoader());
        this.favourite = in.readByte() != 0;
        this.offline = in.readByte() != 0;
    }

    public static final Creator<CityOfflineEntity> CREATOR = new Creator<CityOfflineEntity>() {
        @Override
        public CityOfflineEntity createFromParcel(Parcel source) {
            return new CityOfflineEntity(source);
        }

        @Override
        public CityOfflineEntity[] newArray(int size) {
            return new CityOfflineEntity[size];
        }
    };
}
