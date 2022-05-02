package com.mcbedrock.minecraftnews;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.android.material.color.DynamicColors;
import com.mcbedrock.minecraftnews.databinding.ActivityMainBinding;

import br.tiagohm.markdownview.css.ExternalStyleSheet;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DynamicColors.applyToActivitiesIfAvailable(getApplication());

        binding.MarkdownView.loadMarkdownFromUrl("https://raw.githubusercontent.com/JVMFrog/Minecraft-Changeloges/master/English/Minecraft-Bedrock/Realeses/1-18-30.md");
        binding.MarkdownView.addStyleSheet(ExternalStyleSheet.fromAsset("github.css", null));
    }
}