package io.lose.scores.activities;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import io.lose.scores.R;

public class FeedActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

	public static void newInstance(final Context context) {
		final Intent intent = new Intent(context, FeedActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        setDate(LocalDate.now());
	}

    private void setDate(final LocalDate date) {
        final String formatted = DateTimeFormat.forPattern("EEE, MMM d").print(date);
        findCollapsingToolbar().setTitle(formatted);
        findGameListFragment().setDate(date);
    }

    private CollapsingToolbarLayout findCollapsingToolbar() {
        return (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }

    private GameListActivity.GameListFragment findGameListFragment() {
        final FragmentManager manager = getFragmentManager();
        return (GameListActivity.GameListFragment) manager.findFragmentById(R.id.fragment_game_list);
    }

    private ArticleListActivity.ArticleListFragment findArticleListFragment() {
        final FragmentManager manager = getFragmentManager();
        return (ArticleListActivity.ArticleListFragment) manager.findFragmentById(R.id.fragment_article_list);
    }

    public void onActionButtonClick(final View view) {
        final LocalDate now = LocalDate.now();
        final int year = now.getYear();
        final int month = now.getMonthOfYear();
        final int day = now.getDayOfMonth();

        new DatePickerDialog(this, this, year, month - 1, day).show();
    }

    @Override
    public void onDateSet(final DatePicker datePicker, int year, int month, int day) {

        setDate(new LocalDate(year, month + 1, day));
    }
}