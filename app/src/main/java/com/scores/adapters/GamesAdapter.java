package com.scores.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scores.R;
import com.scores.datasets.GameView;

import java.util.Collection;
import java.util.Locale;

import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ModernCursorAdapter;

public class GamesAdapter extends ModernCursorAdapter {

    public static enum ViewTypes {
        IN_PROGRESS(R.layout.list_item_game_live),
        FINAL(R.layout.list_item_game_final),
        PRE_GAME(R.layout.list_item_game_pre_game);

        private int mLayout;

        ViewTypes(final int layout) {
            mLayout = layout;
        }

        public int getLayout() {
            return mLayout;
        }

        public Binding newBinding(final int viewId, final String column) {
            return new Binding(ordinal(), viewId, column);
        }
    }

    public GamesAdapter(final Context context, final Collection<Binding> bindings) {
        super(context, 0, bindings);
    }

    @Override
    public int getViewTypeCount() {
        return ViewTypes.values().length;
    }

    @Override
    public int getItemViewType(final int position) {
        final Cursor cursor = (Cursor) getItem(position);
        final int index = cursor.getColumnIndex(GameView.Columns.EVENT_STATUS);
        final String typeString = cursor.getString(index).toUpperCase(Locale.getDefault());
        return ViewTypes.valueOf(typeString).ordinal();
    }

    @Override
    public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
        final int index = cursor.getColumnIndex(GameView.Columns.EVENT_STATUS);
        final String typeString = cursor.getString(index).toUpperCase(Locale.getDefault());
        final ViewTypes viewType = ViewTypes.valueOf(typeString);
        final LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(viewType.getLayout(), parent, false);
    }
}