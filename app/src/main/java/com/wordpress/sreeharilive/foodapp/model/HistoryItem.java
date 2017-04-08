package com.wordpress.sreeharilive.foodapp.model;


import java.util.ArrayList;

public class HistoryItem {
    private String date;
    private long orderTimeStamp;
    private long deliveryTimeStamp;
    private String mode;
    private double total;

    private ArrayList<FoodItem> items;

    public HistoryItem(String date, long orderTimeStamp, long deliveryTimeStamp, String mode, double total, ArrayList<FoodItem> items) {
        this.date = date;
        this.orderTimeStamp = orderTimeStamp;
        this.deliveryTimeStamp = deliveryTimeStamp;
        this.mode = mode;
        this.total = total;
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public long getOrderTimeStamp() {
        return orderTimeStamp;
    }

    public long getDeliveryTimeStamp() {
        return deliveryTimeStamp;
    }

    public String getMode() {
        return mode;
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<FoodItem> getItems() {
        return items;
    }
}
