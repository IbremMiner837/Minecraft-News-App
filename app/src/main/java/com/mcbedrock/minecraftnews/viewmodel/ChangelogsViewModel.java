package com.mcbedrock.minecraftnews.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.mcbedrock.minecraftnews.model.ChangelogsModel;
import com.mcbedrock.minecraftnews.repository.ChangelogsRepository;

import java.util.List;

public class ChangelogsViewModel extends ViewModel {
    private ChangelogsRepository changelogsRepository;
    private MutableLiveData<List<ChangelogsModel>> changelogsLiveData;

    public ChangelogsViewModel() {
        changelogsRepository = new ChangelogsRepository();
        changelogsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<ChangelogsModel>> getChangelogs() {
        return changelogsLiveData;
    }

    public void loadChangelogs(Context context, Integer contentType) {
        changelogsRepository.loadChangelogs(context, contentType, new ChangelogsRepository.ChangelogsListener() {
            @Override
            public void onSuccess(List<ChangelogsModel> changelogsItems) {
                changelogsLiveData.setValue(changelogsItems);
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("ChangelogsViewModel", "Error loading changelogs: " + error.toString());
            }
        });
    }
}
