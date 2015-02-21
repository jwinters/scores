package io.elapse.scores.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import io.elapse.scores.R;
import io.elapse.scores.fragments.BoxScoreFragment;
import io.elapse.scores.fragments.GameFragment;

public class GameActivity extends Activity {

    private static interface Extras {
        public static final String GAME_ID = "game_id";
        public static final String BOX_SCORE_ID = "box_score_id";
    }

    public static void newInstance(final Activity activity, final String gameId, final String boxScoreId) {
        final Intent intent = new Intent(activity, GameActivity.class);
        intent.putExtra(Extras.GAME_ID, gameId);
        intent.putExtra(Extras.BOX_SCORE_ID, boxScoreId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final String gameId = getIntent().getStringExtra(Extras.GAME_ID);
        final String boxScoreId = getIntent().getStringExtra(Extras.BOX_SCORE_ID);

        if (gameId != null) {
            findGameFragment().setItemId(gameId);
        }

        if (boxScoreId != null) {
            findBoxScoreFragment().setItemId(boxScoreId);
        }

        setupActionBar();
    }

    private GameFragment findGameFragment() {
        final FragmentManager manager = getFragmentManager();
        return (GameFragment) manager.findFragmentById(R.id.fragment_game);
    }

    private BoxScoreFragment findBoxScoreFragment() {
        final FragmentManager manager = getFragmentManager();
        return (BoxScoreFragment) manager.findFragmentById(R.id.fragment_box_score);
    }

    private void setupActionBar() {
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
