package com.mcbedrock.minecraftnews.ui;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.databinding.ActivityMainBinding;
import com.mcbedrock.minecraftnews.ui.fragment.ContentFragment;
import com.mcbedrock.minecraftnews.ui.fragment.MainFragment;
import com.mcbedrock.minecraftnews.utils.ContentHelper;
import com.mcbedrock.minecraftnews.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private static final int RC_APP_UPDATE = 100;
    private AppUpdateManager mAppUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /*splashScreen.setOnExitAnimationListener(splashScreenView -> {
            splashScreenView.animate().alpha(0).setDuration(500).withEndAction(() -> {
                splashScreenView.setVisibility(View.GONE);
            }); vccbnd
        });*/

        FragmentUtils.changeFragment(this, new MainFragment(), R.id.frame, null);

        binding.toolbar.setSubtitle("");

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
}