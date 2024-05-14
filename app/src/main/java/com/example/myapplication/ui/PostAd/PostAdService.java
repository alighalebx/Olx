package com.example.myapplication.ui.PostAd;

import java.io.File;

public class PostAdService {

    private String description;
    private String title;
    private int price;
    private String userId;
    private File image;

    public PostAdService(String description, String title, int price, String userId, File image) {
        this.description = description;
        this.title = title;
        this.price = price;
        this.userId = userId;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
