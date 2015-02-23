package io.elapse.scores.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.scores.R;
import io.elapse.scores.datasets.BoxScoreTable;
import io.elapse.scores.requests.BoxScoreQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ModernItemAdapter;
import io.pivotal.arca.dispatcher.Error;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.fragments.ArcaItemFragment;
import io.pivotal.arca.fragments.ArcaViewManager;

public class BoxScoreFragment extends ArcaItemFragment {

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
    private ArcaViewManager mManager;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_box_score, container, false);
	}

    @Override
	public CursorAdapter onCreateAdapter(final View view, final Bundle savedInstanceState) {
        return new ModernItemAdapter(getActivity(), BINDINGS);
	}

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mManager = new ArcaViewManager(view);
        mManager.showProgressView();

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
        mItemId = itemId;
        reload();
    }

    private void reload() {
		mManager.showProgressView();
		loadBoxScore();
	}

	private void loadBoxScore() {
        if (mItemId != null) {
            execute(new BoxScoreQuery(mItemId));
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