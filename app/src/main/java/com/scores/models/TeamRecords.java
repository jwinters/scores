package com.scores.models;

import com.google.gson.annotations.SerializedName;

public class TeamRecords {

    public static class Fields {
        public static final String HOME = "home";
        public static final String AWAY = "away";
    }

    @SerializedName(Fields.HOME)
    private TeamRecord mHome;

    public String getHomeAlias() {
        return mHome != null ? mHome.getAlias() : null;
    }

    public Long getHomeWins() {
        return mHome != null ? mHome.getWins() : null;
    }

    public Long getHomeLosses() {
        return mHome != null ? mHome.getLosses() : null;
    }

    public Long getHomeTies() {
        return mHome != null ? mHome.getTies() : null;
    }

    public Long getHomeShots() {
        return mHome != null ? mHome.getShots() : null;
    }

    public Long getHomeFaceoffsWon() {
        return mHome != null ? mHome.getFaceoffsWon() : null;
    }

    public Long getHomeFaceoffsLost() {
        return mHome != null ? mHome.getFaceoffsLost() : null;
    }

    public Long getHomeHits() {
        return mHome != null ? mHome.getHits() : null;
    }

    @SerializedName(Fields.AWAY)
    private TeamRecord mAway;

    public String getAwayAlias() {
        return mAway != null ? mAway.getAlias() : null;
    }

    public Long getAwayWins() {
        return mAway != null ? mAway.getWins() : null;
    }

    public Long getAwayLosses() {
        return mAway != null ? mAway.getLosses() : null;
    }

    public Long getAwayTies() {
        return mAway != null ? mAway.getTies() : null;
    }

    public Long getAwayShots() {
        return mAway != null ? mAway.getShots() : null;
    }

    public Long getAwayFaceoffsWon() {
        return mAway != null ? mAway.getFaceoffsWon() : null;
    }

    public Long getAwayFaceoffsLost() {
        return mAway != null ? mAway.getFaceoffsLost() : null;
    }

    public Long getAwayHits() {
        return mAway != null ? mAway.getHits() : null;
    }
}
