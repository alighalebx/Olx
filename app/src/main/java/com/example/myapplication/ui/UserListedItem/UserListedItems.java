package com.example.myapplication.ui.UserListedItem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.Home.BuyModel;
import com.example.myapplication.ui.User.UserResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListedItems extends AppCompatActivity {

   public RecyclerView recyclerView;
    ListedItemAdapter adapter;
    private static final String BASE_URL = "https://olx.azurewebsites.net/";
//   public ItemViewModel itemViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_listed_items);
        recyclerView = findViewById(R.id.Userrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ListedItemAdapter(BASE_URL);
        recyclerView.setAdapter(adapter);
        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserListedItemApi productService = retrofit.create(UserListedItemApi.class);

        String userId = getUserId();

        Call<ArrayList<BuyModel>> call = productService.getListedProducts(userId);
        call.enqueue(new Callback<ArrayList<BuyModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BuyModel>> call, Response<ArrayList<BuyModel>> response) {
                if (response.isSuccessful()) {
                    // Populate the RecyclerView with the fetched data
                    adapter.setList(response.body());

                } else {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<BuyModel>> call, Throwable t) {

            }
        });
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userResponseJson = sharedPreferences.getString("User", null);
        Gson gson = new Gson();
        UserResponse userResponse = gson.fromJson(userResponseJson, UserResponse.class);
        String userId= userResponse.get_id();
        return userId;
    }
}