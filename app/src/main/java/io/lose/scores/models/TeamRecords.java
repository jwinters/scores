package io.lose.scores.models;

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

    public Long getHomeOvertimeLosses() {
        return mHome != null ? mHome.getOvertimeLosses() : null;
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

    public Float getHomeFaceoffWinningPercentage() {
        return mHome != null ? mHome.getFaceoffWinningPercentage() : null;
    }

    public Long getHomePowerPlayGoals() {
        return mHome != null ? mHome.getPowerPlayGoals() : null;
    }

    public Long getHomePowerPlayOpportunities() {
        return mHome != null ? mHome.getPowerPlayOpportunities() : null;
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

    public Long getAwayOvertimeLosses() {
        return mAway != null ? mAway.getOvertimeLosses() : null;
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

    public Float getAwayFaceoffWinningPercentage() {
        return mAway != null ? mAway.getFaceoffWinningPercentage() : null;
    }

    public Long getAwayPowerPlayGoals() {
        return mAway != null ? mAway.getPowerPlayGoals() : null;
    }

    public Long getAwayPowerPlayOpportunities() {
        return mAway != null ? mAway.getPowerPlayOpportunities() : null;
    }

    public Long getAwayHits() {
        return mAway != null ? mAway.getHits() : null;
    }
}
