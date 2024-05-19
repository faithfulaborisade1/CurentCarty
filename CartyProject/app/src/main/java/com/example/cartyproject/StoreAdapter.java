package com.example.cartyproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private Context context;
    private List<Store> stores;

    public StoreAdapter(Context context, List<Store> stores) {
        this.context = context;
        this.stores = stores;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Store store = stores.get(position);
        holder.storeName.setText(store.getName());
        holder.storeAddress.setText(store.getLocation());
        holder.storeFacing.setText(store.getFacing());
        Glide.with(context).load(store.getImageUrl()).into(holder.storeImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StoreDetailActivity.class);
            intent.putExtra("storeName", store.getName());
            intent.putExtra("facing", store.getFacing());
            intent.putExtra("imageUrl", store.getImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        TextView storeAddress;
        TextView storeFacing;
        ImageView storeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.textViewStoreName);
            storeAddress = itemView.findViewById(R.id.textViewStoreAddress);
            storeFacing = itemView.findViewById(R.id.textViewStoreFacing);
            storeImage = itemView.findViewById(R.id.imageViewStore);
        }
    }
}
