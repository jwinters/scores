package io.elapse.scores.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.scores.R;
import io.elapse.scores.binders.GameListViewBinder;
import io.elapse.scores.datasets.GameView;
import io.elapse.scores.requests.ScoresQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ModernItemAdapter;
import io.pivotal.arca.dispatcher.Error;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.fragments.ArcaItemFragment;
import io.pivotal.arca.fragments.ArcaViewManager;

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

	private String mItemId;
    private ArcaViewManager mManager;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_game, container, false);
	}

    @Override
	public CursorAdapter onCreateAdapter(final View view, final Bundle savedInstanceState) {
        final ModernItemAdapter adapter = new ModernItemAdapter(getActivity(), BINDINGS);
		adapter.setViewBinder(new GameListViewBinder());
		return adapter;
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

    private void reload() {
		mManager.showProgressView();
		loadGame();
	}

	private void loadGame() {
        if (mItemId != null) {
            execute(new ScoresQuery(mItemId));
        }
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