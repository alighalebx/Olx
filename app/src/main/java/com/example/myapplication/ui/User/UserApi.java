package com.example.myapplication.ui.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApi {
    @GET("users/{userId}")
     Call<UserResponse> getUser(@Path("userId") String userId);
}
