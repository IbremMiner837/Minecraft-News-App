package com.mcbedrock.minecraftnews.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mcbedrock.minecraftnews.api.CustomTabAPI;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.model.NewsModel;

import java.util.List;

public class MinecraftNewsAdapter extends RecyclerView.Adapter<MinecraftNewsAdapter.ViewHolder> {
    Context context;
    List<NewsModel> models;

    public MinecraftNewsAdapter(Context context, List<NewsModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_small_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(16));

        holder.title.setText(models.get(position).getTitle());
        holder.sub_header.setText(models.get(position).getSub_header());
        Glide.with(holder.image.getContext())
                .load("https://www.minecraft.net" + models.get(position).getImage_url())
                .apply(requestOptions)
                .into(holder.image);

        holder.itemView.setOnClickListener(view -> {
            new CustomTabAPI()
                    .open(this.context, "https://www.minecraft.net" + models.get(position).getArticle_url());
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, sub_header;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_news_title);
            sub_header = itemView.findViewById(R.id.item_news_sub_header);
            image = itemView.findViewById(R.id.item_news_image);
        }
    }
}