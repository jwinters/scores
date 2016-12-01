package io.lose.scores.binders;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import io.lose.scores.R;
import io.lose.scores.datasets.GoalTable;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ViewBinder;

public class GoalListViewBinder implements ViewBinder {

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
            case R.id.goal_time:
                return setTimeValue(view, cursor, binding);

            case R.id.goal_player:
                return setGoalPlayerValue(view, cursor, binding);

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

	private boolean setTimeValue(final TextView view, final Cursor cursor, final Binding binding) {
		final String period = cursor.getString(binding.getColumnIndex());
        final String minute = cursor.getString(cursor.getColumnIndex(GoalTable.Columns.MINUTE));
        final String second = cursor.getString(cursor.getColumnIndex(GoalTable.Columns.SECOND));
        final String time = second.length() == 1 ? minute + ":" + second + "0" : minute + ":" + second;
		view.setText(period + " @ " + time);
		return true;
	}

    private boolean setGoalPlayerValue(final TextView view, final Cursor cursor, final Binding binding) {
        final String player = cursor.getString(binding.getColumnIndex());
        if (!TextUtils.isEmpty(player)) {
            view.setText("(G) " + player);
        }
        final String a1 = cursor.getString(cursor.getColumnIndex(GoalTable.Columns.A1_PLAYER_NAME));
        if (!TextUtils.isEmpty(a1)) {
            view.setText(view.getText() + ", (A) " + a1);
        }
        final String a2 = cursor.getString(cursor.getColumnIndex(GoalTable.Columns.A2_PLAYER_NAME));
        if (!TextUtils.isEmpty(a2)) {
            view.setText(view.getText() + ", (A) " + a2);
        }
        return true;
    }
}