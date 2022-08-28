package com.mcbedrock.minecraftnews.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.mcbedrock.minecraftnews.adapter.ChangelogsAdapter;
import com.mcbedrock.minecraftnews.model.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JSONParserUtil {

    /*
    для парса: https://www.minecraft.net/content/minecraft-net/_jcr_content.articles.grid
    structure:
    object
    -> article_grid [20]
    [0, 1, 2, 3...]
    -> 0
       -> default_tile
                     -> sub_header: "Что то типо описания"
                     -> image
                            -> content_type: image
                            -> imageURL: /content/dam/games/minecraft/screenshots/soulsoil-1x1.jpg
                     -> tile_size: 1x1 (???)
                     -> title: "Загаловок"
                     -> article_url: /en-us/article/block-week--soul-soil
                     -> publish_date: 12 August 2021 14:45:06 UTC (нафига?)
       -> primary_category: Deep Dives
     */

    public static void parseChangelogsWithShimmerEffect(
            Context context,
            List<BaseModel> list,
            ChangelogsAdapter adapter,
            String jsonURL,
            ShimmerFrameLayout shimmerFrameLayout,
            RecyclerView recyclerView) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonURL, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("entries");
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    BaseModel model = new BaseModel();
                    model.setTitle(jsonObject.getString("title"));
                    model.setVersion(jsonObject.getString("version"));
                    //model.setUrl_text(jsonObject.getString("changelogURL"));
                    model.setImage_url("https://launchercontent.mojang.com/" + jsonObject.getJSONObject("image").getString("url"));

                    list.add(model);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
                adapter = new ChangelogsAdapter(context.getApplicationContext(), list);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
                new DialogsUtil()
                        .showErrorDialog(context, e.toString());
            }
        }, error -> {
            Log.d("tag", "OnErrorResponse" + error.getMessage());
            new DialogsUtil()
                    .showErrorDialog(context, error.toString());
        });

        queue.add(jsonObjectRequest);
    }
}
