package com.mcbedrock.minecraftnews.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.adapter.DashboardAdapter;
import com.mcbedrock.minecraftnews.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(16));
        Glide.with(this)
                .load(R.drawable.minecraft_vanilla_poster)
                .apply(requestOptions)
                .into(binding.ImageView);


        String[] title = {
                "Bedrock", "Java Edition", "Dungeons", "Legends"
        };

        int[] image = {
                R.drawable.minecraft_vanilla_poster,
                R.drawable.minecraft_vanilla_poster,
                R.drawable.minecraft_vanilla_poster,
                R.drawable.minecraft_vanilla_poster,
        };

        binding.recview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.recview.setAdapter(new DashboardAdapter(getActivity(), title, image));

        return binding.getRoot();
    }
}