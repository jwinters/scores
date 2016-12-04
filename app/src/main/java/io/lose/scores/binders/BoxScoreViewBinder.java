package io.lose.scores.binders;

import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import io.lose.scores.R;
import io.lose.scores.datasets.BoxScoreTable;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ViewBinder;

public class BoxScoreViewBinder implements ViewBinder {

    @Override
    public boolean setViewValue(final View view, final Cursor cursor, final Binding binding) {
        if (view instanceof SimpleDraweeView) {
            return loadImage((SimpleDraweeView) view, cursor, binding);
        } else {
            return setViewValue((TextView) view, cursor, binding);
        }
    }

    private boolean setViewValue(final TextView view, final Cursor cursor, final Binding binding) {
		switch (view.getId()) {
            case R.id.box_score_home_record:
                return setHomeRecordValue(view, cursor, binding);

            case R.id.box_score_home_faceoffs:
                return setHomeFaceoffsValue(view, cursor, binding);

            case R.id.box_score_home_power_plays:
                return setHomePowerPlaysValue(view, cursor, binding);

            case R.id.box_score_away_record:
                return setAwayRecordValue(view, cursor, binding);

            case R.id.box_score_away_faceoffs:
                return setAwayFaceoffsValue(view, cursor, binding);

            case R.id.box_score_away_power_plays:
                return setAwayPowerPlaysValue(view, cursor, binding);

            default:
                return false;
        }
	}

    private boolean loadImage(final SimpleDraweeView imageView, final Cursor cursor, final Binding binding) {
        final String url = cursor.getString(binding.getColumnIndex());
        if (url != null) {
            imageView.setImageURI(Uri.parse(url));
        }
        return true;
    }

	private boolean setHomeRecordValue(final TextView view, final Cursor cursor, final Binding binding) {
		final String wins = cursor.getString(binding.getColumnIndex());
        final String losses = cursor.getString(cursor.getColumnIndex(BoxScoreTable.Columns.HOME_LOSSES));
        final String overtime = cursor.getString(cursor.getColumnIndex(BoxScoreTable.Columns.HOME_OVERTIME_LOSSES));
		view.setText(wins + "-" + losses + "-" + overtime);
		return true;
	}

    private boolean setHomeFaceoffsValue(final TextView view, final Cursor cursor, final Binding binding) {
        final String wins = cursor.getString(binding.getColumnIndex());
        final String losses = cursor.getString(cursor.getColumnIndex(BoxScoreTable.Columns.HOME_FACEOFFS_LOST));
        view.setText(wins + "/" + losses);
        return true;
    }

    private boolean setHomePowerPlaysValue(final TextView view, final Cursor cursor, final Binding binding) {
        final String wins = cursor.getString(binding.getColumnIndex());
        final String losses = cursor.getString(cursor.getColumnIndex(BoxScoreTable.Columns.HOME_POWER_PLAY_OPPORTUNITIES));
        view.setText(wins + "/" + losses);
        return true;
    }

    private boolean setAwayRecordValue(final TextView view, final Cursor cursor, final Binding binding) {
        final String wins = cursor.getString(binding.getColumnIndex());
        final String losses = cursor.getString(cursor.getColumnIndex(BoxScoreTable.Columns.AWAY_LOSSES));
        final String overtime = cursor.getString(cursor.getColumnIndex(BoxScoreTable.Columns.AWAY_OVERTIME_LOSSES));
        view.setText(wins + "-" + losses + "-" + overtime);
        return true;
    }

    private boolean setAwayFaceoffsValue(final TextView view, final Cursor cursor, final Binding binding) {
        final String wins = cursor.getString(binding.getColumnIndex());
        final String losses = cursor.getString(cursor.getColumnIndex(BoxScoreTable.Columns.AWAY_FACEOFFS_LOST));
        view.setText(wins + "/" + losses);
        return true;
    }

    private boolean setAwayPowerPlaysValue(final TextView view, final Cursor cursor, final Binding binding) {
        final String wins = cursor.getString(binding.getColumnIndex());
        final String losses = cursor.getString(cursor.getColumnIndex(BoxScoreTable.Columns.AWAY_POWER_PLAY_OPPORTUNITIES));
        view.setText(wins + "/" + losses);
        return true;
    }
}