package io.lose.scores.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import io.lose.scores.R;
import io.lose.scores.datasets.GameView;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ModernCursorAdapter;

public class GamesAdapter extends ModernCursorAdapter {

    public GamesAdapter(final Context context, final Collection<Binding> bindings) {
        super(context, 0, bindings);
    }

    @Override
    public int getItemViewType(final int position) {
        final Cursor cursor = (Cursor) getItem(position);
        final int index = cursor.getColumnIndex(GameView.Columns.EVENT_STATUS);
        final String typeString = cursor.getString(index).toUpperCase();
        return ViewType.parse(typeString).ordinal();
    }

    @Override
    public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
        final int index = cursor.getColumnIndex(GameView.Columns.EVENT_STATUS);
        final String typeString = cursor.getString(index).toUpperCase();
        final int layout = ViewType.parse(typeString).getLayout();
        return LayoutInflater.from(context).inflate(layout, parent, false);
    }

    public enum ViewType {
        PRE_GAME(R.layout.list_item_game_pre_game),
        IN_PROGRESS(R.layout.list_item_game_in_progress),
        FINAL(R.layout.list_item_game_final);

        private final int mLayout;

        ViewType(final int layout) {
            mLayout = layout;
        }

        public int getLayout() {
            return mLayout;
        }

        public Binding newBinding(final int viewId, final String column) {
            return new Binding(ordinal(), viewId, column);
        }

        private static ViewType parse(final String name) {
            try {
                return ViewType.valueOf(name);
            } catch (final Exception e) {
                return ViewType.FINAL;
            }
        }
    }
}