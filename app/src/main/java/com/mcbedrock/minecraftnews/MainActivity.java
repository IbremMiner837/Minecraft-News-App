package com.mcbedrock.minecraftnews;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.color.DynamicColors;
import com.mcbedrock.minecraftnews.databinding.ActivityMainBinding;

import br.tiagohm.markdownview.css.ExternalStyleSheet;
import br.tiagohm.markdownview.css.InternalStyleSheet;
import br.tiagohm.markdownview.css.styles.Github;

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

        InternalStyleSheet css = new Github();
        binding.MarkdownView.addStyleSheet(css);
        binding.MarkdownView.loadMarkdownFromUrl("https://raw.githubusercontent.com/JVMFrog/Minecraft-Changeloges/master/English/Minecraft-Bedrock/Realeses/1-18-30.md");

        BottomAppBar();
    }

    public void BottomAppBar() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_menu);

        binding.BottomAppBar.setNavigationOnClickListener(view1 -> bottomSheetDialog.show());

        binding.BottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.news:
                    break;

                case R.id.settings:
                    break;

                case R.id.about:
                    break;
            }
            return false;
        });
    }
}