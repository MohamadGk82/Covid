package com.example.covid_19.ui.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.covid_19.AppClient;
import com.example.covid_19.R;
import com.example.covid_19.binder.service.ApiService;

public class BaseFragment extends Fragment {

    public static ApiService apiService = AppClient.getRetrofit("https://kidsshoping.ir/covid/").create(ApiService.class);
    public static SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        return view;
    }

    protected void openBookmarkFragment(View v, String fragment) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("fragment_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fragment_name", fragment);
        editor.apply();
        Navigation.findNavController(v).navigate(R.id.bookmarkFragment);
    }

    protected void openAboutUsFragment(View v, String fragment) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("fragment_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fragment_name_about_us", fragment);
        editor.apply();
        Navigation.findNavController(v).navigate(R.id.aboutUsFragment);
    }

}
