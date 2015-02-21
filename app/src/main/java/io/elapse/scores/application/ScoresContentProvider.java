package io.elapse.scores.application;

import android.net.Uri;

import io.elapse.scores.datasets.ArticleTable;
import io.elapse.scores.datasets.BoxScoreTable;
import io.elapse.scores.datasets.GameTable;
import io.elapse.scores.datasets.GameView;
import io.elapse.scores.datasets.GoalTable;
import io.elapse.scores.datasets.TeamTable;
import io.pivotal.arca.provider.DatabaseProvider;

public class ScoresContentProvider extends DatabaseProvider {

	public static final String AUTHORITY = ScoresContentProvider.class.getName();
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

	public static final class Uris {
        public static final Uri ARTICLES = Uri.withAppendedPath(BASE_URI, Paths.ARTICLES);
        public static final Uri SCORES = Uri.withAppendedPath(BASE_URI, Paths.SCORES);
		public static final Uri GAMES = Uri.withAppendedPath(BASE_URI, Paths.GAMES);
        public static final Uri BOX_SCORES = Uri.withAppendedPath(BASE_URI, Paths.BOX_SCORES);
		public static final Uri TEAMS = Uri.withAppendedPath(BASE_URI, Paths.TEAMS);
        public static final Uri GOALS = Uri.withAppendedPath(BASE_URI, Paths.GOALS);
    }

	private static final class Paths {
        public static final String ARTICLES = "articles";
        public static final String SCORES = "scores";
		public static final String GAMES = "games";
        public static final String BOX_SCORES = "box_scores";
		public static final String TEAMS = "teams";
        public static final String GOALS = "goals";
	}

	@Override
	public boolean onCreate() {
        registerDataset(AUTHORITY, Paths.ARTICLES, ArticleTable.class);
		registerDataset(AUTHORITY, Paths.SCORES, GameView.class);
		registerDataset(AUTHORITY, Paths.GAMES, GameTable.class);
        registerDataset(AUTHORITY, Paths.BOX_SCORES, BoxScoreTable.class);
		registerDataset(AUTHORITY, Paths.TEAMS, TeamTable.class);
        registerDataset(AUTHORITY, Paths.GOALS, GoalTable.class);
		return true;
	}

}