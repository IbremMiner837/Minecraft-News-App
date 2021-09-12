package com.mcbedrock.minecraftnews.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mcbedrock.minecraftnews.MainActivity;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.adapter.changelogsAdapter;
import com.mcbedrock.minecraftnews.models.changelogsModel;

public class realeseRecyclerViewFragment extends Fragment {

    //ГЕНЕРАТОР КАРТОЧЕК

    private RecyclerView recview;
    private changelogsAdapter adapter;
    private Toolbar toolbar;

    public realeseRecyclerViewFragment() {
        //
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.bedrock_release_changelogs);

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_realese_recview, container, false);

        recview = (RecyclerView) view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(8)
                .setPageSize(3)
                .build();

        Query query = FirebaseFirestore.getInstance()
                .collection("bedrock_realese_changeloges")
                .orderBy("version", Query.Direction.DESCENDING)//от новых
                .limit(36);

        FirestorePagingOptions<changelogsModel> options = new FirestorePagingOptions.Builder<changelogsModel>()
                .setLifecycleOwner(this)
                .setQuery(query, config, changelogsModel.class)
                .build();

        adapter = new changelogsAdapter(options);
        recview.setHasFixedSize(true);
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
        adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }
}