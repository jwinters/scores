package io.lose.scores.models;

import com.google.gson.annotations.SerializedName;

public class Progress {

    public static class Fields {
        public static final String CLOCK_LABEL = "clock_label";
    }

    @SerializedName(Fields.CLOCK_LABEL)
    private String mClockLabel;

    public String getClockLabel() {
        return mClockLabel;
    }
}
