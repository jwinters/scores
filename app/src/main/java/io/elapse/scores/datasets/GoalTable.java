package io.elapse.scores.datasets;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;
import io.pivotal.arca.provider.Unique.OnConflict;

public class GoalTable extends SQLiteTable {

    public static interface Columns extends SQLiteTable.Columns {
        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER)
        public static final String ID = "id";

        @Column(Column.Type.INTEGER)
        public static final String GAME_ID = "game_id";

        @Column(Column.Type.INTEGER)
        public static final String TEAM_ID = "team_id";

        @Column(Column.Type.INTEGER)
        public static final String MINUTE = "minute";

        @Column(Column.Type.INTEGER)
        public static final String SECOND = "second";

        @Column(Column.Type.INTEGER)
        public static final String PERIOD = "period";

        @Column(Column.Type.INTEGER)
        public static final String SEGMENT = "segment";

        @Column(Column.Type.TEXT)
        public static final String PLAYER_NAME = "player_name";

        @Column(Column.Type.TEXT)
        public static final String A1_PLAYER_NAME = "a1_player_name";

        @Column(Column.Type.TEXT)
        public static final String A2_PLAYER_NAME = "a2_player_name";
    }
}
