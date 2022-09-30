package com.mcbedrock.minecraftnews.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.adapter.VideoAdapter;
import com.mcbedrock.minecraftnews.utils.MainViewModel;
import com.prof.youtubeparser.models.videos.Video;

import java.util.ArrayList;

public class VideosFragment extends Fragment {

    private com.mcbedrock.minecraftnews.databinding.FragmentVideosBinding binding;

    private MainViewModel viewModel;
    private VideoAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = com.mcbedrock.minecraftnews.databinding.FragmentVideosBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.list.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new VideoAdapter(new ArrayList<Video>(), this);
        binding.list.setAdapter(mAdapter);

        viewModel.getVideoLiveList().observe(getViewLifecycleOwner(), videos -> {
            if (videos != null) {
                mAdapter.setVideos(videos);
                mAdapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
                binding.swipeLayout.setRefreshing(false);
            }
        });

        viewModel.getLiveStats().observe(getViewLifecycleOwner(), stats -> {
            if (stats != null) {
                String body = "Views: " + stats.getViewCount() + "\n" +
                        "Like: " + stats.getLikeCount() + "\n" +
                        "Dislike: " + stats.getDislikeCount() + "\n" +
                        "Number of comment: " + stats.getCommentCount() + "\n" +
                        "Number of favourite: " + stats.getFavoriteCount();

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Stats");
                dialogBuilder.setMessage(body);
                dialogBuilder.setCancelable(false);
                dialogBuilder.setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialogBuilder.show();
            }
        });

        /*binding.swipeLayout.canChildScrollUp();
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mAdapter.getList().clear();
                mAdapter.notifyDataSetChanged();
                binding.swipeLayout.setRefreshing(true);
                viewModel.fetchVideos();
            }
        });*/

        if (!isNetworkAvailable()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Error: No internet connection")
                    .setTitle("Please check your internet connection")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();

        } else if (isNetworkAvailable()) {
            viewModel.fetchVideos();
        }

        return binding.getRoot();
    }

    public void getVideoStats(String videoId) {
        if (viewModel != null) {
            viewModel.fetchStatistics(videoId);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}