package com.example.cartyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navview);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Do something when Home is clicked in bottom nav
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Intent to open StoresActivity which contains TabLayout
                startActivity(new Intent(Dashboard.this, StoresActivity.class));
                return true;
            } else if (itemId == R.id.nav_map) {
                // Do something when Map is clicked in bottom nav
                return true;
            }
            return false;
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);

        if (itemId == R.id.home) {
            startActivity(new Intent(this, Home.class));
        } else if (itemId == R.id.profile) {
            startActivity(new Intent(this, Profile.class));
        } else if (itemId == R.id.feedBack) {
            startActivity(new Intent(this, Feedback.class));
        } else if (itemId == R.id.pricesurvey) {
            startActivity(new Intent(this, PriceSurvey.class));
        }

        return true;
    }

}
