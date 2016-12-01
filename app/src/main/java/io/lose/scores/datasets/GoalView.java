package io.lose.scores.datasets;

import io.pivotal.arca.provider.Joins;
import io.pivotal.arca.provider.OrderBy;
import io.pivotal.arca.provider.SQLiteView;
import io.pivotal.arca.provider.Select;
import io.pivotal.arca.provider.SelectFrom;

public class GoalView extends SQLiteView {

    @SelectFrom("GoalTable as goals")

    @Joins({
        "LEFT JOIN TeamTable as team ON goals.team_id = team.id"
    })

    @OrderBy(GoalTable.Columns.SEGMENT + "," + GoalTable.Columns.MINUTE + "," + GoalTable.Columns.SECOND)

    public interface Columns  {
        @Select("goals." + GoalTable.Columns._ID)
        public static final String _ID = "_id";

        @Select("goals." + GoalTable.Columns.ID)
        public static final String ID = "id";

        @Select("goals." + GoalTable.Columns.BOX_SCORE_ID)
        public static final String BOX_SCORE_ID = "box_score_id";

        @Select("goals." + GoalTable.Columns.TEAM_ID)
        public static final String TEAM_ID = "team_id";

        @Select("goals." + GoalTable.Columns.MINUTE)
        public static final String MINUTE = "minute";

        @Select("goals." + GoalTable.Columns.SECOND)
        public static final String SECOND = "second";

        @Select("goals." + GoalTable.Columns.PERIOD)
        public static final String PERIOD = "period";

        @Select("goals." + GoalTable.Columns.SEGMENT)
        public static final String SEGMENT = "segment";

        @Select("goals." + GoalTable.Columns.SEGMENT_STRING)
        public static final String SEGMENT_STRING = "segment_string";

        @Select("goals." + GoalTable.Columns.PLAYER_NAME)
        public static final String PLAYER_NAME = "player_name";

        @Select("goals." + GoalTable.Columns.PLAYER_HEADSHOT)
        public static final String PLAYER_HEADSHOT = "player_headshot";

        @Select("goals." + GoalTable.Columns.A1_PLAYER_NAME)
        public static final String A1_PLAYER_NAME = "a1_player_name";

        @Select("goals." + GoalTable.Columns.A2_PLAYER_NAME)
        public static final String A2_PLAYER_NAME = "a2_player_name";

        @Select("team." + TeamTable.Columns.ABBREVIATION)
        public static final String TEAM_ABBREVIATION = "team_abbreviation";

        @Select("team." + TeamTable.Columns.LOGO_SQUARE)
        public static final String TEAM_LOGO = "team_logo";
    }
}
