package io.lose.scores.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.Collection;

import io.lose.scores.R;
import io.lose.scores.adapters.GamesAdapter;
import io.lose.scores.adapters.GamesAdapter.ViewType;
import io.lose.scores.binders.GameListViewBinder;
import io.lose.scores.datasets.GameView;
import io.lose.scores.monitors.GameListMonitor;
import io.lose.scores.requests.ScoresQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.RecyclerViewCursorAdapter;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleRecyclerViewFragment;

public class GameListActivity extends AppCompatActivity {

	public static void newInstance(final Context context) {
		final Intent intent = new Intent(context, GameListActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);
		
        findGameListFragment().setDate(LocalDate.now());
	}

	private GameListFragment findGameListFragment() {
		final FragmentManager manager = getFragmentManager();
		return (GameListFragment) manager.findFragmentById(R.id.fragment_game_list);
	}

	@ArcaFragment(
        fragmentLayout = R.layout.fragment_swipe_recycler,
        monitor = GameListMonitor.class
    )
    public static class GameListFragment extends ArcaSimpleRecyclerViewFragment implements SwipeRefreshLayout.OnRefreshListener {

        @ArcaFragmentBindings
        private static final Collection<Binding> BINDINGS = Arrays.asList(
            ViewType.PRE_GAME.newBinding(R.id.game_date, GameView.Columns.GAME_DATE),
            ViewType.PRE_GAME.newBinding(R.id.game_home_team_name, GameView.Columns.HOME_TEAM_NAME),
            ViewType.PRE_GAME.newBinding(R.id.game_home_team_logo, GameView.Columns.HOME_TEAM_LOGO),
            ViewType.PRE_GAME.newBinding(R.id.game_away_team_name, GameView.Columns.AWAY_TEAM_NAME),
            ViewType.PRE_GAME.newBinding(R.id.game_away_team_logo, GameView.Columns.AWAY_TEAM_LOGO),
            ViewType.PRE_GAME.newBinding(R.id.game_odd_closing, GameView.Columns.ODD_CLOSING),

            ViewType.IN_PROGRESS.newBinding(R.id.game_date, GameView.Columns.GAME_DATE),
            ViewType.IN_PROGRESS.newBinding(R.id.game_box_score_progress, GameView.Columns.BOX_SCORE_PROGRESS),
            ViewType.IN_PROGRESS.newBinding(R.id.game_home_team_name, GameView.Columns.HOME_TEAM_NAME),
            ViewType.IN_PROGRESS.newBinding(R.id.game_home_team_logo, GameView.Columns.HOME_TEAM_LOGO),
            ViewType.IN_PROGRESS.newBinding(R.id.game_box_score_home, GameView.Columns.BOX_SCORE_HOME_SCORE),
            ViewType.IN_PROGRESS.newBinding(R.id.game_away_team_name, GameView.Columns.AWAY_TEAM_NAME),
            ViewType.IN_PROGRESS.newBinding(R.id.game_away_team_logo, GameView.Columns.AWAY_TEAM_LOGO),
            ViewType.IN_PROGRESS.newBinding(R.id.game_box_score_away, GameView.Columns.BOX_SCORE_AWAY_SCORE),

            ViewType.FINAL.newBinding(R.id.game_date, GameView.Columns.GAME_DATE),
            ViewType.FINAL.newBinding(R.id.game_box_score_progress, GameView.Columns.BOX_SCORE_PROGRESS),
            ViewType.FINAL.newBinding(R.id.game_home_team_name, GameView.Columns.HOME_TEAM_NAME),
            ViewType.FINAL.newBinding(R.id.game_home_team_logo, GameView.Columns.HOME_TEAM_LOGO),
            ViewType.FINAL.newBinding(R.id.game_box_score_home, GameView.Columns.BOX_SCORE_HOME_SCORE),
            ViewType.FINAL.newBinding(R.id.game_away_team_name, GameView.Columns.AWAY_TEAM_NAME),
            ViewType.FINAL.newBinding(R.id.game_away_team_logo, GameView.Columns.AWAY_TEAM_LOGO),
            ViewType.FINAL.newBinding(R.id.game_box_score_away, GameView.Columns.BOX_SCORE_AWAY_SCORE)
        );

        private LocalDate mDate;
        private SwipeRefreshLayout mRefreshLayout;

        @Override
        public RecyclerViewCursorAdapter onCreateAdapter(final RecyclerView adapterView, final Bundle savedInstanceState) {
            final GamesAdapter cursorAdapter = new GamesAdapter(getActivity(), BINDINGS);
            final RecyclerViewCursorAdapter adapter = new RecyclerViewCursorAdapter(cursorAdapter);
            adapter.setViewBinder(new GameListViewBinder());
            return adapter;
        }

        @Override
        public void onItemClick(final RecyclerView adapterView, final View view, final int position, final long id) {
            final Cursor cursor = (Cursor) getRecyclerViewAdapter().getItem(position);
            final String gameId = cursor.getString(cursor.getColumnIndex(GameView.Columns.ID));
            final String boxScoreId = cursor.getString(cursor.getColumnIndex(GameView.Columns.BOX_SCORE_ID));

            GameActivity.newInstance(getActivity(), gameId, boxScoreId);
        }

        @Override
        public void onViewCreated(final View view, final Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mRefreshLayout = (SwipeRefreshLayout) view;
            mRefreshLayout.setOnRefreshListener(this);
            mRefreshLayout.setRefreshing(false);

            onRefresh();
        }

        @Override
        public void onContentChanged(final QueryResult result) {
            super.onContentChanged(result);

            mRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onRefresh() {
            execute(new ScoresQuery(LocalDate.now()));
        }

        public void setDate(final LocalDate date) {
            if (date != null && !date.equals(mDate)) {
                mDate = date;
                onRefresh();
            }
        }
    }
}