package com.example.myapplication.ui.User;

import java.util.List;

public class UserResponse {
    private String _id;
    private String username;
    private String password;
    private String email;
    private String phoneNo;
    private List<String> listedProducts;
    private List<String> wishlist;

    // Getters and setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public List<String> getListedProducts() {
        return listedProducts;
    }

    public void setListedProducts(List<String> listedProducts) {
        this.listedProducts = listedProducts;
    }

    public List<String> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<String> wishlist) {
        this.wishlist = wishlist;
    }
}
