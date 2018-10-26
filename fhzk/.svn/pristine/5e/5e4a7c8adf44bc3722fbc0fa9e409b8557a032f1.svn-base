package com.flym.fhzk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Morphine on 2017/8/3.
 */

public class SharePreUtil {

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor edit = context.getSharedPreferences(key, Context.MODE_PRIVATE).edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor edit = context.getSharedPreferences(key, Context.MODE_PRIVATE).edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor edit = context.getSharedPreferences(key, Context.MODE_PRIVATE).edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static String getSting(Context context, String key) {
        return context.getSharedPreferences(key, Context.MODE_PRIVATE).getString(key, "");
    }

    public static int getInt(Context context, String key) {
        return context.getSharedPreferences(key, Context.MODE_PRIVATE).getInt(key, 0);
    }

    public static boolean getBoolean(Context context, String key) {
        return context.getSharedPreferences(key, Context.MODE_PRIVATE).getBoolean(key, false);
    }

}
