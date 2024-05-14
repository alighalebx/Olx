package com.example.myapplication.ui.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.ui.RetrofitClient;
import com.example.myapplication.ui.User.UserResponse;
import com.example.myapplication.ui.WishList.WishListApi;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private WishListApi apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String itemName = extras.getString("itemName");
            double itemPrice = extras.getDouble("itemPrice");
            String itemImage = extras.getString("itemImage");
            String itemUserName = extras.getString("itemUserName");

            String itemPhoneNumber = extras.getString("itemPhoneNumber");

            TextView itemNameTextView = findViewById(R.id.item_name_detail);
            TextView itemPriceTextView = findViewById(R.id.item_price_detail);
            TextView itemUserNameTextView = findViewById(R.id.UserNameTextView);
            TextView itemPhoneNumberTextView = findViewById(R.id.PhoneNumberTextView);
            ImageView itemImageView = findViewById(R.id.item_image_detail);


            itemUserNameTextView.setText(itemUserName);
            itemPhoneNumberTextView.setText(itemPhoneNumber);
            itemNameTextView.setText(itemName);
            itemPriceTextView.setText("Price: $" + String.valueOf(itemPrice));
            Button wishlistBtn = findViewById(R.id.buy_button);
            Button callBtn = findViewById(R.id.call_button);
            callBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+itemPhoneNumber));
                    startActivity(intent);
                }


            });
            String productId=extras.getString("ItemId");
            Toast.makeText(DetailActivity.this, productId ,Toast.LENGTH_SHORT).show();


            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String userResponseJson = sharedPreferences.getString("User", null);
            Gson gson = new Gson();
            UserResponse userResponse = gson.fromJson(userResponseJson, UserResponse.class);
            String userId= userResponse.get_id();
            Toast.makeText(DetailActivity.this, userId ,Toast.LENGTH_SHORT).show();



            Picasso.get()
                    .load(itemImage)
                    .into(itemImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });
            wishlistBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {





                    apiService = RetrofitClient.getRetrofitInstance().create(WishListApi .class);
                    // Call the API method to add the product to the wishlist
                    Call<Void> call = apiService.addProductToWishList(userId, productId);
                    call.enqueue(new retrofit2.Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(DetailActivity.this, "Succesfully Added to Wishlist" ,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    } );

                }
            });



        }
    }
}
