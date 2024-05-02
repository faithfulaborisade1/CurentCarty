package com.example.cartyproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StoreDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        // Initialize views
        ImageView storeImageView = findViewById(R.id.storeImageView);
        TextView storeNameTextView = findViewById(R.id.storeNameTextView);

        Button viewOnMapButton = findViewById(R.id.viewOnMapButton);
        Button viewSurveysButton = findViewById(R.id.viewSurveysButton);

        // Get data from intent
        String storeName = getIntent().getStringExtra("storeName");
        String lastVisit = getIntent().getStringExtra("lastVisit");
        // You might pass the image as a resource ID or URL depending on your implementation
        // For demonstration assuming an image resource ID was passed
        int imageResourceId = getIntent().getIntExtra("imageResource", R.drawable.background);

        // Set data to views
        storeImageView.setImageResource(imageResourceId);
        storeNameTextView.setText(storeName);


        // Set onClick listeners for buttons
        viewOnMapButton.setOnClickListener(v -> {
            // Intent to open maps activity or fragment
            // Example: show location in maps
        });

        viewSurveysButton.setOnClickListener(v -> {
            // Intent to open survey activity or display surveys
            // Example: show surveys related to the store
        });
    }
}
