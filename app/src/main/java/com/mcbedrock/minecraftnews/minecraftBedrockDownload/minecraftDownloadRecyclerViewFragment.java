package com.mcbedrock.minecraftnews.minecraftBedrockDownload;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.minecraftBedrockDownload.minecraftDownloadModel;
import com.mcbedrock.minecraftnews.minecraftBedrockDownload.minecraftDownloadAdapter;


public class minecraftDownloadRecyclerViewFragment extends Fragment {

    //ГЕНЕРАТОР КАРТОЧЕК

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recview;
    minecraftDownloadAdapter adapter;

    TextView link_text;

    private Boolean card_size;

    public minecraftDownloadRecyclerViewFragment() {

    }

    public static minecraftDownloadRecyclerViewFragment newInstance(String param1, String param2) {
        minecraftDownloadRecyclerViewFragment fragment = new minecraftDownloadRecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //LoadPrefs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minecraft_download_recview, container, false);

        recview = (RecyclerView) view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = FirebaseFirestore.getInstance()
                .collection("minecraft_bedrock_realese_download")
                .limit(50);

        FirestoreRecyclerOptions<minecraftDownloadModel> options = new FirestoreRecyclerOptions.Builder<minecraftDownloadModel>()
                .setQuery(query, minecraftDownloadModel.class)
                .build();

            adapter = new minecraftDownloadAdapter(options);
            recview.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
            adapter.startListening();
    }

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        card_size = sharedPreferences.getBoolean("card_smallsize", true);
    }

}