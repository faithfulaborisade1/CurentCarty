package com.example.cartyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Arrays;
import java.util.Calendar;

public class PriceSurvey extends AppCompatActivity {

    EditText barcodeEditText, priceEditText, dateEditText;
    AutoCompleteTextView storeNameDropdown, locationDropdown, productDropdown;
    Button sendButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_survey); // Ensure this is the correct layout name

        // Initialize Views
        initializeViews();

        // Setup dropdowns
        setupDropdowns();

        // Setup Date Picker
        setupDateInput();

        // Setup the Send Button click listener
        sendButton.setOnClickListener(v -> {
            submitPriceSurvey();
        });
    }

    private void initializeViews() {
        barcodeEditText = findViewById(R.id.barcodeEditText);
        priceEditText = findViewById(R.id.priceEditText);
        dateEditText = findViewById(R.id.dateEditText);
        storeNameDropdown = findViewById(R.id.storeNameDropdown);
        locationDropdown = findViewById(R.id.locationDropdown);
        productDropdown = findViewById(R.id.productDropdown);
        sendButton = findViewById(R.id.sendButton);
        progressBar = findViewById(R.id.progress); // Ensure progressBar is initialized
    }

    private void setupDropdowns() {
        String[] storeNames = getResources().getStringArray(R.array.store_names_price);
        ArrayAdapter<String> storeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, storeNames);
        storeNameDropdown.setAdapter(storeAdapter);

        String[] locations = getResources().getStringArray(R.array.locations);
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, locations);
        locationDropdown.setAdapter(locationAdapter);

        String[] products = getResources().getStringArray(R.array.products);
        ArrayAdapter<String> productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, products);
        productDropdown.setAdapter(productAdapter);
    }

    private void setupDateInput() {
        dateEditText.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                PriceSurvey.this,
                (view, year1, month1, dayOfMonth) -> {
                    String date = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
                    dateEditText.setText(date);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void submitPriceSurvey() {
        // Collect values
        String barcode = barcodeEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String storeName = storeNameDropdown.getText().toString().trim();
        String location = locationDropdown.getText().toString().trim();
        String product = productDropdown.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();

        // Validation
        if (!barcode.isEmpty() && !price.isEmpty() && !storeName.isEmpty() && !location.isEmpty() && !product.isEmpty() && !date.isEmpty()) {

            progressBar.setVisibility(View.VISIBLE);

            // Use Handler to post data to server
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                // Parameters and data
                String[] field = new String[]{"barcode", "price", "storeName", "location", "product", "date"};
                String[] data = new String[]{barcode, price, storeName, location, product, date};

                // Log the data being sent
                Log.d("PriceSurvey", "Sending data: " + Arrays.toString(data));

                PutData putData = new PutData("http://192.168.8.30/LoginRegister/priceSurvey.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();
                        Log.d("PriceSurvey", "Server response: " + result);
                        if ("Price Survey Entry Submitted Successfully".equalsIgnoreCase(result)) {
                            Toast.makeText(getApplicationContext(), "Survey Submitted Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Submission Failed: " + result, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Submission Failed: Unable to complete request", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Submission Failed: Unable to start request", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
        }
    }
}
