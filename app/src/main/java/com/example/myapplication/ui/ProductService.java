package com.example.myapplication.ui;

import com.example.myapplication.ui.Home.BuyModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    @GET("products")
    Call<ArrayList<BuyModel>> getProducts();

}
