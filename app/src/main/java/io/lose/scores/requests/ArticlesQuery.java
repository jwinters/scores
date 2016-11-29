package io.lose.scores.requests;

import io.lose.scores.application.ScoresContentProvider;
import io.pivotal.arca.dispatcher.Query;

public class ArticlesQuery extends Query {

    public ArticlesQuery() {
        super(ScoresContentProvider.Uris.ARTICLES, 1000);
    }
}
