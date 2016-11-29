package io.lose.scores.utils;

import android.util.Log;

public class Logger {

    public static void v(final String message) {
        Log.v("io.lose", message);
    }

    public static void ex(final Throwable throwable) {
        Log.e("io.lose", throwable.getLocalizedMessage(), throwable);
    }
}
