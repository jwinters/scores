package com.scores.datasets;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;
import io.pivotal.arca.provider.Unique.OnConflict;

public class TeamTable extends SQLiteTable {

	public static interface Columns {

        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER)
		public static final String ID = "id";

        @Column(Column.Type.TEXT)
		public static final String NAME = "name";

        @Column(Column.Type.TEXT)
		public static final String ABBREVIATION = "abbreviation";

        @Column(Column.Type.TEXT)
		public static final String FULL_NAME = "full_name";

        @Column(Column.Type.TEXT)
		public static final String CONFERENCE = "conference";

        @Column(Column.Type.TEXT)
		public static final String DIVISION = "division";

        @Column(Column.Type.TEXT)
		public static final String LOGO_LARGE = "logo_large";

        @Column(Column.Type.TEXT)
		public static final String LOGO_SMALL = "logo_small";

        @Column(Column.Type.TEXT)
		public static final String LOGO_SQUARE = "logo_square";
	}
}