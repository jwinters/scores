package io.lose.scores.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Collection;
import java.util.Collections;

import io.lose.scores.R;
import io.lose.scores.datasets.ArticleTable;
import io.lose.scores.requests.ArticlesQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleRecyclerViewFragment;

public class ArticleListActivity extends AppCompatActivity {

	public static void newInstance(final Context context) {
		final Intent intent = new Intent(context, ArticleListActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
	}

	@ArcaFragment(
			fragmentLayout = R.layout.fragment_swipe_recycler,
			adapterItemLayout = R.layout.list_item_article
	)
	public static class ArticleListFragment extends ArcaSimpleRecyclerViewFragment implements SwipeRefreshLayout.OnRefreshListener {

		@ArcaFragmentBindings
		private static final Collection<Binding> BINDINGS = Collections.singletonList(
				new Binding(R.id.article_id, ArticleTable.Columns.ID)
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
			execute(new ArticlesQuery());
		}

		@Override
		public void onItemClick(final RecyclerView recyclerView, final View view, final int position, final long id) {
			final Cursor cursor = (Cursor) getRecyclerViewAdapter().getItem(position);
			final String articleId = cursor.getString(cursor.getColumnIndex(ArticleTable.Columns.ID));

			ArticleActivity.newInstance(getActivity(), articleId);
		}
	}
}