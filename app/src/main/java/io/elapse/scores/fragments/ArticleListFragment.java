package io.elapse.scores.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.scores.R;
import io.elapse.scores.activities.ArticleActivity;
import io.elapse.scores.datasets.ArticleTable;
import io.elapse.scores.requests.ArticlesQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ModernCursorAdapter;
import io.pivotal.arca.dispatcher.Error;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.fragments.ArcaAdapterFragment;
import io.pivotal.arca.fragments.ArcaViewManager;

public class ArticleListFragment extends ArcaAdapterFragment implements OnItemClickListener {

	private static final Collection<Binding> BINDINGS = Arrays.asList(
        new Binding(R.id.article_id, ArticleTable.Columns.ID)
    );

    private ArcaViewManager mManager;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_article_list, container, false);
		final AbsListView list = (AbsListView) view.findViewById(getAdapterViewId());
		list.setOnItemClickListener(this);
		return view;
	}

    @Override
	public CursorAdapter onCreateAdapter(final AdapterView<CursorAdapter> adapterView, final Bundle savedInstanceState) {
		return new ModernCursorAdapter(getActivity(), R.layout.list_item_article, BINDINGS);
	}
	
	@Override
	public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
		final Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
		final String articleId = cursor.getString(cursor.getColumnIndex(ArticleTable.Columns.ID));

		ArticleActivity.newInstance(getActivity(), articleId);
	}

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mManager = new ArcaViewManager(view);
        mManager.showProgressView();

        setHasOptionsMenu(true);
    }

    private void reload() {
		mManager.showProgressView();
		loadArticles();
	}

	private void loadArticles() {
		execute(new ArticlesQuery());
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