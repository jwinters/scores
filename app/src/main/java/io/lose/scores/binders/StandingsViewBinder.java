package io.lose.scores.binders;

import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ViewBinder;

public class StandingsViewBinder implements ViewBinder {


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
}