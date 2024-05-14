package com.example.myapplication.ui.Login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("userId")
    private String userId;

    public String getUserId() {
        return userId;
    }
}
