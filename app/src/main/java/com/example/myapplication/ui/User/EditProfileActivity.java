package com.example.myapplication.ui.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.ui.User.UserResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    private Button saveButton;
    UserResponse userResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fullNameEditText = findViewById(R.id.editTextFullName);
        phoneNumberEditText = findViewById(R.id.editTextPhoneNumber);
        passwordEditText = findViewById(R.id.editTextPassword);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userResponse.get_id();
                String newFullName = fullNameEditText.getText().toString();

                String newPhoneNumber = phoneNumberEditText.getText().toString();
                String newPassword = passwordEditText.getText().toString();

                // Call the API to update user profile
                updateUserProfile(userId, newFullName, newPhoneNumber, newPassword);
            }
        });
    }

    private void updateUserProfile(String userId, String newFullName, String newPhoneNumber, String newPassword) {
        OkHttpClient client = new OkHttpClient();

        // Build request body
        RequestBody formBody = new FormBody.Builder()
                .add("username", newFullName)
                .add("phoneNo", newPhoneNumber)
                .add("password", newPassword)
                .build();

        // Build request
        Request request = new Request.Builder()
                .url("https://olx.azurewebsites.net/users/" + userId) // Include the user ID in the URL
                .put(formBody)
                .build();

        // Execute the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Parse response
                    Gson gson = new Gson();
                    userResponse = gson.fromJson(response.body().string(), UserResponse.class);

                    // Update UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            // Update UI with new user data if needed
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });
    }
}