package com.marekhakala.mynomadlifeapp.Repository.Arguments;

public class OtherFiltersArguments {

    private Integer safetyFrom = null;
    private Integer nightlifeFrom = null;
    private Integer placesToWorkFrom = null;
    private Integer funFrom = null;
    private Integer englishSpeakingFrom = null;
    private Float startupScoreFrom = null;
    private Integer friendlyToForeignersFrom = null;
    private Integer femaleFriendlyFrom = null;
    private Integer gayFriendlyFrom = null;

    public OtherFiltersArguments() {}

    public OtherFiltersArguments(Integer safetyFrom, Integer nightlifeFrom, Integer placesToWorkFrom,
                                 Integer funFrom, Integer englishSpeakingFrom, Float startupScoreFrom,
                                 Integer friendlyToForeignersFrom, Integer femaleFriendlyFrom, Integer gayFriendlyFrom) {
        this.safetyFrom = safetyFrom;
        this.nightlifeFrom = nightlifeFrom;
        this.placesToWorkFrom = placesToWorkFrom;
        this.funFrom = funFrom;
        this.englishSpeakingFrom = englishSpeakingFrom;
        this.startupScoreFrom = startupScoreFrom;
        this.friendlyToForeignersFrom = friendlyToForeignersFrom;
        this.femaleFriendlyFrom = femaleFriendlyFrom;
        this.gayFriendlyFrom = gayFriendlyFrom;
    }

    public Integer getSafetyFrom() {
        return safetyFrom;
    }

    public void setSafetyFrom(Integer safetyFrom) {
        this.safetyFrom = safetyFrom;
    }

    public Integer getNightlifeFrom() {
        return nightlifeFrom;
    }

    public void setNightlifeFrom(Integer nightlifeFrom) {
        this.nightlifeFrom = nightlifeFrom;
    }

    public Integer getPlacesToWorkFrom() {
        return placesToWorkFrom;
    }

    public void setPlacesToWorkFrom(Integer placesToWorkFrom) {
        this.placesToWorkFrom = placesToWorkFrom;
    }

    public Integer getFunFrom() {
        return funFrom;
    }

    public void setFunFrom(Integer funFrom) {
        this.funFrom = funFrom;
    }

    public Integer getEnglishSpeakingFrom() {
        return englishSpeakingFrom;
    }

    public void setEnglishSpeakingFrom(Integer englishSpeakingFrom) {
        this.englishSpeakingFrom = englishSpeakingFrom;
    }

    public Float getStartupScoreFrom() {
        return startupScoreFrom;
    }

    public void setStartupScoreFrom(Float startupScoreFrom) {
        this.startupScoreFrom = startupScoreFrom;
    }

    public Integer getFriendlyToForeignersFrom() {
        return friendlyToForeignersFrom;
    }

    public void setFriendlyToForeignersFrom(Integer friendlyToForeignersFrom) {
        this.friendlyToForeignersFrom = friendlyToForeignersFrom;
    }

    public Integer getFemaleFriendlyFrom() {
        return femaleFriendlyFrom;
    }

    public void setFemaleFriendlyFrom(Integer femaleFriendlyFrom) {
        this.femaleFriendlyFrom = femaleFriendlyFrom;
    }

    public Integer getGayFriendlyFrom() {
        return gayFriendlyFrom;
    }

    public void setGayFriendlyFrom(Integer gayFriendlyFrom) {
        this.gayFriendlyFrom = gayFriendlyFrom;
    }
}
