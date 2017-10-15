package io.lose.scores.datasets;

import io.pivotal.arca.provider.Joins;
import io.pivotal.arca.provider.OrderBy;
import io.pivotal.arca.provider.SQLiteView;
import io.pivotal.arca.provider.Select;
import io.pivotal.arca.provider.SelectFrom;

public class StandingView extends SQLiteView {

    @SelectFrom("StandingTable as standings")

    @Joins({
        "LEFT JOIN TeamTable as teams ON standings.team_id = teams.id"
    })

    @OrderBy(TeamTable.Columns.DIVISION+ "," + StandingTable.Columns.DIVISION_RANK)

    public interface Columns  {
        @Select("standings." + StandingTable.Columns._ID)
        public static final String _ID = "_id";

        @Select("standings." + StandingTable.Columns.ID)
        public static final String ID = "id";

        @Select("standings." + StandingTable.Columns.CONFERENCE_RANK)
        public static final String CONFERENCE_RANK = "conference_rank";

        @Select("standings." + StandingTable.Columns.DIVISION_RANK)
        public static final String DIVISION_RANK = "division_rank";

        @Select("standings." + StandingTable.Columns.GAMES_PLAYED)
        public static final String GAMES_PLAYED = "games_played";

        @Select("standings." + StandingTable.Columns.LAST_TEN_GAMES_RECORD)
        public static final String LAST_TEN_GAMES_RECORD = "last_ten_games_record";

        @Select("standings." + StandingTable.Columns.SHORT_RECORD)
        public static final String SHORT_RECORD = "short_record";

        @Select("standings." + StandingTable.Columns.STREAK)
        public static final String STREAK = "streak";

        @Select("teams." + TeamTable.Columns.ABBREVIATION)
        public static final String TEAM_ABBREVIATION = "team_abbreviation";

        @Select("teams." + TeamTable.Columns.LOGO_SQUARE)
        public static final String TEAM_LOGO = "team_logo";
    }
}
