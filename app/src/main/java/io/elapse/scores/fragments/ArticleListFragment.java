package io.elapse.scores.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.scores.R;
import io.elapse.scores.activities.ArticleActivity;
import io.elapse.scores.datasets.ArticleTable;
import io.elapse.scores.requests.ArticlesQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleAdapterFragment;

@ArcaFragment(
    fragmentLayout = R.layout.fragment_article_list,
    adapterItemLayout = R.layout.list_item_article
)
public class ArticleListFragment extends ArcaSimpleAdapterFragment implements OnItemClickListener {

    @ArcaFragmentBindings
	private static final Collection<Binding> BINDINGS = Arrays.asList(
        new Binding(R.id.article_id, ArticleTable.Columns.ID)
    );

	@Override
	public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
		final Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
		final String articleId = cursor.getString(cursor.getColumnIndex(ArticleTable.Columns.ID));

		ArticleActivity.newInstance(getActivity(), articleId);
	}

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

    private void reload() {
		getViewManager().showProgressView();
        execute(new ArticlesQuery());
	}
}