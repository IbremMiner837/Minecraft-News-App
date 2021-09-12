package com.mcbedrock.minecraftnews.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcbedrock.minecraftnews.R;

public class horizontalRecyclerViewAdapter extends RecyclerView.ViewHolder {

    public ImageView category_image;
    public TextView category_title;
    public TextView category_description;

    public horizontalRecyclerViewAdapter(@NonNull View itemView) {
        super(itemView);

        category_image = itemView.findViewById(R.id.category_image);
        category_title = itemView.findViewById(R.id.category_title);
        category_description = itemView.findViewById(R.id.category_description);
    }
}