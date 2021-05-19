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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.realeseChangelogs;

public class cardSizeSettingsActivity extends AppCompatActivity {

    private Boolean card_size;
    private int theme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_size_settings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        CheckBox set_smallcard = (CheckBox) findViewById(R.id.set_smallcard_checkBox);
        CheckBox set_bigcard = (CheckBox) findViewById(R.id.set_bigcard_checkBox);

        getSupportActionBar().setTitle(R.string.card_size);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadPrefs();

        /*if (theme == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if (theme == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (theme == 2) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }*/

        if (card_size == true) {
            set_smallcard.setChecked(true);
            set_bigcard.setChecked(false);
        } else if (card_size == false) {
            set_bigcard.setChecked(true);
            set_smallcard.setChecked(false);
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
        Intent intent = new Intent(cardSizeSettingsActivity.this, desingSettingsActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        super.onBackPressed();  // optional depending on your needs
    }

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        card_size = sharedPreferences.getBoolean("card_smallsize", true);
        theme = sharedPreferences.getInt("theme", 1);
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

    public void onCheckboxClicked (View view) {
        CheckBox set_smallcard = (CheckBox) findViewById(R.id.set_smallcard_checkBox);
        CheckBox set_bigcard = (CheckBox) findViewById(R.id.set_bigcard_checkBox);
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.set_smallcard_checkBox: {
                set_smallcard.setChecked(true);
                set_bigcard.setChecked(false);
                SavePrefs("card_smallsize", true);
                Toast toast = Toast.makeText(this, R.string.selected, Toast.LENGTH_LONG);
                toast.show();
                break;
            }
            case R.id.set_bigcard_checkBox: {
                set_bigcard.setChecked(true);
                set_smallcard.setChecked(false);
                SavePrefs("card_smallsize", false);
                Toast toast = Toast.makeText(this, R.string.selected, Toast.LENGTH_LONG);
                toast.show();
                break;
            }
        }
    }
}