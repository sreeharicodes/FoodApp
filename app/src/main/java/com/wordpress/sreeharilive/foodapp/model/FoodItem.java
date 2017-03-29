package com.wordpress.sreeharilive.foodapp.model;

import java.io.Serializable;

/***
 * Created by negibabu on 3/18/17.
 */

public class FoodItem implements Serializable{
    private String name,category,imageUrl, description,fid;
    private double price;
    private int quantity = 0, count;


    public FoodItem(String fid, String name, String category, double price, String imageUrl, String description, int count) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.fid = fid;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
