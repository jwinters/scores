package com.scores.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scores.R;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class CalendarAdapter extends ArrayAdapter<LocalDate> {

	private final LocalDate mDate = new LocalDate();

	public CalendarAdapter(final Context context) {
		super(context, 0);
	}

	@Override
	public int getCount() {
		return 5;
	}
	
	@Override
	public LocalDate getItem(final int position) {
		final int days = position - 2;
		return mDate.plusDays(days);
	}
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		if (convertView == null) {
			convertView = newView(parent, R.layout.list_item_date);
		}
		bindView(position, convertView);
		return convertView;
	}
	
	@Override
	public View getDropDownView(final int position, View convertView, final ViewGroup parent) {
		if (convertView == null) {
			convertView = newView(parent, R.layout.list_item_date_dropdown);
		}
		bindView(position, convertView);
		return convertView;
	}

	private View newView(final ViewGroup parent, final int resource) {
		final Context context = parent.getContext();
		final LayoutInflater inflater = LayoutInflater.from(context);
		return inflater.inflate(resource, null);
	}
	
	private void bindView(final int position, final View convertView) {
		final TextView textView = (TextView) convertView.findViewById(R.id.date);
		final LocalDate item = getItem(position);
		if (!item.equals(LocalDate.now())) {
			textView.setText(DateTimeFormat.forPattern("EEEE, MMMM d").print(item));
		} else {
			textView.setText("Today");
		}
	}
}
