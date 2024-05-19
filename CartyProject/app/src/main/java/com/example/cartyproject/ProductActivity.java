package com.example.cartyproject;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private ListView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productList = findViewById(R.id.productList);

        String storeName = getIntent().getStringExtra("storeName");
        fetchProducts(storeName);
    }

    private void fetchProducts(String store) {
        String url = "http://192.168.8.30/LoginRegister/fetch_products.php?store=" + store;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Product> products = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject productObject = response.getJSONObject(i);
                            Product product = new Product(
                                    productObject.getString("prod_name"),
                                    productObject.getString("category"),
                                    productObject.getString("description"),
                                    productObject.getDouble("price"),
                                    productObject.getString("prod_pic")
                            );
                            products.add(product);
                        }
                        ProductAdapter adapter = new ProductAdapter(this, products);
                        productList.setAdapter(adapter);
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
