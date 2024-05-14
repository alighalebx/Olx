package com.example.myapplication.ui.PostAd;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
public class CameraResultContract {

    public static final ActivityResultContract<Void, Bitmap> TAKE_PHOTO =
            new ActivityResultContract<Void, Bitmap>() {
                @NonNull
                @Override
                public Intent createIntent(@NonNull Context context,
                                           Void input) {
                    return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                }
                @Override
                public Bitmap parseResult(int resultCode,
                                          @Nullable Intent intent) {
                    if (resultCode != RESULT_OK || intent == null) {
                        return null;
                    }

                    Bundle extras = intent.getExtras();
                    return (Bitmap) (extras != null ? extras.get("data") : null);
                }
            };
}
