package com.example.cartyproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardMain extends AppCompatActivity {

    ImageView feedBack,profile,create,product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_main);

        // Setup navigation to Feedback activity using the CardView
        feedBack= findViewById(R.id.feedBack);
        profile = findViewById(R.id.profile);
        create = findViewById(R.id.create);
        product = findViewById(R.id.product);


        feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Feedback.class);
                startActivity(intent);
            }
        });

//        String username = getIntent().getStringExtra("username");
//        Log.i("DashboardMain", "Passing username: " + username + " to Profile");
//        Intent intent = new Intent(this, Profile.class);
//        intent.putExtra("username", username);
//        startActivity(intent);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                Log.i("DashboardMain", "Passing username: " + username + " to Profile");
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        // Additional setup for other UI components as needed
    }
}
