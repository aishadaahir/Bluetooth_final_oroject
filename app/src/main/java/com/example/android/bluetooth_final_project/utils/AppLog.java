package com.example.android.bluetooth_final_project.utils;

/**
 * Created by ravi.elluminati on 29-03-2016.
 */
public class AppLog {

    public static final boolean isDebug = true;

    public static final void Log(String tag, String message) {
        if (isDebug) {

            android.util.Log.i(tag, message + "");
        }
    }

    public static final void handleException(String tag, Exception e) {
        if (isDebug) {
            if (e != null) {
                android.util.Log.d(tag, e.toString());
            }
        }
    }
    public static final void handleThrowable(String tag, Throwable t) {
        if (isDebug) {
            if (t != null) {
                android.util.Log.d(tag, t + "");
            }
        }
    }
}
