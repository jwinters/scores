package io.lose.scores.models;

import com.google.gson.annotations.SerializedName;
import io.lose.scores.datasets.GoalTable;

import io.pivotal.arca.provider.ColumnName;

public class Goal {

    public static class Fields {
        public static final String ID = "id";
        public static final String MINUTE = "minute";
        public static final String SECOND = "second";
        public static final String PERIOD = "period";
        public static final String SEGMENT = "segment";
        public static final String SEGMENT_STRING = "segment_string";
        public static final String ASSIST1 = "assist1";
        public static final String ASSIST2 = "assist2";
        public static final String PLAYER = "player";
        public static final String TEAM = "team";
    }

    @ColumnName(GoalTable.Columns.ID)
    @SerializedName(Fields.ID)
    private Long mId;

    @ColumnName(GoalTable.Columns.MINUTE)
    @SerializedName(Fields.MINUTE)
    private Long mMinute;

    @ColumnName(GoalTable.Columns.SECOND)
    @SerializedName(Fields.SECOND)
    private Long mSecond;

    @ColumnName(GoalTable.Columns.PERIOD)
    @SerializedName(Fields.PERIOD)
    private Long mPeriod;

    @ColumnName(GoalTable.Columns.SEGMENT)
    @SerializedName(Fields.SEGMENT)
    private Long mSegment;

    @ColumnName(GoalTable.Columns.SEGMENT_STRING)
    @SerializedName(Fields.SEGMENT_STRING)
    private String mSegmentString;

    @SerializedName(Fields.PLAYER)
    private Player mPlayer;

    @ColumnName(GoalTable.Columns.PLAYER_NAME)
    public String getPlayerName() {
        return mPlayer != null ? mPlayer.getFullName() : null;
    }

    @SerializedName(Fields.ASSIST1)
    private Player mAssist1Player;

    @ColumnName(GoalTable.Columns.A1_PLAYER_NAME)
    public String getAssist1PlayerName() {
        return mAssist1Player != null ? mAssist1Player.getFullName() : null;
    }

    @SerializedName(Fields.ASSIST2)
    private Player mAssist2Player;

    @ColumnName(GoalTable.Columns.A2_PLAYER_NAME)
    public String getAssist2PlayerName() {
        return mAssist2Player != null ? mAssist2Player.getFullName() : null;
    }

    @SerializedName(Fields.TEAM)
    private Team mTeam;

    @ColumnName(GoalTable.Columns.TEAM_ID)
    public Long getTeamId() {
        return mTeam != null ? mTeam.getId() : null;
    }
}
