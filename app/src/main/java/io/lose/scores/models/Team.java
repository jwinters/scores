package io.lose.scores.models;


import com.google.gson.annotations.SerializedName;

import io.lose.scores.datasets.TeamTable.Columns;
import io.pivotal.arca.provider.ColumnName;

public class Team {

	public static class Fields {
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String ABBREVIATION = "abbreviation";
		public static final String FULL_NAME = "full_name";
		public static final String LOGOS = "logos";
		public static final String CONFERENCE = "conference";
		public static final String DIVISION = "division";
	}

	@ColumnName(Columns.ID)
	@SerializedName(Fields.ID) 
	private Long mId;
	
	@ColumnName(Columns.NAME)
	@SerializedName(Fields.NAME) 
	private String mName;
	
	@ColumnName(Columns.ABBREVIATION)
	@SerializedName(Fields.ABBREVIATION) 
	private String mAbbreviation;
	
	@ColumnName(Columns.FULL_NAME)
	@SerializedName(Fields.FULL_NAME) 
	private String mFullName;
	
	@SerializedName(Fields.LOGOS) 
	private Logos mLogos;
	
	@ColumnName(Columns.LOGO_LARGE)
	public String getLogoLarge() {
		return mLogos != null ? mLogos.getLarge() : null;
	}
	
	@ColumnName(Columns.LOGO_SMALL)
	public String getLogoSmall() {
		return mLogos != null ? mLogos.getSmall() : null;
	}
	
	@ColumnName(Columns.LOGO_SQUARE)
	public String getLogoSquare() {
		return mLogos != null ? mLogos.getSquare() : null;
	}
	
	@ColumnName(Columns.CONFERENCE)
	@SerializedName(Fields.CONFERENCE) 
	private String mConference;
	
	@ColumnName(Columns.DIVISION)
	@SerializedName(Fields.DIVISION) 
	private String mDivision;

	public Long getId() {
		return mId;
	}
	
}