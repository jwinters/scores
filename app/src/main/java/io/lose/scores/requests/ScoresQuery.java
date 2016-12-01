package io.lose.scores.requests;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.datasets.GameView;
import io.pivotal.arca.dispatcher.Query;

public class ScoresQuery extends Query {

    private static final DateTimeFormatter FORMATTER = ISODateTimeFormat.dateTimeNoMillis();

    public ScoresQuery(final LocalDate date) {
        super(ScoresContentProvider.Uris.SCORES, 4000);

        final DateTime start = date.toDateTimeAtStartOfDay();
        final DateTime utc = start.toDateTime(DateTimeZone.UTC);

        setWhere(GameView.Columns.GAME_DATE + " BETWEEN ? AND ?",
            FORMATTER.print(utc),
            FORMATTER.print(utc.plusDays(1))
        );
    }

    public ScoresQuery(final String itemId) {
        super(ScoresContentProvider.Uris.SCORES, 5000);

        setWhere(GameView.Columns.ID + "=?", itemId);
    }
}
