package com.example.covid_19.binder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.covid_19.data.Favorite;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM favorite")
    List<Favorite> getFavorites();

    @Insert
    void insertFavorite(Favorite favorite);

    @Query("SELECT EXISTS (SELECT 1 FROM favorite WHERE news_id=:id)")
    public int isFavorite(int id);

    @Delete
    void deleteFavoriteById(Favorite favorite);


}
