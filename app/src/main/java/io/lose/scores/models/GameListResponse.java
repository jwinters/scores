package io.lose.scores.models;

import java.util.ArrayList;
import java.util.List;

public class GameListResponse extends ArrayList<Game> {

	private static final long serialVersionUID = 1L;

	public List<Team> getTeams() {
		final List<Team> teams = new ArrayList<Team>();
		for (final Game game : this) {
			teams.add(game.getHomeTeam());
			teams.add(game.getAwayTeam());
		}
		return teams;
	}

    public List<BoxScore> getBoxScores() {
        final List<BoxScore> boxScores = new ArrayList<BoxScore>();
        for (final Game game : this) {
            final BoxScore boxScore = game.getBoxScore();
            if (boxScore != null) {
                boxScores.add(boxScore);
            }
        }
        return boxScores;
    }
}