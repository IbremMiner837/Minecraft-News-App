package com.mcbedrock.minecraftnews.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mcbedrock.minecraftnews.R;

public class verticalRecyclerViewAdapter extends RecyclerView.ViewHolder {

    public MaterialButton categoryName;
    public RecyclerView categoryRecyclerView;
    public RecyclerView.LayoutManager manager;

    public verticalRecyclerViewAdapter(@NonNull View itemView) {
        super(itemView);
        manager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryName = itemView.findViewById(R.id.category_item);
        categoryRecyclerView = itemView.findViewById(R.id.category_recyclerview);
        categoryRecyclerView.setLayoutManager(manager);
    }
}