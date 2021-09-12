package com.mcbedrock.minecraftnews.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mcbedrock.minecraftnews.API.ChromeCustomTabAPI;
import com.mcbedrock.minecraftnews.MainActivity;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.adapter.changelogsAdapter;
import com.mcbedrock.minecraftnews.adapter.horizontalRecyclerViewAdapter;
import com.mcbedrock.minecraftnews.adapter.verticalRecyclerViewAdapter;
import com.mcbedrock.minecraftnews.jsonParser.minecraftnet_news;
import com.mcbedrock.minecraftnews.models.categoryModel;
import com.mcbedrock.minecraftnews.models.categoryTwoModel;
import com.mcbedrock.minecraftnews.models.changelogsModel;

import java.util.Locale;

public class CategoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<categoryModel, verticalRecyclerViewAdapter> categoryAdapter;
    private FirebaseRecyclerAdapter<categoryTwoModel, horizontalRecyclerViewAdapter> changelogsAdapter;
    private RecyclerView.LayoutManager manager;
    private Context mContext;

    public CategoriesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String locale = getContext().getResources().getConfiguration().locale.getLanguage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        mContext = getContext();

        //Database Init
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Category");

        manager = new LinearLayoutManager(mContext);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(manager);

        FirebaseRecyclerOptions<categoryModel> options = new FirebaseRecyclerOptions.Builder<categoryModel>()
                .setQuery(reference, categoryModel.class)
                .build();

        categoryAdapter = new FirebaseRecyclerAdapter<categoryModel, verticalRecyclerViewAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull verticalRecyclerViewAdapter verticalRecyclerViewAdapter, int i, @NonNull categoryModel categoryModel) {
                verticalRecyclerViewAdapter.categoryName.setText(categoryModel.getCategoryName());
                verticalRecyclerViewAdapter.categoryName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (verticalRecyclerViewAdapter.getAdapterPosition() == 0) {
                            getParentFragmentManager().beginTransaction().replace(R.id.wrapper, new minecraftnet_news()).addToBackStack(null).commit();
                        }
                        if (verticalRecyclerViewAdapter.getAdapterPosition() == 1) {
                            getParentFragmentManager().beginTransaction().replace(R.id.wrapper, new realeseRecyclerViewFragment()).addToBackStack(null).commit();
                        }
                        if (verticalRecyclerViewAdapter.getAdapterPosition() == 2) {
                            getParentFragmentManager().beginTransaction().replace(R.id.wrapper, new javaRecyclerViewFragment()).addToBackStack(null).commit();
                        }
                        if (verticalRecyclerViewAdapter.getAdapterPosition() == 3) {
                            getParentFragmentManager().beginTransaction().replace(R.id.wrapper, new betaRecyclerViewFragment()).addToBackStack(null).commit();
                        }
                        if (verticalRecyclerViewAdapter.getAdapterPosition() == 4) {
                            getParentFragmentManager().beginTransaction().replace(R.id.wrapper, new snapshotRecyclerViewFragment()).addToBackStack(null).commit();
                        }
                    }
                });

                FirebaseRecyclerOptions<categoryTwoModel> options1 = new FirebaseRecyclerOptions.Builder<categoryTwoModel>()
                        .setQuery(reference.child(categoryModel.getCategoryId()).child("data"), categoryTwoModel.class)
                        .build();

                changelogsAdapter = new FirebaseRecyclerAdapter<categoryTwoModel, horizontalRecyclerViewAdapter>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull horizontalRecyclerViewAdapter horizontalRecyclerViewAdapter, int i, @NonNull categoryTwoModel categoryTwoModel) {
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
                        horizontalRecyclerViewAdapter.category_title.setText(categoryTwoModel.getTitle());
                        horizontalRecyclerViewAdapter.category_description.setText(categoryTwoModel.getDescription());
                        Glide.with(horizontalRecyclerViewAdapter.category_image.getContext())
                                .load(categoryTwoModel.getImageURL())
                                .apply(requestOptions)
                                .into(horizontalRecyclerViewAdapter.category_image);

                        horizontalRecyclerViewAdapter.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                                final ChromeCustomTabAPI chromeCustomTabAPI = new ChromeCustomTabAPI(activity);
                                chromeCustomTabAPI.OpenCustomTab(activity, categoryTwoModel.getArticleURL(), R.color.primaryColor);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public horizontalRecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.category_card_view, parent, false);
                        return new horizontalRecyclerViewAdapter(view);
                    }
                };
                changelogsAdapter.startListening();
                changelogsAdapter.notifyDataSetChanged();
                verticalRecyclerViewAdapter.categoryRecyclerView.setAdapter(changelogsAdapter);
            }

            @NonNull
            @Override
            public verticalRecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_item_view, parent, false);
                return new verticalRecyclerViewAdapter(view);
            }
        };

        categoryAdapter.startListening();
        categoryAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(categoryAdapter);

        return view;
    }
}
