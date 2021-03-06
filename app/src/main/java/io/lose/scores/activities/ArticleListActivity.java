package io.lose.scores.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;
import java.util.Collection;

import io.lose.scores.R;
import io.lose.scores.binders.ArticleListViewBinder;
import io.lose.scores.datasets.ArticleTable;
import io.lose.scores.monitors.ArticleListMonitor;
import io.lose.scores.requests.ArticlesQuery;
import io.lose.scores.utils.Logger;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;

public class ArticleListActivity extends ScoresActivity {

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
			fragmentLayout = R.layout.fragment_recycler_refresh,
			adapterItemLayout = R.layout.list_item_article,
            monitor = ArticleListMonitor.class,
            binder = ArticleListViewBinder.class
	)
	public static class ArticleListFragment extends RefreshRecyclerViewFragment {

		@ArcaFragmentBindings
		private static final Collection<Binding> BINDINGS = Arrays.asList(
				new Binding(R.id.article_title, ArticleTable.Columns.TITLE),
				new Binding(R.id.article_image, ArticleTable.Columns.MEDIUM_IMAGE)
		);

        @Override
        public RecyclerView.LayoutManager onCreateLayoutManager(final RecyclerView recyclerView, final Bundle savedInstanceState) {
            return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }

		@Override
		public void onRefresh() {
			execute(new ArticlesQuery());
		}

		@Override
		public void onItemClick(final RecyclerView recyclerView, final View view, final int position, final long id) {
			final Cursor cursor = (Cursor) getRecyclerViewAdapter().getItem(position);
			final String link = cursor.getString(cursor.getColumnIndex(ArticleTable.Columns.LINK));

            try {
                startActivity(Intent.parseUri(link, Intent.URI_INTENT_SCHEME));
            } catch (final Exception e) {
                Logger.ex(e);
            }
        }
	}
}