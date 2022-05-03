package com.mcbedrock.minecraftnews.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.DynamicColors;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.adapter.MinecraftNewsAdapter;
import com.mcbedrock.minecraftnews.databinding.ActivityMainBinding;
import com.mcbedrock.minecraftnews.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<NewsModel> models;
    private MinecraftNewsAdapter adapter;
    private String jsonURL = "https://www.minecraft.net/content/minecraft-net/_jcr_content.articles.grid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DynamicColors.applyToActivitiesIfAvailable(getApplication());

        models = new ArrayList<>();

        //InternalStyleSheet css = new Github();
        //binding.MarkdownView.addStyleSheet(css);
        //binding.MarkdownView.loadMarkdownFromUrl("https://raw.githubusercontent.com/JVMFrog/Minecraft-Changeloges/master/English/Minecraft-Bedrock/Realeses/1-18-30.md");

        BottomAppBar();
        ExtractJson();
    }

    private void ExtractJson() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonURL, null, response -> {

            try {
                JSONArray jsonArray = response.getJSONArray("article_grid");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    NewsModel model = new NewsModel();
                    model.setTitle(jsonObject1.getJSONObject("default_title").getString("title"));
                    model.setSub_header(jsonObject1.getJSONObject("default_title").getString("sub_header"));
                    model.setImage_url(jsonObject1.getJSONObject("default_title").getJSONObject("image").getString("imageURL"));
                    models.add(model);
                }
                binding.recview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new MinecraftNewsAdapter(getApplicationContext(), models);
                binding.recview.setAdapter(adapter);
            } catch (JSONException e) {
                //
            }
        }, error -> Log.d("tag", "OnErrorResponse" + error.getMessage()));

        queue.add(jsonObjectRequest);
    }

    public void BottomAppBar() {

        MaterialButton btn_news, btn_bedrock, btn_beta_and_preview, btn_java, btn_snapshot;

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

        btn_news = bottomSheetDialog.findViewById(R.id.BS_MinecraftNewsBtn);
        btn_bedrock = bottomSheetDialog.findViewById(R.id.BS_MinecraftBedrockBtn);
        btn_beta_and_preview = bottomSheetDialog.findViewById(R.id.BS_MinecraftBetaBtn);
        btn_java = bottomSheetDialog.findViewById(R.id.BS_MinecraftJavaBtn);
        btn_snapshot = bottomSheetDialog.findViewById(R.id.BS_MinecraftSnapshotBtn);

        btn_news.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        btn_bedrock.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        btn_beta_and_preview.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        btn_java.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        btn_snapshot.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });
    }
}