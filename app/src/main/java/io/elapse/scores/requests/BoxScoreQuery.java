package io.elapse.scores.requests;

import io.elapse.scores.application.ScoresContentProvider;
import io.elapse.scores.datasets.BoxScoreTable;
import io.pivotal.arca.dispatcher.Query;

public class BoxScoreQuery extends Query {

    public BoxScoreQuery(final String itemId) {
        super(ScoresContentProvider.Uris.BOX_SCORES, 2000);

        setWhere(BoxScoreTable.Columns.ID + "=?", itemId);
    }
}
