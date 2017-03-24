package com.wordpress.sreeharilive.foodapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wordpress.sreeharilive.foodapp.Cart;
import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.adapter.CartItemsListAdapter;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        cartRecyclerView.setLayoutManager(layoutManager);

        CartItemsListAdapter adapter = new CartItemsListAdapter(this, Cart.getInstance().getCartList());
        cartRecyclerView.setAdapter(adapter);
    }
}
