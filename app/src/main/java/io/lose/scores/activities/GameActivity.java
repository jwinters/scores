package io.lose.scores.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collection;

import io.lose.scores.R;
import io.lose.scores.binders.GameListViewBinder;
import io.lose.scores.binders.GoalListViewBinder;
import io.lose.scores.datasets.BoxScoreTable;
import io.lose.scores.datasets.GameView;
import io.lose.scores.datasets.GoalView;
import io.lose.scores.requests.BoxScoreQuery;
import io.lose.scores.requests.GoalsQuery;
import io.lose.scores.requests.ScoresQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleItemFragment;
import io.pivotal.arca.fragments.ArcaSimpleRecyclerViewFragment;

public class GameActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private interface Extras {
        String GAME_ID = "game_id";
        String BOX_SCORE_ID = "box_score_id";
    }

    public static void newInstance(final Activity activity, final String gameId, final String boxScoreId) {
        final Intent intent = new Intent(activity, GameActivity.class);
        intent.putExtra(Extras.GAME_ID, gameId);
        intent.putExtra(Extras.BOX_SCORE_ID, boxScoreId);
        activity.startActivity(intent);
    }

    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setRefreshing(false);

        final String gameId = getIntent().getStringExtra(Extras.GAME_ID);
        final String boxScoreId = getIntent().getStringExtra(Extras.BOX_SCORE_ID);

        if (gameId != null) {
            findGameFragment().setItemId(gameId);
        }

        if (boxScoreId != null) {
            findBoxScoreFragment().setItemId(boxScoreId);
            findGoalListFragment().setItemId(boxScoreId);
        }
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(false);

        findGameFragment().onRefresh();
        findGoalListFragment().onRefresh();
        findBoxScoreFragment().onRefresh();
    }

    private GameFragment findGameFragment() {
        final FragmentManager manager = getFragmentManager();
        return (GameFragment) manager.findFragmentById(R.id.fragment_game);
    }

    private GoalListFragment findGoalListFragment() {
        final FragmentManager manager = getFragmentManager();
        return (GoalListFragment) manager.findFragmentById(R.id.fragment_goal_list);
    }

    private BoxScoreFragment findBoxScoreFragment() {
        final FragmentManager manager = getFragmentManager();
        return (BoxScoreFragment) manager.findFragmentById(R.id.fragment_box_score);
    }

    @ArcaFragment(
        fragmentLayout = R.layout.fragment_game,
        binder = GameListViewBinder.class
    )
    public static class GameFragment extends ArcaSimpleItemFragment implements SwipeRefreshLayout.OnRefreshListener {

        @ArcaFragmentBindings
        private static final Collection<Binding> BINDINGS = Arrays.asList(
            new Binding(R.id.game_date, GameView.Columns.GAME_DATE),
            new Binding(R.id.game_box_score_progress, GameView.Columns.BOX_SCORE_PROGRESS),
            new Binding(R.id.game_home_team_name, GameView.Columns.HOME_TEAM_NAME),
            new Binding(R.id.game_home_team_logo, GameView.Columns.HOME_TEAM_LOGO),
            new Binding(R.id.game_box_score_home, GameView.Columns.BOX_SCORE_HOME_SCORE),
            new Binding(R.id.game_away_team_name, GameView.Columns.AWAY_TEAM_NAME),
            new Binding(R.id.game_away_team_logo, GameView.Columns.AWAY_TEAM_LOGO),
            new Binding(R.id.game_box_score_away, GameView.Columns.BOX_SCORE_AWAY_SCORE)
        );

        private String mItemId;

        public void setItemId(final String itemId) {
            if (itemId != null) {
                mItemId = itemId;
                onRefresh();
            }
        }

        @Override
        public void onRefresh() {
            execute(new ScoresQuery(mItemId));
        }
    }

    @ArcaFragment(
        fragmentLayout = R.layout.fragment_recycler,
        adapterItemLayout = R.layout.list_item_goal,
        binder = GoalListViewBinder.class
    )
    public static class GoalListFragment extends ArcaSimpleRecyclerViewFragment implements SwipeRefreshLayout.OnRefreshListener {

        @ArcaFragmentBindings
        private static final Collection<Binding> BINDINGS = Arrays.asList(
            new Binding(R.id.goal_time, GoalView.Columns.SEGMENT_STRING),
            new Binding(R.id.goal_player, GoalView.Columns.PLAYER_NAME)
        );

        private String mItemId;

        public void setItemId(final String itemId) {
            if (itemId != null) {
                mItemId = itemId;
                onRefresh();
            }
        }

        @Override
        public void onRefresh() {
            execute(new GoalsQuery(mItemId));
        }
    }

    @ArcaFragment(
        fragmentLayout = R.layout.fragment_box_score
    )
    public static class BoxScoreFragment extends ArcaSimpleItemFragment implements SwipeRefreshLayout.OnRefreshListener {

        @ArcaFragmentBindings
        private static final Collection<Binding> BINDINGS = Arrays.asList(
            new Binding(R.id.box_score_away_wins, BoxScoreTable.Columns.AWAY_WINS),
            new Binding(R.id.box_score_away_losses, BoxScoreTable.Columns.AWAY_LOSSES),
            new Binding(R.id.box_score_away_overtime_losses, BoxScoreTable.Columns.AWAY_OVERTIME_LOSSES),
            new Binding(R.id.box_score_away_shots, BoxScoreTable.Columns.AWAY_SHOTS),
            new Binding(R.id.box_score_away_fo_wins, BoxScoreTable.Columns.AWAY_FACEOFFS_WON),
            new Binding(R.id.box_score_away_fo_losses, BoxScoreTable.Columns.AWAY_FACEOFFS_LOST),
            new Binding(R.id.box_score_away_fo_percentage, BoxScoreTable.Columns.AWAY_FACEOFF_WINNING_PERCENTAGE),
            new Binding(R.id.box_score_away_pp_goals, BoxScoreTable.Columns.AWAY_POWER_PLAY_GOALS),
            new Binding(R.id.box_score_away_pp_opportunities, BoxScoreTable.Columns.AWAY_POWER_PLAY_OPPORTUNITIES),
            new Binding(R.id.box_score_away_hits, BoxScoreTable.Columns.AWAY_HITS),

            new Binding(R.id.box_score_home_wins, BoxScoreTable.Columns.HOME_WINS),
            new Binding(R.id.box_score_home_losses, BoxScoreTable.Columns.HOME_LOSSES),
            new Binding(R.id.box_score_home_overtime_losses, BoxScoreTable.Columns.HOME_OVERTIME_LOSSES),
            new Binding(R.id.box_score_home_shots, BoxScoreTable.Columns.HOME_SHOTS),
            new Binding(R.id.box_score_home_fo_wins, BoxScoreTable.Columns.HOME_FACEOFFS_WON),
            new Binding(R.id.box_score_home_fo_losses, BoxScoreTable.Columns.HOME_FACEOFFS_LOST),
            new Binding(R.id.box_score_home_fo_percentage, BoxScoreTable.Columns.HOME_FACEOFF_WINNING_PERCENTAGE),
            new Binding(R.id.box_score_home_pp_goals, BoxScoreTable.Columns.HOME_POWER_PLAY_GOALS),
            new Binding(R.id.box_score_home_pp_opportunities, BoxScoreTable.Columns.HOME_POWER_PLAY_OPPORTUNITIES),
            new Binding(R.id.box_score_home_hits, BoxScoreTable.Columns.HOME_HITS)
        );

        private String mItemId;

        public void setItemId(final String itemId) {
            if (itemId != null) {
                mItemId = itemId;
                onRefresh();
            }
        }

        @Override
        public void onRefresh() {
            execute(new BoxScoreQuery(mItemId));
        }
    }
}
