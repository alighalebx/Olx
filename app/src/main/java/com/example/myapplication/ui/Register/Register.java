package com.example.myapplication.ui.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityRegisterBinding;
import com.example.myapplication.ui.Login.Login;
import com.example.myapplication.ui.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity implements View.OnClickListener {

    public ActivityRegisterBinding binding;

    private  RegisterApi registerApi;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText usernameEditText;

    private EditText PhoneNoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize RegisterApi
        registerApi = RetrofitClient.getRetrofitInstance().create(RegisterApi.class);

        // Set click listener for the registration button
        binding.registerbtn.setOnClickListener(this);
        binding.loginTextView.setOnClickListener(this);

    }


    public void register(){
        emailEditText = binding.editTextEmail;
        passwordEditText = binding.editTextTextPassword;
        usernameEditText = binding.editTextFullName;
        PhoneNoText = binding.editTextPhoneNumber;

        // Create RegService object with user data
        RegService user = new RegService(
                emailEditText.getText().toString(),
                PhoneNoText.getText().toString(),
                usernameEditText.getText().toString(),
                passwordEditText.getText().toString());


        // Make API call to register the user
        Call<Void> call = registerApi.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Registration successful, show a toast message
                    Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Registration failed, show appropriate error message
                    Toast.makeText(Register.this, "Email Already exists,Registration failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Network error or other failure occurred, show appropriate error message
                Toast.makeText(Register.this, "Failed to register. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.registerbtn.getId()) {
            register();
        } else if (v.getId() == binding.loginTextView.getId()) {
            // If loginTextView was clicked, create an intent to navigate to the login activity
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }
}