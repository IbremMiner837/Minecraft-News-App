package com.mcbedrock.minecraftnews.betaChangelog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.mcbedrock.minecraftnews.R;

public class betaAdapter extends FirebaseRecyclerAdapter<BetaChangelogModel, betaAdapter.myviewholder> {

    //КАРТОЧКА + ДЕЙСТВИЯ ПРИ КЛИКЕ

    ImageView imageView;
    Dialog dialog;

    public betaAdapter(@NonNull FirebaseRecyclerOptions<BetaChangelogModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull BetaChangelogModel betaChangelogModel) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        holder.name_text.setText(betaChangelogModel.getName());
        holder.version_text.setText(betaChangelogModel.getVersion());
        holder.link_text.setText(betaChangelogModel.getLink());
        Glide.with(holder.img.getContext())
                .load(betaChangelogModel.getImg())
                .apply(requestOptions)
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new descriptionFragment(snapshotChangelogModel.getLink())).addToBackStack(null).commit();

                //Toast toast = Toast.makeText(activity, "Hello Android!", Toast.LENGTH_LONG);
                //toast.show();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.dialog_changelog_info, null);
                ImageView dialog_changelog_img;
                TextView dialog_changelog_name;
                TextView dialog_changelog_version;
                String dialog_changelog_link;
                String dialog_download_link;

                //btn open changelog and download
                Button dialog_changelog_btn;
                Button dialog_download_btn;

                dialog_changelog_img = dialogView.findViewById(R.id.dialog_img);
                dialog_changelog_name = dialogView.findViewById(R.id.dialog_changelog_name);
                dialog_changelog_version = dialogView.findViewById(R.id.dialog_changelog_version);

                //btn open changelog and download
                dialog_changelog_btn = dialogView.findViewById(R.id.dialog_changelog_btn);
                //dialog_download_btn = dialogView.findViewById(R.id.dialog_download_btn);

                Glide.with(dialogView.getContext()).load(betaChangelogModel.getImg()).into(dialog_changelog_img);
                dialog_changelog_name.setText(betaChangelogModel.name);
                dialog_changelog_version.setText(betaChangelogModel.version);
                dialog_changelog_link = betaChangelogModel.link;
                dialog_download_link = betaChangelogModel.download_link;

                /*dialog_download_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(dialog_download_link));
                        String title = URLUtil.guessFileName(dialog_download_link, null, null);
                        request.setTitle(title);
                        request.setDescription("Download...");
                        String cookie = CookieManager.getInstance().getCookie(dialog_download_link);
                        request.addRequestHeader("cookie", cookie);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                        downloadManager.enqueue(request);
                    }
                });*/

                dialog_changelog_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(dialog_changelog_link); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        activity.startActivity(intent);
                    }
                });

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesing_small, parent, false);
        return  new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name_text, version_text, link_text;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name_text = itemView.findViewById(R.id.name_text);
            version_text = itemView.findViewById(R.id.version_text);
            link_text = itemView.findViewById(R.id.link_text);

        }
    }

}
