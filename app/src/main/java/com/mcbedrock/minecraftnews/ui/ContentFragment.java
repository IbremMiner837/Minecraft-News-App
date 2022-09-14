package com.mcbedrock.minecraftnews.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.adapter.ChangelogsAdapter;
import com.mcbedrock.minecraftnews.adapter.MinecraftNewsAdapter;
import com.mcbedrock.minecraftnews.databinding.FragmentContentBinding;
import com.mcbedrock.minecraftnews.model.BaseModel;
import com.mcbedrock.minecraftnews.model.NewsModel;
import com.mcbedrock.minecraftnews.utils.ContentHelper;
import com.mcbedrock.minecraftnews.utils.DialogsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContentFragment extends Fragment {

    private FragmentContentBinding binding;
    private RecyclerView recview;
    private ShimmerFrameLayout shimmerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContentBinding.inflate(inflater, container, false);

        recview = getActivity().findViewById(R.id.recview);
        shimmerLayout = getActivity().findViewById(R.id.shimmer_layout);

        ContentHelper.getNews(
                getActivity(),
                binding.recview,
                binding.shimmerLayout
        );

        return binding.getRoot();
    }
}