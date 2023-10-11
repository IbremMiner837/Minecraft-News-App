package com.mcbedrock.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.mcbedrock.app.R;
import com.mcbedrock.app.model.ChangelogsModel;

import java.util.List;

public class ChangelogsAdapter extends RecyclerView.Adapter<ChangelogsAdapter.ViewHolder> {


    private final Context context;
    private List<ChangelogsModel> models;

    public ChangelogsAdapter(Context context, List<ChangelogsModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_small_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop());

        holder.title.setText(models.get(position).getTitle());
        holder.version.setText(models.get(position).getVersion());
        Glide.with(holder.image.getContext())
                .load(models.get(position).getImage())
                .apply(requestOptions)
                .into(holder.image);

        holder.actionButton.setOnClickListener(view -> {
            Bundle finalBundle = new Bundle();
            finalBundle.putString("URL", models.get(position).getUrl());
            navController.navigate(R.id.action_contentFragment_to_articleFragment, finalBundle);
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialButton actionButton;
        TextView title, version;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            actionButton = itemView.findViewById(R.id.ActionButton);
            title = itemView.findViewById(R.id.TitleView);
            version = itemView.findViewById(R.id.VersionView);
            image = itemView.findViewById(R.id.ImageView);
        }
    }
}