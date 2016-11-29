package io.lose.scores.requests;

import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.datasets.GameView;
import io.pivotal.arca.dispatcher.Query;

public class ScoresQuery extends Query {

    public ScoresQuery() {
        super(ScoresContentProvider.Uris.SCORES, 4500);
    }

    public ScoresQuery(final LocalDateTime date) {
        super(ScoresContentProvider.Uris.SCORES, 4000);

        setWhere(GameView.Columns.GAME_DATE + " BETWEEN ? AND ?",
            ISODateTimeFormat.date().print(date),
            ISODateTimeFormat.date().print(date.plusDays(1))
        );
    }

    public ScoresQuery(final String itemId) {
        super(ScoresContentProvider.Uris.SCORES, 5000);

        setWhere(GameView.Columns.ID + "=?", itemId);
    }
}
