package com.example.myapplication.ui.WishList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.Home.BuyModel;
import com.example.myapplication.ui.RetrofitClient;
import com.example.myapplication.ui.User.UserResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishListItems extends AppCompatActivity {
    private WishListApi apiService;
    public RecyclerView recyclerView;
    WishListAdapter adapter;
    private static final String BASE_URL = "https://olx.azurewebsites.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list_items);

        recyclerView = findViewById(R.id.Wishrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WishListAdapter(BASE_URL);
        recyclerView.setAdapter(adapter);

        // Initialize Retrofit
        apiService = RetrofitClient.getRetrofitInstance().create(WishListApi.class);
        String userId = getUserId();

        Call<ArrayList<BuyModel>> call = apiService.getWishlist(userId);
        call.enqueue(new Callback<ArrayList<BuyModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BuyModel>> call, Response<ArrayList<BuyModel>> response) {
                if (response.isSuccessful()) {


                        adapter.setList(response.body());

                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BuyModel>> call, Throwable t) {
                // Handle failure here
            }
        });
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userResponseJson = sharedPreferences.getString("User", null);
        Gson gson = new Gson();
        UserResponse userResponse = gson.fromJson(userResponseJson, UserResponse.class);
       return userResponse != null ? userResponse.get_id() : "";

    }
}