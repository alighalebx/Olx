package com.example.myapplication.ui.Home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.ProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuyFragment extends Fragment {

    private RecyclerView recyclerView;
    private BuyListAdapter adapter;
    private SearchView searchBar;

    private static final String BASE_URL = "https://olx.azurewebsites.net/";
    private static final String TAG = "BuyFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy_items, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        searchBar = view.findViewById(R.id.search_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BuyListAdapter(BASE_URL);
        recyclerView.setAdapter(adapter);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductService productService = retrofit.create(ProductService.class);

        Call<ArrayList<BuyModel>> call = productService.getProducts();
        call.enqueue(new Callback<ArrayList<BuyModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BuyModel>> call, Response<ArrayList<BuyModel>> response) {
                if (response.isSuccessful()) {
                    // Populate the RecyclerView with the fetched data
                    adapter.setList(response.body());
                } else {
                    Log.e(TAG, "Failed to fetch products: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BuyModel>> call, Throwable t) {
                Log.e(TAG, "Failed to fetch products: " + t.getMessage());
            }
        });

        // Implement search functionality
        // Assuming searchBar is your SearchView
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // This method is called when the user submits the query
                return false; // Return true if the query has been handled
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // This method is called when the text in the SearchView changes
                adapter.filter(newText); // Call filter method on adapter
                Log.e(TAG, "search " + newText);
                return true; // Return true to indicate that the text change has been handled
            }
        });


        return view;
    }
}
