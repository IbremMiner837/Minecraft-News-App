package com.mcbedrock.minecraftnews.jsonParser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.mcbedrock.minecraftnews.API.ChromeCustomTabAPI;
import com.mcbedrock.minecraftnews.AboutActivity;
import com.mcbedrock.minecraftnews.R;

import java.util.List;

public class jsonAdapter extends RecyclerView.Adapter<jsonAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<jsonData> jsonDataList;
    Context mContext;

    public jsonAdapter(Context context, List<jsonData> jsonDataList) {
        this.inflater = LayoutInflater.from(context);
        this.jsonDataList = jsonDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.minecraftnet_card, parent, false);
        return new jsonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        holder.title.setText(jsonDataList.get(position).getTitle());
        holder.sub_header.setText(jsonDataList.get(position).getSubHeader());
        holder.article_url.setText("https://www.minecraft.net" + jsonDataList.get(position).getArticleURL());
        Glide.with(holder.img.getContext())
                .load("https://www.minecraft.net" + jsonDataList.get(position).getImageURL())
                .apply(requestOptions)
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                final BottomSheetDialog dialog = new BottomSheetDialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.minecraft_net_view_bottom_sheet);

                ImageView minecraft_net_bottom_sheet_view_image = (ImageView) dialog.findViewById(R.id.minecraft_net_bottom_sheet_view_image);
                TextView minecraft_net_bottom_sheet_view_title = (TextView) dialog.findViewById(R.id.minecraft_net_bottom_sheet_view_title);
                TextView minecraft_net_bottom_sheet_view_description = (TextView) dialog.findViewById(R.id.minecraft_net_bottom_sheet_view_description);
                MaterialButton minecraft_net_bottom_sheet_view_button_all_changelog = (MaterialButton) dialog.findViewById(R.id.minecraft_net_bottom_sheet_view_button_all_changelog);

                minecraft_net_bottom_sheet_view_button_all_changelog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ChromeCustomTabAPI chromeCustomTabAPI = new ChromeCustomTabAPI(activity);
                        chromeCustomTabAPI.OpenCustomTab(activity, "https://www.minecraft.net" + jsonDataList.get(position).getArticleURL(), R.color.primaryColor);

                        dialog.dismiss();
                        dialog.hide();
                    }
                });

                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

                Glide.with(minecraft_net_bottom_sheet_view_image)
                        .load("https://www.minecraft.net" + jsonDataList.get(position).getImageURL())
                        .apply(requestOptions)
                        .into(minecraft_net_bottom_sheet_view_image);
                minecraft_net_bottom_sheet_view_title.setText(jsonDataList.get(position).getTitle());
                minecraft_net_bottom_sheet_view_description.setText(jsonDataList.get(position).getSubHeader().replaceAll("\\\\n", "\n"));


                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
    }

    @Override
    public int getItemCount() {
        return jsonDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, sub_header, article_url;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            sub_header = itemView.findViewById(R.id.sub_header);
            img = itemView.findViewById(R.id.img);
            article_url = itemView.findViewById(R.id.article_url_text);
        }
    }
}