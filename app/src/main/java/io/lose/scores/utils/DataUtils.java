package io.lose.scores.utils;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;

public class DataUtils {

    public static <T> ContentValues[] get(final Class<? extends SQLiteTable> klass, final List<Map<String, T>> data) throws Exception {
        final List<ContentValues> result = new ArrayList<>();
        for (final Map<String, T> object : data) {
            final ContentValues values = new ContentValues();
            getColumns(klass, object, values);
            result.add(values);
        }
        return result.toArray(new ContentValues[result.size()]);
    }

    public static <T> ContentValues get(final Class<? extends SQLiteTable> klass, final Map<String, T> data) {
        final ContentValues values = new ContentValues();
        try {
            getColumns(klass, data, values);
        } catch (Exception e) {
            Logger.ex(e);
        }
        return values;
    }

    private static <T> void getColumns(final Class<?> klass, final Map<String, T> data, final ContentValues values) throws Exception {
        final Field[] fields = klass.getFields();
        for (final Field field : fields) {
            getField(field, data, values);
        }

        final Class<?>[] klasses = klass.getDeclaredClasses();
        for (final Class<?> k : klasses) {
            getColumns(k, data, values);
        }
    }

    private static <T> void getField(final Field field, final Map<String, T> data, final ContentValues values) throws Exception {
        final Column columnType = field.getAnnotation(Column.class);
        if (columnType != null) {
            final String columnName = (String) field.get(null);
            final T value = data.get(columnName);
            if (value instanceof String) {
                values.put(columnName, (String) value);

            } else if (value instanceof Integer) {
                values.put(columnName, (Integer) value);

            } else if (value instanceof Float) {
                values.put(columnName, (Float) value);

            } else if (value instanceof byte[]) {
                values.put(columnName, (byte[]) value);
            }
        }
    }
}
