package io.lose.scores.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Arrays;
import java.util.Collection;

import io.lose.scores.R;
import io.lose.scores.binders.StandingsViewBinder;
import io.lose.scores.datasets.StandingView;
import io.lose.scores.requests.RankingQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;

public class StandingsActivity extends ScoresActivity {

	public static void newInstance(final Context context) {
		final Intent intent = new Intent(context, StandingsActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standings);
	}

	@ArcaFragment(
			fragmentLayout = R.layout.fragment_recycler_refresh,
			adapterItemLayout = R.layout.list_item_standing,
			binder = StandingsViewBinder.class
	)
	public static class StandingsFragment extends RefreshRecyclerViewFragment {

		@ArcaFragmentBindings
		private static final Collection<Binding> BINDINGS = Arrays.asList(
			new Binding(R.id.standing_team_logo, StandingView.Columns.TEAM_LOGO),
			new Binding(R.id.standing_team_abbreviation, StandingView.Columns.TEAM_ABBREVIATION),
			new Binding(R.id.standing_conference_rank, StandingView.Columns.CONFERENCE_RANK),
			new Binding(R.id.standing_division_rank, StandingView.Columns.DIVISION_RANK),
			new Binding(R.id.standing_games_played, StandingView.Columns.GAMES_PLAYED),
			new Binding(R.id.standing_last_ten_games_record, StandingView.Columns.LAST_TEN_GAMES_RECORD),
			new Binding(R.id.standing_short_record, StandingView.Columns.SHORT_RECORD),
			new Binding(R.id.standing_streak, StandingView.Columns.STREAK)
		);

		@Override
		public void onRefresh() {
			execute(new RankingQuery());
		}
	}
}