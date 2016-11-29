package io.lose.scores.binders;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import io.lose.scores.R;
import io.lose.scores.datasets.GameView;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ViewBinder;

public class GameListViewBinder implements ViewBinder {

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
            case R.id.game_date:
                return setDateValue(view, cursor, binding);

            case R.id.game_odd_closing:
                return setOddsValue(view, cursor, binding);

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

    private boolean setDateValue(final TextView view, final Cursor cursor, final Binding binding) {
        final String date = cursor.getString(binding.getColumnIndex());
        final DateTime parsed = ISODateTimeFormat.dateTimeNoMillis().parseDateTime(date);
        view.setText(DateTimeFormat.shortTime().withZone(DateTimeZone.getDefault()).print(parsed));
        return true;
    }

	private boolean setOddsValue(final TextView view, final Cursor cursor, final Binding binding) {
		final String odds = cursor.getString(binding.getColumnIndex());
		final String description = cursor.getString(cursor.getColumnIndex(GameView.Columns.GAME_DESCRIPTION));
		view.setText(!TextUtils.isEmpty(odds) ? odds : description);
		return true;
	}
}