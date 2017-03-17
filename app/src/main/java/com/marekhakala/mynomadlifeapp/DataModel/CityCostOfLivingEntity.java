package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityCostOfLivingEntity implements Parcelable {

    @Expose
    @SerializedName("nomad_cost")
    private Float nomadCost;

    @Expose
    @SerializedName("expat_cost_of_living")
    private Float expatCostOfLiving;

    @Expose
    @SerializedName("local_cost_of_living")
    private Float localCostOfLiving;

    @Expose
    @SerializedName("one_bedroom_apartment")
    private Float oneBedroomApartment;

    @Expose
    @SerializedName("hotel_room")
    private Float hotelRoom;

    @Expose
    @SerializedName("airbnb_apartment_month")
    private Float airbnbApartmentMonth;

    @Expose
    @SerializedName("airbnb_apartment_day")
    private Float airbnbApartmentDay;

    @Expose
    @SerializedName("coworking_space")
    private Float coworkingSpace;

    @Expose
    @SerializedName("coca_cola_in_cafe")
    private Float cocaColaInCafe;

    @Expose
    @SerializedName("pint_of_beer_in_bar")
    private Float pintOfBeerInBar;

    @Expose
    @SerializedName("cappucino_in_cafe")
    private Float cappucinoInCafe;

    public Float getNomadCost() {
        return nomadCost;
    }

    public void setNomadCost(Float nomadCost) {
        this.nomadCost = nomadCost;
    }

    public Float getExpatCostOfLiving() {
        return expatCostOfLiving;
    }

    public void setExpatCostOfLiving(Float expatCostOfLiving) {
        this.expatCostOfLiving = expatCostOfLiving;
    }

    public Float getLocalCostOfLiving() {
        return localCostOfLiving;
    }

    public void setLocalCostOfLiving(Float localCostOfLiving) {
        this.localCostOfLiving = localCostOfLiving;
    }

    public Float getOneBedroomApartment() {
        return oneBedroomApartment;
    }

    public void setOneBedroomApartment(Float oneBedroomApartment) {
        this.oneBedroomApartment = oneBedroomApartment;
    }

    public Float getHotelRoom() {
        return hotelRoom;
    }

    public void setHotelRoom(Float hotelRoom) {
        this.hotelRoom = hotelRoom;
    }

    public Float getAirbnbApartmentMonth() {
        return airbnbApartmentMonth;
    }

    public void setAirbnbApartmentMonth(Float airbnbApartmentMonth) {
        this.airbnbApartmentMonth = airbnbApartmentMonth;
    }

    public Float getAirbnbApartmentDay() {
        return airbnbApartmentDay;
    }

    public void setAirbnbApartmentDay(Float airbnbApartmentDay) {
        this.airbnbApartmentDay = airbnbApartmentDay;
    }

    public Float getCoworkingSpace() {
        return coworkingSpace;
    }

    public void setCoworkingSpace(Float coworkingSpace) {
        this.coworkingSpace = coworkingSpace;
    }

    public Float getCocaColaInCafe() {
        return cocaColaInCafe;
    }

    public void setCocaColaInCafe(Float cocaColaInCafe) {
        this.cocaColaInCafe = cocaColaInCafe;
    }

    public Float getPintOfBeerInBar() {
        return pintOfBeerInBar;
    }

    public void setPintOfBeerInBar(Float pintOfBeerInBar) {
        this.pintOfBeerInBar = pintOfBeerInBar;
    }

    public Float getCappucinoInCafe() {
        return cappucinoInCafe;
    }

    public void setCappucinoInCafe(Float cappucinoInCafe) {
        this.cappucinoInCafe = cappucinoInCafe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.nomadCost);
        dest.writeValue(this.expatCostOfLiving);
        dest.writeValue(this.localCostOfLiving);
        dest.writeValue(this.oneBedroomApartment);
        dest.writeValue(this.hotelRoom);
        dest.writeValue(this.airbnbApartmentMonth);
        dest.writeValue(this.airbnbApartmentDay);
        dest.writeValue(this.coworkingSpace);
        dest.writeValue(this.cocaColaInCafe);
        dest.writeValue(this.pintOfBeerInBar);
        dest.writeValue(this.cappucinoInCafe);
    }

    public CityCostOfLivingEntity() {
    }

    protected CityCostOfLivingEntity(Parcel in) {
        this.nomadCost = (Float) in.readValue(Double.class.getClassLoader());
        this.expatCostOfLiving = (Float) in.readValue(Double.class.getClassLoader());
        this.localCostOfLiving = (Float) in.readValue(Double.class.getClassLoader());
        this.oneBedroomApartment = (Float) in.readValue(Double.class.getClassLoader());
        this.hotelRoom = (Float) in.readValue(Double.class.getClassLoader());
        this.airbnbApartmentMonth = (Float) in.readValue(Double.class.getClassLoader());
        this.airbnbApartmentDay = (Float) in.readValue(Double.class.getClassLoader());
        this.coworkingSpace = (Float) in.readValue(Double.class.getClassLoader());
        this.cocaColaInCafe = (Float) in.readValue(Double.class.getClassLoader());
        this.pintOfBeerInBar = (Float) in.readValue(Double.class.getClassLoader());
        this.cappucinoInCafe = (Float) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<CityCostOfLivingEntity> CREATOR = new Creator<CityCostOfLivingEntity>() {
        @Override
        public CityCostOfLivingEntity createFromParcel(Parcel source) {
            return new CityCostOfLivingEntity(source);
        }

        @Override
        public CityCostOfLivingEntity[] newArray(int size) {
            return new CityCostOfLivingEntity[size];
        }
    };
}
