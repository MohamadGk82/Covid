package com.example.covid_19.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.example.covid_19.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoronaTestFragment extends Fragment {

    @BindView(R.id.webView_corona_test_fragment_home)
    WebView webView_corona_test;
    @BindView(R.id.imageButton_back_layout_toolbar_fragment)
    ImageButton imageButton_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_corona_test, container, false);
        ButterKnife.bind(this, view);
        setWebView_corona_test();
        setImageButton_back();
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView_corona_test() {
        WebSettings webSettings = webView_corona_test.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView_corona_test.loadUrl("https://test.corona.ir/");
    }

    private void setImageButton_back() {
        imageButton_back.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.homeFragment);
        });
    }

}