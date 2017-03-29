package com.wordpress.sreeharilive.foodapp.model;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wordpress.sreeharilive.foodapp.Cart;
import com.wordpress.sreeharilive.foodapp.util.RandomIdGenerator;


public class Order {

    private String locality = "";
    private String address = "";
    private String modeOfPayment = "";
    private Cart cart;
    private String uid = "";
    private OnOrderCompleteListener onOrderCompleteListener;
    private String orderId;

    public interface OnOrderCompleteListener {
        void onComplete();
        void onError(OrderException e);
    }

    private Order() {
        this.orderId = RandomIdGenerator.newId();
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
        if (uid.isEmpty()){
            onOrderCompleteListener.onError(new OrderException("Invalid UID"));
            return;
        }
        if (modeOfPayment.isEmpty()){
            onOrderCompleteListener.onError(new OrderException("Invalid Payment Mode"));
            return;
        }
        if (address.isEmpty()){
            onOrderCompleteListener.onError(new OrderException("Address Empty"));
            return;
        }
        if (locality.isEmpty()){
            onOrderCompleteListener.onError(new OrderException("Locality Empty"));
            return;
        }
        if (cart == null){
            onOrderCompleteListener.onError(new OrderException("Cart is Empty"));
            return;
        }
        DatabaseReference database = FirebaseDatabase.getInstance().getReference()
                .child("orders")
                .child(orderId);
        database.child("userId").setValue(uid);
        database.child("locality").setValue(locality);
        database.child("address").setValue(address);
        database.child("mode_of_payment").setValue(modeOfPayment);
        DatabaseReference ordersList = database.child("order");
        for (final FoodItem item : cart.getCartList()){
            DatabaseReference thisItem = ordersList.child(RandomIdGenerator.newId());
            thisItem.child("item").setValue(item.getName());
            thisItem.child("category").setValue(item.getCategory());
            thisItem.child("quantity").setValue(item.getQuantity());
            FirebaseDatabase.getInstance().getReference().child("items")
                    .child(item.getCategory())
                    .child(item.getFid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseDatabase.getInstance().getReference().child("items")
                            .child(item.getCategory())
                            .child(item.getFid())
                            .child("count")
                            .setValue(((int)dataSnapshot.getValue()) - 1);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        ordersList.child("total").setValue(cart.getTotal());
        onOrderCompleteListener.onComplete();
    }

    public static class OrderBuilder {

        Order order;

        public OrderBuilder() {
            order = new Order();
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

    public class OrderException {
        private String message;
        OrderException(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
