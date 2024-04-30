package com.example.cartyproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private ListView productList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productList = findViewById(R.id.productList);
        items = new ArrayList<>();

        String storeName = getIntent().getStringExtra("storeName");
        fetchProducts(storeName);
    }

    private void fetchProducts(String store) {
        String url = "http://192.168.110.210/LoginRegister/fetch_products.php?store=" + store;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Product> products = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject productObject = response.getJSONObject(i);
                            Product product = new Product(
                                    productObject.getString("prod_name"),
                                    productObject.getString("category"),
                                    productObject.getString("description"),  // Make sure your JSON has these keys
                                    productObject.getDouble("price")
                            );
                            products.add(product);
                        }
                        productList.setAdapter(new ProductAdapter(this, products));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error parsing products", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(getApplicationContext(), "Error fetching products", Toast.LENGTH_SHORT).show();
        });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

}
