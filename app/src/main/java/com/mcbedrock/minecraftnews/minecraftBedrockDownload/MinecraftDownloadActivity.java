package com.mcbedrock.minecraftnews.minecraftBedrockDownload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mcbedrock.minecraftnews.AboutActivity;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.RewardAd;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.realeseChangelogs;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.realeseRecyclerViewFragment;
import com.mcbedrock.minecraftnews.desingSettingsActivity;

import java.util.Arrays;
import java.util.List;

public class MinecraftDownloadActivity extends AppCompatActivity {

    private Boolean sort_by_descending;
    private int theme = 0;

    public MinecraftDownloadActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minecraft_download);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle(R.string.download_minecraft);

        //TextView textView = (TextView) findViewById(R.id.file_size_text);
        //textView.setText(R.string.file_size);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new minecraftDownloadRecyclerViewFragment()).commit();

        /*final RewardAd rewardAd = new RewardAd(this);
        rewardAd.AdsInit();

        List<String> testDeviceIds = Arrays.asList("19F3304F67F5416E1C38CC303C6EF994");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);*/

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //handle other actionbar item clicks here

        if(id == R.id.sort_descending) {
            //от новых
            //SavePrefs("sort_by_descending", true);
            Toast toast = Toast.makeText(MinecraftDownloadActivity.this, R.string.function_not_available, Toast.LENGTH_SHORT);
            toast.show();
        }

        if(id == R.id.sort_ascending) {
            //SavePrefs("sort_by_descending", false);
            Toast toast = Toast.makeText(MinecraftDownloadActivity.this, R.string.function_not_available, Toast.LENGTH_SHORT);
            toast.show();
        }
        return super.onOptionsItemSelected(item);
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