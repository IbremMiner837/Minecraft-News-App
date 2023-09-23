package com.mcbedrock.minecraftnews.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mcbedrock.minecraftnews.model.ChangelogsModel;
import com.mcbedrock.minecraftnews.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChangelogsRepository {
    public static final String CONTENT = "https://launchercontent.mojang.com/";
    public static final String BEDROCK_PATCH_NOTES = "https://launchercontent.mojang.com/bedrockPatchNotes.json";
    public static final String BETA_PATCH_NOTES = "https://launchercontent.mojang.com//testing/bedrockPatchNotes.json";
    public static final String JAVA_PATCH_NOTES = "https://launchercontent.mojang.com/javaPatchNotes.json";
    public static final String SNAPSHOTS_PATCH_NOTES = "https://launchercontent.mojang.com/testing/javaPatchNotes.json";
    public static final String DUNGEONS_PATCH_NOTES = "https://launchercontent.mojang.com/dungeonsPatchNotes.json";
    public static final String LEGENDS_PATCH_NOTES = "https://launchercontent.mojang.com/legendsPatchNotes.json";

    private static final String[] JSON_URL = {BEDROCK_PATCH_NOTES, JAVA_PATCH_NOTES, DUNGEONS_PATCH_NOTES, LEGENDS_PATCH_NOTES};

    public interface ChangelogsListener {
        void onSuccess(List<ChangelogsModel> changelogsItems);
        void onError(VolleyError error);
    }

    public void loadChangelogs(Context context, Integer contentType, ChangelogsListener listener) {
        List<ChangelogsModel> changelogsItems = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL[contentType], null,
                response -> {
                    try {
                        JSONArray jsonEntries = response.getJSONArray("entries");
                        for (int i = 0; i < jsonEntries.length(); i++) {
                            JSONObject jsonEntry = jsonEntries.getJSONObject(i);
                            ChangelogsModel changelogsModel = new ChangelogsModel(
                                    jsonEntry.getString("title"),
                                    jsonEntry.getString("version"),
                                    CONTENT + jsonEntry.getJSONObject("image").getString("url"),
                                    CONTENT + jsonEntry.getString("contentPath")
                            );
                            changelogsItems.add(changelogsModel);
                        }
                        listener.onSuccess(changelogsItems);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onError(new VolleyError("Error parsing JSON"));
                    }
                },
                error -> listener.onError(error));

        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}
