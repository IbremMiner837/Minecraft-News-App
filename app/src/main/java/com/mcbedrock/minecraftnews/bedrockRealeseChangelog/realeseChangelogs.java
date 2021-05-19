package com.mcbedrock.minecraftnews.bedrockRealeseChangelog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mcbedrock.minecraftnews.AboutActivity;
import com.mcbedrock.minecraftnews.minecraftBedrockDownload.MinecraftDownloadActivity;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.desingSettingsActivity;

public class realeseChangelogs extends AppCompatActivity {

    private Boolean sort_by_descending;

    private int theme = 0;

    public realeseChangelogs() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realese_changelogs);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle(R.string.changelogs);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setLogo(R.drawable.creeper_512);
        //actionBar.setDisplayUseLogoEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        LoadPrefs();

        if (theme == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if (theme == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (theme == 2) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new realeseRecyclerViewFragment()).commit();
    }


    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getInt("theme", 1);
        sort_by_descending = sharedPreferences.getBoolean("sort_by_descending",true);
    }

    private void SavePrefs(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void SavePrefs(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}