package com.wordpress.sreeharilive.foodapp.model;

/***
 * Created by negibabu on 3/18/17.
 */

public class FoodItem {
    private String name,category;
    private double price;

    public FoodItem(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
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
