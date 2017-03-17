package com.marekhakala.mynomadlifeapp.DataModel;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marekhakala.mynomadlifeapp.Database.CitiesContract;

public class CityEntity implements Parcelable {

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
    private Float costPerMonth;

    @Expose
    @SerializedName("internet_speed")
    private Integer internetSpeed;

    @Expose
    @SerializedName("population")
    private Float population;

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
    private Float cityCurrencyRate;

    @Expose
    private CityScoresEntity scores;

    @Expose
    @SerializedName("cost_of_living")
    private CityCostOfLivingEntity costOfLiving;

    private boolean favourite;

    private boolean offline;

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

    public Float getCostPerMonth() {
        return costPerMonth;
    }

    public void setCostPerMonth(Float costPerMonth) {
        this.costPerMonth = costPerMonth;
    }

    public Integer getInternetSpeed() {
        return internetSpeed;
    }

    public void setInternetSpeed(Integer internetSpeed) {
        this.internetSpeed = internetSpeed;
    }

    public Float getPopulation() {
        return population;
    }

    public void setPopulation(Float population) {
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

    public Float getCityCurrencyRate() {
        return cityCurrencyRate;
    }

    public void setCityCurrencyRate(Float cityCurrencyRate) {
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

    public CityEntity() {
    }

    protected CityEntity(Parcel in) {
        this.slug = in.readString();
        this.region = in.readString();
        this.country = in.readString();
        this.temperatureC = (Integer) in.readValue(Integer.class.getClassLoader());
        this.temperatureF = (Integer) in.readValue(Integer.class.getClassLoader());
        this.humidity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.rank = (Integer) in.readValue(Integer.class.getClassLoader());
        this.costPerMonth = (Float) in.readValue(Float.class.getClassLoader());
        this.internetSpeed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.population = (Float) in.readValue(Float.class.getClassLoader());
        this.genderRatio = in.readString();
        this.religious = in.readString();
        this.cityCurrency = in.readString();
        this.cityCurrencyRate = (Float) in.readValue(Float.class.getClassLoader());
        this.scores = in.readParcelable(CityScoresEntity.class.getClassLoader());
        this.costOfLiving = in.readParcelable(CityCostOfLivingEntity.class.getClassLoader());
        this.favourite = in.readByte() != 0;
        this.offline = in.readByte() != 0;
    }

    public ContentValues getValues() {
        ContentValues values = new ContentValues();

        // General
        values.put(CitiesContract.Cities.CITY_SLUG, this.slug);
        values.put(CitiesContract.Cities.CITY_REGION, this.region);
        values.put(CitiesContract.Cities.CITY_COUNTRY, this.country);
        values.put(CitiesContract.Cities.CITY_TEMPERATURE_C, this.temperatureC);
        values.put(CitiesContract.Cities.CITY_TEMPERATURE_F, this.temperatureF);
        values.put(CitiesContract.Cities.CITY_HUMIDITY, this.humidity);
        values.put(CitiesContract.Cities.CITY_RANK, this.rank);
        values.put(CitiesContract.Cities.CITY_COST_PER_MONTH, this.costPerMonth);
        values.put(CitiesContract.Cities.CITY_INTERNET_SPEED, this.internetSpeed);
        values.put(CitiesContract.Cities.CITY_POPULATION, this.population);
        values.put(CitiesContract.Cities.CITY_GENDER_RATIO, this.genderRatio);
        values.put(CitiesContract.Cities.CITY_RELIGIOUS, this.religious);
        values.put(CitiesContract.Cities.CITY_CURRENCY, this.cityCurrency);
        values.put(CitiesContract.Cities.CITY_CURRENCY_RATE, this.cityCurrencyRate);

        // Scores
        values.put(CitiesContract.Cities.CITY_SCORE_NOMAD_SCORE, this.scores.getNomadScore());
        values.put(CitiesContract.Cities.CITY_SCORE_LIFE_SCORE, this.scores.getLifeScore());
        values.put(CitiesContract.Cities.CITY_SCORE_COST, this.scores.getCost());
        values.put(CitiesContract.Cities.CITY_SCORE_INTERNET, this.scores.getInternet());
        values.put(CitiesContract.Cities.CITY_SCORE_FUN, this.scores.getFun());
        values.put(CitiesContract.Cities.CITY_SCORE_SAFETY, this.scores.getSafety());
        values.put(CitiesContract.Cities.CITY_SCORE_PEACE, this.scores.getPeace());
        values.put(CitiesContract.Cities.CITY_SCORE_NIGHTLIFE, this.scores.getNightlife());
        values.put(CitiesContract.Cities.CITY_SCORE_FREE_WIFI_IN_CITY, this.scores.getFreeWifiInCity());
        values.put(CitiesContract.Cities.CITY_SCORE_PLACES_TO_WORK, this.scores.getPlacesToWork());
        values.put(CitiesContract.Cities.CITY_SCORE_AC_OR_HEATING, this.scores.getAcOrHeating());
        values.put(CitiesContract.Cities.CITY_SCORE_FRIENDLY_TO_FOREIGNERS, this.scores.getFriendlyToForeigners());
        values.put(CitiesContract.Cities.CITY_SCORE_FEMALE_FRIENDLY, this.scores.getFemaleFriendly());
        values.put(CitiesContract.Cities.CITY_SCORE_GAY_FRIENDLY, this.scores.getGayFriendly());
        values.put(CitiesContract.Cities.CITY_SCORE_STARTUP_SCORE, this.scores.getStartupScore());
        values.put(CitiesContract.Cities.CITY_SCORE_ENGLISH_SPEAKING, this.scores.getEnglishSpeaking());

        // Cost of living
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_NOMAD_COST, this.costOfLiving.getNomadCost());
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_EXPAT_COST_OF_LIVING, this.costOfLiving.getExpatCostOfLiving());
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_LOCAL_COST_OF_LIVING, this.costOfLiving.getLocalCostOfLiving());
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_ONE_BEDROOM_APARTMENT, this.costOfLiving.getOneBedroomApartment());
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_HOTEL_ROOM, this.costOfLiving.getHotelRoom());
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_MONTH, this.costOfLiving.getAirbnbApartmentMonth());
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_AIRBNB_APARTMENT_DAY, this.costOfLiving.getAirbnbApartmentDay());
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_COWORKING_SPACE, this.costOfLiving.getCoworkingSpace());
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_COCA_COLA_IN_CAFE, this.costOfLiving.getCocaColaInCafe());
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_PINT_OF_BEER_IN_BAR, this.costOfLiving.getPintOfBeerInBar());
        values.put(CitiesContract.Cities.CITY_COST_OF_LIVING_CAPPUCCINO_IN_CAFE, this.costOfLiving.getCappucinoInCafe());

        return values;
    }

    public CityOfflineEntity offlineCopy() {
        CityOfflineEntity entity = new CityOfflineEntity();

        entity.setSlug(getSlug());
        entity.setRegion(getRegion());
        entity.setCountry(getCountry());
        entity.setTemperatureC(getTemperatureC());
        entity.setTemperatureF(getTemperatureF());
        entity.setHumidity(getHumidity());
        entity.setRank(getRank());
        entity.setCostPerMonth(getCostPerMonth());
        entity.setInternetSpeed(getInternetSpeed());
        entity.setPopulation(getPopulation());
        entity.setGenderRatio(getGenderRatio());
        entity.setReligious(getReligious());
        entity.setCityCurrency(getCityCurrency());
        entity.setCityCurrencyRate(getCityCurrencyRate());
        entity.setScores(getScores());
        entity.setCostOfLiving(getCostOfLiving());
        entity.setFavourite(isFavourite());
        entity.setOffline(isOffline());

        return entity;
    }

    public static final Creator<CityEntity> CREATOR = new Creator<CityEntity>() {
        @Override
        public CityEntity createFromParcel(Parcel source) {
            return new CityEntity(source);
        }

        @Override
        public CityEntity[] newArray(int size) {
            return new CityEntity[size];
        }
    };
}
