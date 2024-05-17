package com.example.cartyproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    private Spinner countySpinner, townSpinner, storeSpinner;
    private Button nextButton;
    private RequestQueue requestQueue;
    private List<String> counties = new ArrayList<>();
    private List<String> towns = new ArrayList<>();
    private List<String> stores = new ArrayList<>();
    private int selectedStoreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        countySpinner = findViewById(R.id.countySpinner);
        townSpinner = findViewById(R.id.townSpinner);
        storeSpinner = findViewById(R.id.storeSpinner);
        nextButton = findViewById(R.id.nextButton);

        requestQueue = Volley.newRequestQueue(this);

        loadCounties();
        setupNextButton();
    }

    private void loadCounties() {
        counties.add("Select County");
        counties.add("County 1");
        counties.add("County 2");
        counties.add("County 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, counties);
        countySpinner.setAdapter(adapter);

        countySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    loadTowns(counties.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadTowns(String county) {
        String url = "http://192.168.8.30/LoginRegister/fetch_town.php?county=" + county;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        towns.clear();
                        towns.add("Select Town");
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject town = response.getJSONObject(i);
                            towns.add(town.getString("town_name"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_dropdown_item, towns);
                        townSpinner.setAdapter(adapter);

                        townSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    loadStores(towns.get(position));
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });

                    } catch (JSONException e) {
                        Log.e("JSONError", "Error parsing JSON data", e);
                        Toast.makeText(getApplicationContext(), "Error parsing data", Toast.LENGTH_LONG).show();
                    }
                }, error -> {
            Log.e("VolleyError", "Error fetching towns: " + error.toString());
            Toast.makeText(getApplicationContext(), "Error fetching towns. Check connection and server.", Toast.LENGTH_LONG).show();
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void loadStores(String town) {
        String url = "http://192.168.218.210/LoginRegister/fetch_stores.php?town=" + town;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        stores.clear();
                        stores.add("Select Store");
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject store = response.getJSONObject(i);
                            String storeDisplay = store.getString("client_name") + " - " + store.getString("client_address");
                            stores.add(storeDisplay);
                            if (store.getString("client_name").equals(storeSpinner.getSelectedItem().toString())) {
                                selectedStoreId = store.getInt("client_id");
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_dropdown_item, stores);
                        storeSpinner.setAdapter(adapter);

                    } catch (JSONException e) {
                        Log.e("JSONError", "Error parsing JSON data", e);
                        Toast.makeText(getApplicationContext(), "Error parsing data", Toast.LENGTH_LONG).show();
                    }
                }, error -> {
            Log.e("VolleyError", "Error fetching stores: " + error.toString());
            Toast.makeText(getApplicationContext(), "Error fetching stores. Check connection and server.", Toast.LENGTH_LONG).show();
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void setupNextButton() {
        nextButton.setOnClickListener(v -> {
            String store = storeSpinner.getSelectedItem().toString();
            if (!store.isEmpty() && !store.equals("Select Store")) {
                Intent intent = new Intent(Search.this, ProductActivity.class);
                intent.putExtra("storeName", store);
                intent.putExtra("storeId", selectedStoreId);
                startActivity(intent);
            } else {
                Toast.makeText(Search.this, "Please select a store", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
