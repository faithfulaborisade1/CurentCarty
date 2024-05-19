package com.example.cartyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            holder = new ViewHolder();
            holder.productName = convertView.findViewById(R.id.textViewProductName);
            holder.category = convertView.findViewById(R.id.textViewCategory);
            holder.description = convertView.findViewById(R.id.textViewDescription);
            holder.price = convertView.findViewById(R.id.textViewPrice);
            holder.productImage = convertView.findViewById(R.id.imageViewProduct);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);

        holder.productName.setText(product.getName());
        holder.category.setText(product.getCategory());
        holder.description.setText(product.getDescription());
        holder.price.setText(String.format(context.getString(R.string.product_price), product.getPrice()));
        Glide.with(context).load(product.getProductPic()).into(holder.productImage);

        return convertView;
    }

    private static class ViewHolder {
        TextView productName;
        TextView category;
        TextView description;
        TextView price;
        ImageView productImage;
    }
}
