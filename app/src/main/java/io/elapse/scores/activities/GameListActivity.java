package io.elapse.scores.activities;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.joda.time.LocalDate;

import io.elapse.scores.R;
import io.elapse.scores.adapters.CalendarAdapter;
import io.elapse.scores.fragments.GameListFragment;

public class GameListActivity extends Activity implements OnNavigationListener {

	public static final void newInstance(final Context context) {
		final Intent intent = new Intent(context, GameListActivity.class);
		context.startActivity(intent);
	}
	
	private GameListFragment mFragment;
	private CalendarAdapter mAdapter;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);
		
		mFragment = findFragment();
		mAdapter = createAdapter();
		
		setupActionBar(mAdapter);
	}

	private CalendarAdapter createAdapter() {
		return new CalendarAdapter(this);
	}

	private GameListFragment findFragment() {
		final int id = R.id.fragment_game_list;
		final FragmentManager manager = getFragmentManager();
		return (GameListFragment) manager.findFragmentById(id);
	}
	
	private void setupActionBar(final ArrayAdapter adapter) {
		final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            actionBar.setListNavigationCallbacks(adapter, this);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setSelectedNavigationItem(2);
        }
	}

	@Override
	public boolean onNavigationItemSelected(final int position, final long itemId) {
		final LocalDate date = mAdapter.getItem(position);
		mFragment.setDate(date);
		return true;
	}
}