package io.elapse.scores.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import io.elapse.scores.R;
import io.elapse.scores.fragments.ArticleFragment;

public class ArticleActivity extends Activity {

    private static interface Extras {
        public static final String ARTICLE_ID = "article_id";
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

        setupActionBar();
    }

    private ArticleFragment findArticleFragment() {
        final FragmentManager manager = getFragmentManager();
        return (ArticleFragment) manager.findFragmentById(R.id.fragment_article);
    }

    private void setupActionBar() {
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
