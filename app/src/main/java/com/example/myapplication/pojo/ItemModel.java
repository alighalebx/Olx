package com.example.myapplication.pojo;

public class ItemModel {
    int id;
    String name;
    float price;
    String description;
    int sellerId;

    public ItemModel(){
        this.id = 0;
        this.name = "";
        this.price = 0;
        this.description = "";
        this.sellerId = 0;
    }
    public ItemModel(int id, String name, float price, String description, int sellerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.sellerId = sellerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}
