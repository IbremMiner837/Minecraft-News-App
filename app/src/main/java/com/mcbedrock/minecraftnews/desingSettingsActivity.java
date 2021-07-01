package com.mcbedrock.minecraftnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.MaterialToolbar;

public class desingSettingsActivity extends AppCompatActivity {

    private int theme = 0;
    private int view_changelog_info_type = 0;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desing_settings);

        //mAdView = findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);

        LoadPrefs();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton set_system_theme = (RadioButton) findViewById(R.id.set_system_theme_radio_btn);
        RadioButton set_light_theme = (RadioButton) findViewById(R.id.set_light_theme_radio_btn);
        RadioButton set_dark_theme = (RadioButton) findViewById(R.id.set_dark_theme_radio_btn);

        RadioGroup changelog_info_radio_group = (RadioGroup) findViewById(R.id.changelog_info_radio_group);
        RadioButton set_dialog_changelog_info_radio_btn = (RadioButton) findViewById(R.id.set_dialog_changelog_info_radio_btn);
        RadioButton set_fullscreen_changelog_info_radio_btn = (RadioButton) findViewById(R.id.set_fullscreen_changelog_info_radio_btn);

        //ImageView theme_settings_icon = (ImageView) findViewById(R.id.theme_settings_icon);
        TextView system_theme_desc = (TextView) findViewById(R.id.system_theme_desc);
        TextView select_card_size_btn = (TextView) findViewById(R.id.select_card_size_btn);
        TextView theme_text = (TextView) findViewById(R.id.theme_text);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.set_system_theme_radio_btn: {
                        set_system_theme.setChecked(true);
                        set_light_theme.setChecked(false);
                        set_dark_theme.setChecked(false);
                        system_theme_desc.setVisibility(View.VISIBLE);
                        theme_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_dark_light_24, 0, 0, 0);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        //theme_settings_icon.setImageResource(R.drawable.ic_action_view_dark_light);
                        theme = 0;
                        SavePrefs("theme", 0);
                        Toast toast = Toast.makeText(desingSettingsActivity.this, R.string.selected, Toast.LENGTH_LONG);
                        //toast.show();
                        break;
                    } case R.id.set_light_theme_radio_btn: {
                        set_system_theme.setChecked(false);
                        set_light_theme.setChecked(true);
                        set_dark_theme.setChecked(false);
                        system_theme_desc.setVisibility(View.GONE);
                        theme_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_light_mode_24, 0, 0, 0);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        //theme_settings_icon.setImageResource(R.drawable.ic_action_view_light_mode);
                        theme = 1;
                        SavePrefs("theme", 1);
                        Toast toast1 = Toast.makeText(desingSettingsActivity.this, R.string.selected, Toast.LENGTH_LONG);
                        //toast1.show();
                        break;
                    } case R.id.set_dark_theme_radio_btn: {
                        set_system_theme.setChecked(false);
                        set_light_theme.setChecked(false);
                        set_dark_theme.setChecked(true);
                        system_theme_desc.setVisibility(View.GONE);
                        theme_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_dark_mode_24, 0, 0, 0);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        //theme_settings_icon.setImageResource(R.drawable.ic_action_view_dark_mode);
                        theme = 2;
                        SavePrefs("theme", 2);
                        Toast toast2 = Toast.makeText(desingSettingsActivity.this, R.string.selected, Toast.LENGTH_LONG);
                        //toast2.show();
                    }
                }
            }
        });

        changelog_info_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.set_dialog_changelog_info_radio_btn: {
                        set_dialog_changelog_info_radio_btn.setChecked(true);
                        set_fullscreen_changelog_info_radio_btn.setChecked(false);
                        SavePrefs("view_changelog_info_type", 0);
                        view_changelog_info_type = 0;
                        Toast toast = Toast.makeText(desingSettingsActivity.this, R.string.selected, Toast.LENGTH_LONG);
                        //toast.show();
                        break;
                    } case R.id.set_fullscreen_changelog_info_radio_btn: {
                        set_dialog_changelog_info_radio_btn.setChecked(false);
                        set_fullscreen_changelog_info_radio_btn.setChecked(true);
                        SavePrefs("view_changelog_info_type", 1);
                        view_changelog_info_type = 1;
                        Toast toast1 = Toast.makeText(desingSettingsActivity.this, R.string.selected, Toast.LENGTH_LONG);
                        //toast1.show();
                        break;
                    }
                }
            }
        });

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

        if (theme == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            set_system_theme.setChecked(true);
            set_light_theme.setChecked(false);
            set_dark_theme.setChecked(false);
            system_theme_desc.setEnabled(true);
            system_theme_desc.setVisibility(View.VISIBLE);
            theme_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_dark_light_24, 0, 0, 0);
            //theme_settings_icon.setImageResource(R.drawable.ic_action_view_dark_light);
        } else if (theme == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            set_system_theme.setChecked(false);
            set_light_theme.setChecked(true);
            set_dark_theme.setChecked(false);
            system_theme_desc.setVisibility(View.GONE);
            theme_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_light_mode_24, 0, 0, 0);
            //theme_settings_icon.setImageResource(R.drawable.ic_action_view_light_mode);
        } else if (theme == 2) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            set_system_theme.setChecked(false);
            set_light_theme.setChecked(false);
            set_dark_theme.setChecked(true);
            system_theme_desc.setVisibility(View.GONE);
            theme_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_dark_mode_24, 0, 0, 0);
            //theme_settings_icon.setImageResource(R.drawable.ic_action_view_dark_mode);
        }

        if (view_changelog_info_type == 0) {
            set_dialog_changelog_info_radio_btn.setChecked(true);
            set_fullscreen_changelog_info_radio_btn.setChecked(false);
            //
        } else if (view_changelog_info_type == 1) {
            set_dialog_changelog_info_radio_btn.setChecked(false);
            set_fullscreen_changelog_info_radio_btn.setChecked(true);
        }

        MaterialToolbar actionbar = (MaterialToolbar) findViewById(R.id.topAppBar);
        if (null != actionbar) {
            actionbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);

            actionbar.setTitle(R.string.settings);
            actionbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //NavUtils.navigateUpFromSameTask(fullScreenChangelog_info.this);
                    onBackPressed();
                }
            });
        }
    }

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getInt("theme", 0);
        view_changelog_info_type = sharedPreferences.getInt("view_changelog_info_type", 1);
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
        Intent intent = new Intent(desingSettingsActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        super.onBackPressed();  // optional depending on your needs
    }
}