package com.mcbedrock.minecraftnews.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mcbedrock.minecraftnews.adapter.MinecraftNewsAdapter;
import com.mcbedrock.minecraftnews.databinding.FragmentNewsBinding;
import com.mcbedrock.minecraftnews.viewmodel.NewsViewModel;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getNews().observe(getViewLifecycleOwner(), newsItems -> {
            binding.recview.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.recview.setAdapter(new MinecraftNewsAdapter(requireActivity(), newsItems));
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);

        });

        binding.shimmerLayout.startShimmer();
        newsViewModel.loadNews(requireActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}