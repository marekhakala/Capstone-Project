package com.marekhakala.mynomadlifeapp.RealmDataModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CityCostOfLiving extends RealmObject {

    @PrimaryKey
    private long id;

    private Double nomadCost;
    private Double expatCostOfLiving;
    private Double localCostOfLiving;
    private Double oneBedroomApartment;
    private Double hotelRoom;
    private Double airbnbApartmentMonth;
    private Double airbnbApartmentDay;
    private Double coworkingSpace;
    private Double cocaColaInCafe;
    private Double pintOfBeerInBar;
    private Double cappucinoInCafe;

    public CityCostOfLiving() {}

    public CityCostOfLiving(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
