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

        BottomAppBar();
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

    public void BottomAppBar() {

        MaterialButton btn_news, btn_bedrock, btn_beta_and_preview, btn_java, btn_snapshot;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_menu);

        binding.BottomAppBar.setNavigationOnClickListener(view1 -> bottomSheetDialog.show());

        binding.BottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.news:
                    //binding.toolbar.setSubtitle("");
                    bottomSheetDialog.dismiss();
                    break;

                case R.id.about:
                    Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.right_to_left_finish, R.anim.left_to_right_finish);
                    break;
            }
            return false;
        });

        btn_news = bottomSheetDialog.findViewById(R.id.BS_MinecraftNewsBtn);
        btn_bedrock = bottomSheetDialog.findViewById(R.id.BS_MinecraftBedrockBtn);
        btn_beta_and_preview = bottomSheetDialog.findViewById(R.id.BS_MinecraftBetaBtn);
        btn_java = bottomSheetDialog.findViewById(R.id.BS_MinecraftJavaBtn);
        btn_snapshot = bottomSheetDialog.findViewById(R.id.BS_MinecraftSnapshotBtn);

        btn_news.setOnClickListener(view -> {
            binding.toolbar.setSubtitle("");
            ContentHelper.getNews(
                    this,
                    findViewById(R.id.recview),
                    findViewById(R.id.shimmer_layout));;
            bottomSheetDialog.dismiss();
        });

        btn_bedrock.setOnClickListener(view -> {
            binding.toolbar.setSubtitle(R.string.minecraft_bedrock_releases);
            ContentHelper.getChangelogs(
                    this,
                    ContentHelper.BEDROCK_PATCH_NOTES,
                    findViewById(R.id.recview),
                    findViewById(R.id.shimmer_layout));
                    bottomSheetDialog.dismiss();
        });

        btn_java.setOnClickListener(view -> {
            binding.toolbar.setSubtitle(R.string.minecraft_java_edition_releases);
            ContentHelper.getChangelogs(
                    this,
                    ContentHelper.JAVA_PATCH_NOTES,
                    findViewById(R.id.recview),
                    findViewById(R.id.shimmer_layout));
                    bottomSheetDialog.dismiss();
        });

        btn_snapshot.setOnClickListener(view -> {
            binding.toolbar.setSubtitle(R.string.minecraft_java_edition_snapshots);
            ContentHelper.getChangelogs(
                    this,
                    ContentHelper.SNAPSHOTS_PATCH_NOTES,
                    findViewById(R.id.recview),
                    findViewById(R.id.shimmer_layout));
                    bottomSheetDialog.dismiss();
        });
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