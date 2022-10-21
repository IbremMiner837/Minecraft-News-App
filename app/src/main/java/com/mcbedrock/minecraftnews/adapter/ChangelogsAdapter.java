package com.mcbedrock.minecraftnews.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.model.BaseModel;
import com.mcbedrock.minecraftnews.ui.fragment.ArticleFragment;
import com.mcbedrock.minecraftnews.utils.FragmentUtils;

import java.util.List;

public class ChangelogsAdapter extends RecyclerView.Adapter<ChangelogsAdapter.ViewHolder> {

    private final Context context;
    private List<BaseModel> models;

    public ChangelogsAdapter(Context context, List<BaseModel> models) {
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
            FragmentUtils.changeFragmentWithBackStack(
                    (FragmentActivity) context,
                    new ArticleFragment(),
                    R.id.frame,
                    "back",
                    finalBundle);
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