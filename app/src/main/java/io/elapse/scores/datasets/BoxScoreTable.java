package io.elapse.scores.datasets;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;
import io.pivotal.arca.provider.Unique.OnConflict;

public class BoxScoreTable extends SQLiteTable {

	public static interface Columns extends SQLiteTable.Columns {
        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER)
		public static final String ID = "id";

        @Column(Column.Type.INTEGER)
        public static final String PROGRESS = "progress";

        @Column(Column.Type.TEXT)
        public static final String HOME_ALIAS = "home_alias";

        @Column(Column.Type.INTEGER)
        public static final String HOME_SCORE = "home_score";

        @Column(Column.Type.INTEGER)
        public static final String HOME_WINS = "home_wins";

        @Column(Column.Type.INTEGER)
        public static final String HOME_LOSSES = "home_losses";

        @Column(Column.Type.INTEGER)
        public static final String HOME_OVERTIME_LOSSES = "home_overtime_losses";

        @Column(Column.Type.INTEGER)
        public static final String HOME_SHOTS = "home_shots";

        @Column(Column.Type.INTEGER)
        public static final String HOME_FACEOFFS_WON = "home_faceoffs_won";

        @Column(Column.Type.INTEGER)
        public static final String HOME_FACEOFFS_LOST = "home_faceoffs_lost";

        @Column(Column.Type.REAL)
        public static final String HOME_FACEOFF_WINNING_PERCENTAGE = "home_faceoff_winning_percentage";

        @Column(Column.Type.INTEGER)
        public static final String HOME_HITS = "home_hits";
        
        @Column(Column.Type.TEXT)
        public static final String AWAY_ALIAS = "away_alias";

        @Column(Column.Type.INTEGER)
        public static final String AWAY_SCORE = "away_score";

        @Column(Column.Type.INTEGER)
        public static final String AWAY_WINS = "away_wins";

        @Column(Column.Type.INTEGER)
        public static final String AWAY_LOSSES = "away_losses";

        @Column(Column.Type.INTEGER)
        public static final String AWAY_OVERTIME_LOSSES = "away_overtime_losses";

        @Column(Column.Type.INTEGER)
        public static final String AWAY_SHOTS = "away_shots";

        @Column(Column.Type.INTEGER)
        public static final String AWAY_FACEOFFS_WON = "away_faceoffs_won";

        @Column(Column.Type.INTEGER)
        public static final String AWAY_FACEOFFS_LOST = "away_faceoffs_lost";

        @Column(Column.Type.REAL)
        public static final String AWAY_FACEOFF_WINNING_PERCENTAGE = "away_faceoff_winning_percentage";

        @Column(Column.Type.INTEGER)
        public static final String AWAY_HITS = "away_hits";
    }
}