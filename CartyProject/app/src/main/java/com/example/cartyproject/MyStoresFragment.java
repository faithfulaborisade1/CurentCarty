package com.example.cartyproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

public class MyStoresFragment extends Fragment {

    private RecyclerView recyclerView;
    private StoreAdapter adapter;
    private List<Store> storeList;
    private ProgressBar progressBar;

    public MyStoresFragment() {
        // Required empty public constructor
    }

    public static MyStoresFragment newInstance() {
        return new MyStoresFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_stores, container, false);

        recyclerView = view.findViewById(R.id.rvMyStores);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        storeList = new ArrayList<>();
        adapter = new StoreAdapter(getContext(), storeList);
        recyclerView.setAdapter(adapter);

        loadStores();
        return view;
    }

    private void loadStores() {
        SharedPreferences prefs = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("userId", "");

        if (userId.isEmpty()) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_LONG).show();
            return;
        }

        String url = "http://192.168.8.30/LoginRegister/my_store.php?user_id=" + userId;
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        storeList.clear();
                        if (response.length() == 0) {
                            Toast.makeText(getContext(), "No stores found.", Toast.LENGTH_LONG).show();
                        }
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
                        Toast.makeText(getContext(), "Error parsing JSON data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Error fetching data: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
