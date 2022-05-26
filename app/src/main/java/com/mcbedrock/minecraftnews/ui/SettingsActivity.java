package com.mcbedrock.minecraftnews.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.slider.Slider;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.api.CustomDialogAPI;
import com.mcbedrock.minecraftnews.config.Settings;
import com.mcbedrock.minecraftnews.config.SettingsAssist;
import com.mcbedrock.minecraftnews.databinding.ActivitySettingsBinding;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        File settingsFile = new File(getFilesDir(), "Settings.json");
        if(!settingsFile.exists()) {
            try {
                SettingsAssist.save(settingsFile, Settings.class);
            } catch (IOException e) {
                e.printStackTrace();
                new CustomDialogAPI()
                        .showErrorDialog(this, e.toString());
            }
        }
        loadSettings();
        setContentView(view);

        binding.toolbar.setOnClickListener(view1 -> onBackPressed());
        newsCardSettings();
    }

    public void newsCardSettings() {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(Settings.imageCornerRadius));

        Glide.with(binding.itemNewsImage)
                .load(R.drawable.minecraft_background_0)
                .apply(requestOptions)
                .into(binding.itemNewsImage);
        binding.sliderNewsCardTextSize.setValue(Settings.textSize);
        binding.itemSettingsTitle.setTextSize(Settings.textSize);
        binding.itemSettingsSubHeader.setTextSize(Settings.textSize);
        binding.itemNewsImage.getLayoutParams().width = Settings.textSize * 12;
        binding.itemNewsImage.getLayoutParams().height = Settings.textSize * 12;
        binding.sliderNewsCardImageCornerRadius.setValue(Settings.imageCornerRadius);
        binding.sliderNewsCardCornerRadius.setValue(Settings.cardCornerRadius);
        binding.newsCard.setRadius(Settings.cardCornerRadius);

        if (Settings.isTitleBolded) {
            binding.itemSettingsTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            binding.switchIsTitleBolded.setChecked(true);
        } else {
            binding.itemSettingsTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            binding.switchIsTitleBolded.setChecked(false);
        }

        binding.switchIsTitleBolded.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.itemSettingsTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                Settings.isTitleBolded = true;
            } else {
                binding.itemSettingsTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                Settings.isTitleBolded = false;
            }
            saveSettings();
        });

        binding.sliderNewsCardTextSize.addOnChangeListener((slider, value, fromUser) -> {
            binding.itemSettingsTitle.setTextSize(Math.round(value));
            binding.itemSettingsSubHeader.setTextSize(Math.round(value));
            binding.itemNewsImage.getLayoutParams().width = Math.round(value) * 12;
            binding.itemNewsImage.getLayoutParams().height = Math.round(value) * 12;

            Settings.textSize = Math.round(value);
        });

        binding.sliderNewsCardTextSize.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                //
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                saveSettings();
            }
        });

        binding.sliderNewsCardImageCornerRadius.addOnChangeListener((slider, value, fromUser) -> {
            RequestOptions requestOptionss = new RequestOptions();
            requestOptionss = requestOptionss.transform(new CenterCrop(), new RoundedCorners((int) value));

            Glide.with(binding.itemNewsImage)
                    .load(R.drawable.minecraft_background_0)
                    .apply(requestOptionss)
                    .into(binding.itemNewsImage);

            Settings.imageCornerRadius = Math.round(value);
        });

        binding.sliderNewsCardImageCornerRadius.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                //
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                saveSettings();
            }
        });

        binding.sliderNewsCardCornerRadius.addOnChangeListener((slider, value, fromUser) -> {

            binding.newsCard.setRadius(value);
            Settings.cardCornerRadius = Math.round(value);
        });

        binding.sliderNewsCardCornerRadius.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                //
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                saveSettings();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_to_right_start, R.anim.right_to_left_start);
    }

    public void saveSettings() {
        File settingsFile = new File(getFilesDir(), "Settings.json");

        try {
            SettingsAssist.save(settingsFile, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
            new CustomDialogAPI()
                    .showErrorDialog(this, e.toString());
        }
    }

    public void loadSettings() {
        File settingsFile = new File(getFilesDir(), "Settings.json");

        try {
            SettingsAssist.load(settingsFile, Settings.class);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            new CustomDialogAPI()
                    .showErrorDialog(this, e.toString());
        }
    }
}