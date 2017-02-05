package com.marekhakala.mynomadlifeapp.Repository.Arguments;

public class PopulationArguments {

    private Long populationFrom = null;
    private Long populationTo = null;

    public PopulationArguments() {}

    public PopulationArguments(Long populationFrom, Long populationTo) {
        this.populationFrom = populationFrom;
        this.populationTo = populationTo;
    }

    public Long getPopulationFrom() {
        return populationFrom;
    }

    public void setPopulationFrom(Long populationFrom) {
        this.populationFrom = populationFrom;
    }

    public Long getPopulationTo() {
        return populationTo;
    }

    public void setPopulationTo(Long populationTo) {
        this.populationTo = populationTo;
    }
}
