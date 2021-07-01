package com.mcbedrock.minecraftnews.bedrockRealeseChangelog;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mcbedrock.minecraftnews.R;

public class realeseRecyclerViewFragment extends Fragment {

    //ГЕНЕРАТОР КАРТОЧЕК

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recview;
    bedrockRealeseAdapter adapter;
    bedrockRealeseBigCardAdapter adapterBC;

    TextView link_text;

    private Boolean card_size;
    private Boolean sort_by_descending;

    public realeseRecyclerViewFragment() {

    }

    public static realeseRecyclerViewFragment newInstance(String param1, String param2) {
        realeseRecyclerViewFragment fragment = new realeseRecyclerViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_realese_recview, container, false);
        /*View card_view = inflater.inflate(R.layout.singlerowdesing_small, container, false);
        View card_view1 = inflater.inflate(R.layout.singlerowdesing_small, container, false);
        TextView link_text = (TextView) card_view.findViewById(R.id.link_text);
        link_text.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        link_text.setSelected(true);*/

        recview = (RecyclerView) view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        LoadPrefs();

        if(sort_by_descending) {
            Query query = FirebaseFirestore.getInstance()
                    .collection("bedrock_realese_changeloges")
                    .orderBy("version", Query.Direction.DESCENDING)//от новых
                    .limit(50);

            FirestoreRecyclerOptions<RealeseChangelogModel> options = new FirestoreRecyclerOptions.Builder<RealeseChangelogModel>()
                    .setQuery(query, RealeseChangelogModel.class)
                    .build();

            if (card_size) {
                adapter = new bedrockRealeseAdapter(options);
                recview.setAdapter(adapter);
            } else {
                adapterBC = new bedrockRealeseBigCardAdapter(options);
                recview.setAdapter(adapterBC);
            }
        } else {
            Query query = FirebaseFirestore.getInstance()
                    .collection("bedrock_realese_changeloges")
                    .orderBy("version", Query.Direction.ASCENDING)//от новых
                    .limit(50);

            FirestoreRecyclerOptions<RealeseChangelogModel> options = new FirestoreRecyclerOptions.Builder<RealeseChangelogModel>()
                    .setQuery(query, RealeseChangelogModel.class)
                    .build();

            if (card_size) {
                adapter = new bedrockRealeseAdapter(options);
                recview.setAdapter(adapter);
            } else {
                adapterBC = new bedrockRealeseBigCardAdapter(options);
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