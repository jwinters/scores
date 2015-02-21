package io.elapse.scores.binders;

import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import io.elapse.scores.R;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ViewBinder;

public class GameListViewBinder implements ViewBinder {

	@Override
	public boolean setViewValue(final View view, final Cursor cursor, final Binding binding) {
		if (view instanceof ImageView) {
			return loadImage((ImageView) view, cursor, binding);
		} else {
			return setViewValue((TextView) view, cursor, binding);
		}
	}

	private boolean loadImage(final ImageView imageView, final Cursor cursor, final Binding binding) {
		final String url = cursor.getString(binding.getColumnIndex());
        Picasso.with(imageView.getContext()).load(url).into(imageView);
		return true;
	}

	private boolean setViewValue(final TextView view, final Cursor cursor, final Binding binding) {
		if (view.getId() == R.id.game_date) {
            return setDateValue(view, cursor, binding);
		} else {
			return false;
		}
	}

    private boolean setDateValue(final TextView view, final Cursor cursor, final Binding binding) {
        final String date = cursor.getString(binding.getColumnIndex());
        final DateTime parsed = ISODateTimeFormat.dateTimeNoMillis().parseDateTime(date);
        view.setText(DateTimeFormat.shortTime().print(parsed));
        return true;
    }
}