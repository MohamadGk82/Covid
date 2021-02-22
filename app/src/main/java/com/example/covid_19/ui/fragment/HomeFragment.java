package com.example.covid_19.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.covid_19.R;
import com.example.covid_19.ui.util.BaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.spinner_layout_select_country)
    AppCompatSpinner spinner_countries;
    @BindView(R.id.imageView_flag_layout_select_country)
    ImageView imageView_flag_countries;
    @BindView(R.id.roundedImageview_onlineTest_fragment_home)
    RoundedImageView imageView_banner_test;
    @BindView(R.id.cardView_call_fragment_home)
    CardView cardView_call;
    @BindView(R.id.cardView_sms_fragment_home)
    CardView cardView_sms;
    @BindView(R.id.imageButton_bookmark_layout_toolbar_main)
    ImageButton imageButton_bookmark;
    @BindView(R.id.imageButton_info_layout_toolbar_main)
    ImageButton imageButton_about_us;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setImageView_banner_test();
        setSpinner_countries();
        onClick();
        return view;
    }

    private void setSpinner_countries() {
        List<String> categories = new ArrayList<String>();
        categories.add("ایران");
        categories.add("آمریکا");
        categories.add("انگلستان");
        categories.add("چین");
        categories.add("روسیه");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_countries.setAdapter(dataAdapter);

        preferences = getContext().getSharedPreferences("region", Context.MODE_PRIVATE);
        int selection = preferences.getInt("region_id", -1);
        spinner_countries.setSelection(selection);

        spinner_countries.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setImage_countries(parent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setImage_countries(AdapterView<?> parent) {
        preferences = getContext().getSharedPreferences("region", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        switch (Long.toString(parent.getSelectedItemId())) {

            case "0":
                imageView_flag_countries.setBackgroundResource(R.drawable.ic_iran);
                save_selected_region(editor, 0);
                break;

            case "1":
                imageView_flag_countries.setBackgroundResource(R.drawable.ic_united_states);
                save_selected_region(editor, 1);
                break;

            case "2":
                imageView_flag_countries.setBackgroundResource(R.drawable.ic_united_kingdom);
                save_selected_region(editor, 2);
                break;

            case "3":
                imageView_flag_countries.setBackgroundResource(R.drawable.ic_china);
                save_selected_region(editor, 3);
                break;

            case "4":
                imageView_flag_countries.setBackgroundResource(R.drawable.ic_russia);
                save_selected_region(editor, 4);
                break;
        }
    }

    private void setImageView_banner_test() {

        imageView_banner_test.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.coronaTestFragment);
        });

    }

    private void save_selected_region(SharedPreferences.Editor editor, int region_id) {
        editor.putInt("region_id", region_id);
        editor.apply();
    }

    private void onClick() {
        cardView_call.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:4030"));
            getContext().startActivity(callIntent);
        });

        cardView_sms.setOnClickListener(v -> {
            String smsNumber = "30006115";
            Uri uri = Uri.parse("smsto:" + smsNumber);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//            intent.putExtra("sms_body", smsText);
            startActivity(intent);
        });

        imageButton_bookmark.setOnClickListener(v -> {openBookmarkFragment(v,"home");});
        imageButton_about_us.setOnClickListener(v -> {openAboutUsFragment(v,"home");});

    }
}