package io.lose.scores.datasets;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;
import io.pivotal.arca.provider.Unique.OnConflict;

public class BoxScoreTable extends SQLiteTable {

	public interface Columns extends SQLiteTable.Columns {
        @Unique(OnConflict.REPLACE)
        @Column(Column.Type.INTEGER) String ID = "id";
        @Column(Column.Type.INTEGER) String PROGRESS = "progress";
        @Column(Column.Type.INTEGER) String HOME_SCORE = "home_score";
        @Column(Column.Type.INTEGER) String HOME_WINS = "home_wins";
        @Column(Column.Type.INTEGER) String HOME_LOSSES = "home_losses";
        @Column(Column.Type.INTEGER) String HOME_OVERTIME_LOSSES = "home_overtime_losses";
        @Column(Column.Type.INTEGER) String HOME_SHOTS = "home_shots";
        @Column(Column.Type.INTEGER) String HOME_FACEOFFS_WON = "home_faceoffs_won";
        @Column(Column.Type.INTEGER) String HOME_FACEOFFS_LOST = "home_faceoffs_lost";
        @Column(Column.Type.REAL) String HOME_FACEOFF_WINNING_PERCENTAGE = "home_faceoff_winning_percentage";
        @Column(Column.Type.INTEGER) String HOME_POWER_PLAY_GOALS = "home_power_play_goals";
        @Column(Column.Type.INTEGER) String HOME_POWER_PLAY_OPPORTUNITIES = "home_power_play_opportunities";
        @Column(Column.Type.INTEGER) String HOME_HITS = "home_hits";
        @Column(Column.Type.INTEGER) String AWAY_SCORE = "away_score";
        @Column(Column.Type.INTEGER) String AWAY_WINS = "away_wins";
        @Column(Column.Type.INTEGER) String AWAY_LOSSES = "away_losses";
        @Column(Column.Type.INTEGER) String AWAY_OVERTIME_LOSSES = "away_overtime_losses";
        @Column(Column.Type.INTEGER) String AWAY_SHOTS = "away_shots";
        @Column(Column.Type.INTEGER) String AWAY_FACEOFFS_WON = "away_faceoffs_won";
        @Column(Column.Type.INTEGER) String AWAY_FACEOFFS_LOST = "away_faceoffs_lost";
        @Column(Column.Type.REAL) String AWAY_FACEOFF_WINNING_PERCENTAGE = "away_faceoff_winning_percentage";
        @Column(Column.Type.INTEGER) String AWAY_POWER_PLAY_GOALS = "away_power_play_goals";
        @Column(Column.Type.INTEGER) String AWAY_POWER_PLAY_OPPORTUNITIES = "away_power_play_opportunities";
        @Column(Column.Type.INTEGER) String AWAY_HITS = "away_hits";
    }
}