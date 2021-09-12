package com.mcbedrock.minecraftnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.mcbedrock.minecraftnews.API.ChromeCustomTabAPI;

public class AboutActivity extends AppCompatActivity {

    private MaterialButton version, changelog, source_code, license, email, github, other_apps;
    private String SOURCE_CODE_URL = "https://github.com/IbremMiner837/Minecraft-News-App";
    private String MY_GITHUB_URL = "https://github.com/IbremMiner837";
    private String UPDATE_HISTORY = "https://vk.com/@ibremminer837_dev-update-history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.about_app);

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

        final ChromeCustomTabAPI chromeCustomTabAPI = new ChromeCustomTabAPI(AboutActivity.this);

        version = findViewById(R.id.version);
        changelog = findViewById(R.id.changelog);
        source_code = findViewById(R.id.source_code);
        license = findViewById(R.id.license);
        email = findViewById(R.id.email);
        github = findViewById(R.id.github);
        other_apps = findViewById(R.id.other_apps);

        version.setText(getString(R.string.version) + ":" + " " + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")");

        changelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chromeCustomTabAPI.OpenCustomTab(AboutActivity.this, UPDATE_HISTORY, R.color.primaryColor);
            }
        });

        source_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chromeCustomTabAPI.OpenCustomTab(AboutActivity.this, SOURCE_CODE_URL, R.color.primaryColor);
            }
        });

        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, getString(R.string.coming_soon) + "...", Toast.LENGTH_SHORT).show();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:ibremminer837.dev@gail.com"));
                startActivity(Intent.createChooser(emailIntent, "Send feedback"));
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chromeCustomTabAPI.OpenCustomTab(AboutActivity.this, MY_GITHUB_URL, R.color.primaryColor);
            }
        });

        other_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, getString(R.string.coming_soon) + "...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}