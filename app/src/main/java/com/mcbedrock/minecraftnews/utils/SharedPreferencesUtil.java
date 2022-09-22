package com.mcbedrock.minecraftnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    // function to safe boolean value to SharedPreferences
    public static void saveBooleanToSharedPreferences(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // function to get boolean value from SharedPreferences
    public static boolean getBooleanFromSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
}
