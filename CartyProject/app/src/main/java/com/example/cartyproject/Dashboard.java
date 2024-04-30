package com.example.cartyproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Spinner storeSpinner, townSpinner, countySpinner;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String username = getIntent().getStringExtra("username");
        if (username != null) {
            Log.i("Dashboard", "Received username: " + username);
        } else {
            Log.e("Dashboard", "Username is null.");
        }



        storeSpinner = findViewById(R.id.storeSpinner);
        townSpinner = findViewById(R.id.townSpinner); // Ensure you have this Spinner in your layout
        countySpinner = findViewById(R.id.countySpinner);
        nextButton = findViewById(R.id.nextButton);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ((NavigationView) findViewById(R.id.navview)).setNavigationItemSelectedListener(this);
        loadStores();
        setupCountySpinner();
        setupNextButton();
    }

    private void setupCountySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.counties, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countySpinner.setAdapter(adapter);
    }

    private void loadStores() {
        String url = "http://192.168.110.210/LoginRegister/fetch_stores.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<String> stores = new ArrayList<>();
                    List<String> towns = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject store = response.getJSONObject(i);
                            stores.add(store.getString("client_name"));
                            towns.add(store.getString("client_address")); // Assuming client_address is the town
                        }
                        setSpinnerData(storeSpinner, stores);
                        setSpinnerData(townSpinner, towns);
                    } catch (JSONException e) {
                        Log.e("JSONError", "Error parsing JSON data", e);
                        Toast.makeText(getApplicationContext(), "Error parsing data", Toast.LENGTH_LONG).show();
                    }
                }, error -> {
            Log.e("VolleyError", "Error fetching stores: " + error.toString());
            Toast.makeText(getApplicationContext(), "Error fetching stores. Check connection and server.", Toast.LENGTH_LONG).show();
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000, // Timeout in milliseconds.
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); // Backoff multiplier

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void setSpinnerData(Spinner spinner, List<String> data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, data);
        spinner.setAdapter(adapter);
    }

    private void setupNextButton() {
        nextButton.setOnClickListener(v -> {
            String store = storeSpinner.getSelectedItem().toString();
            String town = townSpinner.getSelectedItem().toString(); // You can use this data as needed
            if (!store.isEmpty() && !store.equals("Select Store")) {
                Intent intent = new Intent(Dashboard.this, ProductActivity.class);
                intent.putExtra("storeName", store);
                startActivity(intent);
            } else {
                Toast.makeText(Dashboard.this, "Please select a store", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.home) {
            intent = new Intent(this, Home.class);
        } else if (id == R.id.profile) {
            intent = new Intent(this, Profile.class);
            String username = getIntent().getStringExtra("username");
            intent.putExtra("username", username);
        } else if (id == R.id.feedBack) {
            intent = new Intent(this, Feedback.class);
        } else if (id == R.id.pricesurvey) {
            intent = new Intent(this, PriceSurvey.class);
        }

        if (intent != null) {
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        return false;
    }
}
