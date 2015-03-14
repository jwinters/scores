package io.elapse.scores.fragments;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.scores.R;
import io.elapse.scores.datasets.BoxScoreTable;
import io.elapse.scores.requests.BoxScoreQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleItemFragment;

@ArcaFragment(
    fragmentLayout = R.layout.fragment_box_score
)
public class BoxScoreFragment extends ArcaSimpleItemFragment {

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

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
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
        if (itemId != null) {
            mItemId = itemId;
            reload();
        }
    }

    private void reload() {
		getViewManager().showProgressView();
        execute(new BoxScoreQuery(mItemId));
	}
}