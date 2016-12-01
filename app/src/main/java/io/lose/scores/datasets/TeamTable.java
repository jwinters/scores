package io.lose.scores.datasets;

import android.database.sqlite.SQLiteDatabase;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;
import io.pivotal.arca.provider.Unique.OnConflict;

public class TeamTable extends SQLiteTable {

	public interface Columns extends SQLiteTable.Columns {
        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER) String ID = "id";
        @Column(Column.Type.TEXT) String NAME = "name";
        @Column(Column.Type.TEXT) String ABBREVIATION = "abbreviation";
        @Column(Column.Type.TEXT) String COLOUR_1 = "colour_1";
        @Column(Column.Type.TEXT) String COLOUR_2 = "colour_2";
        @Column(Column.Type.TEXT) String FULL_NAME = "full_name";
        @Column(Column.Type.TEXT) String LOCATION = "location";
        @Column(Column.Type.TEXT) String CONFERENCE = "conference";
        @Column(Column.Type.TEXT) String DIVISION = "division";
        @Column(Column.Type.TEXT) String LOGO_LARGE = "logo_large";
        @Column(Column.Type.TEXT) String LOGO_SMALL = "logo_small";
        @Column(Column.Type.TEXT) String LOGO_SQUARE = "logo_square";
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}