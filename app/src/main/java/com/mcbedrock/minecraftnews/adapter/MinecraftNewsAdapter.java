package com.mcbedrock.minecraftnews.adapter;

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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.model.NewsModel;
import com.mcbedrock.minecraftnews.utils.ContentHelper;
import com.mcbedrock.minecraftnews.utils.CustomTabUtil;

import java.util.List;

public class MinecraftNewsAdapter extends RecyclerView.Adapter<MinecraftNewsAdapter.ViewHolder> {
    private final Context context;
    private final List<NewsModel> models;

    public MinecraftNewsAdapter(Context context, List<NewsModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop());

        holder.title.setText(models.get(position).getTitle());
        holder.textView.setText(models.get(position).getText());
        Glide.with(holder.image.getContext())
                .load(ContentHelper.CONTENT + models.get(position).getImage())
                .apply(requestOptions)
                .into(holder.image);

        holder.materialButton.setOnClickListener(view -> new CustomTabUtil()
                .open(this.context, models.get(position).getReadMoreUrl()));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialButton materialButton;
        TextView title, textView;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            materialButton = itemView.findViewById(R.id.ActionButton);
            title = itemView.findViewById(R.id.TitleView);
            textView = itemView.findViewById(R.id.TextView);
            image = itemView.findViewById(R.id.ImageView);
        }
    }
}