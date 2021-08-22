package com.mcbedrock.minecraftnews.fragments;

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
import com.mcbedrock.minecraftnews.adapter.changelogsAdapter;
import com.mcbedrock.minecraftnews.adapter.changelogsAdapterBG;
import com.mcbedrock.minecraftnews.models.changelogsModel;

public class betaRecyclerViewFragment extends Fragment {

    //ГЕНЕРАТОР КАРТОЧЕК
    RecyclerView recview;
    changelogsAdapter adapter;
    changelogsAdapterBG adapterBC;

    private Boolean card_size;
    private Boolean sort_by_descending;

    public betaRecyclerViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoadPrefs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_realese_recview, container, false);

        recview = (RecyclerView) view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        LoadPrefs();

        if (sort_by_descending) {
            Query query = FirebaseFirestore.getInstance()
                    .collection("bedrock_beta_changeloges")
                    .orderBy("version", Query.Direction.DESCENDING)//от новых
                    .limit(50);

            FirestoreRecyclerOptions<changelogsModel> options = new FirestoreRecyclerOptions.Builder<changelogsModel>()
                    .setQuery(query, changelogsModel.class)
                    .build();


            if (card_size) {
                adapter = new changelogsAdapter(options);
                recview.setAdapter(adapter);
            } else {
                adapterBC = new changelogsAdapterBG(options);
                recview.setAdapter(adapterBC);
            }
        } else {
            Query query = FirebaseFirestore.getInstance()
                    .collection("bedrock_beta_changeloges")
                    .orderBy("version", Query.Direction.ASCENDING)//от новых
                    .limit(50);

            FirestoreRecyclerOptions<changelogsModel> options = new FirestoreRecyclerOptions.Builder<changelogsModel>()
                    .setQuery(query, changelogsModel.class)
                    .build();


            if (card_size) {
                adapter = new changelogsAdapter(options);
                recview.setAdapter(adapter);
            } else {
                adapterBC = new changelogsAdapterBG(options);
                recview.setAdapter(adapterBC);
            }
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LoadPrefs();

        if (card_size) {
            adapter.startListening();
        } else {
            adapterBC.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LoadPrefs();

        if (card_size) {
            adapter.startListening();
        } else {
            adapterBC.startListening();
        }
    }

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        card_size = sharedPreferences.getBoolean("card_size", true);
        sort_by_descending = sharedPreferences.getBoolean("sort_by_descending",true);
    }
}