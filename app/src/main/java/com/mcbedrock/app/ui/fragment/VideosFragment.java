package com.mcbedrock.app.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcbedrock.app.adapter.VideoAdapter;
import com.mcbedrock.app.databinding.FragmentVideosBinding;
import com.mcbedrock.app.utils.NetworkCheckHelper;
import com.mcbedrock.app.viewmodel.MainViewModel;
import com.prof.youtubeparser.models.videos.Video;

import java.util.ArrayList;

public class VideosFragment extends Fragment {
    private FragmentVideosBinding binding;
    private MainViewModel viewModel;
    private VideoAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideosBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding.recview.setVisibility(View.GONE);
        binding.shimmerLayout.setVisibility(View.VISIBLE);
        binding.shimmerLayout.startShimmer();

        binding.recview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recview.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new VideoAdapter(new ArrayList<Video>(), this);
        binding.recview.setAdapter(mAdapter);


        viewModel.getVideoLiveList().observe(getViewLifecycleOwner(), videos -> {
            if (videos != null) {
                mAdapter.setVideos(videos);
                mAdapter.notifyDataSetChanged();
                binding.recview.setVisibility(View.VISIBLE);
                binding.shimmerLayout.setVisibility(View.GONE);
                binding.shimmerLayout.stopShimmer();
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

        if (!NetworkCheckHelper.isNetworkAvailable(getContext())) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Error: No internet connection")
                    .setTitle("Please check your internet connection")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();

        } else {
            viewModel.fetchVideos();
        }

        return binding.getRoot();
    }
}