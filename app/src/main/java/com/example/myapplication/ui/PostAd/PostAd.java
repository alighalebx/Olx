package com.example.myapplication.ui.PostAd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityPostAdBinding;
import com.example.myapplication.ui.Home.MainActivity;
import com.example.myapplication.ui.RetrofitClient;
import com.example.myapplication.ui.User.UserResponse;
import com.example.myapplication.ui.UserListedItem.UserListedItemDetails;
import com.example.myapplication.ui.UserListedItem.UserListedItems;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAd extends AppCompatActivity implements View.OnClickListener {

    public ActivityPostAdBinding binding;

    private  PostAdApi PostAdApi;
    private EditText TitleEditText;
    private EditText descEditText;
    private EditText priceEditText;

    File imageFile ;
    // Define ActivityResultLauncher for picking visual media

    private File saveImageToFile(Bitmap bitmap) {
        // Create a file to save the image
        File imagesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(imagesDir, "captured_image.jpg");

        try {
            // Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapData = bos.toByteArray();

            // Write the bytes to the file
            FileOutputStream fos = new FileOutputStream(imageFile);
            fos.write(bitmapData);
            fos.flush();
            fos.close();

            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(
            new ActivityResultContracts.PickVisualMedia(),
            uri -> {
                if (uri != null) {
                    imageFile = new File(getRealPathFromURI(uri));
                    if (imageFile.exists()) {
                        // Now you can use this imageFile object to send it as a file
                        // For example, you can pass it to a method to send it somewhere
                        // Example: sendFile(imageFile);
                        loadImageIntoImageView(uri);
                        String message = "Image file path: " + imageFile.getAbsolutePath();
                        showToast(message);
                    } else {
                        // File does not exist, handle this situation
                        String errorMessage = getRealPathFromURI(uri);
                        showToast(errorMessage);
                    }
                } else {
                    // Handle case where URI is null, if needed
                    String errorMessage = "URI is null!";
                    showToast(errorMessage);
                }
            }
    );
    ActivityResultLauncher<Void> takePicture = registerForActivityResult(CameraResultContract.TAKE_PHOTO,
            result -> {
                if (result != null) {


                    File imageFilee = saveImageToFile(result);
                    imageFile = imageFilee;
                    // Display the saved image
                    loadImageIntoImageView(Uri.fromFile(imageFile));
                }
            }
    );






    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostAdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize RegisterApi
        PostAdApi = RetrofitClient.getRetrofitInstance().create(PostAdApi.class);

        // Set click listener for the registration button
        binding.PostAdBtn.setOnClickListener(this);
        binding.browseImgBtn.setOnClickListener(this);
        binding.captureBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        if (v.getId() == binding.PostAdBtn.getId()) {

            TitleEditText = binding.ADeditText;
            priceEditText = binding.PriceeditText;
            descEditText = binding.DescriptioneditText;
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String userResponseJson = sharedPreferences.getString("User", null);
            Gson gson = new Gson();
            UserResponse userResponse = gson.fromJson(userResponseJson, UserResponse.class);
            String userId= userResponse.get_id();
            Toast.makeText(PostAd.this, "UserId: " + userId, Toast.LENGTH_SHORT).show();

            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);

            MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", imageFile.getName(), fileRequestBody);


            Call<Void> call = PostAdApi.postAd(
                    TitleEditText.getText().toString(),
                    descEditText.getText().toString(),
                    Integer.parseInt(priceEditText.getText().toString()),
                    userId,
                    filePart
            );


            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Intent intent = new Intent(PostAd.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            } );

        } else if (v.getId() == binding.browseImgBtn.getId()) {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        }
        else if (v.getId() == binding.captureBtn.getId()) {
            // Launch camera to capture image
            // Ensure that you have appropriate permissions in your AndroidManifest.xml file
            takePicture.launch(null);
        }




    }
    private void loadImageIntoImageView(Bitmap bitmap) {
        // Display the captured image in your ImageView
        binding.imageView.setImageBitmap(bitmap);
    }
    private void loadImageIntoImageView(Uri uri) {
        Picasso.get()
                .load(uri)
                .into(binding.imageView);
    }


}