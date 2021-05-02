package com.mcbedrock.minecraftnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.realeseChangelogs;

public class desingSettingsActivity extends AppCompatActivity {

    private int theme = 0;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desing_settings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CheckBox set_system_theme = (CheckBox) findViewById(R.id.set_system_theme_checkBox);
        CheckBox set_light_theme = (CheckBox) findViewById(R.id.set_light_theme_checkBox);
        CheckBox set_dark_theme = (CheckBox) findViewById(R.id.set_dark_theme_checkBox);
        ImageView theme_settings_icon = (ImageView) findViewById(R.id.theme_settings_icon);
        TextView system_theme_desc = (TextView) findViewById(R.id.system_theme_desc);
        TextView select_card_size_btn = (TextView) findViewById(R.id.select_card_size_btn);

        select_card_size_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(desingSettingsActivity.this, cardSizeSettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
            }
        });

        LoadPrefs();

        if (theme == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            set_system_theme.setChecked(true);
            set_light_theme.setChecked(false);
            set_dark_theme.setChecked(false);
            system_theme_desc.setEnabled(true);
            system_theme_desc.setVisibility(View.VISIBLE);
            theme_settings_icon.setImageResource(R.drawable.ic_action_view_dark_light);
        } else if (theme == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            set_system_theme.setChecked(false);
            set_light_theme.setChecked(true);
            set_dark_theme.setChecked(false);
            system_theme_desc.setVisibility(View.GONE);
            theme_settings_icon.setImageResource(R.drawable.ic_action_view_light_mode);
        } else if (theme == 2) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            set_system_theme.setChecked(false);
            set_light_theme.setChecked(false);
            set_dark_theme.setChecked(true);
            system_theme_desc.setVisibility(View.GONE);
            theme_settings_icon.setImageResource(R.drawable.ic_action_view_dark_mode);
        }
    }

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getInt("theme", 1);
    }

    private void SavePrefs(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void SavePrefs(String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private void SavePrefs(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void onCheckboxClicked (View view) {
        CheckBox set_system_theme = (CheckBox) findViewById(R.id.set_system_theme_checkBox);
        CheckBox set_light_theme = (CheckBox) findViewById(R.id.set_light_theme_checkBox);
        CheckBox set_dark_theme = (CheckBox) findViewById(R.id.set_dark_theme_checkBox);
        ImageView theme_settings_icon = (ImageView) findViewById(R.id.theme_settings_icon);
        TextView system_theme_desc = (TextView) findViewById(R.id.system_theme_desc);
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.set_system_theme_checkBox: {
                set_system_theme.setChecked(true);
                set_light_theme.setChecked(false);
                set_dark_theme.setChecked(false);
                system_theme_desc.setVisibility(View.VISIBLE);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                theme_settings_icon.setImageResource(R.drawable.ic_action_view_dark_light);
                SavePrefs("theme", 0);
                Toast toast = Toast.makeText(this, R.string.selected, Toast.LENGTH_LONG);
                toast.show();
                break;
            }
            case R.id.set_light_theme_checkBox: {
                set_system_theme.setChecked(false);
                set_light_theme.setChecked(true);
                set_dark_theme.setChecked(false);
                system_theme_desc.setVisibility(View.GONE);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                theme_settings_icon.setImageResource(R.drawable.ic_action_view_light_mode);
                SavePrefs("theme", 1);
                Toast toast = Toast.makeText(this, R.string.selected, Toast.LENGTH_LONG);
                toast.show();
                break;
            }
            case R.id.set_dark_theme_checkBox: {
                set_system_theme.setChecked(false);
                set_light_theme.setChecked(false);
                set_dark_theme.setChecked(true);
                system_theme_desc.setVisibility(View.GONE);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                theme_settings_icon.setImageResource(R.drawable.ic_action_view_dark_mode);
                SavePrefs("theme", 2);
                Toast toast = Toast.makeText(this, R.string.selected, Toast.LENGTH_LONG);
                toast.show();
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(desingSettingsActivity.this, realeseChangelogs.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        super.onBackPressed();  // optional depending on your needs
    }
}