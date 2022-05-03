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
import com.mcbedrock.minecraftnews.adapter.ChangelogsAdapter;
import com.mcbedrock.minecraftnews.adapter.MinecraftNewsAdapter;
import com.mcbedrock.minecraftnews.databinding.ActivityMainBinding;
import com.mcbedrock.minecraftnews.model.BaseModel;
import com.mcbedrock.minecraftnews.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<BaseModel> models;
    private ChangelogsAdapter adapter;
    private String BEDROCK_JSON = "https://raw.githubusercontent.com/JVMFrog/Minecraft-Changeloges/master/English/bedrock-releases.json";
    private String BETA_JSON = "https://raw.githubusercontent.com/JVMFrog/Minecraft-Changeloges/master/English/bedrock-beta-and-preview.json";
    private String JAVA_JSON = "https://raw.githubusercontent.com/JVMFrog/Minecraft-Changeloges/master/English/java-realeses.json";
    private String SNAPSHOT_JSON = "https://raw.githubusercontent.com/JVMFrog/Minecraft-Changeloges/master/English/java-snapshots.json";

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
        ExtractJson(BEDROCK_JSON);
    }

    private void ExtractJson(String jsonURL) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        BaseModel model = new BaseModel();
                        /*model.setTitle(jsonObject1.getJSONObject("default_title").getString("title"));
                        model.setSub_header(jsonObject1.getJSONObject("default_title").getString("sub_header"));
                        model.setImage_url(jsonObject1.getJSONObject("default_title").getJSONObject("image").getString("image"));*/

                        model.setTitle(jsonObject.getString("title"));
                        model.setVersion(jsonObject.getString("version"));
                        model.setUrl_text(jsonObject.getString("changelogURL"));
                        model.setImage_url(jsonObject.getString("imageURL"));

                        models.add(model);
                    }
                    binding.recview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new ChangelogsAdapter(getApplicationContext(), models);
                    binding.recview.setAdapter(adapter);
                } catch (JSONException e) {
                    //
                }
            }
        }, error -> Log.d("tag", "OnErrorResponse" + error.getMessage()));

        queue.add(jsonArrayRequest);

        /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONObject("").getString("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/
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
            models.clear();
            ExtractJson(BEDROCK_JSON);
            bottomSheetDialog.dismiss();
        });

        btn_beta_and_preview.setOnClickListener(view -> {
            models.clear();
            ExtractJson(BETA_JSON);
            bottomSheetDialog.dismiss();
        });

        btn_java.setOnClickListener(view -> {
            models.clear();
            ExtractJson(JAVA_JSON);
            bottomSheetDialog.dismiss();
        });

        btn_snapshot.setOnClickListener(view -> {
            models.clear();
            ExtractJson(SNAPSHOT_JSON);
            bottomSheetDialog.dismiss();
        });
    }
}