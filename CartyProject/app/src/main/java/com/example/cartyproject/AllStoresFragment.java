package com.example.cartyproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllStoresFragment extends Fragment {

    private RecyclerView recyclerView;
    private StoreAdapter adapter;
    private List<Store> storeList;

    public AllStoresFragment() {
        // Required empty public constructor
    }

    public static AllStoresFragment newInstance() {
        return new AllStoresFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_stores, container, false);
        recyclerView = view.findViewById(R.id.rvAllStores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        storeList = new ArrayList<>();
        adapter = new StoreAdapter(getActivity(), storeList);
        recyclerView.setAdapter(adapter);

        loadStores();  // Method to load stores from the database
        return view;
    }

    private void loadStores() {
        String url = "http://192.168.8.30/LoginRegister/fetch_stores.php"; // Change to your actual URL

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        storeList.clear();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject store = response.getJSONObject(i);
                            String name = store.getString("client_name");
                            String address = store.getString("client_address");
                            String facing = store.getString("facing");
                            String imageUrl = store.getString("image_url");
                            storeList.add(new Store(name, address, facing, imageUrl));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error parsing JSON data", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Error fetching data: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        requestQueue.add(jsonArrayRequest);
    }
}
