package com.example.cartyproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    private EditText fullNameEditText, usernameEditText, emailEditText, contactEditText;
    private Button editButton, saveButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        contactEditText = findViewById(R.id.contactEditText);
        editButton = findViewById(R.id.editButton);
        saveButton = findViewById(R.id.saveButton);

        // Assuming "username" could be passed through Intent or SharedPreferences
        String username = getIntent().getStringExtra("username");
        Log.d("ProfileActivity", "Received username: " + username);

        if (username != null) {
            fetchUserProfile(username,"users");
        } else {
            Log.e("ProfileActivity", "Username is null.");
        }
//
//        fetchUserProfile(username,table);

        saveButton.setEnabled(false); // Initial state

        editButton.setOnClickListener(v -> {
            setEditTextsEditable(true);
            editButton.setEnabled(false);
            saveButton.setEnabled(true);
        });

        saveButton.setOnClickListener(v -> {
            updateUserProfile(
                    usernameEditText.getText().toString(),
                    fullNameEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    contactEditText.getText().toString()
            );
        });
    }

    private void setEditTextsEditable(boolean editable) {
        fullNameEditText.setEnabled(editable);
        usernameEditText.setEnabled(editable);
        emailEditText.setEnabled(editable);
        contactEditText.setEnabled(editable);

        // Optional: Apply visual cues for edit mode
        if (editable) {
            // Modify if needed for visual cue
        }
    }

    private void fetchUserProfile(String username, String tableName) {
        String url = "http://192.168.8.30/LoginRegister/getUserProfile.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("ProfileRequest", "Fetching profile for username: " + username + " from table: " + tableName);
                    try {
                        Log.d("ProfileResponse", response);
                        JSONObject obj = new JSONObject(response);
                        fullNameEditText.setText(obj.optString("fullname"));
                        usernameEditText.setText(obj.optString("username"));
                        emailEditText.setText(obj.optString("email"));
                        contactEditText.setText(obj.optString("contact"));
                        // Disable editing initially
                        setEditTextsEditable(false);
                        editButton.setEnabled(true);
                    } catch (JSONException e) {
                        Log.e("ProfileError", "Error parsing profile data: " + e.getMessage());
                        Toast.makeText(this, "Error parsing profile data", Toast.LENGTH_LONG).show();
                    }
                }, error -> {
            Log.e("ProfileError", "Error fetching profile");
            Toast.makeText(this, "Error fetching profile", Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("table", tableName); // Include the table name in the request
                return params;
            }
        };

        queue.add(stringRequest);
    }


    private void updateUserProfile(String username, String fullname, String email, String contact) {
        String url = "http://192.168.8.30/LoginRegister/updateUserProfile.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                    setEditTextsEditable(false);
                    editButton.setEnabled(true);
                    saveButton.setEnabled(false);
                }, error -> Toast.makeText(this, "Error updating profile", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("table", "users"); // Specify the table name here
                params.put("username", username);
                params.put("fullname", fullname);
                params.put("email", email);
                params.put("contact", contact);
                return params;
            }
        };

        queue.add(stringRequest);
    }

}
