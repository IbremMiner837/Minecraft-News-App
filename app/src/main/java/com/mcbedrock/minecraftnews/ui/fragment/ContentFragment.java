package com.mcbedrock.minecraftnews.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.databinding.FragmentContentBinding;
import com.mcbedrock.minecraftnews.utils.ContentHelper;

public class ContentFragment extends Fragment {

    private FragmentContentBinding binding;
    private RecyclerView recview;
    private ShimmerFrameLayout shimmerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContentBinding.inflate(inflater, container, false);

        recview = getActivity().findViewById(R.id.recview);
        shimmerLayout = getActivity().findViewById(R.id.shimmer_layout);

        Bundle bundle = new Bundle();
        bundle.putAll(getArguments());
        if (bundle.getInt("contentType") == 0) {
            ContentHelper.getChangelogs(
                    getActivity(),
                    ContentHelper.BEDROCK_PATCH_NOTES,
                    binding.recview,
                    binding.shimmerLayout
            );
        } else if (bundle.getInt("contentType") == 1) {
            ContentHelper.getChangelogs(
                    getActivity(),
                    ContentHelper.JAVA_PATCH_NOTES,
                    binding.recview,
                    binding.shimmerLayout
            );
        }  else if (bundle.getInt("contentType") == 2) {
            ContentHelper.getChangelogs(
                    getActivity(),
                    ContentHelper.DUNGEONS_PATCH_NOTES,
                    binding.recview,
                    binding.shimmerLayout
            );
        }

        return binding.getRoot();
    }
}