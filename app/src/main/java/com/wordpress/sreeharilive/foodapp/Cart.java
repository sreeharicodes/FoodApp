package com.wordpress.sreeharilive.foodapp;

import com.wordpress.sreeharilive.foodapp.model.FoodItem;

import java.util.ArrayList;


public class Cart{

    private ArrayList<FoodItem> cartList;

    private static Cart cart;

    private Cart(){
        cartList = new ArrayList<>();
    }

    public ArrayList<FoodItem> getCartList(){
        return cartList;
    }

    public void updateCartList(ArrayList<FoodItem> newList){
        cartList = newList;
    }

    public void addToCart(FoodItem item){
        cartList.add(item);
    }

    public static Cart getInstance(){
        if(cart == null){
            cart = new Cart();
        }
        return cart;
    }

    public double getTotal(){
        double total = 0;
        for (FoodItem item : cartList){
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public void clearCart() {
        cart = new Cart();
    }
}
