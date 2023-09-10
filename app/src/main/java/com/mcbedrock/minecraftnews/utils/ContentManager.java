package com.mcbedrock.minecraftnews.utils;

import static com.android.volley.RequestQueue.RequestEvent.REQUEST_CACHE_LOOKUP_FINISHED;
import static com.android.volley.RequestQueue.RequestEvent.REQUEST_CACHE_LOOKUP_STARTED;
import static com.android.volley.RequestQueue.RequestEvent.REQUEST_FINISHED;
import static com.android.volley.RequestQueue.RequestEvent.REQUEST_NETWORK_DISPATCH_FINISHED;
import static com.android.volley.RequestQueue.RequestEvent.REQUEST_NETWORK_DISPATCH_STARTED;
import static com.android.volley.RequestQueue.RequestEvent.REQUEST_QUEUED;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mcbedrock.minecraftnews.model.ChangelogsModel;
import com.mcbedrock.minecraftnews.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContentManager {
    public static final String NEWS_JSON = "https://www.minecraft.net/content/minecraft-net/_jcr_content.articles.grid";
    public static final String ALL_NEWS_JSON = "https://launchercontent.mojang.com/news.json";
    public static final String CONTENT = "https://launchercontent.mojang.com/";
    public static final String BEDROCK_PATCH_NOTES = "https://launchercontent.mojang.com/bedrockPatchNotes.json";
    public static final String BETA_PATCH_NOTES = "https://launchercontent.mojang.com//testing/bedrockPatchNotes.json";
    public static final String JAVA_PATCH_NOTES = "https://launchercontent.mojang.com/javaPatchNotes.json";
    public static final String SNAPSHOTS_PATCH_NOTES = "https://launchercontent.mojang.com/testing/javaPatchNotes.json";
    public static final String DUNGEONS_PATCH_NOTES = "https://launchercontent.mojang.com/dungeonsPatchNotes.json";

    private final List<ChangelogsModel> changelogsSet = new ArrayList<>();
    private final List<NewsModel> newsSet = new ArrayList<>();
    private final MutableLiveData<Boolean> isRequestFinished = new MutableLiveData<>();

    public List<ChangelogsModel> getChangelogsSet() {
        return changelogsSet;
    }

    public List<NewsModel> getNewsSet() {
        return newsSet;
    }

    public MutableLiveData<Boolean> isRequestFinished() {
        return isRequestFinished;
    }

    public void updateChangelogsAdapterData(Context context, String jsonURL) {
        if (changelogsSet.isEmpty()) {
            isRequestFinished.setValue(false);
            try {
                RequestQueue queue = Volley.newRequestQueue(context);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonURL, null,
                        response -> {
                            try {
                                parseChangelogs(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> Log.e("Volley", error.toString())
                );
                queue.addRequestEventListener(createRequestEventListener());
                queue.add(jsonObjectRequest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void parseChangelogs(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("entries");
        List<ChangelogsModel> changelogsModels = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String title = jsonObject.getString("title");
            String version = jsonObject.getString("version");
            String image = CONTENT + jsonObject.getJSONObject("image").getString("url");
            String url = CONTENT + jsonObject.getString("contentPath");
            changelogsSet.add(new ChangelogsModel(title, version, image, url));
        }
    }

    public void updateNewsAdapterData(Context context) {
        if (newsSet.isEmpty()) {
            isRequestFinished.setValue(false);
            try {
                RequestQueue queue = Volley.newRequestQueue(context);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ALL_NEWS_JSON, null,
                        response -> {
                            try {
                                parseChangelogs(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> Log.e("Volley", error.toString())
                );
                queue.addRequestEventListener(createRequestEventListener());
                queue.add(jsonObjectRequest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void parseNews(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("entries");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("text");
            String image = jsonObject.getJSONObject("playPageImage").getString("url");
            String url = jsonObject.getString("readMoreLink");
            newsSet.add(new NewsModel(title, description, image, url));
        }
    }

    private RequestQueue.RequestEventListener createRequestEventListener() {
        return (request, event) -> {
            switch (event) {
                case REQUEST_QUEUED:
                case REQUEST_CACHE_LOOKUP_STARTED:
                case REQUEST_NETWORK_DISPATCH_STARTED:
                    isRequestFinished.postValue(false);
                    break;
                case REQUEST_FINISHED:
                case REQUEST_CACHE_LOOKUP_FINISHED:
                case REQUEST_NETWORK_DISPATCH_FINISHED:
                    isRequestFinished.postValue(true);
                    break;
                default:
                    break;
            }
        };
    }
}
