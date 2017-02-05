package com.marekhakala.mynomadlifeapp.Repository.Arguments;

public class InternetSpeedArguments {

    private Integer internetSpeedFrom = null;
    private Integer internetSpeedTo = null;

    public InternetSpeedArguments() {}

    public InternetSpeedArguments(Integer internetSpeedFrom, Integer internetSpeedTo) {
        this.internetSpeedFrom = internetSpeedFrom;
        this.internetSpeedTo = internetSpeedTo;
    }

    public Integer getInternetSpeedFrom() {
        return internetSpeedFrom;
    }

    public void setInternetSpeedFrom(Integer internetSpeedFrom) {
        this.internetSpeedFrom = internetSpeedFrom;
    }

    public Integer getInternetSpeedTo() {
        return internetSpeedTo;
    }

    public void setInternetSpeedTo(Integer internetSpeedTo) {
        this.internetSpeedTo = internetSpeedTo;
    }
}
