package com.wordpress.sreeharilive.foodapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.wordpress.sreeharilive.foodapp.model.FoodItem;
import com.wordpress.sreeharilive.foodapp.util.BadgeDrawable;

import java.util.ArrayList;


public class Cart{

    private ArrayList<FoodItem> cartList;

    private static Cart cart;

    private OnCartUpdateListener onCartUpdateListener;

    private Cart(){
        cartList = new ArrayList<>();
    }

    public ArrayList<FoodItem> getCartList(){
        return cartList;
    }

    public void updateCartList(ArrayList<FoodItem> newList){
        cartList = newList;
        onCartUpdateListener.onUpdate(cartList.size());
    }

    public void addToCart(FoodItem item){
        cartList.add(item);
        onCartUpdateListener.onUpdate(cartList.size());
    }

    public static Cart getInstance(){
        if(cart == null){
            cart = new Cart();
        }
        return cart;
    }

    public void setOnCartUpdateListener(OnCartUpdateListener onCartUpdateListener){
        this.onCartUpdateListener = onCartUpdateListener;
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
        onCartUpdateListener.onUpdate(cartList.size());
    }

    public boolean isEmpty() {
        return cartList.isEmpty();
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(String.valueOf(count));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public interface OnCartUpdateListener {
        void onUpdate(int count);
    }
}
