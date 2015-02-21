package io.elapse.scores.models;

import com.google.gson.annotations.SerializedName;

public class Odd {

	public static class Fields {
		public static final String CLOSING = "closing";
	}

	@SerializedName(Fields.CLOSING) 
	private String mClosing;
	
	public String getClosing() {
		return mClosing;
	}

}
