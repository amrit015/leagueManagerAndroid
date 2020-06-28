package com.android.leaguemanager.utils;

import com.android.leaguemanager.BuildConfig;

public class AppLogs {
    private static boolean isLogEnabled;

    public static void i(String TAG, String message) {
        isLogEnabled = getLogBasedOnBuildTypes();
        if (isLogEnabled) {
            android.util.Log.i(TAG, message);
        }
    }

    public static void e(String TAG, String message) {
        isLogEnabled = getLogBasedOnBuildTypes();
        if (isLogEnabled) {
            android.util.Log.e(TAG, message);
        }
    }

    public static void d(String TAG, String message) {
        isLogEnabled = getLogBasedOnBuildTypes();
        if (isLogEnabled) {
            android.util.Log.e(TAG, message);
        }
    }

    public static void v(String TAG, String message) {
        isLogEnabled = getLogBasedOnBuildTypes();
        if (isLogEnabled) {
            android.util.Log.e(TAG, message);
        }
    }

    private static boolean getLogBasedOnBuildTypes() {
        return BuildConfig.DEBUG;
    }
}
