package com.example.myapplication.ui.UserListedItem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.ui.Home.MainActivity;
import com.example.myapplication.ui.Login.Login;
import com.example.myapplication.ui.Login.LoginApi;
import com.example.myapplication.ui.Login.LoginResponse;
import com.example.myapplication.ui.RetrofitClient;
import com.example.myapplication.ui.User.UserResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Response;

public class UserListedItemDetails extends AppCompatActivity implements View.OnClickListener {

 public UserListedItemApi  userListedItemApi;
  public  String ProductId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_listed_item_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String itemName = extras.getString("itemName");
            double itemPrice = extras.getDouble("itemPrice");

            String itemImage = extras.getString("itemImage"); // Add this line to retrieve the image URL

            TextView itemNameTextView = findViewById(R.id.TitleUserListedDetail);
            TextView itemPriceTextView = findViewById(R.id.PriceUserListedDetail);

            ImageView itemImageView = findViewById(R.id.ImageUserListedDetail);

            Button removeItem = findViewById(R.id.RemoveBtnUserListedDetail);
             ProductId = extras.getString("itemId");

            itemNameTextView.setText(itemName);
            itemPriceTextView.setText(String.valueOf(itemPrice));

            // Initialize RegisterApi
            userListedItemApi = RetrofitClient.getRetrofitInstance().create(UserListedItemApi.class);
            Toast.makeText(this, ProductId, Toast.LENGTH_SHORT).show();
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

            removeItem.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        Call<Void> call = userListedItemApi.deleteProduct(ProductId);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(UserListedItemDetails.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}