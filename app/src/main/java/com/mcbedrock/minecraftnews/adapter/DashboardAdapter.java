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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.ui.fragment.ContentFragment;
import com.mcbedrock.minecraftnews.utils.FragmentUtils;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private final Context context;
    private int[] title;
    private int[] image;

    public DashboardAdapter(Context context, int[] title, int[] image) {
        this.context = context;
        this.title = title;
        this.image = image;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_fragment_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(16));

        holder.title.setText(context.getString(title[position]));
        Glide.with(holder.image.getContext())
                .load(image[position])
                .apply(requestOptions)
                .into(holder.image);

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("contentType", position);
            FragmentUtils.changeFragmentWithBackStack(
                    (FragmentActivity) context,
                    new ContentFragment(),
                    R.id.frame,
                    "back",
                    bundle);
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.TitleView);
            image = itemView.findViewById(R.id.ImageView);
        }
    }
}