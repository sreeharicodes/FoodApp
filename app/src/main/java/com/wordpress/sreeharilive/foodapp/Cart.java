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

    public void addToCart(FoodItem item){
        cartList.add(item);
    }

    public static Cart getInstance(){
        if(cart == null){
            cart = new Cart();
        }
        return cart;
    }

}
