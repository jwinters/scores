package io.lose.scores.requests;

import io.lose.scores.application.ScoresContentProvider;
import io.pivotal.arca.dispatcher.Query;

public class RankingQuery extends Query {

    public RankingQuery() {
        super(ScoresContentProvider.Uris.RANKING, 7000);
    }
}
