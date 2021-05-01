package com.mcbedrock.minecraftnews.javaRealeseChangelog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.RealeseChangelogModel;

public class javaRecyclerViewFragment extends Fragment {

    //ГЕНЕРАТОР КАРТОЧЕК

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recview;
    javaAdapter adapter;
    javaBigCardAdapter adapterBC;

    private Boolean card_size;

    public javaRecyclerViewFragment() {
    }

    public static javaRecyclerViewFragment newInstance(String param1, String param2) {
        javaRecyclerViewFragment fragment = new javaRecyclerViewFragment();
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

        LoadPrefs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_java_recview, container, false);

        recview = (RecyclerView) view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = FirebaseFirestore.getInstance()
                .collection("java_realese_changeloges")
                .limit(50);

        FirestoreRecyclerOptions<javaChangelogModel> options = new FirestoreRecyclerOptions.Builder<javaChangelogModel>()
                .setQuery(query, javaChangelogModel.class)
                .build();

        if (card_size) {
            adapter = new javaAdapter(options);
            recview.setAdapter(adapter);
        } else {
            adapterBC = new javaBigCardAdapter(options);
            recview.setAdapter(adapterBC);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (card_size) {
            adapter.startListening();
        } else {
            adapterBC.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (card_size) {
            adapter.startListening();
        } else {
            adapterBC.startListening();
        }
    }

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        card_size = sharedPreferences.getBoolean("card_smallsize", true);
    }
}