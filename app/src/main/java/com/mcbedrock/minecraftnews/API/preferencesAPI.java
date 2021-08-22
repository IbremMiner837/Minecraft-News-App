package com.mcbedrock.minecraftnews.API;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class preferencesAPI {

    private Activity activity;

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        //card_size = sharedPreferences.getBoolean("card_smallsize", true);
        //theme = sharedPreferences.getInt("theme", 1);
    }

    private void SavePrefs(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void SavePrefs(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
