package io.lose.scores.requests;

import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.datasets.BoxScoreTable;
import io.pivotal.arca.dispatcher.Query;

public class BoxScoreQuery extends Query {

    public BoxScoreQuery(final String itemId) {
        super(ScoresContentProvider.Uris.BOX_SCORES, 2000);

        setWhere(BoxScoreTable.Columns.ID + "=?", itemId);
    }
}
