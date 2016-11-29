package io.lose.scores.requests;

import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.datasets.GoalTable;
import io.pivotal.arca.dispatcher.Query;

public class GoalsQuery extends Query {

    public GoalsQuery(final String boxScoreId) {
        super(ScoresContentProvider.Uris.GOALS, 3000);

        setWhere(GoalTable.Columns.BOX_SCORE_ID + "=?", boxScoreId);
    }
}
