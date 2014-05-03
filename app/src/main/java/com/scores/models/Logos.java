package com.scores.models;

import com.google.gson.annotations.SerializedName;

public class Logos {

	public static class Fields {
		public static final String LARGE = "large";
		public static final String SMALL = "small";
		public static final String W72XH72 = "w72xh72";
		public static final String TINY = "tiny";
	}

	@SerializedName(Fields.LARGE) 
	private String mLarge;
	
	@SerializedName(Fields.SMALL) 
	private String mSmall;
	
	@SerializedName(Fields.W72XH72) 
	private String mW72XH72;
	
	@SerializedName(Fields.TINY) 
	private String mTiny;

	public String getLarge() {
		return mLarge;
	}

	public String getSmall() {
		return mSmall;
	}

	public String getSquare() {
		return mW72XH72;
	}
	
}
