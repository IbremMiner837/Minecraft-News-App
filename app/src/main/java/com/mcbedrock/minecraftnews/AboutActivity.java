package com.mcbedrock.minecraftnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.realeseChangelogs;
import com.mcbedrock.minecraftnews.minecraftBedrockDownload.MinecraftDownloadActivity;

public class AboutActivity extends AppCompatActivity {

    TextView version_name_text, version_code_text, build_type_text;
    private int theme = 0;

    //crowdin token
    //78cce953a66615c110b9492707720c223f55c0abfc247c545a11e071d12184b467651ff647a584e3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle(R.string.about);

        //set action bar title
        //getSupportActionBar().setTitle(R.string.about);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //For TikTok
        //Icons made by Freepik from https://www.flaticon.com

        //For Discord
        //Icons made by Pixel Perfect from https://www.flaticon.com/

        //For VK
        //Icons made by Pixel perfect from https://www.flaticon.com/

        version_name_text = findViewById(R.id.version_name_text);
        version_code_text = findViewById(R.id.version_code_text);
        build_type_text = findViewById(R.id.build_type_text);

        version_name_text.setText(String.valueOf(BuildConfig.VERSION_NAME));
        version_code_text.setText("(" + String.valueOf(BuildConfig.VERSION_CODE + ")"));
        build_type_text.setText(String.valueOf(BuildConfig.BUILD_TYPE));

        ImageView img = (ImageView) findViewById(R.id.about_img);
        
        Button vk_btn = (Button) findViewById(R.id.vk_btn);
        Button tiktok_btn = (Button) findViewById(R.id.tiktok_btn);
        Button discord_btn = (Button) findViewById(R.id.discord_btn);

        LoadPrefs();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgClick();
            }
        });



        vk_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://vk.com/mcbedrock"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        tiktok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.tiktok.com/@mcbedrock_"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        discord_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://discord.gg/abS99Au"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        /*if (theme == 0) {
            //line.setVisibility(View.VISIBLE);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if (theme == 1) {
            //line.setVisibility(View.GONE);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (theme == 2) {
            //line.setVisibility(View.VISIBLE);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }*/
    }

    public void imgClick() {
        Toast toast = Toast.makeText(this, "Boom ;)", Toast.LENGTH_LONG);
        toast.show();

        Intent intent = new Intent(Intent.EXTRA_DONT_KILL_APP);
        startActivity(intent);
    }

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getInt("theme", 1);
    }

    /*
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
    }*/

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(AboutActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        super.onBackPressed();  // optional depending on your needs
    }
}