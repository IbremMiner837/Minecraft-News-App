package com.mcbedrock.minecraftnews.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.databinding.FragmentSettingsBinding;
import com.mcbedrock.minecraftnews.utils.DialogsUtil;
import com.mcbedrock.minecraftnews.utils.SharedPreferencesUtil;

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

        return binding.getRoot();
    }
}