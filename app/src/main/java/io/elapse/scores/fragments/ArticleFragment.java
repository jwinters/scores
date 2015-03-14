package io.elapse.scores.fragments;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.scores.R;
import io.elapse.scores.datasets.ArticleTable;
import io.elapse.scores.requests.ScoresQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleItemFragment;

@ArcaFragment(
    fragmentLayout = R.layout.fragment_article
)
public class ArticleFragment extends ArcaSimpleItemFragment {

    @ArcaFragmentBindings
	private static final Collection<Binding> BINDINGS = Arrays.asList(
        new Binding(R.id.article_id, ArticleTable.Columns.ID)
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
            return true;
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
        execute(new ScoresQuery(mItemId));
	}
}