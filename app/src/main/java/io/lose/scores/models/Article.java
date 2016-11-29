package io.lose.scores.models;

import com.google.gson.annotations.SerializedName;

import io.lose.scores.datasets.EventTable.Columns;
import io.pivotal.arca.provider.ColumnName;

public class Article {

    public static class Fields {
		public static final String ID = "id";
	}

	@ColumnName(Columns.ID)
	@SerializedName(Fields.ID)
	private Long mId;

    public Long getId() {
        return mId;
    }
}