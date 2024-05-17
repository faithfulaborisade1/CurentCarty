package com.example.cartyproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    ProgressBar progressBar;
    CheckBox checkBoxRememberMe;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LoginActivity", "Current theme: " + getResources().getResourceName(R.style.Theme_CartyProject));
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progress);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Check if remember me data exists
        if (sharedPreferences.getBoolean("rememberMe", false)) {
            String savedUsername = sharedPreferences.getString("username", "");
            String savedPassword = sharedPreferences.getString("password", "");
            textInputEditTextUsername.setText(savedUsername);
            textInputEditTextPassword.setText(savedPassword);
            checkBoxRememberMe.setChecked(true);
        }

        buttonLogin.setOnClickListener(v -> {
            String username = textInputEditTextUsername.getText().toString();
            String password = textInputEditTextPassword.getText().toString();
            boolean rememberMe = checkBoxRememberMe.isChecked();

            if (!username.isEmpty() && !password.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[]{"username", "password"};
                    String[] data = new String[]{username, password};
                    PutData putData = new PutData("http://192.168.8.30/LoginRegister/login.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if (jsonObject.getString("status").equals("success")) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    if (rememberMe) { // Save username and password if Remember Me is checked
                                        editor.putBoolean("rememberMe", true);
                                        editor.putString("username", username);
                                        editor.putString("password", password);
                                    } else {
                                        editor.clear(); // Clear the saved credentials if Remember Me is unchecked
                                    }
                                    editor.putString("userId", jsonObject.getString("userId"));
                                    editor.apply();

                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
            }
        });
    }
}
