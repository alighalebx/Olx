package com.example.myapplication.ui.WishList;

import com.example.myapplication.ui.Home.BuyModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WishListApi {

    @GET("wishlist/{userId}")
    Call<ArrayList<BuyModel>> getWishlist(@Path("userId") String userId);
    @POST("wishlist/{userId}/{productId}/add")

    Call<Void> addProductToWishList(@Path("userId") String userId, @Path("productId") String productId);

    @POST("wishlist/{userId}/{productId}/remove")

    Call<Void> removeProductFromWishList(@Path("userId") String userId, @Path("productId") String productId);

}
