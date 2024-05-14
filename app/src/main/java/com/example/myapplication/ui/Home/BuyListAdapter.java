package com.example.myapplication.ui.Home;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BuyListAdapter extends RecyclerView.Adapter<BuyListAdapter.BuyViewHolder> {
    private ArrayList<BuyModel> buyList = new ArrayList<>();
    private ArrayList<BuyModel> buyListFiltered = new ArrayList<>();

    private String baseUrl;

    // Constructor accepting the base URL
    public BuyListAdapter(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @NonNull
    @Override
    public BuyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BuyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_list_item, parent, false));
    }

    @Override

    public void onBindViewHolder(@NonNull BuyViewHolder holder, int position) {
        final BuyModel buyModel = buyListFiltered.get(position);

        //to remove the double qoutes
        String title = buyModel.getTitle().replaceAll("\"", "");
        holder.itemName.setText(title);
        holder.itemPrice.setText(String.valueOf(buyModel.getPrice()));

        // Load image using Picasso
        Picasso.get().load(baseUrl + buyModel.getImageUrl()).into(holder.itemImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("itemName", title);
                intent.putExtra("itemPrice", buyModel.getPrice());
                intent.putExtra("itemImage", baseUrl + buyModel.getImageUrl());
                intent.putExtra("itemUserName", buyModel.getUserName());
                intent.putExtra("itemPhoneNumber", buyModel.getPhoneNumber());
                intent.putExtra("ItemId", buyModel.getId());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return buyListFiltered.size(); // Use filtered list size
    }

    public void setList(ArrayList<BuyModel> buyList) {
        this.buyList = buyList;
        this.buyListFiltered = new ArrayList<>(buyList); // Initialize filtered list with all items
        notifyDataSetChanged();
    }

    // Filter method
    public void filter(String query) {
        buyListFiltered.clear();
        if (query.isEmpty()) {
            buyListFiltered.addAll(buyList); // If the query is empty, show all items
        } else {
            query = query.toLowerCase();
            for (BuyModel buy : buyList) {
                if (buy.getTitle().toLowerCase().contains(query)) {
                    buyListFiltered.add(buy); // Add items matching the query
                    Log.e(TAG,"seachhhh " + buy.getTitle().toString());

                }
            }
        }
        notifyDataSetChanged(); // Notify adapter about the changes
    }


    public class BuyViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;

        public BuyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
        }
    }
}
