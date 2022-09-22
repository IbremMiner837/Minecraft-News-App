package com.mcbedrock.minecraftnews.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

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
                }
            } else {
                if (TranslationHelper.isLanguageDownloaded(TranslationHelper.getSystemLanguage())) {
                    TranslationHelper.deleteModelTranslateRemoteModel();
                }
            }
        });

        if (TranslationHelper.isLanguageDownloaded(TranslationHelper.getSystemLanguage())) {
            binding.deleteModelBtn.setVisibility(View.VISIBLE);
        } else {
            binding.deleteModelBtn.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }
}