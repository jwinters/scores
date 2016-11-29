package io.lose.scores.datasets;

import android.database.sqlite.SQLiteDatabase;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;

public class StandingTable extends SQLiteTable {

    public interface Columns extends SQLiteTable.Columns {
        @Unique(Unique.OnConflict.REPLACE)
        @Column(Column.Type.INTEGER) String ID = "id";
        @Column(Column.Type.INTEGER) String DIVISION_RANK = "division_rank";
        @Column(Column.Type.INTEGER) String CONFERENCE_RANK = "conference_rank";
        @Column(Column.Type.INTEGER) String GAMES_PLAYED = "games_played";
        @Column(Column.Type.INTEGER) String GOALS_FOR = "goals_for";
        @Column(Column.Type.INTEGER) String GOALS_AGAINST = "goals_against";
        @Column(Column.Type.TEXT) String LAST_TEN_GAMES_RECORD = "last_ten_games_record";
        @Column(Column.Type.TEXT) String SHORT_RECORD = "short_record";
        @Column(Column.Type.TEXT) String STREAK = "streak";
        @Column(Column.Type.INTEGER) String TEAM_ID = "team_id";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
