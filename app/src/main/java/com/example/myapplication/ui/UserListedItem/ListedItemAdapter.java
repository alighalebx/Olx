package com.example.myapplication.ui.UserListedItem;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import com.example.myapplication.ui.Home.BuyModel;
import com.example.myapplication.ui.Home.DetailActivity;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class ListedItemAdapter extends RecyclerView.Adapter<ListedItemAdapter.ListViewHolder>{


    private ArrayList<BuyModel> buyList = new ArrayList<>();
    private String baseUrl;

    // Constructor accepting the base URL
    public ListedItemAdapter(String baseUrl) {
        this.baseUrl = baseUrl;
    }



    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final BuyModel buyModel = buyList.get(position);
        String title = buyModel.getTitle().replaceAll("\"", "");
        String desc = buyModel.getDescription().replaceAll("\"", "");
        holder.itemName.setText(title);
        holder.itemDesc.setText(desc);
        holder.itemPrice.setText(String.valueOf(buyModel.getPrice()));
        Picasso.get().load(baseUrl + buyModel.getImageUrl()).into(holder.itemImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserListedItemDetails.class);
                intent.putExtra("itemName", title);
                intent.putExtra("itemPrice", buyModel.getPrice());
                intent.putExtra("itemImage", baseUrl + buyModel.getImageUrl());
                intent.putExtra("itemDesc", desc);
                intent.putExtra("itemId", buyModel.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return buyList.size();
    }

    public void setList(ArrayList<BuyModel> buyList) {
        this.buyList = buyList;
        notifyDataSetChanged();
    }
    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        TextView itemDesc;
        ImageView itemImage;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPrice = itemView.findViewById(R.id.PriceTextViewUserProduct);
            itemName = itemView.findViewById(R.id.TitleTextViewUserProduct);
            itemDesc= itemView.findViewById(R.id.DecriptionTextViewUserProduct);
            itemImage=itemView.findViewById(R.id.ImageImageViewUserProduct);

        }
    }
}
