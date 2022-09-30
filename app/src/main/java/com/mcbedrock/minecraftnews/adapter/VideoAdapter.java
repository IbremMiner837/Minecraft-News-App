package com.mcbedrock.minecraftnews.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.ui.MainActivity;
import com.mcbedrock.minecraftnews.ui.fragment.VideosFragment;
import com.mcbedrock.minecraftnews.utils.MainViewModel;
import com.prof.youtubeparser.models.videos.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<Video> videos;
    private VideosFragment context;

    public VideoAdapter(List<Video> list, VideosFragment context) {
        this.videos = list;
        this.context = context;
    }

    public void clearData() {
        if (videos != null)
            videos.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_youtube_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final Video currentVideo = videos.get(position);

        //retrieve video link
        final String videoId = currentVideo.getVideoId();
        final String link = "https://www.youtube.com/watch?v=" + videoId;

        viewHolder.title.setText(currentVideo.getTitle());
        viewHolder.pubDate.setText(currentVideo.getDate().split("T")[0]);

        //load thumbnail
        viewHolder.playerView.initialize("AIzaSyC0-QuSGQA31mwQAlNwyLghxdMaNIaYjdc", new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(currentVideo.getVideoId());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        //show statistic of the selected video
        viewHolder.itemView.setOnClickListener(view -> {
            //context.getVideoStats(videoId);
            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) view.getContext(), "QuSGQA31mwQAlNwyLghxdMaNIaYjdc", currentVideo.getVideoId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    public List<Video> getList() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        YouTubeThumbnailView playerView;
        TextView title;
        TextView pubDate;

        ViewHolder(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.YouTubeVideoName);
            pubDate = itemView.findViewById(R.id.YouTubeVideoPublishDate);
            playerView = itemView.findViewById(R.id.YouTubePlayerView);
        }
    }
}
