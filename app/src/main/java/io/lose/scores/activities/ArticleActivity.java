package io.lose.scores.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Collection;
import java.util.Collections;

import io.lose.scores.R;
import io.lose.scores.datasets.ArticleTable;
import io.lose.scores.requests.ScoresQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleItemFragment;

public class ArticleActivity extends AppCompatActivity {

    private interface Extras {
        String ARTICLE_ID = "article_id";
    }

    public static void newInstance(final Activity activity, final String articleId) {
        final Intent intent = new Intent(activity, ArticleActivity.class);
        intent.putExtra(Extras.ARTICLE_ID, articleId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        final String articleId = getIntent().getStringExtra(Extras.ARTICLE_ID);

        if (articleId != null) {
            findArticleFragment().setItemId(articleId);
        }
    }

    private ArticleFragment findArticleFragment() {
        final FragmentManager manager = getFragmentManager();
        return (ArticleFragment) manager.findFragmentById(R.id.fragment_article);
    }

    @ArcaFragment(
            fragmentLayout = R.layout.fragment_article
    )
    public static class ArticleFragment extends ArcaSimpleItemFragment implements SwipeRefreshLayout.OnRefreshListener {

        @ArcaFragmentBindings
        private static final Collection<Binding> BINDINGS = Collections.singletonList(
                new Binding(R.id.article_id, ArticleTable.Columns.ID)
        );

        private String mItemId;
        private SwipeRefreshLayout mRefreshLayout;

        @Override
        public void onViewCreated(final View view, final Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mRefreshLayout = (SwipeRefreshLayout) view;
            mRefreshLayout.setOnRefreshListener(this);
            mRefreshLayout.setRefreshing(false);

            onRefresh();
        }

        public void setItemId(final String itemId) {
            mItemId = itemId;
            onRefresh();
        }

        @Override
        public void onRefresh() {
            if (mItemId != null) {
                execute(new ScoresQuery(mItemId));
            }
        }
    }
}
