package com.example.covid_19.ui.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.covid_19.R;
import com.example.covid_19.binder.adapter.New_News_Adapter;
import com.example.covid_19.binder.adapter.Trend_News_Adapter;
import com.example.covid_19.data.News;
import com.example.covid_19.ui.util.BaseFragment;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends BaseFragment {

    @BindView(R.id.recyclerview_new_fragment_news)
    RecyclerView recyclerView_new_news;
    @BindView(R.id.recyclerview_trend_fragment_news)
    RecyclerView recyclerView_trend_news;
    @BindView(R.id.imageButton_bookmark_layout_toolbar_main)
    ImageButton imageButton_bookmark;
    @BindView(R.id.progressbar_fragment_news)
    ProgressBar progressBar_fragment_news;
    @BindView(R.id.linearlayout_fragment_news)
    LinearLayout linearLayout_fragment_news;
    @BindView(R.id.textview_popular)
    TextView textview_popular;
    @BindView(R.id.imageButton_info_layout_toolbar_main)
    ImageButton imageButton_about_us;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment 
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        get_news();
        onClick();
        return view;
    }

    private void get_news() {
        Call<List<News>> call = apiService.get_new_news();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NotNull Call<List<News>> call, @NotNull Response<List<News>> response) {
                if (response.isSuccessful()) {
                    New_News_Adapter adapter = new New_News_Adapter(response.body(), getContext());
                    recyclerView_new_news.setAdapter(adapter);
                    recyclerView_new_news.hasFixedSize();
                    recyclerView_new_news.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                    get_trend_news();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<News>> call, @NotNull Throwable t) {
                Log.e("err", t.getMessage());
            }
        });
    }

    private void get_trend_news() {
        Call<List<News>> call = apiService.get_trend_news();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NotNull Call<List<News>> call, @NotNull Response<List<News>> response) {
                Trend_News_Adapter adapter = new Trend_News_Adapter(response.body(), getContext());
                recyclerView_trend_news.setAdapter(adapter);
                recyclerView_trend_news.hasFixedSize();
                recyclerView_trend_news.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                progressBar_fragment_news.setVisibility(View.GONE);
                linearLayout_fragment_news.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(@NotNull Call<List<News>> call, @NotNull Throwable t) {
                Log.e("err", t.getMessage());
            }
        });
    }

    private void onClick() {
        imageButton_bookmark.setOnClickListener(v -> {
            openBookmarkFragment(v, "news");
        });

        imageButton_about_us.setOnClickListener(v -> {openAboutUsFragment(v,"news");});

    }

}