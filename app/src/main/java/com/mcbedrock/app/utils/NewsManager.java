package com.mcbedrock.app.utils;

import static com.android.volley.RequestQueue.RequestEvent.REQUEST_CACHE_LOOKUP_FINISHED;
import static com.android.volley.RequestQueue.RequestEvent.REQUEST_CACHE_LOOKUP_STARTED;
import static com.android.volley.RequestQueue.RequestEvent.REQUEST_FINISHED;
import static com.android.volley.RequestQueue.RequestEvent.REQUEST_NETWORK_DISPATCH_STARTED;
import static com.android.volley.RequestQueue.RequestEvent.REQUEST_NETWORK_DISPATCH_FINISHED;
import static com.android.volley.RequestQueue.RequestEvent.REQUEST_QUEUED;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mcbedrock.app.Constants;
import com.mcbedrock.app.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsManager {
    private final List<NewsModel> newsSet = new ArrayList<>();
    private final MutableLiveData<Boolean> isRequestFinished = new MutableLiveData<>();

    public List<NewsModel> getNewsSet() {
        return newsSet;
    }

    public MutableLiveData<Boolean> isRequestFinished() {
        return isRequestFinished;
    }

    public void updateAdapterData(Context context) {
        if (newsSet.isEmpty()) {
            isRequestFinished.setValue(false);
            try {
                RequestQueue queue = Volley.newRequestQueue(context);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.ALL_NEWS, null,
                        response -> {
                            try {
                                parseResponse(response);
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

    private void parseResponse(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("entries");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String title = jsonObject.getString("title");
            String text = jsonObject.getString("text");
            String image_link = jsonObject.getJSONObject("playPageImage").getString("url");
            String read_more_link = jsonObject.getString("readMoreLink");
            newsSet.add(new NewsModel(title, text, image_link, read_more_link));
        }
    }

    private RequestQueue.RequestEventListener createRequestEventListener() {
        return (request, event) -> {
            switch (event) {
                case REQUEST_QUEUED, REQUEST_CACHE_LOOKUP_STARTED, REQUEST_NETWORK_DISPATCH_STARTED ->
                        isRequestFinished.postValue(false);
                case REQUEST_FINISHED, REQUEST_CACHE_LOOKUP_FINISHED, REQUEST_NETWORK_DISPATCH_FINISHED ->
                        isRequestFinished.postValue(true);
                default -> {
                }
            }
        };
    }
}
