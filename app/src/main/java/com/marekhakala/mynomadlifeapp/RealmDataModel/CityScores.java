package com.marekhakala.mynomadlifeapp.RealmDataModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CityScores extends RealmObject {

    @PrimaryKey
    private long id;

    private Double nomadScore;
    private Double lifeScore;
    private Integer cost;
    private Integer internet;
    private Integer fun;
    private Integer safety;
    private Integer peace;
    private Integer nightlife;
    private Integer freeWifiInCity;
    private Integer placesToWork;
    private Integer acOrHeating;
    private Integer friendlyToForeigners;
    private Integer femaleFriendly;
    private Integer gayFriendly;
    private Double startupScore;
    private Integer englishSpeaking;

    public CityScores() {}

    public CityScores(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getNomadScore() {
        return nomadScore;
    }

    public void setNomadScore(Double nomadScore) {
        this.nomadScore = nomadScore;
    }

    public Double getLifeScore() {
        return lifeScore;
    }

    public void setLifeScore(Double lifeScore) {
        this.lifeScore = lifeScore;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getInternet() {
        return internet;
    }

    public void setInternet(Integer internet) {
        this.internet = internet;
    }

    public Integer getFun() {
        return fun;
    }

    public void setFun(Integer fun) {
        this.fun = fun;
    }

    public Integer getSafety() {
        return safety;
    }

    public void setSafety(Integer safety) {
        this.safety = safety;
    }

    public Integer getPeace() {
        return peace;
    }

    public void setPeace(Integer peace) {
        this.peace = peace;
    }

    public Integer getNightlife() {
        return nightlife;
    }

    public void setNightlife(Integer nightlife) {
        this.nightlife = nightlife;
    }

    public Integer getFreeWifiInCity() {
        return freeWifiInCity;
    }

    public void setFreeWifiInCity(Integer freeWifiInCity) {
        this.freeWifiInCity = freeWifiInCity;
    }

    public Integer getPlacesToWork() {
        return placesToWork;
    }

    public void setPlacesToWork(Integer placesToWork) {
        this.placesToWork = placesToWork;
    }

    public Integer getAcOrHeating() {
        return acOrHeating;
    }

    public void setAcOrHeating(Integer acOrHeating) {
        this.acOrHeating = acOrHeating;
    }

    public Integer getFriendlyToForeigners() {
        return friendlyToForeigners;
    }

    public void setFriendlyToForeigners(Integer friendlyToForeigners) {
        this.friendlyToForeigners = friendlyToForeigners;
    }

    public Integer getFemaleFriendly() {
        return femaleFriendly;
    }

    public void setFemaleFriendly(Integer femaleFriendly) {
        this.femaleFriendly = femaleFriendly;
    }

    public Integer getGayFriendly() {
        return gayFriendly;
    }

    public void setGayFriendly(Integer gayFriendly) {
        this.gayFriendly = gayFriendly;
    }

    public Double getStartupScore() {
        return startupScore;
    }

    public void setStartupScore(Double startupScore) {
        this.startupScore = startupScore;
    }

    public Integer getEnglishSpeaking() {
        return englishSpeaking;
    }

    public void setEnglishSpeaking(Integer englishSpeaking) {
        this.englishSpeaking = englishSpeaking;
    }
}
