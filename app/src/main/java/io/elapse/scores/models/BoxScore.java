package io.elapse.scores.models;

import com.google.gson.annotations.SerializedName;
import io.elapse.scores.datasets.BoxScoreTable.Columns;

import io.pivotal.arca.provider.ColumnName;

public class BoxScore {

    public static class Fields {
		public static final String ID = "id";
		public static final String UPDATED_AT = "updated_at";
		public static final String PROGRESS = "progress";
		public static final String SCORE = "score";
		public static final String AWAY_STRENGTH = "away_strength";
		public static final String HOME_STRENGTH = "home_strength";
        public static final String TEAM_RECORDS = "team_records";
	}

    @ColumnName(Columns.ID)
	@SerializedName(Fields.ID) 
	private Long mId;

    public Long getId() {
        return mId;
    }

	@SerializedName(Fields.UPDATED_AT) 
	private String mUpdatedAt;
	
	@SerializedName(Fields.PROGRESS) 
	private Progress mProgress;
	
	@SerializedName(Fields.SCORE) 
	private Scores mScore;
	
	@SerializedName(Fields.AWAY_STRENGTH) 
	private String mAwayStrength;
	
	@SerializedName(Fields.HOME_STRENGTH) 
	private String mHomeStrength;

    @ColumnName(Columns.PROGRESS)
	public String getProgressClockLabel() {
		return mProgress != null ? mProgress.getClockLabel() : null;
	}

    @ColumnName(Columns.HOME_SCORE)
	public Long getHomeScore() {
		return mScore != null ? mScore.getHomeScore() : null;
	}

    @ColumnName(Columns.AWAY_SCORE)
	public Long getAwayScore() {
		return mScore != null ? mScore.getAwayScore() : null;
	}

    @SerializedName(Fields.TEAM_RECORDS)
    private TeamRecords mTeamRecords;

    @ColumnName(Columns.HOME_ALIAS)
    public String getHomeAlias() {
        return mTeamRecords != null ? mTeamRecords.getHomeAlias() : null;
    }

    @ColumnName(Columns.HOME_WINS)
    public Long getHomeWins() {
        return mTeamRecords != null ? mTeamRecords.getHomeWins() : null;
    }

    @ColumnName(Columns.HOME_LOSSES)
    public Long getHomeLosses() {
        return mTeamRecords != null ? mTeamRecords.getHomeLosses() : null;
    }

    @ColumnName(Columns.HOME_OVERTIME_LOSSES)
    public Long getHomeTies() {
        return mTeamRecords != null ? mTeamRecords.getHomeOvertimeLosses() : null;
    }

    @ColumnName(Columns.HOME_SHOTS)
    public Long getHomeShots() {
        return mTeamRecords != null ? mTeamRecords.getHomeShots() : null;
    }

    @ColumnName(Columns.HOME_FACEOFFS_WON)
    public Long getHomeFaceoffsWon() {
        return mTeamRecords != null ? mTeamRecords.getHomeFaceoffsWon() : null;
    }

    @ColumnName(Columns.HOME_FACEOFFS_LOST)
    public Long getHomeFaceoffsLost() {
        return mTeamRecords != null ? mTeamRecords.getHomeFaceoffsLost() : null;
    }

    @ColumnName(Columns.HOME_FACEOFF_WINNING_PERCENTAGE)
    public Float getHomeFaceoffWinningPercentage() {
        return mTeamRecords != null ? mTeamRecords.getHomeFaceoffWinningPercentage() : null;
    }

    @ColumnName(Columns.HOME_HITS)
    public Long getHomeHits() {
        return mTeamRecords != null ? mTeamRecords.getHomeHits() : null;
    }

    @ColumnName(Columns.AWAY_ALIAS)
    public String getAwayAlias() {
        return mTeamRecords != null ? mTeamRecords.getAwayAlias() : null;
    }

    @ColumnName(Columns.AWAY_WINS)
    public Long getAwayWins() {
        return mTeamRecords != null ? mTeamRecords.getAwayWins() : null;
    }

    @ColumnName(Columns.AWAY_LOSSES)
    public Long getAwayLosses() {
        return mTeamRecords != null ? mTeamRecords.getAwayLosses() : null;
    }

    @ColumnName(Columns.AWAY_OVERTIME_LOSSES)
    public Long getAwayOvertimeLosses() {
        return mTeamRecords != null ? mTeamRecords.getAwayOvertimeLosses() : null;
    }

    @ColumnName(Columns.AWAY_SHOTS)
    public Long getAwayShots() {
        return mTeamRecords != null ? mTeamRecords.getAwayShots() : null;
    }

    @ColumnName(Columns.AWAY_FACEOFFS_WON)
    public Long getAwayFaceoffsWon() {
        return mTeamRecords != null ? mTeamRecords.getAwayFaceoffsWon() : null;
    }

    @ColumnName(Columns.AWAY_FACEOFFS_LOST)
    public Long getAwayFaceoffsLost() {
        return mTeamRecords != null ? mTeamRecords.getAwayFaceoffsLost() : null;
    }

    @ColumnName(Columns.AWAY_FACEOFF_WINNING_PERCENTAGE)
    public Float getAwayFaceoffWinningPercentage() {
        return mTeamRecords != null ? mTeamRecords.getAwayFaceoffWinningPercentage() : null;
    }

    @ColumnName(Columns.AWAY_HITS)
    public Long getAwayHits() {
        return mTeamRecords != null ? mTeamRecords.getAwayHits() : null;
    }
}
