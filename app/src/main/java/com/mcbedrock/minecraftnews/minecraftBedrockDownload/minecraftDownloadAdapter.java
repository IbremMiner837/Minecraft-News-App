package com.mcbedrock.minecraftnews.minecraftBedrockDownload;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.mcbedrock.minecraftnews.DownloadFiles;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.RewardAd;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.realeseChangelogs;
import com.mcbedrock.minecraftnews.minecraftBedrockDownload.minecraftDownloadModel;

public class minecraftDownloadAdapter extends FirestoreRecyclerAdapter<minecraftDownloadModel, minecraftDownloadAdapter.minecraftDownloadHolder> {

    //КАРТОЧКА + ДЕЙСТВИЯ ПРИ КЛИКЕ

    public minecraftDownloadAdapter(@NonNull FirestoreRecyclerOptions<minecraftDownloadModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull minecraftDownloadHolder holder, int position, @NonNull minecraftDownloadModel minecraftDownloadModel) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        holder.name_text.setText(minecraftDownloadModel.getName_title());
        holder.version_text.setText(minecraftDownloadModel.getVersion());
        holder.file_size.setText(minecraftDownloadModel.getFile_size() + "MB");
        Glide.with(holder.img.getContext())
                .load(minecraftDownloadModel.getImg_link())
                .apply(requestOptions)
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new descriptionFragment(model.getLink())).addToBackStack(null).commit();

                //Toast toast = Toast.makeText(activity, "Hello Android!", Toast.LENGTH_LONG);
                //toast.show();

                final DownloadFiles downloadFiles = new DownloadFiles(activity);
                //final RewardAd rewardAd = new RewardAd(activity);

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                AlertDialog alertDialog = builder.create();
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.dialog_minecraft_download, null);

                TextView dialog_changelog_name, dialog_changelog_version, file_size, dialog_modification;
                String file_output_name, file_output_version;
                String dialog_download_link;

                Button dialog_download_btn, dialog_cancel_btn;

                dialog_changelog_name = dialogView.findViewById(R.id.name_text);
                dialog_changelog_version = dialogView.findViewById(R.id.version_text);
                file_size = dialogView.findViewById(R.id.file_size);
                dialog_modification = dialogView.findViewById(R.id.modification);

                //btn open changelog and download
                dialog_download_btn = dialogView.findViewById(R.id.dialog_download_btn);
                dialog_cancel_btn = dialogView.findViewById(R.id.dialog_cancel_btn);

                dialog_changelog_name.setText(minecraftDownloadModel.name_title);
                dialog_changelog_version.setText(minecraftDownloadModel.version);
                file_size.setText(minecraftDownloadModel.getFile_size() + "MB");
                dialog_modification.setText(minecraftDownloadModel.modification);
                dialog_download_link = minecraftDownloadModel.download_link;

                file_output_name = minecraftDownloadModel.name_title;
                file_output_version = minecraftDownloadModel.version;

                dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                        alertDialog.dismiss();
                    }
                });

                dialog_download_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //rewardAd.showRewardAd();
                        downloadFiles.downloadFiles(dialog_download_link, file_output_name + " " + file_output_version + ".apk");
                        alertDialog.cancel();
                        alertDialog.dismiss();

                        Toast toast = Toast.makeText(activity, R.string.downloading_file, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                alertDialog.setView(dialogView);
                alertDialog.setCancelable(true);
                alertDialog.show();
            }
        });
    }

    @NonNull
    @Override
    public minecraftDownloadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.minecraft_download_card, parent, false);
        return  new minecraftDownloadHolder(view);
    }

    public class minecraftDownloadHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name_text, version_text, file_size, modification;
        //RelativeLayout cardView;
        public minecraftDownloadHolder(@NonNull View itemView) {
            super(itemView);
            //cardView = itemView.findViewById(R.id.cardView);
            img = itemView.findViewById(R.id.img);
            name_text = itemView.findViewById(R.id.name_text);
            version_text = itemView.findViewById(R.id.version_text);
            file_size = itemView.findViewById(R.id.file_size);
            modification = itemView.findViewById(R.id.modification);
        }
    }
}
