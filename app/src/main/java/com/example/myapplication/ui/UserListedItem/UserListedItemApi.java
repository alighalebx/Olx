package com.example.myapplication.ui.UserListedItem;

import com.example.myapplication.ui.Home.BuyModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserListedItemApi {
    @GET("products/user/{userId}")
    Call<ArrayList<BuyModel>> getListedProducts(@Path("userId") String userId);

    @DELETE("products/{productId}")

    Call<Void> deleteProduct(@Path("productId") String productId);

}
