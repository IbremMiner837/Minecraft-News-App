package com.mcbedrock.minecraftnews.repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mcbedrock.minecraftnews.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsRepository {
    public static final String NEWS_JSON = "https://www.minecraft.net/content/minecraft-net/_jcr_content.articles.grid";
    private static final String JSON_URL = "https://launchercontent.mojang.com/news.json";

    public interface NewsListener {
        void onSuccess(List<NewsModel> newsItems);
        void onError(VolleyError error);
    }

    public void loadNews(Context context, NewsListener listener) {
        List<NewsModel> newsItems = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null,
                response -> {
                    try {
                        JSONArray jsonEntries = response.getJSONArray("entries");
                        for (int i = 0; i < jsonEntries.length(); i++) {
                            JSONObject jsonEntry = jsonEntries.getJSONObject(i);
                            NewsModel newsModel = new NewsModel(
                                    jsonEntry.getString("title"),
                                    jsonEntry.getString("text"),
                                    jsonEntry.getJSONObject("playPageImage").getString("url"),
                                    jsonEntry.getString("readMoreLink")
                            );
                            newsItems.add(newsModel);
                        }
                        listener.onSuccess(newsItems);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onError(new VolleyError("Error parsing JSON"));
                    }
                },
                error -> listener.onError(error));

        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}