package io.elapse.scores.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;

import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.scores.R;
import io.elapse.scores.activities.GameActivity;
import io.elapse.scores.adapters.GamesAdapter;
import io.elapse.scores.adapters.GamesAdapter.ViewType;
import io.elapse.scores.binders.GameListViewBinder;
import io.elapse.scores.datasets.GameView;
import io.elapse.scores.monitors.GameListMonitor;
import io.elapse.scores.requests.ScoresQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleAdapterFragment;

@ArcaFragment(
    fragmentLayout = R.layout.fragment_game_list,
    monitor = GameListMonitor.class
)
public class GameListFragment extends ArcaSimpleAdapterFragment {

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
        ViewType.IN_PROGRESS.newBinding(R.id.game_odd_closing, GameView.Columns.ODD_CLOSING),

        ViewType.FINAL.newBinding(R.id.game_date, GameView.Columns.GAME_DATE),
        ViewType.FINAL.newBinding(R.id.game_box_score_progress, GameView.Columns.BOX_SCORE_PROGRESS),
        ViewType.FINAL.newBinding(R.id.game_home_team_name, GameView.Columns.HOME_TEAM_NAME),
        ViewType.FINAL.newBinding(R.id.game_home_team_logo, GameView.Columns.HOME_TEAM_LOGO),
        ViewType.FINAL.newBinding(R.id.game_box_score_home, GameView.Columns.BOX_SCORE_HOME_SCORE),
        ViewType.FINAL.newBinding(R.id.game_away_team_name, GameView.Columns.AWAY_TEAM_NAME),
        ViewType.FINAL.newBinding(R.id.game_away_team_logo, GameView.Columns.AWAY_TEAM_LOGO),
        ViewType.FINAL.newBinding(R.id.game_box_score_away, GameView.Columns.BOX_SCORE_AWAY_SCORE),
        ViewType.FINAL.newBinding(R.id.game_odd_closing, GameView.Columns.ODD_CLOSING)
    );

	private LocalDate mDate;

    @Override
	public CursorAdapter onCreateAdapter(final AdapterView<CursorAdapter> adapterView, final Bundle savedInstanceState) {
		final GamesAdapter adapter = new GamesAdapter(getActivity(), BINDINGS);
		adapter.setViewBinder(new GameListViewBinder());
		return adapter;
	}

	@Override
	public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
		final Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
		final String gameId = cursor.getString(cursor.getColumnIndex(GameView.Columns.ID));
        final String boxScoreId = cursor.getString(cursor.getColumnIndex(GameView.Columns.BOX_SCORE_ID));

		GameActivity.newInstance(getActivity(), gameId, boxScoreId);
	}

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
    }
	
	@Override
	public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_game_list, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		if (item.getItemId() == R.id.menu_reload) {
			reload();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
    
    public void setDate(final LocalDate date) {
    	if (date != null && !date.equals(mDate)) {
    		mDate = date;
			reload();
    	}
	}

    private void reload() {
        getViewManager().showProgressView();
        execute(new ScoresQuery(mDate));
    }
}