package io.lose.scores.application;

import android.net.Uri;

import io.lose.scores.datasets.ArticleTable;
import io.lose.scores.datasets.BoxScoreTable;
import io.lose.scores.datasets.EventTable;
import io.lose.scores.datasets.GameView;
import io.lose.scores.datasets.GoalTable;
import io.lose.scores.datasets.GoalView;
import io.lose.scores.datasets.StandingTable;
import io.lose.scores.datasets.StandingView;
import io.lose.scores.datasets.TeamTable;
import io.pivotal.arca.provider.DatabaseProvider;

public class ScoresContentProvider extends DatabaseProvider {

	public static final String AUTHORITY = ScoresContentProvider.class.getName();
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public interface Uris {
        Uri ARTICLES = Uri.withAppendedPath(BASE_URI, Paths.ARTICLES);
		Uri EVENTS = Uri.withAppendedPath(BASE_URI, Paths.EVENTS);
        Uri BOX_SCORES = Uri.withAppendedPath(BASE_URI, Paths.BOX_SCORES);
		Uri TEAMS = Uri.withAppendedPath(BASE_URI, Paths.TEAMS);
        Uri GOALS = Uri.withAppendedPath(BASE_URI, Paths.GOALS);
        Uri STANDINGS = Uri.withAppendedPath(BASE_URI, Paths.STANDINGS);
        Uri SCORES = Uri.withAppendedPath(BASE_URI, Paths.SCORES);
        Uri SCORING = Uri.withAppendedPath(BASE_URI, Paths.SCORING);
        Uri RANKING = Uri.withAppendedPath(BASE_URI, Paths.RANKING);
    }

	private interface Paths {
        String ARTICLES = "articles";
        String SCORES = "scores";
		String EVENTS = "events";
        String BOX_SCORES = "box_scores";
		String TEAMS = "teams";
        String GOALS = "goals";
        String STANDINGS = "standings";
        String SCORING = "scoring";
        String RANKING = "ranking";
	}

	@Override
	public boolean onCreate() {
        registerDataset(AUTHORITY, Paths.ARTICLES, ArticleTable.class);
		registerDataset(AUTHORITY, Paths.EVENTS, EventTable.class);
        registerDataset(AUTHORITY, Paths.BOX_SCORES, BoxScoreTable.class);
		registerDataset(AUTHORITY, Paths.TEAMS, TeamTable.class);
        registerDataset(AUTHORITY, Paths.GOALS, GoalTable.class);
        registerDataset(AUTHORITY, Paths.STANDINGS, StandingTable.class);
        registerDataset(AUTHORITY, Paths.SCORES, GameView.class);
        registerDataset(AUTHORITY, Paths.SCORING, GoalView.class);
        registerDataset(AUTHORITY, Paths.RANKING, StandingView.class);
        return true;
	}

}