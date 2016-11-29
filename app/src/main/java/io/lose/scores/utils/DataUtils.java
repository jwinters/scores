package io.lose.scores.utils;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;

public class DataUtils {

    public static ContentValues get(final Class<? extends SQLiteTable> klass, final Map<String, String> data) {
        final ContentValues values = new ContentValues();
        try {
            getColumns(klass, data, values);
        } catch (Exception e) {
            Logger.ex(e);
        }
        return values;
    }

    private static void getColumns(final Class<?> klass, final Map<String, String> data, final ContentValues values) throws Exception {
        final Field[] fields = klass.getFields();
        for (final Field field : fields) {
            getField(field, data, values);
        }

        final Class<?>[] klasses = klass.getDeclaredClasses();
        for (int i = 0; i < klasses.length; i++) {
            getColumns(klasses[i], data, values);
        }
    }

    private static void getField(final Field field, final Map<String, String> data, final ContentValues values) throws Exception {
        final Column columnType = field.getAnnotation(Column.class);
        if (columnType != null) {
            final String columnName = (String) field.get(null);
            final String value = data.get(columnName);
            if (value != null) {
                values.put(columnName, value);
            }
        }
    }

    public static <T> ContentValues[] get(final Class<? extends SQLiteTable> klass, final List<T> data) {
        final List<ContentValues> result = new ArrayList<ContentValues>();
        for (final T object : data) {
            final ContentValues values = new ContentValues();
            getColumns(klass, object, values);
            result.add(values);
        }
        return result.toArray(new ContentValues[result.size()]);
    }

    private static <T> void getColumns(final Class<?> klass, final T data, final ContentValues values) {
        final Field[] fields = klass.getFields();
        for (final Field field : fields) {
            getField(field, data, values);
        }

        final Class<?>[] klasses = klass.getDeclaredClasses();
        for (int i = 0; i < klasses.length; i++) {
            getColumns(klasses[i], data, values);
        }
    }

    private static <T> void getField(final Field field, final T data, final ContentValues values) {
        try {
            final String columnName = (String) field.get(null);
            final Field declared = data.getClass().getDeclaredField(columnName);
            values.put(columnName, (String) declared.get(data));
        } catch (final Exception e) {
            // do nothing
        }
    }
}
