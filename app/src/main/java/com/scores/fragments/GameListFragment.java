package com.scores.fragments;

import android.database.Cursor;
import android.net.Uri;
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
import android.widget.Toast;

import com.scores.R;
import com.scores.activities.GameActivity;
import com.scores.adapters.GamesAdapter;
import com.scores.adapters.GamesAdapter.ViewTypes;
import com.scores.binders.GameListViewBinder;
import com.scores.datasets.GameView;
import com.scores.monitors.GameListMonitor;
import com.scores.providers.ScoresContentProvider;
import com.xtremelabs.imageutils.ImageLoader;

import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Arrays;
import java.util.Collection;

import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.dispatcher.Error;
import io.pivotal.arca.dispatcher.Query;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.fragments.ArcaAdapterFragment;
import io.pivotal.arca.monitor.ArcaDispatcher;

public class GameListFragment extends ArcaAdapterFragment implements OnItemClickListener {

	private static final Collection<Binding> BINDINGS = Arrays.asList(
        ViewTypes.PRE_GAME.newBinding(R.id.game_date, GameView.Columns.GAME_DATE),
        ViewTypes.PRE_GAME.newBinding(R.id.game_home_team_name, GameView.Columns.HOME_TEAM_NAME),
        ViewTypes.PRE_GAME.newBinding(R.id.game_home_team_logo, GameView.Columns.HOME_TEAM_LOGO),
        ViewTypes.PRE_GAME.newBinding(R.id.game_away_team_name, GameView.Columns.AWAY_TEAM_NAME),
        ViewTypes.PRE_GAME.newBinding(R.id.game_away_team_logo, GameView.Columns.AWAY_TEAM_LOGO),
        ViewTypes.PRE_GAME.newBinding(R.id.game_odd_closing, GameView.Columns.ODD_CLOSING),

        ViewTypes.IN_PROGRESS.newBinding(R.id.game_date, GameView.Columns.GAME_DATE),
        ViewTypes.IN_PROGRESS.newBinding(R.id.game_box_score_progress, GameView.Columns.BOX_SCORE_PROGRESS),
        ViewTypes.IN_PROGRESS.newBinding(R.id.game_home_team_name, GameView.Columns.HOME_TEAM_NAME),
        ViewTypes.IN_PROGRESS.newBinding(R.id.game_home_team_logo, GameView.Columns.HOME_TEAM_LOGO),
        ViewTypes.IN_PROGRESS.newBinding(R.id.game_box_score_home, GameView.Columns.BOX_SCORE_HOME_SCORE),
        ViewTypes.IN_PROGRESS.newBinding(R.id.game_away_team_name, GameView.Columns.AWAY_TEAM_NAME),
        ViewTypes.IN_PROGRESS.newBinding(R.id.game_away_team_logo, GameView.Columns.AWAY_TEAM_LOGO),
        ViewTypes.IN_PROGRESS.newBinding(R.id.game_box_score_away, GameView.Columns.BOX_SCORE_AWAY_SCORE),

        ViewTypes.FINAL.newBinding(R.id.game_date, GameView.Columns.GAME_DATE),
        ViewTypes.FINAL.newBinding(R.id.game_box_score_progress, GameView.Columns.BOX_SCORE_PROGRESS),
        ViewTypes.FINAL.newBinding(R.id.game_home_team_name, GameView.Columns.HOME_TEAM_NAME),
        ViewTypes.FINAL.newBinding(R.id.game_home_team_logo, GameView.Columns.HOME_TEAM_LOGO),
        ViewTypes.FINAL.newBinding(R.id.game_box_score_home, GameView.Columns.BOX_SCORE_HOME_SCORE),
        ViewTypes.FINAL.newBinding(R.id.game_away_team_name, GameView.Columns.AWAY_TEAM_NAME),
        ViewTypes.FINAL.newBinding(R.id.game_away_team_logo, GameView.Columns.AWAY_TEAM_LOGO),
        ViewTypes.FINAL.newBinding(R.id.game_box_score_away, GameView.Columns.BOX_SCORE_AWAY_SCORE)
    );

	private ImageLoader mLoader;
	private LocalDate mDate;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_game_list, container, false);
		final AbsListView list = (AbsListView) view.findViewById(getAdapterViewId());
		list.setOnItemClickListener(this);
		setHasOptionsMenu(true);
		return view;
	}

	@Override
	public CursorAdapter onCreateAdapter(final AdapterView<CursorAdapter> adapterView, final Bundle savedInstanceState) {
		final GamesAdapter adapter = new GamesAdapter(getActivity(), BINDINGS);
		mLoader = ImageLoader.buildImageLoaderForFragment(this);
		adapter.setViewBinder(new GameListViewBinder(mLoader));
		return adapter;
	}

	@Override
	public ArcaDispatcher onCreateDispatcher(final Bundle savedInstaceState) {
		final ArcaDispatcher dispatcher = super.onCreateDispatcher(savedInstaceState);
		dispatcher.setRequestMonitor(new GameListMonitor());
		return dispatcher;
	}
	
	@Override
	public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
		final Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
		final int columnIndex = cursor.getColumnIndex(GameView.Columns._ID);
		final String itemId = cursor.getString(columnIndex);
		GameActivity.newInstance(getActivity(), itemId);
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
	
    @Override
    public void onDestroyView() {
    	super.onDestroyView();
    	mLoader.destroy();
    }

    private void reload() {
		showLoading();
		loadGames();
	}
    
    public void setDate(final LocalDate date) {
    	if (date != null && !date.equals(mDate)) {
    		mDate = date;
			reload();
    	}
	}

	private void loadGames() {
		final Uri uri = ScoresContentProvider.Uris.SCORES;
		final Query request = new Query(uri, 1000);
		request.setWhere("game_date BETWEEN ? AND ?", new String[] {
			ISODateTimeFormat.date().print(mDate),
			ISODateTimeFormat.date().print(mDate.plusDays(1)),
		});
		execute(request);
	}

	private View getLoadingView() {
		return getView().findViewById(R.id.loading);
	}

	private View getEmptyView() {
		return getView().findViewById(R.id.empty);
	}

	private void showLoading() {
		getAdapterView().setVisibility(View.INVISIBLE);
		getLoadingView().setVisibility(View.VISIBLE);
		getEmptyView().setVisibility(View.INVISIBLE);
	}

	private void showResults() {
		getAdapterView().setVisibility(View.VISIBLE);
		getLoadingView().setVisibility(View.INVISIBLE);
		getEmptyView().setVisibility(View.INVISIBLE);
	}

	private void showNoResults() {
		getAdapterView().setVisibility(View.INVISIBLE);
		getLoadingView().setVisibility(View.INVISIBLE);
		getEmptyView().setVisibility(View.VISIBLE);
	}

	private void showError(final Error error) {
		final String message = String.format("ERROR: %s", error.getMessage());
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onContentChanged(final QueryResult result) {
		final CursorAdapter adapter = getCursorAdapter();
		if (adapter.getCount() > 0) {
			showResults();
		} else if (!result.isSyncing()) {
			showNoResults();
		}
	}

	@Override
	public void onContentError(final Error error) {
		showNoResults();
		showError(error);
	}
}