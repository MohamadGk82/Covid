package com.example.covid_19.ui.util;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.covid_19.binder.NewsDao;
import com.example.covid_19.data.Favorite;

@Database(entities = {Favorite.class}, version = 1 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NewsDao cardDAO();

}
