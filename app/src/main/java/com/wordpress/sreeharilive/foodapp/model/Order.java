package com.wordpress.sreeharilive.foodapp.model;


import android.content.Context;

import com.wordpress.sreeharilive.foodapp.Cart;


public class Order {

    private Context context;
    private String locality;
    private String address;
    private String modeOfPayment;
    private Cart cart;
    private String uid;
    private OnOrderCompleteListener onOrderCompleteListener;

    public interface OnOrderCompleteListener {
        void onComplete();
        void onError();
    }

    private Order(Context context) {
        this.context = context;
    }

    private void setOnOrderCompleteListener(OnOrderCompleteListener onOrderCompleteListener) {
        this.onOrderCompleteListener = onOrderCompleteListener;
    }

    private void setLocality(String locality) {
        this.locality = locality;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    private void setCart(Cart cart) {
        this.cart = cart;
    }

    private void setUid(String uid) {
        this.uid = uid;
    }

    public void place() {

    }

    public static class OrderBuilder {

        Order order;

        public OrderBuilder(Context context) {
            order = new Order(context);
        }

        public OrderBuilder setLocality(String locality) {
            order.setLocality(locality);
            return this;
        }

        public OrderBuilder setAddress(String address) {
            order.setAddress(address);
            return this;
        }

        public OrderBuilder setCart(Cart cart) {
            order.setCart(cart);
            return this;
        }

        public OrderBuilder setModeOfPayment(String modeOfPayment) {
            order.setModeOfPayment(modeOfPayment);
            return this;
        }


        public OrderBuilder setUserID(String uid) {
            order.setUid(uid);
            return this;
        }

        public Order build() {
            return order;
        }

        public OrderBuilder setOrderCompleteListener(OnOrderCompleteListener onOrderCompleteListener) {
            order.setOnOrderCompleteListener(onOrderCompleteListener);
            return this;
        }
    }
}
