package com.scores.binders;

import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scores.R;
import com.xtremelabs.imageutils.ImageLoader;

import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ViewBinder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

public class GameListViewBinder implements ViewBinder {

	private final ImageLoader mLoader;

	public GameListViewBinder(final ImageLoader loader) {
		mLoader = loader;
	}

	@Override
	public boolean setViewValue(final View view, final Cursor cursor, final Binding binding) {
		if (view instanceof ImageView) {
			return setTeamLogo((ImageView) view, cursor, binding);
		} else {
			return setDefaultValue((TextView) view, cursor, binding);
		}
	}

	private boolean setTeamLogo(final ImageView imageView, final Cursor cursor, final Binding binding) {
		final int columnIndex = binding.getColumnIndex();
		final String url = cursor.getString(columnIndex);
		mLoader.loadImage(imageView, url);
		return true;
	}

	private boolean setDefaultValue(final TextView view, final Cursor cursor, final Binding binding) {
		if (view.getId() == R.id.game_date) {
			final int columnIndex = binding.getColumnIndex();
			final String date = cursor.getString(columnIndex);
			final DateTime parsed = ISODateTimeFormat.dateTimeNoMillis().parseDateTime(date);
			final String formatted = DateTimeFormat.shortTime().print(parsed);
			view.setText(formatted);
			return true;
		} else {
			return false;
		}
	}
}