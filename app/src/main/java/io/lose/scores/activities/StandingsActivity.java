package io.lose.scores.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Collection;
import java.util.Collections;

import io.lose.scores.R;
import io.lose.scores.datasets.StandingTable;
import io.lose.scores.requests.StandingsQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleRecyclerViewFragment;

public class StandingsActivity extends AppCompatActivity {

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
	public static class StandingsFragment extends ArcaSimpleRecyclerViewFragment implements SwipeRefreshLayout.OnRefreshListener {

		@ArcaFragmentBindings
		private static final Collection<Binding> BINDINGS = Collections.singletonList(
				new Binding(R.id.standing_id, StandingTable.Columns.ID)
		);

		private SwipeRefreshLayout mRefreshLayout;

		@Override
		public void onViewCreated(final View view, final Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			mRefreshLayout = (SwipeRefreshLayout) view;
			mRefreshLayout.setOnRefreshListener(this);
			mRefreshLayout.setRefreshing(false);

			onRefresh();
		}

		@Override
		public void onRefresh() {
			execute(new StandingsQuery());
		}
	}
}