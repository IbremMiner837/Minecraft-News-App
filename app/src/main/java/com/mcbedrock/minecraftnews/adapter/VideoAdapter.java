package com.mcbedrock.minecraftnews.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.ui.fragment.VideosFragment;
import com.mcbedrock.minecraftnews.utils.OtherAPI;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.prof.youtubeparser.VideoStats;
import com.prof.youtubeparser.models.stats.Statistics;
import com.prof.youtubeparser.models.videos.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private static final String TAG = VideoAdapter.class.getSimpleName();
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

        final String videoId = currentVideo.getVideoId();
        final String link = "https://www.youtube.com/watch?v=" + videoId;

        VideoStats videoStats = new VideoStats();
        String url = videoStats.generateStatsRequest(currentVideo.getVideoId(), "AIzaSyC0-QuSGQA31mwQAlNwyLghxdMaNIaYjdc");
        videoStats.execute(url);
        videoStats.onFinish(new VideoStats.OnTaskCompleted() {

            @Override
            public void onTaskCompleted(@NonNull Statistics stats) {
                //Here you can set the statistic to a Text View for instance

                //for example:
                String body = "Views: " + stats.getViewCount() + "\n" +
                        "Like: " + stats.getLikeCount() + "\n" +
                        "Dislike: " + stats.getDislikeCount() + "\n" +
                        "Number of comment: " + stats.getCommentCount() + "\n" +
                        "Number of favourite: " + stats.getFavoriteCount();
            }

            @Override
            public void onError(@NonNull Exception e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }
        });

        viewHolder.title.setText(currentVideo.getTitle());
        viewHolder.pubDate.setText(OtherAPI.formatDate(currentVideo.getDate()));

        context.getLifecycle().addObserver(viewHolder.playerView);
        viewHolder.playerView.addYouTubePlayerListener(new YouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(currentVideo.getVideoId(), 0);
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState playerState) {

            }

            @Override
            public void onPlaybackQualityChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackQuality playbackQuality) {

            }

            @Override
            public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {

            }

            @Override
            public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError playerError) {
                Log.e(TAG, "onError: " + playerError.toString());
            }

            @Override
            public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float v) {
                Log.d(TAG, "onVideoDuration: " + v);
            }

            @Override
            public void onVideoLoadedFraction(@NonNull YouTubePlayer youTubePlayer, float v) {
                //
            }

            @Override
            public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String s) {
                Log.d(TAG, "onVideoId: " + s);
            }

            @Override
            public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {
                Log.d(TAG, "onApiChange: " + youTubePlayer.toString());
            }
        });

        //open video on click
        /*viewHolder.playerView.setOnClickListener(v -> {
            Intent intent = YouTubeStandalonePlayer.createVideoIntent(context.getActivity(), "AIzaSyC0-QuSGQA31mwQAlNwyLghxdMaNIaYjdc", videoId);
            context.startActivity(intent);
        });*/

        //fetch statistics

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
        YouTubePlayerView playerView;
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
