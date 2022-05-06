package com.mcbedrock.minecraftnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mcbedrock.minecraftnews.API.ChromeCustomTabAPI;
import com.mcbedrock.minecraftnews.BuildConfig;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;
    private ChromeCustomTabAPI chromeCustomTabAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        chromeCustomTabAPI = new ChromeCustomTabAPI(this);

        binding.AAAppVersionBtn.setText(getString(R.string.version) + ": " + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")");
        binding.AAUpdateHistoryBtn.setOnClickListener(view1 -> Toast.makeText(this, R.string.coming_soon, Toast.LENGTH_SHORT).show());
        binding.AASourceCodeBtn.setOnClickListener(view1 -> chromeCustomTabAPI.OpenCustomTab(this, getString(R.string.SOURCE_CODE)));
        binding.AALicenseBtn.setOnClickListener(view1 -> Toast.makeText(this, R.string.coming_soon, Toast.LENGTH_SHORT).show());
        binding.AAIbragimBtn.setOnClickListener(view1 -> chromeCustomTabAPI.OpenCustomTab(this, getString(R.string.MY_VK)));
        binding.BSVKGroupBtn.setOnClickListener(view1 -> chromeCustomTabAPI.OpenCustomTab(this, getString(R.string.JVMFrog)));
        binding.BSOtherAppsBtn.setOnClickListener(view1 -> {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:JVMFrog")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.GOOGLE_PLAY))));
            }
        });

        binding.toolbar.setOnClickListener(view1 -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
    }
}