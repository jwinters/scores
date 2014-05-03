package com.scores.providers;

import android.net.Uri;

import com.scores.datasets.BoxScoreTable;
import com.scores.datasets.GameTable;
import com.scores.datasets.GameView;
import com.scores.datasets.GoalTable;
import com.scores.datasets.TeamTable;

import io.pivotal.arca.provider.DatabaseProvider;

public class ScoresContentProvider extends DatabaseProvider {

	public static final String AUTHORITY = "com.scores.providers.ScoresContentProvider";

	public static final class Uris {
		public static final Uri SCORES = Uri.parse("content://" + AUTHORITY + "/" + Paths.SCORES);
		public static final Uri GAMES = Uri.parse("content://" + AUTHORITY + "/" + Paths.GAMES);
        public static final Uri BOX_SCORES = Uri.parse("content://" + AUTHORITY + "/" + Paths.BOX_SCORES);
		public static final Uri TEAMS = Uri.parse("content://" + AUTHORITY + "/" + Paths.TEAMS);
        public static final Uri GOALS = Uri.parse("content://" + AUTHORITY + "/" + Paths.GOALS);
    }

	private static final class Paths {
		public static final String SCORES = "scores";
		public static final String GAMES = "games";
        public static final String BOX_SCORES = "box_scores";
		public static final String TEAMS = "teams";
        public static final String GOALS = "goals";
	}

	@Override
	public boolean onCreate() {
		registerDataset(AUTHORITY, Paths.SCORES, GameView.class);
		registerDataset(AUTHORITY, Paths.SCORES + "/*", GameView.class);
		registerDataset(AUTHORITY, Paths.GAMES, GameTable.class);
		registerDataset(AUTHORITY, Paths.GAMES + "/*", GameTable.class);
        registerDataset(AUTHORITY, Paths.BOX_SCORES, BoxScoreTable.class);
        registerDataset(AUTHORITY, Paths.BOX_SCORES + "/*", BoxScoreTable.class);
		registerDataset(AUTHORITY, Paths.TEAMS, TeamTable.class);
		registerDataset(AUTHORITY, Paths.TEAMS + "/*", TeamTable.class);
        registerDataset(AUTHORITY, Paths.GOALS, GoalTable.class);
        registerDataset(AUTHORITY, Paths.GOALS + "/*", GoalTable.class);
		return true;
	}

}