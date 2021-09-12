package com.mcbedrock.minecraftnews.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.mcbedrock.minecraftnews.API.ChromeCustomTabAPI;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.full_screen_changelog_view;
import com.mcbedrock.minecraftnews.models.changelogsModel;

public class changelogsAdapter extends FirestoreRecyclerAdapter<changelogsModel, changelogsAdapter.holder> {

    public changelogsAdapter(FirestoreRecyclerOptions<changelogsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(holder holder, int position, changelogsModel model) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        holder.name_text.setText(model.getName_title());
        holder.version_text.setText(model.getVersion());
        //holder.link_text.setText(model.getChangelog_link());
        Glide.with(holder.img.getContext())
                .load(model.getImg_link())
                .apply(requestOptions)
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new fullScreenChangelogInfo(model.getName_title(), model.getVersion(), model.getImg_link(), model.getDescription(), model.getChangelog_link())).addToBackStack(null).commit();
                /*Intent intent = new Intent(activity.getApplicationContext(), full_screen_changelog_view.class);
                intent.putExtra("img_link", model.getImg_link());
                intent.putExtra("name_title", model.getName_title());
                intent.putExtra("version", model.getVersion());
                intent.putExtra("description", model.getDescription());
                intent.putExtra("changelog_link", model.getChangelog_link());
                activity.startActivity(intent);*/

                final BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.changelog_view_bottom_sheet);

                ImageView changelog_bottom_sheet_view_image = (ImageView) dialog.findViewById(R.id.changelog_bottom_sheet_view_image);
                TextView changelog_bottom_sheet_view_title = (TextView) dialog.findViewById(R.id.changelog_bottom_sheet_view_title);
                TextView changelog_bottom_sheet_view_version = (TextView) dialog.findViewById(R.id.changelog_bottom_sheet_view_version);
                TextView changelog_bottom_sheet_view_description = (TextView) dialog.findViewById(R.id.changelog_bottom_sheet_view_description);
                MaterialButton changelog_bottom_sheet_view_button_all_changelog = (MaterialButton) dialog.findViewById(R.id.changelog_bottom_sheet_view_button_all_changelog);

                changelog_bottom_sheet_view_button_all_changelog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ChromeCustomTabAPI chromeCustomTabAPI = new ChromeCustomTabAPI(activity);
                        chromeCustomTabAPI.OpenCustomTab(activity, model.getChangelog_link(), R.color.primaryColor);

                        dialog.dismiss();
                        dialog.hide();
                    }
                });

                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

                Glide.with(changelog_bottom_sheet_view_image)
                        .load(model.getImg_link())
                        .apply(requestOptions)
                        .into(changelog_bottom_sheet_view_image);
                changelog_bottom_sheet_view_title.setText(model.getName_title());
                changelog_bottom_sheet_view_version.setText(model.getVersion());
                changelog_bottom_sheet_view_description.setText(model.getDescription().replaceAll("\\\\n", "\n"));


                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.changelog_view_item_card, parent, false);
        return new changelogsAdapter.holder(view);
    }

    public class holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name_text, version_text, link_text;

        public holder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name_text = itemView.findViewById(R.id.name_text);
            version_text = itemView.findViewById(R.id.version_text);
            //link_text = itemView.findViewById(R.id.link_text);
        }
    }
}