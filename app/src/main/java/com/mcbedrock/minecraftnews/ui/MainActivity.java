package com.mcbedrock.minecraftnews.ui;

import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.databinding.ActivityMainBinding;
import com.mcbedrock.minecraftnews.ui.fragment.MainFragment;
import com.mcbedrock.minecraftnews.ui.fragment.NewsFragment;
import com.mcbedrock.minecraftnews.ui.fragment.VideosFragment;
import com.mcbedrock.minecraftnews.utils.DialogsUtil;
import com.mcbedrock.minecraftnews.utils.FragmentUtils;
import com.mcbedrock.minecraftnews.ui.fragment.SettingsFragment;
import com.mcbedrock.minecraftnews.utils.TranslationHelper;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private TranslationHelper translationHelper;
    private static final int RC_APP_UPDATE = 100;
    private AppUpdateManager mAppUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TranslationHelper.getAvailableModels();
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /*splashScreen.setOnExitAnimationListener(splashScreenView -> {
            splashScreenView.animate().alpha(0).setDuration(500).withEndAction(() -> {
                splashScreenView.setVisibility(View.GONE);
            }); vccbnd
        });*/

        translationHelper = new TranslationHelper(this);
        if (TranslationHelper.isLanguageDownloaded(TranslationHelper.getSystemLanguage())) {
            new DialogsUtil().downloadTranslateModel(this);
        }

        FragmentUtils.changeFragment(this, new NewsFragment(), R.id.frame, null);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.news:
                    FragmentUtils.changeFragment(this, new NewsFragment(), R.id.frame, null);
                    break;
                case R.id.changeloges:
                    FragmentUtils.changeFragment(this, new MainFragment(), R.id.frame, null);
                    break;
                case R.id.videos:
                    FragmentUtils.changeFragment(this, new VideosFragment(), R.id.frame, null);
                    break;
            }
            return true;
        });

        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.toolbar_menu_item_settings:
                    FragmentUtils.changeFragmentWithBackStack(this, new SettingsFragment(), R.id.frame, "back", null);
                    break;
            }
            return true;
        });
        //Play Core Update
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this, RC_APP_UPDATE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
        //mAppUpdateManager.registerListener(installStateUpdatedListener);
    }

    //Play Core Update
    private final InstallStateUpdatedListener installStateUpdatedListener = state -> {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            showCompletedUpdate();
        }
    };

    //Play Core Update
    private void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.new_update_availability,
                Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.update, v -> mAppUpdateManager.completeUpdate());
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Play Core Update
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                try {
                    mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this, RC_APP_UPDATE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }
}