package com.mcbedrock.app.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcbedrock.app.BuildConfig;
import com.mcbedrock.app.R;
import com.mcbedrock.app.databinding.FragmentAboutBinding;
import com.mcbedrock.app.utils.BugReportHelper;
import com.mcbedrock.app.utils.CustomTabUtil;

public class AboutFragment extends Fragment {
    private FragmentAboutBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);

        binding.appVersionBtn.setText(getString(R.string.version) + ": " + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")");
        binding.translateAppBtn.setOnClickListener(view -> new CustomTabUtil().OpenCustomTab(getActivity(), getString(R.string.CROWDIN), R.color.md_theme_light_onSecondary));

        binding.sourceCodeBtn.setOnClickListener(v -> new CustomTabUtil().OpenCustomTab(getActivity(), getString(R.string.SOURCE_CODE), R.color.md_theme_light_onSecondary));
        binding.ibragimBtn.setOnClickListener(v -> new CustomTabUtil().OpenCustomTab(getActivity(), getString(R.string.MY_GITHUB), R.color.md_theme_light_onSecondary));
        binding.mailBtn.setOnClickListener(v -> BugReportHelper.sendEmail(getActivity()));
        binding.rateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + getActivity().getPackageName()));
            startActivity(intent);
        });
        binding.vkGroupBtn.setOnClickListener(v -> new CustomTabUtil().OpenCustomTab(getActivity(), getString(R.string.JVMFrog), R.color.md_theme_light_onSecondary));
        binding.otherAppsBtn.setOnClickListener(view1 -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:JVMFrog")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.GOOGLE_PLAY))));
            }
        });

        return binding.getRoot();
    }
}