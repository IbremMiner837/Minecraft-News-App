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

import com.mcbedrock.minecraftnews.adapter.ChangelogsAdapter;
import com.mcbedrock.minecraftnews.databinding.FragmentContentBinding;
import com.mcbedrock.minecraftnews.viewmodel.ChangelogsViewModel;

public class ContentFragment extends Fragment {
    private FragmentContentBinding binding;
    private ChangelogsViewModel changelogsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContentBinding.inflate(inflater, container, false);

        /* Доделать!!!
        String contentType;
        switch(bundle.getInt("contentType")){
            case 0:
                contentType = "BEDROCK_PATCH_NOTES";
                break;
            case 1:
                contentType = "JAVA_PATCH_NOTES";
                break;
            case 2:
                contentType = "DUNGEONS_PATCH_NOTES";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + bundle.getInt("contentType"));
        }
        ContentManager.getChangelogs(
                getActivity(),
                contentType,
                binding.recview,
                binding.shimmerLayout);
         */

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle.putAll(getArguments());

        changelogsViewModel = new ViewModelProvider(this).get(ChangelogsViewModel.class);
        changelogsViewModel.getChangelogs().observe(getViewLifecycleOwner(), changelogsItems -> {
            binding.recview.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.recview.setAdapter(new ChangelogsAdapter(requireActivity(), changelogsItems));
            binding.recview.setVisibility(View.VISIBLE);
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        });

        binding.recview.setVisibility(View.GONE);
        binding.shimmerLayout.setVisibility(View.VISIBLE);
        binding.shimmerLayout.startShimmer();
        changelogsViewModel.loadChangelogs(requireActivity(), bundle.getInt("contentType"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}