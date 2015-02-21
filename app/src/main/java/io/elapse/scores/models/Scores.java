package io.elapse.scores.models;

import com.google.gson.annotations.SerializedName;

public class Scores {

    public static class Fields {
        public static final String HOME = "home";
        public static final String AWAY = "away";
    }

    @SerializedName(Fields.HOME)
    private Score mHome;

    @SerializedName(Fields.AWAY)
    private Score mAway;

    public Long getHomeScore() {
        return mHome != null ? mHome.getScore() : null;
    }

    public Long getAwayScore() {
        return mAway != null ? mAway.getScore() : null;
    }
}
