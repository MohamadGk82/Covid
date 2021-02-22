package com.example.covid_19.binder.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid_19.R;
import com.example.covid_19.data.News;
import com.example.covid_19.ui.fragment.ReadNewsFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class New_News_Adapter extends RecyclerView.Adapter<New_News_Adapter.ViewHolder> {

    List<News> newsList;
    Context context;

    public New_News_Adapter(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public New_News_Adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_fragment_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull New_News_Adapter.ViewHolder holder, int position) {
        News news = newsList.get(position);
        Glide.with(context).load(news.getImage()).into(holder.imageView_news);
        Glide.with(context).load(news.getImage_announcer()).into(holder.imageView_announcer);
        holder.textView_announcer.setText(news.getAnnouncer());
        holder.textView_title.setText(news.getTitle());
        holder.textView_date.setText(news.getDate());

        holder.itemView.setOnClickListener(v -> {
            SharedPreferences.Editor editor = context.getSharedPreferences("news", MODE_PRIVATE).edit();
            editor.putString("news_id", news.getNews_id());
            editor.putString("title", news.getTitle());
            editor.putString("image", news.getImage());
            editor.putString("summary", news.getSummary());
            editor.putString("description", news.getDescription());
            editor.putString("announcer", news.getAnnouncer());
            editor.putString("image_announcer", news.getImage_announcer());
            editor.putString("category", news.getCategory());
            editor.putString("date", news.getDate());
            editor.putBoolean("fragment",false);
            editor.apply();
            Navigation.findNavController(v).navigate(R.id.readNewsFragment);
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageview_news_item_new)
        ImageView imageView_news;
        @BindView(R.id.textview_title_item_new)
        TextView textView_title;
        @BindView(R.id.imageview_announcer_item_new)
        RoundedImageView imageView_announcer;
        @BindView(R.id.textview_announcer_item_new)
        TextView textView_announcer;
        @BindView(R.id.textview_date_item_new)
        TextView textView_date;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
