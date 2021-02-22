package com.example.covid_19.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.covid_19.R;
import com.example.covid_19.binder.adapter.FavoriteAdapter;
import com.example.covid_19.data.Favorite;
import com.example.covid_19.ui.util.AppDatabase;
import com.example.covid_19.ui.util.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarkFragment extends BaseFragment {

    @BindView(R.id.recyclerview_bookmark)
    RecyclerView recyclerView_bookmark;
    @BindView(R.id.imageButton_back_layout_toolbar_fragment)
    ImageButton imageButton_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        ButterKnife.bind(this, view);

        AppDatabase database = Room.databaseBuilder(getContext(), AppDatabase.class, "news")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        List<Favorite> favorites = database.cardDAO().getFavorites();

        if (favorites.isEmpty()) {
            recyclerView_bookmark.setVisibility(View.GONE);
        } else {
            recyclerView_bookmark.setLayoutManager(new LinearLayoutManager(getContext()));
            FavoriteAdapter adapter = new FavoriteAdapter(favorites, getContext());
            recyclerView_bookmark.setAdapter(adapter);
        }

        imageButton_back.setOnClickListener(v -> {
            get_fragment_name(view);
        });

        return view;
    }

    private void get_fragment_name(View view){
        preferences = getContext().getSharedPreferences("fragment_name", Context.MODE_PRIVATE);
        String fragment_name = preferences.getString("fragment_name","");
        switch (fragment_name) {
            case "home":
                Navigation.findNavController(view).navigate(R.id.homeFragment);
                break;
            case "news":
                Navigation.findNavController(view).navigate(R.id.newsFragment);
                break;
            case "statistics":
                Navigation.findNavController(view).navigate(R.id.statisticsFragment);
                break;
        }
    }

}