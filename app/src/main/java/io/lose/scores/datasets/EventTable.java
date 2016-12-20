package io.lose.scores.datasets;

import android.database.sqlite.SQLiteDatabase;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;
import io.pivotal.arca.provider.Unique.OnConflict;

public class EventTable extends SQLiteTable {

	public interface Columns extends SQLiteTable.Columns {
        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER)String ID = "id";
        @Column(Column.Type.TEXT)String EVENT_STATUS = "event_status";
        @Column(Column.Type.TEXT) String GAME_DATE = "game_date";
        @Column(Column.Type.TEXT) String GAME_DESCRIPTION = "game_description";
        @Column(Column.Type.TEXT) String UPDATED_AT = "updated_at";
        @Column(Column.Type.TEXT) String STATUS = "status";
        @Column(Column.Type.TEXT) String PREVIEW = "preview";
        @Column(Column.Type.TEXT) String RECAP = "recap";
        @Column(Column.Type.TEXT) String LOCATION = "location";
        @Column(Column.Type.TEXT) String STADIUM = "stadium";
        @Column(Column.Type.INTEGER) String AWAY_TEAM_ID = "away_team_id";
        @Column(Column.Type.INTEGER) String HOME_TEAM_ID = "home_team_id";
        @Column(Column.Type.INTEGER) String ODD_CLOSING = "odd_closing";
        @Column(Column.Type.INTEGER) String BOX_SCORE_ID = "box_score_id";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}