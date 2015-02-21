package io.elapse.scores.models;

import com.google.gson.annotations.SerializedName;

public class Player {

    public static class Fields {
        public static final String ID = "id";
        public static final String FULL_NAME = "full_name";
    }

    @SerializedName(Fields.ID)
    private Long mId;

    @SerializedName(Fields.FULL_NAME)
    private String mFullName;

    public String getFullName() {
        return mFullName;
    }
}
