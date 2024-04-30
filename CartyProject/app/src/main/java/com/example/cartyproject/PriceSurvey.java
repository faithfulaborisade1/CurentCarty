package com.example.cartyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class PriceSurvey extends AppCompatActivity {

    EditText barcodeEditText, priceEditText;
    AutoCompleteTextView storeNameDropdown, locationDropdown, productDropdown;
    Button sendButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_survey); // Make sure this is the correct layout name

        // Initialize Views
        initializeViews();

        // Setup dropdowns
        setupDropdowns();

        // Setup the Send Button click listener
        sendButton.setOnClickListener(v -> {
            submitPriceSurvey();
        });
    }

    private void initializeViews() {
        barcodeEditText = findViewById(R.id.barcodeEditText);
        priceEditText = findViewById(R.id.priceEditText);
        storeNameDropdown = findViewById(R.id.storeNameDropdown);
        locationDropdown = findViewById(R.id.locationDropdown);
        productDropdown = findViewById(R.id.productDropdown);
        sendButton = findViewById(R.id.sendButton);
        progressBar = findViewById(R.id.progress);
    }

    private void setupDropdowns() {
        AutoCompleteTextView storeNameDropdown = findViewById(R.id.storeNameDropdown);
        String[] storeNames = getResources().getStringArray(R.array.store_names_price);
        ArrayAdapter<String> storeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, storeNames);
        storeNameDropdown.setAdapter(storeAdapter);

        // Setup for the Location Dropdown
        AutoCompleteTextView locationDropdown = findViewById(R.id.locationDropdown);
        String[] locations = getResources().getStringArray(R.array.locations);
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, locations);
        locationDropdown.setAdapter(locationAdapter);

        // Setup for the Product Dropdown
        AutoCompleteTextView productDropdown = findViewById(R.id.productDropdown);
        String[] products = getResources().getStringArray(R.array.products);
        ArrayAdapter<String> productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, products);
        productDropdown.setAdapter(productAdapter);
    }

    private void submitPriceSurvey() {
        // Collect values
        String barcode = barcodeEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String storeName = storeNameDropdown.getText().toString().trim();
        String location = locationDropdown.getText().toString().trim();
        String product = productDropdown.getText().toString().trim();

        // Validation
        if (!barcode.isEmpty() && !price.isEmpty() && !storeName.isEmpty() && !location.isEmpty() && !product.isEmpty()) {

            // Use Handler to post data to server
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                // Parameters and data
                String[] field = new String[]{"barcode", "price", "storeName", "location", "product"};
                String[] data = new String[]{barcode, price, storeName, location, product};
                PutData putData = new PutData("http://192.168.110.210/LoginRegister/priceSurvey.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {

                        String result = putData.getResult();
                        if ("Price Survey Entry Submitted Successfully".equalsIgnoreCase(result)) {
                            Toast.makeText(getApplicationContext(), "Survey Submitted Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Submission Failed: " + result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
        }
    }
}
