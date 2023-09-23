package com.mcbedrock.minecraftnews.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.mcbedrock.minecraftnews.model.NewsModel;
import com.mcbedrock.minecraftnews.repository.NewsRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private NewsRepository newsRepository;
    private MutableLiveData<List<NewsModel>> newsLiveData;

    public NewsViewModel() {
        newsRepository = new NewsRepository();
        newsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<NewsModel>> getNews() {
        return newsLiveData;
    }

    public void loadNews(Context context) {
        newsRepository.loadNews(context, new NewsRepository.NewsListener() {
            @Override
            public void onSuccess(List<NewsModel> newsItems) {
                newsLiveData.setValue(newsItems);
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("NewsViewModel", "Error loading news: " + error.toString());
            }
        });
    }
}
