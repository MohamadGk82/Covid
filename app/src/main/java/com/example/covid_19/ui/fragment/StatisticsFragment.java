package com.example.covid_19.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.covid_19.data.GlobalModel;

import com.example.covid_19.R;
import com.example.covid_19.data.DataModel;
import com.example.covid_19.data.Video;
import com.example.covid_19.ui.util.BaseFragment;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatisticsFragment extends BaseFragment {

    @BindView(R.id.textview_myCountry_fragment_statistics)
    TextView textView_myCountry_statistics;
    @BindView(R.id.textview_global_fragment_statistics)
    TextView textView_global_statistics;
    @BindView(R.id.textview_active_fragment_statistics)
    TextView textView_active_statistics;
    @BindView(R.id.textview_affected_fragment_statistics)
    TextView textView_affected_statistics;
    @BindView(R.id.textview_death_fragment_statistics)
    TextView textView_death_statistics;
    @BindView(R.id.textview_serious_fragment_statistics)
    TextView textView_serious_statistics;
    @BindView(R.id.textview_recovered_fragment_statistics)
    TextView textView_recovered_statistics;
    @BindView(R.id.videoView_fragment_statistics)
    PlayerView exoPlayerView;
    @BindView(R.id.imageButton_bookmark_layout_toolbar_main)
    ImageButton imageButton_bookmark;
    @BindView(R.id.navigationview_fragment_statistics)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout_fragment_statistics)
    DrawerLayout drawerLayout;
    @BindView(R.id.imageButton_play_video)
    ImageButton imageButton_play_video;
    @BindView(R.id.progressbar_play_video)
    ProgressBar progressBar_play_video;
    @BindView(R.id.imageButton_info_layout_toolbar_main)
    ImageButton imageButton_about_us;

    SimpleExoPlayer exoPlayer;

    String active, affected, death, serious, recovered, region_name, videoURL;
    int region_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        get_by_region_Statistics();
        onClick();
        return view;
    }

    private void get_region_id() {
        preferences = getContext().getSharedPreferences("region", Context.MODE_PRIVATE);
        region_id = preferences.getInt("region_id", -1);
    }

    private void set_region() {

//        set region_name
        switch (region_id) {
            case 0:
                region_name = "iran";
                break;
            case 1:
                region_name = "usa";
                break;
            case 2:
                region_name = "uk";
                break;
            case 3:
                region_name = "china";
                break;
            case 4:
                region_name = "russia";
                break;
        }

        textView_global_statistics.setOnClickListener(v -> {
            get_global_statistics();
            textView_global_statistics.setBackgroundResource(R.drawable.shape_text_statistics);
            textView_myCountry_statistics.setBackgroundResource(R.drawable.shape_statistics);
        });

        textView_myCountry_statistics.setOnClickListener(v -> {
            preferences.getInt("region_id", -1);
            get_by_region_Statistics();
            textView_myCountry_statistics.setBackgroundResource(R.drawable.shape_text_statistics);
            textView_global_statistics.setBackgroundResource(R.drawable.shape_statistics);
        });

    }

    private void get_by_region_Statistics() {

        get_region_id();
        set_region();
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("در حال دریافت آمار");
        dialog.setCancelable(false);
        dialog.show();
        Call<DataModel> call = apiService.get_statistics_by_region(region_name);
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(@NotNull Call<DataModel> call, @NotNull Response<DataModel> response) {
                dialog.dismiss();
                set_decimal_number_by_region_statistics(response.body().getData().getSummary().getRecovered(),
                        response.body().getData().getSummary().getDeaths(),
                        response.body().getData().getSummary().getTotal_cases(),
                        response.body().getData().getSummary().getCritical(),
                        response.body().getData().getSummary().getActive_cases());

                set_TextViews_statistics(recovered, death, affected, serious, active);
            }

            @Override
            public void onFailure(@NotNull Call<DataModel> call, @NotNull Throwable t) {
                dialog.dismiss();
                Log.e("error : ", t.getMessage());
            }
        });

    }

    private void get_global_statistics() {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("در حال دریافت آمار");
        dialog.setCancelable(false);
        dialog.show();
        Call<GlobalModel> call = apiService.get_statistics_global();
        call.enqueue(new Callback<GlobalModel>() {
            @Override
            public void onResponse(@NotNull Call<GlobalModel> call, @NotNull Response<GlobalModel> response) {
                dialog.dismiss();

                set_TextViews_statistics(response.body().getWorld_total().getTotal_recovered(),
                        response.body().getWorld_total().getTotal_deaths()
                        , response.body().getWorld_total().getTotal_cases(),
                        response.body().getWorld_total().getSerious_critical(),
                        response.body().getWorld_total().getActive_cases());
            }

            @Override
            public void onFailure(@NotNull Call<GlobalModel> call, @NotNull Throwable t) {
                dialog.dismiss();
                Log.d("ERR", t.getMessage());
            }
        });
    }

    private void getVideo() {
        progressBar_play_video.setVisibility(View.VISIBLE);

        Call<Video> call = apiService.get_video_url();
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(@NotNull Call<Video> call, @NotNull Response<Video> response) {
                videoURL = response.body().getVideo_url();
                setVideo(videoURL);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar_play_video.setVisibility(View.GONE);
                    }
                }, 18000);
            }

            @Override
            public void onFailure(@NotNull Call<Video> call, @NotNull Throwable t) {
                Log.e("ERR ", t.getMessage());
            }
        });

    }

    private void setVideo(String videoURL) {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory());

        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        Uri videouri = Uri.parse(videoURL);


        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");


        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);

        exoPlayerView.setPlayer(exoPlayer);

        exoPlayer.prepare(mediaSource);

        exoPlayer.setPlayWhenReady(true);

    }

    private void set_decimal_number_by_region_statistics(String recover, String deaths, String affect, String critical, String activated) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        recovered = decimalFormat.format(Integer.valueOf(recover));
        death = decimalFormat.format(Integer.valueOf(deaths));
        affected = decimalFormat.format(Integer.valueOf(affect));
        serious = decimalFormat.format(Integer.valueOf(critical));
        active = decimalFormat.format(Integer.valueOf(activated));
    }

    private void set_TextViews_statistics(String recovered, String death, String affected, String serious,
                                          String active) {

        textView_recovered_statistics.setText(recovered);
        textView_death_statistics.setText(death);
        textView_affected_statistics.setText(affected);
        textView_serious_statistics.setText(serious);
        textView_active_statistics.setText(active);

    }

    private void onClick() {
        imageButton_bookmark.setOnClickListener(v -> {
            openBookmarkFragment(v, "statistics");
        });

        imageButton_play_video.setOnClickListener(v -> {
            getVideo();
            imageButton_play_video.setVisibility(View.GONE);
        });

        imageButton_about_us.setOnClickListener(v -> {
            openAboutUsFragment(v, "statistics");
        });
    }
}