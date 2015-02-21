package io.elapse.scores.models;

import com.google.gson.annotations.SerializedName;

public class Score {

    public static class Fields {
        public static final String SCORE = "score";
    }

    @SerializedName(Fields.SCORE)
    private Long mScore;

    public Long getScore() {
        return mScore;
    }

}
