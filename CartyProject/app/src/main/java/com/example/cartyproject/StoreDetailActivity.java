package com.example.cartyproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class StoreDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        // Initialize views
        ImageView storeImageView = findViewById(R.id.storeImageView);
        TextView storeNameTextView = findViewById(R.id.storeNameTextView);
        TextView storeFacingTextView = findViewById(R.id.storeFacingTextView);

        Button viewOnMapButton = findViewById(R.id.viewOnMapButton);
        Button feedback = findViewById(R.id.viewSurveysButton);
        Button product = findViewById(R.id.viewProductsButton);

        // Get data from intent
        String storeName = getIntent().getStringExtra("storeName");
        String facing = getIntent().getStringExtra("facing");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Set data to views
        storeNameTextView.setText(storeName);
        storeFacingTextView.setText(facing);
        Glide.with(this).load(imageUrl).into(storeImageView);

        // Set onClick listeners for buttons
        viewOnMapButton.setOnClickListener(v -> {
            // Intent to open maps activity or fragment
            // Example: show location in maps
        });

        feedback.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Feedback.class);
            startActivity(intent);
        });

        product.setOnClickListener(v -> {
            Intent intent = new Intent(StoreDetailActivity.this, ProductActivity.class);
            intent.putExtra("storeName", storeName);
            startActivity(intent);
        });
    }
}
