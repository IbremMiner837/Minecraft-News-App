package com.mcbedrock.minecraftnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mcbedrock.minecraftnews.realeseChangelog.realeseChangelogs;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Intent intent=new Intent(splashScreen.this, realeseChangelogs.class);
        startActivity(intent);
        finish();
    }
}