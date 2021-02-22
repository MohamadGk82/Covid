package com.example.covid_19.binder.service;

import com.example.covid_19.data.DataModel;
import com.example.covid_19.data.GlobalModel;
import com.example.covid_19.data.News;
import com.example.covid_19.data.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("by_region.php")
    @FormUrlEncoded
    Call<DataModel> get_statistics_by_region(@Field("region") String region);

    @GET("global.php")
    Call<GlobalModel> get_statistics_global();

    @GET("video_learn.json")
    Call<Video> get_video_url();

    @GET("new_news.php")
    Call<List<News>> get_new_news();

    @GET("trend_news.php")
    Call<List<News>> get_trend_news();

}
