package com.scores.models;

import com.google.gson.annotations.SerializedName;
import com.scores.datasets.GameTable.Columns;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import io.pivotal.arca.provider.ColumnName;

public class Game {

    public static class Fields {
		public static final String ID = "id";
        public static final String EVENT_STATUS = "event_status";
        public static final String GAME_DATE = "game_date";
		public static final String UPDATED_AT = "updated_at";
		public static final String STATUS = "status";
		public static final String PREVIEW = "preview";
		public static final String RECAP = "recap";
		public static final String AWAY_TEAM = "away_team";
		public static final String HOME_TEAM = "home_team";
		public static final String LOCATION = "location";
		public static final String STADIUM = "stadium";
		public static final String ODD = "odd";
		public static final String BOX_SCORE = "box_score";
	}

	@ColumnName(Columns.ID)
	@SerializedName(Fields.ID)
	private Long mId;

    public Long getId() {
        return mId;
    }

    @ColumnName(Columns.EVENT_STATUS)
    @SerializedName(Fields.EVENT_STATUS)
    private String mEventStatus;

    public String getEventStatus() {
        return mEventStatus;
    }

	@SerializedName(Fields.GAME_DATE) 
	private String mGameDate;

	@ColumnName(Columns.GAME_DATE)
	public String getGameDate() {
		final String pattern = "EEE, dd MMM yyyy HH:mm:ss -0000";
		final DateTime parsed = DateTimeFormat.forPattern(pattern).withZoneUTC().parseDateTime(mGameDate);
		return ISODateTimeFormat.dateTimeNoMillis().withZone(DateTimeZone.getDefault()).print(parsed);
	}
	
	@ColumnName(Columns.UPDATED_AT)
	@SerializedName(Fields.UPDATED_AT) 
	private String mUpdatedAt;
	
	@ColumnName(Columns.STATUS)
	@SerializedName(Fields.STATUS) 
	private String mStatus;
	
	@ColumnName(Columns.PREVIEW)
	@SerializedName(Fields.PREVIEW) 
	private String mPreview;
	
	@ColumnName(Columns.RECAP)
	@SerializedName(Fields.RECAP) 
	private String mRecap;
	
	@SerializedName(Fields.AWAY_TEAM) 
	private Team mAwayTeam;

	public Team getAwayTeam() {
		return mAwayTeam;
	}
	
	@ColumnName(Columns.AWAY_TEAM_ID)
	public Long getAwayTeamId() {
		return mAwayTeam != null ? mAwayTeam.getId() : null;
	}
	
	@SerializedName(Fields.HOME_TEAM) 
	private Team mHomeTeam;
	
	public Team getHomeTeam() {
		return mHomeTeam;
	}
	
	@ColumnName(Columns.HOME_TEAM_ID)
	public Long getHomeTeamId() {
		return mHomeTeam != null ? mHomeTeam.getId() : null;
	}
	
	@ColumnName(Columns.LOCATION)
	@SerializedName(Fields.LOCATION) 
	private String mLocation;
	
	@ColumnName(Columns.STADIUM)
	@SerializedName(Fields.STADIUM) 
	private String mStadium;
	
	@SerializedName(Fields.ODD) 
	private Odd mOdd;

	@ColumnName(Columns.ODD_CLOSING)
	public String getOddClosing() {
		return mOdd != null ? mOdd.getClosing() : null;
	}
	
	@SerializedName(Fields.BOX_SCORE) 
	private BoxScore mBoxScore;

    public BoxScore getBoxScore() {
        return mBoxScore;
    }

    @ColumnName(Columns.BOX_SCORE_ID)
    public Long getBoxScoreId() {
        return mBoxScore != null ? mBoxScore.getId() : null;
    }
}