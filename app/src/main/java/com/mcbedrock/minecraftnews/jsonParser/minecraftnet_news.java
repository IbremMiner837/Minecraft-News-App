package com.mcbedrock.minecraftnews.jsonParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcbedrock.minecraftnews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class minecraftnet_news extends Fragment {

    private static String JSON_URL = "https://www.minecraft.net/content/minecraft-net/_jcr_content.articles.grid";
    RecyclerView recyclerView;
    List<jsonData> jsonDataList;
    jsonAdapter adapter;

    public minecraftnet_news() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GetData getData = new GetData();
        getData.execute();
    }

    private void PutDataIntoRecyclerView(List<jsonData> jsonDataList) {
        adapter = new jsonAdapter(getContext(), jsonDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_minecraftnet_news, container, false);
        View view1 = inflater.inflate(R.layout.minecraftnet_card, container, false);

        jsonDataList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recview);

        TextView title = (TextView) view1.findViewById(R.id.title);
        title.setSelected(true);

        TextView sub_header = (TextView) view1.findViewById(R.id.sub_header);
        sub_header.setSelected(true);

        return view;
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();
                    while (data != -1) {
                        current += (char) data;
                        data = inputStreamReader.read();
                    }

                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("article_grid");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    jsonData model = new jsonData();
                    model.setTitle(jsonObject1.getJSONObject("default_tile").getString("title"));
                    model.setSubHeader(jsonObject1.getJSONObject("default_tile").getString("sub_header"));
                    model.setArticleURL(jsonObject1.getString("article_url"));
                    model.setImageURL(jsonObject1.getJSONObject("default_tile").getJSONObject("image").getString("imageURL"));

                    jsonDataList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(jsonDataList);
        }
    }
}