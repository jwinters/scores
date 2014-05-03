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
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.scores.R;
import com.scores.binders.GameListViewBinder;
import com.scores.datasets.GameView;
import com.scores.providers.ScoresContentProvider;
import com.xtremelabs.imageutils.ImageLoader;

import java.util.Arrays;
import java.util.Collection;

import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ModernItemAdapter;
import io.pivotal.arca.dispatcher.Error;
import io.pivotal.arca.dispatcher.Query;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.fragments.ArcaItemFragment;

public class GameFragment extends ArcaItemFragment {

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

	private ImageLoader mLoader;
	private String mItemId;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_game, container, false);
		setHasOptionsMenu(true);
		return view;
	}

	@Override
	public CursorAdapter onCreateAdapter(final View view, final Bundle savedInstanceState) {
        final ModernItemAdapter adapter = new ModernItemAdapter(getActivity(), BINDINGS);
		mLoader = ImageLoader.buildImageLoaderForFragment(this);
		adapter.setViewBinder(new GameListViewBinder(mLoader));
		return adapter;
	}
	
	@Override
	public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_game, menu);
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

    public void setItemId(final String itemId) {
        mItemId = itemId;
        reload();
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

	private void loadGames() {
        if (mItemId != null) {
            final Uri uri = ScoresContentProvider.Uris.SCORES;
            final Query request = new Query(uri, 1000);
            request.setWhere(GameView.Columns._ID + "=?", mItemId);
            execute(request);
        }
	}

    private View getItemView() {
        return getView().findViewById(R.id.item);
    }

	private View getLoadingView() {
		return getView().findViewById(R.id.loading);
	}

	private View getEmptyView() {
		return getView().findViewById(R.id.empty);
	}

	private void showLoading() {
		getItemView().setVisibility(View.INVISIBLE);
		getLoadingView().setVisibility(View.VISIBLE);
		getEmptyView().setVisibility(View.INVISIBLE);
	}

	private void showResults() {
        getItemView().setVisibility(View.VISIBLE);
		getLoadingView().setVisibility(View.INVISIBLE);
		getEmptyView().setVisibility(View.INVISIBLE);
	}

	private void showNoResults() {
        getItemView().setVisibility(View.INVISIBLE);
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
            setTitle(result);
			showResults();
		} else if (!result.isSyncing()) {
			showNoResults();
		}
	}

    private void setTitle(final QueryResult result) {
        final Cursor cursor = result.getResult();
        final String home = cursor.getString(cursor.getColumnIndex(GameView.Columns.HOME_TEAM_NAME));
        final String away = cursor.getString(cursor.getColumnIndex(GameView.Columns.AWAY_TEAM_NAME));
        final String title = String.format("%s vs. %s", away, home);
        getActivity().setTitle(title);
    }

    @Override
	public void onContentError(final Error error) {
		showNoResults();
		showError(error);
	}
}