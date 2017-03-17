package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityScoresEntity implements Parcelable {

    @Expose
    @SerializedName("nomad_score")
    private Float nomadScore;

    @Expose
    @SerializedName("life_score")
    private Float lifeScore;

    @Expose
    private Integer cost;

    @Expose
    private Integer internet;

    @Expose
    private Integer fun;

    @Expose
    private Integer safety;

    @Expose
    private Integer peace;

    @Expose
    private Integer nightlife;

    @Expose
    @SerializedName("free_wifi_in_city")
    private Integer freeWifiInCity;

    @Expose
    @SerializedName("places_to_work")
    private Integer placesToWork;

    @Expose
    @SerializedName("ac_or_heating")
    private Integer acOrHeating;

    @Expose
    @SerializedName("friendly_to_foreigners")
    private Integer friendlyToForeigners;

    @Expose
    @SerializedName("female_friendly")
    private Integer femaleFriendly;

    @Expose
    @SerializedName("gay_friendly")
    private Integer gayFriendly;

    @Expose
    @SerializedName("startup_score")
    private Float startupScore;

    @Expose
    @SerializedName("english_speaking")
    private Integer englishSpeaking;

    public Float getNomadScore() {
        return nomadScore;
    }

    public void setNomadScore(Float nomadScore) {
        this.nomadScore = nomadScore;
    }

    public Float getLifeScore() {
        return lifeScore;
    }

    public void setLifeScore(Float lifeScore) {
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

    public Float getStartupScore() {
        return startupScore;
    }

    public void setStartupScore(Float startupScore) {
        this.startupScore = startupScore;
    }

    public Integer getEnglishSpeaking() {
        return englishSpeaking;
    }

    public void setEnglishSpeaking(Integer englishSpeaking) {
        this.englishSpeaking = englishSpeaking;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.nomadScore);
        dest.writeValue(this.lifeScore);
        dest.writeValue(this.cost);
        dest.writeValue(this.internet);
        dest.writeValue(this.fun);
        dest.writeValue(this.safety);
        dest.writeValue(this.peace);
        dest.writeValue(this.nightlife);
        dest.writeValue(this.freeWifiInCity);
        dest.writeValue(this.placesToWork);
        dest.writeValue(this.acOrHeating);
        dest.writeValue(this.friendlyToForeigners);
        dest.writeValue(this.femaleFriendly);
        dest.writeValue(this.gayFriendly);
        dest.writeValue(this.startupScore);
        dest.writeValue(this.englishSpeaking);
    }

    public CityScoresEntity() {
    }

    protected CityScoresEntity(Parcel in) {
        this.nomadScore = (Float) in.readValue(Float.class.getClassLoader());
        this.lifeScore = (Float) in.readValue(Float.class.getClassLoader());
        this.cost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.internet = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fun = (Integer) in.readValue(Integer.class.getClassLoader());
        this.safety = (Integer) in.readValue(Integer.class.getClassLoader());
        this.peace = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nightlife = (Integer) in.readValue(Integer.class.getClassLoader());
        this.freeWifiInCity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.placesToWork = (Integer) in.readValue(Integer.class.getClassLoader());
        this.acOrHeating = (Integer) in.readValue(Integer.class.getClassLoader());
        this.friendlyToForeigners = (Integer) in.readValue(Integer.class.getClassLoader());
        this.femaleFriendly = (Integer) in.readValue(Integer.class.getClassLoader());
        this.gayFriendly = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startupScore = (Float) in.readValue(Float.class.getClassLoader());
        this.englishSpeaking = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<CityScoresEntity> CREATOR = new Parcelable.Creator<CityScoresEntity>() {
        @Override
        public CityScoresEntity createFromParcel(Parcel source) {
            return new CityScoresEntity(source);
        }

        @Override
        public CityScoresEntity[] newArray(int size) {
            return new CityScoresEntity[size];
        }
    };
}
