package io.lose.scores.requests;

import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.datasets.GoalTable;
import io.pivotal.arca.dispatcher.Query;

public class ScoringQuery extends Query {

    public ScoringQuery(final String boxScoreId) {
        super(ScoresContentProvider.Uris.SCORING, 6000);

        setWhere(GoalTable.Columns.BOX_SCORE_ID + "=?", boxScoreId);
    }
}
