package com.scores.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.scores.R;
import com.scores.fragments.GameFragment;

public class GameActivity extends Activity {

    private static interface Extras {
        public static final String ITEM_ID = "item_id";
    }

    public static void newInstance(final Activity activity, final String itemId) {
        final Intent intent = new Intent(activity, GameActivity.class);
        intent.putExtra(Extras.ITEM_ID, itemId);
        activity.startActivity(intent);
    }

    private GameFragment mFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final String itemId = getIntent().getStringExtra(Extras.ITEM_ID);

        if (itemId != null) {
            mFragment = findFragment();
            mFragment.setItemId(itemId);
        }

        setupActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private GameFragment findFragment() {
        final int id = R.id.fragment_game;
        final FragmentManager manager = getFragmentManager();
        return (GameFragment) manager.findFragmentById(id);
    }

    private void setupActionBar() {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
