package com.mcbedrock.minecraftnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.mcbedrock.minecraftnews.realeseChangelog.realeseChangelogs;

public class cardSizeSettingsActivity extends AppCompatActivity {

    private Boolean card_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_size_settings);
        CheckBox set_smallcard = (CheckBox) findViewById(R.id.set_smallcard_checkBox);
        CheckBox set_bigcard = (CheckBox) findViewById(R.id.set_bigcard_checkBox);

        LoadPrefs();

        if (card_size == true) {
            set_smallcard.setChecked(true);
            set_bigcard.setChecked(false);
        } else if (card_size == false) {
            set_bigcard.setChecked(true);
            set_smallcard.setChecked(false);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(cardSizeSettingsActivity.this, realeseChangelogs.class);
        startActivity(intent);
        finish();
        super.onBackPressed();  // optional depending on your needs
    }

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        card_size = sharedPreferences.getBoolean("card_smallsize", true);
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