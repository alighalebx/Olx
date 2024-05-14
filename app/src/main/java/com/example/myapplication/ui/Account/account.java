package com.example.myapplication.ui.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.AccountBinding;
import com.example.myapplication.ui.Login.Login;
import com.example.myapplication.ui.User.EditProfileActivity;
import com.example.myapplication.ui.UserListedItem.UserListedItems;
import com.example.myapplication.ui.WishList.WishListItems;


public class account extends Fragment implements View.OnClickListener {


    public AccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = AccountBinding.inflate(getLayoutInflater());
        binding.LogOutBtn.setOnClickListener(this);
        binding.ListedItemsBtn.setOnClickListener(this);
        binding.WishListBtn.setOnClickListener(this);
        binding.editbutton.setOnClickListener(this);
        return binding.getRoot();


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == binding.LogOutBtn.getId()) {
            Intent intent = new Intent(getContext(), Login.class);
            startActivity(intent);
        } else if (v.getId() == binding.ListedItemsBtn.getId()) {

            Intent intent = new Intent(getContext(), UserListedItems.class);
            startActivity(intent);

        } else if (v.getId() == binding.WishListBtn.getId()) {

            Intent intent = new Intent(getContext(), WishListItems.class);
            startActivity(intent);
        } else if (v.getId() == binding.editbutton.getId()) {
            // Handle editButton click
            // Navigate to EditProfileActivity
            Intent intent = new Intent(getContext(), EditProfileActivity.class);

            // Pass current user information to EditProfileActivity
            intent.putExtra("currentFullName", binding.editTextFullName.getText().toString());
            intent.putExtra("currentPhoneNumber", binding.editTextPhoneNumber.getText().toString());
            intent.putExtra("currentPassword", binding.editTextTextPassword.getText().toString());
            startActivity(intent);


        }
    }

}