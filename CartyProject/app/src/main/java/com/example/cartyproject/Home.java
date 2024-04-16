package com.example.cartyproject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {

    private Spinner countySpinner, townSpinner, storeSpinner;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        countySpinner = findViewById(R.id.countySpinner);
        townSpinner = findViewById(R.id.townSpinner);
        storeSpinner = findViewById(R.id.storeSpinner);
        nextButton = findViewById(R.id.nextButton);

        // Setup spinners
        setupSpinner(countySpinner, R.array.county_names);
        setupSpinner(townSpinner, R.array.town_names);
        setupSpinner(storeSpinner, R.array.store_names);

        // Setup button listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedToNextActivity();
            }
        });
    }

    private void setupSpinner(Spinner spinner, int arrayResId) {
        List<String> list = new ArrayList<>(Arrays.asList(getResources().getStringArray(arrayResId)));
        list.add(0, "Select an option"); // Adding hint as the first item
        HintArrayAdapter adapter = new HintArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void proceedToNextActivity() {
        // Logic to validate selection and proceed
        String county = countySpinner.getSelectedItem().toString();
        String town = townSpinner.getSelectedItem().toString();
        String store = storeSpinner.getSelectedItem().toString();

        if ("Select an option".equals(county) || "Select an option".equals(town) || "Select an option".equals(store)) {
            Toast.makeText(Home.this, "Please complete all selections", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Home.this, "Proceeding with " + county + ", " + town + ", " + store, Toast.LENGTH_LONG).show();
            // Intent to navigate to another activity can be placed here
        }
    }

    private class HintArrayAdapter extends ArrayAdapter<String> {
        public HintArrayAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public boolean isEnabled(int position) {
            // Disable the first item which functions as hint
            return position != 0;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            view.setVisibility(position == 0 ? View.GONE : View.VISIBLE);  // Hide hint from dropdown
            return view;
        }
    }
}
