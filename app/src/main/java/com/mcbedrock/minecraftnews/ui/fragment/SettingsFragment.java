package com.mcbedrock.minecraftnews.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.databinding.FragmentSettingsBinding;
import com.mcbedrock.minecraftnews.utils.DialogsUtil;
import com.mcbedrock.minecraftnews.utils.SharedPreferencesUtil;
import com.mcbedrock.minecraftnews.utils.TranslationHelper;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        binding.enableTranslationSwitch.setChecked(SharedPreferencesUtil.getBooleanFromSharedPreferences(getContext(), "enable_translation"));
        binding.enableTranslationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferencesUtil.saveBooleanToSharedPreferences(getContext(), "enable_translation", isChecked);
            if (isChecked) {
                if (!TranslationHelper.isLanguageDownloaded(TranslationHelper.getSystemLanguage())) {
                    new DialogsUtil().downloadTranslateModel(getContext());
                } else {
                    new DialogsUtil().deleteTranslationModel(getContext());
                }
            }
        });

        if (!TranslationHelper.isLanguageDownloaded(TranslationHelper.getSystemLanguage())) {
            binding.isModelDownloadedText.setText(R.string.model_not_downloaded);
            binding.deleteModelBtn.setVisibility(View.GONE);
        } else {
            binding.isModelDownloadedText.setText(R.string.model_downloaded);
            binding.downloadModelBtn.setVisibility(View.GONE);
        }

        binding.downloadModelBtn.setOnClickListener(v -> {
            new DialogsUtil().downloadTranslateModel(getContext());
        });

        binding.deleteModelBtn.setOnClickListener(v -> {
            new DialogsUtil().deleteTranslationModel(getContext());
        });

        return binding.getRoot();
    }
}