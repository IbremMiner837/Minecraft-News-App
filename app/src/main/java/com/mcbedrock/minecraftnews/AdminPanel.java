package com.mcbedrock.minecraftnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        AutoCompleteTextView set_changelog_type = (AutoCompleteTextView) findViewById(R.id.autoCompleteText);

        String[] option = {
                getString(R.string.release_changelog),
                getString(R.string.beta_changelog),
                getString(R.string.snapshot_changelog),
        };

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.option_item, option);
        set_changelog_type.setText(arrayAdapter.getItem(0).toString(), false);
        set_changelog_type.setAdapter(arrayAdapter);
    }
}