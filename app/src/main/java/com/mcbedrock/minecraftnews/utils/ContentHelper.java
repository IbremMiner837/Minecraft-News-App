package com.mcbedrock.minecraftnews.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.mcbedrock.minecraftnews.adapter.ChangelogsAdapter;
import com.mcbedrock.minecraftnews.adapter.MinecraftNewsAdapter;
import com.mcbedrock.minecraftnews.model.BaseModel;
import com.mcbedrock.minecraftnews.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContentHelper {
    public static final String NEWS_JSON = "https://www.minecraft.net/content/minecraft-net/_jcr_content.articles.grid";
    public static final String ALL_NEWS_JSON = "https://launchercontent.mojang.com/news.json";
    public static final String CONTENT = "https://launchercontent.mojang.com";
    public static final String BEDROCK_PATCH_NOTES = "https://launchercontent.mojang.com/bedrockPatchNotes.json";
    public static final String BETA_PATCH_NOTES = "https://launchercontent.mojang.com//testing/bedrockPatchNotes.json";
    public static final String JAVA_PATCH_NOTES = "https://launchercontent.mojang.com/javaPatchNotes.json";
    public static final String SNAPSHOTS_PATCH_NOTES = "https://launchercontent.mojang.com/testing/javaPatchNotes.json";
    public static final String DUNGEONS_PATCH_NOTES = "https://launchercontent.mojang.com/dungeonsPatchNotes.json";

    public static void getNews(Context context, RecyclerView recyclerView, ShimmerFrameLayout shimmerFrameLayout) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ALL_NEWS_JSON, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("entries");
                List<NewsModel> newsList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    String description = jsonObject.getString("text");
                    String image = jsonObject.getJSONObject("playPageImage").getString("url");
                    String url = jsonObject.getString("readMoreLink");
                    newsList.add(new NewsModel(title, description, image, url));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new MinecraftNewsAdapter(context, newsList));
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.e("Volley", error.toString());
        });
        queue.add(jsonObjectRequest);
    }

    public static void getChangelogs(Context context, String jsonURL, RecyclerView recyclerView, ShimmerFrameLayout shimmerFrameLayout) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonURL, null, response -> {
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            try {
                JSONArray jsonArray = response.getJSONArray("entries");
                List<BaseModel> baseModels = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    String description = jsonObject.getString("version");
                    String image = CONTENT + jsonObject.getJSONObject("image").getString("url");
                    String url = CONTENT + jsonObject.getString("contentPath");
                    baseModels.add(new BaseModel(title, description, url, image));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new ChangelogsAdapter(context, baseModels));
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.e("Volley", error.toString());
        });
        queue.add(jsonObjectRequest);
    }
}
