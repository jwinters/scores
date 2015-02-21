package io.elapse.scores.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import io.pivotal.arca.dispatcher.Error;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.fragments.ArcaAdapterFragment;
import io.pivotal.arca.fragments.ArcaViewManager;
import io.pivotal.arca.monitor.ArcaDispatcher;

public class GameListFragment extends ArcaAdapterFragment implements OnItemClickListener {

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
    private ArcaViewManager mManager;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_game_list, container, false);
		final AbsListView list = (AbsListView) view.findViewById(getAdapterViewId());
		list.setOnItemClickListener(this);
		return view;
	}

    @Override
	public CursorAdapter onCreateAdapter(final AdapterView<CursorAdapter> adapterView, final Bundle savedInstanceState) {
		final GamesAdapter adapter = new GamesAdapter(getActivity(), BINDINGS);
		adapter.setViewBinder(new GameListViewBinder());
		return adapter;
	}

	@Override
	public ArcaDispatcher onCreateDispatcher(final Bundle savedInstanceState) {
		final ArcaDispatcher dispatcher = super.onCreateDispatcher(savedInstanceState);
		dispatcher.setRequestMonitor(new GameListMonitor());
		return dispatcher;
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

        mManager = new ArcaViewManager(view);
        mManager.showProgressView();

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

    private void reload() {
		mManager.showProgressView();
		loadGames();
	}
    
    public void setDate(final LocalDate date) {
    	if (date != null && !date.equals(mDate)) {
    		mDate = date;
			reload();
    	}
	}

	private void loadGames() {
		execute(new ScoresQuery(mDate));
	}

	@Override
	public void onContentChanged(final QueryResult result) {
		mManager.checkResult(result);
	}

	@Override
	public void onContentError(final Error error) {
		mManager.checkError(error);
	}
}