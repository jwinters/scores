package io.lose.scores.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.fragments.ArcaSimpleRecyclerViewFragment;

public class ScoresActivity extends AppCompatActivity {


	public static class RefreshRecyclerViewFragment extends ArcaSimpleRecyclerViewFragment implements SwipeRefreshLayout.OnRefreshListener {

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
		public void onContentChanged(final QueryResult result) {
			super.onContentChanged(result);

			mRefreshLayout.setRefreshing(false);
		}

		@Override
		public void onRefresh() {

		}
	}
}