package com.example.myapplication.ui.PostAd;



import org.w3c.dom.Text;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface PostAdApi {

    @Multipart
    @POST("products")
    Call<Void> postAd(
            @Part("title") String title,
            @Part("description") String description,
            @Part("price") int price,
            @Part("userId") String userId,
            @Part MultipartBody.Part file
    );
    @POST("listed-products/{userId}/{productId}/add")
    Call<Void> addProductToList(@Path("userId") String userId, @Path("productId") String productId);


}
