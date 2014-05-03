package com.scores.datasets;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;
import io.pivotal.arca.provider.Unique.OnConflict;

public class GameTable extends SQLiteTable {

	public static interface Columns extends SQLiteTable.Columns {

        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER)
        public static final String ID = "id";

        @Column(Column.Type.TEXT)
        public static final String EVENT_STATUS = "event_status";

        @Column(Column.Type.TEXT)
		public static final String GAME_DATE = "game_date";

        @Column(Column.Type.TEXT)
		public static final String UPDATED_AT = "updated_at";

        @Column(Column.Type.TEXT)
		public static final String STATUS = "status";

        @Column(Column.Type.TEXT)
		public static final String PREVIEW = "preview";

        @Column(Column.Type.TEXT)
		public static final String RECAP = "recap";

        @Column(Column.Type.TEXT)
		public static final String LOCATION = "location";

        @Column(Column.Type.TEXT)
		public static final String STADIUM = "stadium";

        @Column(Column.Type.INTEGER)
		public static final String AWAY_TEAM_ID = "away_team_id";

        @Column(Column.Type.INTEGER)
		public static final String HOME_TEAM_ID = "home_team_id";

        @Column(Column.Type.INTEGER)
		public static final String ODD_CLOSING = "odd_closing";

        @Column(Column.Type.INTEGER)
		public static final String BOX_SCORE_ID = "box_score_id";
	}
}