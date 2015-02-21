package io.elapse.scores.models;

import com.google.gson.annotations.SerializedName;

public class TeamRecord {

    public static class Fields {
        public static final String ALIAS = "alias";
        public static final String WINS = "wins";
        public static final String LOSSES = "losses";
        public static final String OVERTIME_LOSSES = "overtime_losses";
        public static final String SHOTS = "shots";
        public static final String FACEOFFS_WON = "faceoffs_won";
        public static final String FACEOFFS_LOST = "faceoffs_lost";
        public static final String FACEOFF_WINNING_PERCENTAGE = "faceoff_winning_percentage";
        public static final String HITS = "hits";
    }

    @SerializedName(Fields.ALIAS)
    private String mAlias;

    public String getAlias() {
        return mAlias;
    }

    @SerializedName(Fields.WINS)
    private Long mWins;

    public Long getWins() {
        return mWins;
    }

    @SerializedName(Fields.LOSSES)
    private Long mLosses;

    public Long getLosses() {
        return mLosses;
    }

    @SerializedName(Fields.OVERTIME_LOSSES)
    private Long mOvertimeLosses;

    public Long getOvertimeLosses() {
        return mOvertimeLosses;
    }

    @SerializedName(Fields.SHOTS)
    private Long mShots;

    public Long getShots() {
        return mShots;
    }

    @SerializedName(Fields.FACEOFFS_WON)
    private Long mFaceoffsWon;

    public Long getFaceoffsWon() {
        return mFaceoffsWon;
    }

    @SerializedName(Fields.FACEOFFS_LOST)
    private Long mFaceoffsLost;

    public Long getFaceoffsLost() {
        return mFaceoffsLost;
    }

    @SerializedName(Fields.FACEOFF_WINNING_PERCENTAGE)
    private Float mFaceoffWinningPercentage;

    public Float getFaceoffWinningPercentage() {
        return mFaceoffWinningPercentage;
    }

    @SerializedName(Fields.HITS)
    private Long mHits;

    public Long getHits() {
        return mHits;
    }
}
