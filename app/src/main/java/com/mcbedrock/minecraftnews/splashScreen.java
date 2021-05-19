package com.mcbedrock.minecraftnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.realeseChangelogs;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class splashScreen extends AppCompatActivity {

    private int theme = 0;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    //private static int SPLASH_TIME_OUT = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Handler handler = new Handler();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        LoadPrefs();

        final AppUpdate appUpdate = new AppUpdate(splashScreen.this);

        HashMap<String, Object> defaultRate = new HashMap<>();
        defaultRate.put("new_version_code", String.valueOf(BuildConfig.VERSION_CODE));

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(defaultRate);

        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Boolean> task) {
                if(task.isSuccessful()) {

                }
            }
        });

        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashScreen.this, realeseChangelogs.class);
                startActivity(intent);
                finish();
            }
        }, 3800);*/

        Thread t = new Thread()
        {
            public void run()
            {
                try {
                    sleep(2000);
                    Intent intent = new Intent(splashScreen.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getInt("theme", 1);
    }
}