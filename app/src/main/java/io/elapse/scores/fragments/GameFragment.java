package io.elapse.scores.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.scores.R;
import io.elapse.scores.binders.GameListViewBinder;
import io.elapse.scores.datasets.GameView;
import io.elapse.scores.requests.ScoresQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleItemFragment;

@ArcaFragment(
    fragmentLayout = R.layout.fragment_game,
    binder = GameListViewBinder.class
)
public class GameFragment extends ArcaSimpleItemFragment {

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

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
			return false;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

    public void setItemId(final String itemId) {
        mItemId = itemId;
        reload();
    }

    private void reload() {
		getViewManager().showProgressView();
        execute(new ScoresQuery(mItemId));
	}
}