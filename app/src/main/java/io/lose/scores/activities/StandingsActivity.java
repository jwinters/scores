package io.lose.scores.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Collection;
import java.util.Collections;

import io.lose.scores.R;
import io.lose.scores.datasets.StandingTable;
import io.lose.scores.requests.StandingsQuery;
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
			adapterItemLayout = R.layout.list_item_standing
	)
	public static class StandingsFragment extends RefreshRecyclerViewFragment {

		@ArcaFragmentBindings
		private static final Collection<Binding> BINDINGS = Collections.singletonList(
				new Binding(R.id.standing_id, StandingTable.Columns.ID)
		);

		@Override
		public void onRefresh() {
			execute(new StandingsQuery());
		}
	}
}