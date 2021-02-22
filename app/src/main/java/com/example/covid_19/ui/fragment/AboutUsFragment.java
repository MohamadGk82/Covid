package com.example.covid_19.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.covid_19.R;
import com.example.covid_19.ui.util.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsFragment extends BaseFragment {

    @BindView(R.id.imageButton_back_layout_toolbar_fragment)
    ImageButton imageButton_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this,view);
        onClick();
        return view;
    }

    private void onClick(){
        imageButton_back.setOnClickListener(this::get_fragment_name);
    }

    private void get_fragment_name(View view){
        preferences = getContext().getSharedPreferences("fragment_name", Context.MODE_PRIVATE);
        String fragment_name = preferences.getString("fragment_name_about_us","");
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