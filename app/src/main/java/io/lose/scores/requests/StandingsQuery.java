package io.lose.scores.requests;

import io.lose.scores.application.ScoresContentProvider;
import io.pivotal.arca.dispatcher.Query;

public class StandingsQuery extends Query {

    public StandingsQuery() {
        super(ScoresContentProvider.Uris.STANDINGS, 6000);
    }
}
