package com.example.covid_19.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.covid_19.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottomBar_activity_main)
    SmoothBottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setBottomBar();
    }

    private void setBottomBar() {
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_activity_main).navigate(R.id.newsFragment);
                        break;

                    case 1:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_activity_main).navigate(R.id.statisticsFragment);
                        break;

                    case 2:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_activity_main).navigate(R.id.homeFragment);
                        break;
                }

                return false;
            }
        });
    }

}