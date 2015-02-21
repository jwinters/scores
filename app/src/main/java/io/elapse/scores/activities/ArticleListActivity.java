package io.elapse.scores.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import io.elapse.scores.R;

public class ArticleListActivity extends Activity {

	public static final void newInstance(final Context context) {
		final Intent intent = new Intent(context, ArticleListActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
	}
}