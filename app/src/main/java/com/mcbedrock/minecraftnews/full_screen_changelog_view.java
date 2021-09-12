package com.mcbedrock.minecraftnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcbedrock.minecraftnews.R;

public class full_screen_changelog_view extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView changelog_view_image;
    private TextView changelog_view_title, changelog_view_version, changelog_view_description;
    private String changelog_link, changelog_view_image_s, changelog_view_title_s, changelog_view_version_s, changelog_view_description_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_changelog_view);

        changelog_view_image = findViewById(R.id.changelog_view_image);
        changelog_view_title = findViewById(R.id.changelog_view_title);
        changelog_view_version = findViewById(R.id.changelog_view_version);
        changelog_view_description = findViewById(R.id.changelog_view_description);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.about_changelog);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = new Intent();

        changelog_view_image_s = getIntent().getStringExtra("img_link");
        changelog_view_title_s = getIntent().getStringExtra("name_title");
        changelog_view_version_s = getIntent().getStringExtra("version");
        changelog_view_description_s = getIntent().getStringExtra("description");

        Glide.with(changelog_view_image)
                .load(changelog_view_image_s)
                .into(changelog_view_image);
        changelog_view_title.setText(changelog_view_title_s);
        changelog_view_version.setText(changelog_view_version_s);
        changelog_view_description.setText(changelog_view_description_s.replaceAll("\\\\n", "\n"));
        changelog_link = getIntent().getStringExtra("changelog_link");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}