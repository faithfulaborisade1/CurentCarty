package com.example.cartyproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
    private SharedPreferences sharedPreferences;

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
            }  else if (itemId == R.id.search) {
                startActivity(new Intent(Dashboard.this, Search.class));
            return true;
        } else if (itemId == R.id.nav_map) {
                openGoogleMaps();
                return true;
            }
            return false;
        });

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }


    private void openGoogleMaps() {
        // Create an intent to open Google Maps
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Handle the case where Google Maps is not installed
            Uri webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=restaurants");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
            startActivity(webIntent);
        }
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
        } else if (itemId == R.id.logout) {
            // Handle logout
            logout();
        }

        return true;
    }

    private void logout() {
        // Clear SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Navigate back to the Login activity
        Intent intent = new Intent(Dashboard.this, Login.class);
        startActivity(intent);
        finish();
    }
}
