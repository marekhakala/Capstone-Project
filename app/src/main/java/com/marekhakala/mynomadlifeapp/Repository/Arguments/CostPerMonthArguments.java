package com.marekhakala.mynomadlifeapp.Repository.Arguments;

public class CostPerMonthArguments {

    private Float costPerMonthFrom = null;
    private Float costPerMonthTo = null;

    public CostPerMonthArguments() {}

    public CostPerMonthArguments(Float costPerMonthFrom, Float costPerMonthTo) {
        this.costPerMonthFrom = costPerMonthFrom;
        this.costPerMonthTo = costPerMonthTo;
    }

    public Float getCostPerMonthFrom() {
        return costPerMonthFrom;
    }

    public void setCostPerMonthFrom(Float costPerMonthFrom) {
        this.costPerMonthFrom = costPerMonthFrom;
    }

    public Float getCostPerMonthTo() {
        return costPerMonthTo;
    }

    public void setCostPerMonthTo(Float costPerMonthTo) {
        this.costPerMonthTo = costPerMonthTo;
    }
}
