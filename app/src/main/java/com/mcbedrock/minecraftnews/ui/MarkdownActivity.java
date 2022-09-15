package com.mcbedrock.minecraftnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.databinding.ActivityMarkdownBinding;
import com.mcbedrock.minecraftnews.utils.ContentHelper;

import org.json.JSONException;
import org.json.JSONObject;

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

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        final String mimeType = "text/html";
                        final String encoding = "UTF-8";
                        String html = response.getString("body");
                        String title = response.getString("title");
                        binding.MarkdownView.loadDataWithBaseURL("", html, mimeType, encoding, "");

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions = requestOptions.transform(new CenterCrop());
                        Glide.with(this)
                                .load(ContentHelper.CONTENT + response.getJSONObject("image").getString("url"))
                                .apply(requestOptions)
                                .into(binding.ImageView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                }
        );
        queue.add(jsonObjectRequest);

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