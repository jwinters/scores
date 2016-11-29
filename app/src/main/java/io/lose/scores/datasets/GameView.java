package io.lose.scores.datasets;

import io.pivotal.arca.provider.GroupBy;
import io.pivotal.arca.provider.Joins;
import io.pivotal.arca.provider.SQLiteView;
import io.pivotal.arca.provider.Select;
import io.pivotal.arca.provider.SelectFrom;

public class GameView extends SQLiteView {

    @SelectFrom("EventTable as events")

    @Joins({
        "LEFT JOIN TeamTable as home ON events.home_team_id = home.id",
        "LEFT JOIN TeamTable as away ON events.away_team_id = away.id",
        "LEFT JOIN BoxScoreTable as scores ON events.box_score_id = scores.id"
    })

    @GroupBy("events.id")

    public interface Columns  {
        @Select("events." + EventTable.Columns._ID)
        public static final String _ID = "_id";

        @Select("events." + EventTable.Columns.ID)
        public static final String ID = "id";

        @Select("events." + EventTable.Columns.BOX_SCORE_ID)
        public static final String BOX_SCORE_ID = "box_score_id";

        @Select("events." + EventTable.Columns.EVENT_STATUS)
        public static final String EVENT_STATUS = "event_status";

        @Select("events." + EventTable.Columns.GAME_DATE)
        public static final String GAME_DATE = "game_date";

        @Select("events." + EventTable.Columns.GAME_DESCRIPTION)
        public static final String GAME_DESCRIPTION = "game_description";

        @Select("events." + EventTable.Columns.ODD_CLOSING)
        public static final String ODD_CLOSING = "odd_closing";

        @Select("home." + TeamTable.Columns.ABBREVIATION)
        public static final String HOME_TEAM_NAME = "home_team_name";

        @Select("away." + TeamTable.Columns.ABBREVIATION)
        public static final String AWAY_TEAM_NAME = "away_team_name";

        @Select("home." + TeamTable.Columns.LOGO_SQUARE)
        public static final String HOME_TEAM_LOGO = "home_team_logo";

        @Select("away." + TeamTable.Columns.LOGO_SQUARE)
        public static final String AWAY_TEAM_LOGO = "away_team_logo";

        @Select("scores." + BoxScoreTable.Columns.PROGRESS)
        public static final String BOX_SCORE_PROGRESS = "box_score_progress";

        @Select("scores." + BoxScoreTable.Columns.HOME_SCORE)
        public static final String BOX_SCORE_HOME_SCORE = "box_score_home_score";

        @Select("scores." + BoxScoreTable.Columns.AWAY_SCORE)
        public static final String BOX_SCORE_AWAY_SCORE = "box_score_away_score";
    }
}