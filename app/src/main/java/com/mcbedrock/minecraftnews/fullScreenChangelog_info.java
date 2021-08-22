package com.mcbedrock.minecraftnews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;

public class fullScreenChangelog_info extends AppCompatActivity {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrapper);

        /*Intent intent = getIntent();

        MaterialToolbar actionbar = (MaterialToolbar) findViewById(R.id.topAppBar);
        if (null != actionbar) {
            actionbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);

            actionbar.setTitle(R.string.changelog);
            actionbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //NavUtils.navigateUpFromSameTask(fullScreenChangelog_info.this);
                    onBackPressed();
                }
            });
            // Inflate a menu to be displayed in the toolbar
            actionbar.inflateMenu(R.menu.view_changelog_info_actionbar_menu);

            actionbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId() == R.id.share_btn) {
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Ссылка на чейнджлог" + ": " + intent.getStringExtra("changelog_link") + "\n\n" + "Скачай приложение MineNews App в Google Play" + ": " + "https://play.google.com/store/apps/details?id=com.mcbedrock.minecraftnews";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share"));
                    }
                    return true;
                }
            });
        }

        Button open_changelog_btn = (Button) findViewById(R.id.fs_changelog_btn);

        open_changelog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(intent.getStringExtra("changelog_link"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //mAdView = findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);

        ImageView imageView = findViewById(R.id.fs_changelog_image);
        TextView name_title = findViewById(R.id.fs_changelog_name);
        TextView version = findViewById(R.id.fs_changelog_version);
        String changelog_link;
        ExpandableTextView description = findViewById(R.id.expand_text_view);

        Glide.with(this)
                .load(intent.getStringExtra("img_link"))
                .into(imageView);
        name_title.setText(intent.getStringExtra("name_title"));
        version.setText(intent.getStringExtra("version"));
        //description.setText(ChangelogParser.parse(intent.getStringExtra("description").replaceAll("\\\\n", "\n")));
        description.setText(intent.getStringExtra("description").replaceAll("\\\\n", "\n"));
        changelog_link = intent.getStringExtra("changelog_link");
    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();  // optional depending on your needs
         */
    }
}