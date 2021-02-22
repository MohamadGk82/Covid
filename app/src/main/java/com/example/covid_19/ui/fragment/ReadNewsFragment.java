package com.example.covid_19.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.covid_19.R;
import com.example.covid_19.data.Favorite;
import com.example.covid_19.ui.util.AppDatabase;
import com.example.covid_19.ui.util.BaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadNewsFragment extends BaseFragment {

    @BindView(R.id.imageButton_back_layout_toolbar_read_news)
    ImageButton imageButton_back;
    @BindView(R.id.imageview_news_fragment_read_news)
    RoundedImageView imageView_news;
    @BindView(R.id.textview_category_fragment_read_news)
    TextView textView_category;
    @BindView(R.id.textview_title_fragment_read_news)
    TextView textView_title;
    @BindView(R.id.textview_announcer_fragment_read_news)
    TextView textView_announcer;
    @BindView(R.id.textview_date_fragment_read_news)
    TextView textView_date;
    @BindView(R.id.imageview_announcer_fragment_read_news)
    RoundedImageView imageView_announcer;
    @BindView(R.id.textview_description_fragment_read_news)
    TextView textView_description;
    @BindView(R.id.textview_save_toolbar_read_news)
    TextView textView_save;

    SharedPreferences sharedPreferences;
    public static AppDatabase database;
    String news_id, title, image, summary, description, announcer, image_announcer, category, date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read_news, container, false);
        ButterKnife.bind(this, view);

        database = Room.databaseBuilder(getContext(), AppDatabase.class, "news")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        get_data();

        if (database.cardDAO().isFavorite(Integer.parseInt(news_id)) == 1)
            textView_save.setTextColor(Color.RED);
        else
            textView_save.setTextColor(Color.BLACK);


        return view;
    }

    private void get_data() {
        sharedPreferences = getContext().getSharedPreferences("news", Context.MODE_PRIVATE);
        news_id = sharedPreferences.getString("news_id", "");
        title = sharedPreferences.getString("title", "");
        image = sharedPreferences.getString("image", "");
        summary = sharedPreferences.getString("summary", "");
        description = sharedPreferences.getString("description", "");
        announcer = sharedPreferences.getString("announcer", "");
        image_announcer = sharedPreferences.getString("image_announcer", "");
        date = sharedPreferences.getString("date", "");
        category = sharedPreferences.getString("category", "");

        if (category.equals("trend")) {
            category = "پر بازدید ها";
        } else {
            category = "جدید ترین ها";
        }

        set_view();
        onClick();
    }

    private void set_view() {
        Glide.with(getContext()).load(image).into(imageView_news);
        Glide.with(getContext()).load(image_announcer).into(imageView_announcer);
        textView_announcer.setText(announcer);
        textView_category.setText(category);
        textView_date.setText(date);
        textView_description.setText(description);
        textView_title.setText(title);
    }

    private void onClick() {

        imageButton_back.setOnClickListener(v -> {
            sharedPreferences  = getContext().getSharedPreferences("news",Context.MODE_PRIVATE);
            boolean homeFragment = sharedPreferences.getBoolean("fragment",false);
            if (homeFragment){
                Navigation.findNavController(v).navigate(R.id.bookmarkFragment);
            }else {
                Navigation.findNavController(v).navigate(R.id.newsFragment);
            }
        });

        textView_save.setOnClickListener(v -> {
                Favorite favorite = new Favorite();

                favorite.setNews_id(Integer.parseInt(news_id));
                favorite.setCategory(category);
                favorite.setImage(image);
                favorite.setTitle(title);
                favorite.setDate(date);
                favorite.setSummary(summary);
                favorite.setDescription(description);
                favorite.setImage_announcer(image_announcer);
                favorite.setAnnouncer(announcer);

                int idd = Integer.parseInt(news_id);

                if (ReadNewsFragment.database.cardDAO().isFavorite(idd) != 1) {
                    textView_save.setTextColor(Color.RED);
                    ReadNewsFragment.database.cardDAO().insertFavorite(favorite);
                    Toast.makeText(getContext(), "با موفقیت به علاقه مندی ها اضافه شد!", Toast.LENGTH_SHORT).show();
                } else {
                    textView_save.setTextColor(Color.BLACK);
                    ReadNewsFragment.database.cardDAO().deleteFavoriteById(favorite);
                    Toast.makeText(getContext(), "این خبر از لیست علاقه مندی ها حذف شد!", Toast.LENGTH_SHORT).show();
                }
        });

    }

}