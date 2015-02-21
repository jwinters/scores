package io.elapse.scores.requests;

import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

import io.elapse.scores.application.ScoresContentProvider;
import io.elapse.scores.datasets.GameView;
import io.pivotal.arca.dispatcher.Query;

public class ScoresQuery extends Query {

    public ScoresQuery(final LocalDate date) {
        super(ScoresContentProvider.Uris.SCORES, 1000);

        setWhere(GameView.Columns.GAME_DATE + " BETWEEN ? AND ?",
            ISODateTimeFormat.date().print(date),
            ISODateTimeFormat.date().print(date.plusDays(1))
        );
    }

    public ScoresQuery(final String itemId) {
        super(ScoresContentProvider.Uris.SCORES, 2000);

        setWhere(GameView.Columns.ID + "=?", itemId);
    }
}
