package com.marekhakala.mynomadlifeapp.RealmDataModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CityOffline extends RealmObject {

    @PrimaryKey
    private long id;

    private Boolean favourite;
    private Boolean offline;

    private String slug;
    private String region;
    private String country;
    private Integer temperatureC;
    private Integer temperatureF;
    private Integer humidity;
    private Integer rank;
    private Double costPerMonth;
    private Integer internetSpeed;
    private Double population;
    private String genderRatio;
    private String religious;
    private String cityCurrency;
    private Double cityCurrencyRate;
    private CityScores scores;
    private CityCostOfLiving costOfLiving;

    public CityOffline() {
        this.favourite = false;
        this.offline = true;
    }

    public CityOffline(long id) {
        this.id = id;
        this.favourite = false;
        this.offline = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
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

    public CityScores getScores() {
        return scores;
    }

    public void setScores(CityScores scores) {
        this.scores = scores;
    }

    public CityCostOfLiving getCostOfLiving() {
        return costOfLiving;
    }

    public void setCostOfLiving(CityCostOfLiving costOfLiving) {
        this.costOfLiving = costOfLiving;
    }
}
