package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityCostOfLivingEntity implements Parcelable {

    @Expose
    @SerializedName("nomad_cost")
    private Double nomadCost;

    @Expose
    @SerializedName("expat_cost_of_living")
    private Double expatCostOfLiving;

    @Expose
    @SerializedName("local_cost_of_living")
    private Double localCostOfLiving;

    @Expose
    @SerializedName("one_bedroom_apartment")
    private Double oneBedroomApartment;

    @Expose
    @SerializedName("hotel_room")
    private Double hotelRoom;

    @Expose
    @SerializedName("airbnb_apartment_month")
    private Double airbnbApartmentMonth;

    @Expose
    @SerializedName("airbnb_apartment_day")
    private Double airbnbApartmentDay;

    @Expose
    @SerializedName("coworking_space")
    private Double coworkingSpace;

    @Expose
    @SerializedName("coca_cola_in_cafe")
    private Double cocaColaInCafe;

    @Expose
    @SerializedName("pint_of_beer_in_bar")
    private Double pintOfBeerInBar;

    @Expose
    @SerializedName("cappucino_in_cafe")
    private Double cappucinoInCafe;

    public Double getNomadCost() {
        return nomadCost;
    }

    public void setNomadCost(Double nomadCost) {
        this.nomadCost = nomadCost;
    }

    public Double getExpatCostOfLiving() {
        return expatCostOfLiving;
    }

    public void setExpatCostOfLiving(Double expatCostOfLiving) {
        this.expatCostOfLiving = expatCostOfLiving;
    }

    public Double getLocalCostOfLiving() {
        return localCostOfLiving;
    }

    public void setLocalCostOfLiving(Double localCostOfLiving) {
        this.localCostOfLiving = localCostOfLiving;
    }

    public Double getOneBedroomApartment() {
        return oneBedroomApartment;
    }

    public void setOneBedroomApartment(Double oneBedroomApartment) {
        this.oneBedroomApartment = oneBedroomApartment;
    }

    public Double getHotelRoom() {
        return hotelRoom;
    }

    public void setHotelRoom(Double hotelRoom) {
        this.hotelRoom = hotelRoom;
    }

    public Double getAirbnbApartmentMonth() {
        return airbnbApartmentMonth;
    }

    public void setAirbnbApartmentMonth(Double airbnbApartmentMonth) {
        this.airbnbApartmentMonth = airbnbApartmentMonth;
    }

    public Double getAirbnbApartmentDay() {
        return airbnbApartmentDay;
    }

    public void setAirbnbApartmentDay(Double airbnbApartmentDay) {
        this.airbnbApartmentDay = airbnbApartmentDay;
    }

    public Double getCoworkingSpace() {
        return coworkingSpace;
    }

    public void setCoworkingSpace(Double coworkingSpace) {
        this.coworkingSpace = coworkingSpace;
    }

    public Double getCocaColaInCafe() {
        return cocaColaInCafe;
    }

    public void setCocaColaInCafe(Double cocaColaInCafe) {
        this.cocaColaInCafe = cocaColaInCafe;
    }

    public Double getPintOfBeerInBar() {
        return pintOfBeerInBar;
    }

    public void setPintOfBeerInBar(Double pintOfBeerInBar) {
        this.pintOfBeerInBar = pintOfBeerInBar;
    }

    public Double getCappucinoInCafe() {
        return cappucinoInCafe;
    }

    public void setCappucinoInCafe(Double cappucinoInCafe) {
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
        this.nomadCost = (Double) in.readValue(Double.class.getClassLoader());
        this.expatCostOfLiving = (Double) in.readValue(Double.class.getClassLoader());
        this.localCostOfLiving = (Double) in.readValue(Double.class.getClassLoader());
        this.oneBedroomApartment = (Double) in.readValue(Double.class.getClassLoader());
        this.hotelRoom = (Double) in.readValue(Double.class.getClassLoader());
        this.airbnbApartmentMonth = (Double) in.readValue(Double.class.getClassLoader());
        this.airbnbApartmentDay = (Double) in.readValue(Double.class.getClassLoader());
        this.coworkingSpace = (Double) in.readValue(Double.class.getClassLoader());
        this.cocaColaInCafe = (Double) in.readValue(Double.class.getClassLoader());
        this.pintOfBeerInBar = (Double) in.readValue(Double.class.getClassLoader());
        this.cappucinoInCafe = (Double) in.readValue(Double.class.getClassLoader());
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
