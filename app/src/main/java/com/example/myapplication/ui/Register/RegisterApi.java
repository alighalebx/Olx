package com.example.myapplication.ui.Register;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterApi {

    @POST("register")
    Call<Void> registerUser(@Body RegService registerRequest);
}
