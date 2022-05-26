package com.mcbedrock.minecraftnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.databinding.ActivityMarkdownBinding;

import br.tiagohm.markdownview.css.InternalStyleSheet;
import br.tiagohm.markdownview.css.styles.Github;

public class MarkdownActivity extends AppCompatActivity {

    private ActivityMarkdownBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMarkdownBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = this.getIntent();
        String URL = intent.getExtras().getString("URL");

        InternalStyleSheet css = new Github();
        binding.MarkdownView.addStyleSheet(css);
        binding.MarkdownView.loadMarkdownFromUrl(URL);

        binding.toolbar.setOnClickListener(view1 -> {
            onBackPressed();
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_to_right_start, R.anim.right_to_left_start);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
    }
}