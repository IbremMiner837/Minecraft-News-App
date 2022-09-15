package com.mcbedrock.minecraftnews.adapter;

import android.content.Context;
import android.graphics.Paint;
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.ui.fragment.ArticleFragment;
import com.mcbedrock.minecraftnews.model.BaseModel;
import com.mcbedrock.minecraftnews.utils.FragmentUtils;

import java.util.List;

public class ChangelogsAdapter extends RecyclerView.Adapter<ChangelogsAdapter.ViewHolder> {

    Context context;
    List<BaseModel> models;

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
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(16));

        holder.title.setText(models.get(position).getTitle());
        holder.version.setText(models.get(position).getVersion());
        holder.url_text.setText(models.get(position).getUrl_text());
        Glide.with(holder.image.getContext())
                .load(models.get(position).getImage_url())
                .apply(requestOptions)
                .into(holder.image);

        holder.itemView.setOnClickListener(view -> {
            Bundle finalBundle = new Bundle();
            finalBundle.putString("URL", models.get(position).getUrl_text());
            FragmentUtils.changeFragmentWithBackStack(
                    (FragmentActivity) view.getContext(),
                    new ArticleFragment(),
                    R.id.frame,
                    "ContentFragment",
                    finalBundle
            );
        });

        holder.url_text.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, version, url_text;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_small_title);
            version = itemView.findViewById(R.id.item_small_version);
            url_text = itemView.findViewById(R.id.item_small_url_text);
            image = itemView.findViewById(R.id.item_small_image);
        }
    }
}