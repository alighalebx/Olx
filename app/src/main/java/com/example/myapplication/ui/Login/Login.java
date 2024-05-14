package com.example.myapplication.ui.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.ui.Home.MainActivity;
import com.example.myapplication.ui.Register.Register;
import com.example.myapplication.ui.RetrofitClient;
import com.example.myapplication.ui.User.UserApi;
import com.example.myapplication.ui.User.UserResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    public ActivityLoginBinding binding;
    private LoginApi loginApi;
    private UserApi UserApi;
    private EditText emailEditText;
    private EditText passwordEditText;

    private WrongCredentialsFragment wrongCredentialsFragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize fragment and fragment manager
        wrongCredentialsFragment = new WrongCredentialsFragment();
        fragmentManager = getSupportFragmentManager();

        // Initialize EditText fields after inflating the layout
        passwordEditText = binding.editTextTextPassword;
        emailEditText = binding.EmailText;

        // Initialize RegisterApi
        loginApi = RetrofitClient.getRetrofitInstance().create(LoginApi.class);

        // Set click listener for the registration button
        binding.LoginBtn.setOnClickListener(this);
        binding.registerTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == binding.LoginBtn.getId()) {
            // If LoginBtn was clicked, call the method to handle login
            LoginButton();
        } else if (v.getId() == binding.registerTextView.getId()) {
            // If registerTextView was clicked, create an intent to navigate to the register activity
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }


    }

    private void LoginButton() {
        LoginService user = new LoginService(emailEditText.getText().toString(), passwordEditText.getText().toString());
        Call<LoginResponse> call = loginApi.LoginUser(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                // Extract token from response body
                if (response.isSuccessful()) {
                    String userId = response.body().getUserId();

//                    Toast.makeText(Login.this, "Login Success: " + userId , Toast.LENGTH_SHORT).show();

                    // Initialize UserApi
                    GetUser(userId);

                    // Start PostAd activity
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    // Log the error code and response body for debugging
//                    int errorCode = response.code();
//                    String errorBody = response.errorBody().toString();
//                    Toast.makeText(Login.this, "Error: " + errorCode + ", " + errorBody, Toast.LENGTH_SHORT).show();
                    displayWrongCredentialsFragment();
                }
            }

            private void displayWrongCredentialsFragment() {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, wrongCredentialsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetUser(String userId) {
        UserApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);
        // Call getUser method to retrieve user data
        Call<UserResponse> callUser = UserApi.getUser(userId);

        callUser.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                // Handle user data response
                if (response.isSuccessful()) {
                    // Do something with the user
                    UserResponse userResponse = response.body();
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String userResponseJson = gson.toJson(userResponse);

                    // Store the JSON string in SharedPreferences
                    editor.putString("User", userResponseJson);
                    editor.apply();
                } else {
                    // Handle unsuccessful response
                    int errorCode = response.code();
                    String errorBody = response.errorBody().toString();
                    Toast.makeText(Login.this, "Error: " + errorCode + ", " + errorBody, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Handle failure to retrieve user data
                Toast.makeText(Login.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}