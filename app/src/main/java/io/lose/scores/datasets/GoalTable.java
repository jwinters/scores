package io.lose.scores.datasets;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;
import io.pivotal.arca.provider.Unique.OnConflict;

public class GoalTable extends SQLiteTable {

    public interface Columns extends SQLiteTable.Columns {

        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER) String BOX_SCORE_ID = "box_score_id";

        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER) String SEGMENT = "segment";

        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER) String MINUTE = "minute";

        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER) String SECOND = "second";

        @Column(Column.Type.INTEGER) String ID = "id";
        @Column(Column.Type.INTEGER) String PERIOD = "period";
        @Column(Column.Type.INTEGER) String TEAM_ID = "team_id";
        @Column(Column.Type.TEXT) String SEGMENT_STRING = "segment_string";
        @Column(Column.Type.TEXT) String GOAL_STRENGTH = "goal_strength";
        @Column(Column.Type.TEXT) String PLAYER_NAME = "player_name";
        @Column(Column.Type.TEXT) String PLAYER_HEADSHOT = "player_headshot";
        @Column(Column.Type.TEXT) String A1_PLAYER_NAME = "a1_player_name";
        @Column(Column.Type.TEXT) String A2_PLAYER_NAME = "a2_player_name";
    }
}
