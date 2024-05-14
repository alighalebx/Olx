package com.example.myapplication.ui.WishList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.ui.Home.MainActivity;
import com.example.myapplication.ui.RetrofitClient;
import com.example.myapplication.ui.User.UserResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishItemDetails extends AppCompatActivity implements View.OnClickListener {

    private WishListApi wishListApi;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_item_details);

        // Retrieve data from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String itemName = extras.getString("itemName");
            double itemPrice = extras.getDouble("itemPrice");
            String itemImage = extras.getString("itemImage");
            productId = extras.getString("itemId");

            // Display item details
            TextView itemNameTextView = findViewById(R.id.TitleWishListedDetail);
            TextView itemPriceTextView = findViewById(R.id.PriceWishListedDetail);
            ImageView itemImageView = findViewById(R.id.ImageWishListedDetail);
            Button removeItemButton = findViewById(R.id.RemoveBtnWishList);

            itemNameTextView.setText(itemName);
            itemPriceTextView.setText(String.valueOf(itemPrice));
            Picasso.get().load(itemImage).into(itemImageView);

            // Initialize WishListApi
            wishListApi = RetrofitClient.getRetrofitInstance().create(WishListApi.class);

            // Set click listener for removeItemButton
            removeItemButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.RemoveBtnWishList) {
            // Call the API to remove the product from the wishlist
            Call<Void> call = wishListApi.removeProductFromWishList(getUserId(), productId);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(WishItemDetails.this, "Item removed from wishlist", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(WishItemDetails.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(WishItemDetails.this, "Failed to remove item from wishlist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(WishItemDetails.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userResponseJson = sharedPreferences.getString("User", null);
        Gson gson = new Gson();
        UserResponse userResponse = gson.fromJson(userResponseJson, UserResponse.class);
        return userResponse != null ? userResponse.get_id() : "";

    }
}
