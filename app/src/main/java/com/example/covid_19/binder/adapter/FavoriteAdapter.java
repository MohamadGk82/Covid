package com.example.covid_19.binder.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid_19.R;
import com.example.covid_19.data.Favorite;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    List<Favorite> favoriteList;
    Context context;

    public FavoriteAdapter(List<Favorite> favoriteList, Context context) {
        this.favoriteList = favoriteList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteAdapter.ViewHolder holder, int position) {
        Favorite favorite = favoriteList.get(position);
        Glide.with(context).load(favorite.getImage()).into(holder.imageView);
        holder.textViewdate.setText(favorite.getDate());
        holder.textViewTitle.setText(favorite.getTitle());

        holder.itemView.setOnClickListener(v -> {
            SharedPreferences.Editor editor = context.getSharedPreferences("news", MODE_PRIVATE).edit();
            editor.putString("news_id", Integer.toString(favorite.getNews_id()));
            editor.putString("title", favorite.getTitle());
            editor.putString("image", favorite.getImage());
            editor.putString("summary", favorite.getSummary());
            editor.putString("description", favorite.getDescription());
            editor.putString("announcer", favorite.getAnnouncer());
            editor.putString("image_announcer", favorite.getImage_announcer());
            editor.putString("category", favorite.getCategory());
            editor.putString("date", favorite.getDate());
            editor.putBoolean("fragment",true);
            editor.apply();
            Navigation.findNavController(v).navigate(R.id.readNewsFragment);
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageview_favorite)
        ImageView imageView;
        @BindView(R.id.txtview_title_favorite)
        TextView textViewTitle;
        @BindView(R.id.txtview_date_favorite)
        TextView textViewdate;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
