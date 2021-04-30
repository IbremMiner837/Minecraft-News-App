package com.mcbedrock.minecraftnews.minecraftBedrockDownload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mcbedrock.minecraftnews.AboutActivity;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.realeseChangelogs;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.realeseRecyclerViewFragment;

public class MinecraftDownloadActivity extends AppCompatActivity {

    public MinecraftDownloadActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minecraft_download);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new minecraftDownloadRecyclerViewFragment()).commit();

        //Bottom Nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_minecraft_download);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_minecraft_download:
                        return true;

                    case R.id.navigation_changelogs:
                        startActivity(new Intent(getApplicationContext(), realeseChangelogs.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_settings:
                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });


    }
}