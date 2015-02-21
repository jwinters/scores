package io.elapse.scores.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.scores.R;
import io.elapse.scores.datasets.ArticleTable;
import io.elapse.scores.requests.ScoresQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ModernItemAdapter;
import io.pivotal.arca.dispatcher.Error;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.fragments.ArcaItemFragment;
import io.pivotal.arca.fragments.ArcaViewManager;

public class ArticleFragment extends ArcaItemFragment {

	private static final Collection<Binding> BINDINGS = Arrays.asList(
        new Binding(R.id.article_id, ArticleTable.Columns.ID)
    );

	private String mItemId;
    private ArcaViewManager mManager;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_article, container, false);
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

    public void setItemId(final String itemId) {
        mItemId = itemId;
        reload();
    }

    private void reload() {
		mManager.showProgressView();
        loadArticle();
	}

	private void loadArticle() {
        if (mItemId != null) {
            execute(new ScoresQuery(mItemId));
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