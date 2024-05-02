package com.example.cartyproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    private List<Store> stores;
    private Context context;



    public StoreAdapter(Context context, List<Store> stores) {
        this.context = context;
        this.stores = stores;
    }
    public StoreAdapter(List<Store> stores) {
        this.stores = stores;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = stores.get(position);
        holder.storeName.setText(store.getName());
        holder.storeLocation.setText(store.getLocation());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext(); // Get context from the view
            if (context != null) { // Check if the context is not null
                Intent intent = new Intent(context, StoreDetailActivity.class);
                intent.putExtra("storeName", store.getName());
//                intent.putExtra("lastVisit", "Last visited: " + store.getLastVisitDate());
//                intent.putExtra("imageResource", store.getImageResourceId());
                context.startActivity(intent);
            } else {
                Log.e("StoreAdapter", "Context is null");
            }
        });
    }


    @Override
    public int getItemCount() {
        return stores.size();
    }
//
//    @Override
//    public int getItemCount() {
//        return stores != null ? stores.size() : 0;
//    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView storeName, storeLocation;

        StoreViewHolder(View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.store_name);
            storeLocation = itemView.findViewById(R.id.store_location);
        }
    }
}
