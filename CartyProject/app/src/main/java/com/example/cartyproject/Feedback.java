package com.example.cartyproject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Feedback extends AppCompatActivity {

    private Spinner feedbackProductSpinner, feedbackStoreSpinner;
    private TextView feedbackTitle, feedbackDescription;
    private Button submitFeedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialize views
        feedbackProductSpinner = findViewById(R.id.feedbackProductSpinner);
        feedbackStoreSpinner = findViewById(R.id.feedbackStoreSpinner);
        feedbackTitle = findViewById(R.id.feedbackTitle);
        feedbackDescription = findViewById(R.id.feedbackDescription);
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton);

        // Setup spinners with custom adapter to include hints
        setupSpinner(feedbackProductSpinner, R.array.product_names);
        setupSpinner(feedbackStoreSpinner, R.array.store_names);

        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFeedback();
            }
        });
    }

    private void setupSpinner(Spinner spinner, int arrayResId) {
        List<String> list = new ArrayList<>(Arrays.asList(getResources().getStringArray(arrayResId)));
        HintArrayAdapter adapter = new HintArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void submitFeedback() {
        // Retrieve selected item, ensuring it's not the hint
        String product = feedbackProductSpinner.getSelectedItemPosition() > 0 ? feedbackProductSpinner.getSelectedItem().toString() : "";
        String store = feedbackStoreSpinner.getSelectedItemPosition() > 0 ? feedbackStoreSpinner.getSelectedItem().toString() : "";
        String title = feedbackTitle.getText().toString().trim();
        String description = feedbackDescription.getText().toString().trim();

        if (product.isEmpty() || store.isEmpty() || title.isEmpty() || description.isEmpty()) {
            Toast.makeText(Feedback.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // Initialize arrays for parameters and data
                    String[] field = new String[4]; // Adjust the size based on your form fields
                    field[0] = "product";
                    field[1] = "title";
                    field[2] = "store";
                    field[3] = "description";

                    // Data array corresponding to the parameters
                    String[] data = new String[4]; // Ensure this matches the size of 'field'
                    data[0] = product; // Assuming 'product' is a variable holding the input from the user
                    data[1] = title; // Same assumption as above
                    data[2] = store; // And so on...
                    data[3] = description;

                    // PutData points to your feedback submission endpoint
                    PutData putData = new PutData("http://192.168.154.210/LoginRegister/feedback.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            Log.i("PutData", result);

                            // Customize the response handling
                            if (result.equals("Feedback Submitted Successfully")) {
                                Log.i("PutData", "Feedback Submitted Successfully");
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                // Optionally, redirect or clear the form
                            } else {
                                Log.i("PutData", "Feedback Submission Failed");
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });


        }
        clearFields();
    }

    class HintArrayAdapter extends ArrayAdapter<String> {
        Context context;

        public HintArrayAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @Override
        public boolean isEnabled(int position) {
            return position != 0; // Make the hint item non-selectable
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            if (position == 0) {
                // Set the hint text color
                view.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
            } else {
                view.setTextColor(context.getResources().getColor(android.R.color.black));
            }
            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            if (position == 0) {
                // Set the dropdown hint text color
                view.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
            } else {
                view.setTextColor(context.getResources().getColor(android.R.color.black));
            }
            return view;
        }
    }

    private void clearFields() {
        feedbackProductSpinner.setSelection(0);
        feedbackStoreSpinner.setSelection(0);
        feedbackTitle.setText("");
        feedbackDescription.setText("");
    }
}




