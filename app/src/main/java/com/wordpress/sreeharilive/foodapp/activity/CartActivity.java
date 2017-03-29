package com.wordpress.sreeharilive.foodapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wordpress.sreeharilive.foodapp.Cart;
import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.adapter.CartItemsListAdapter;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecyclerView;
    CartItemsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        cartRecyclerView.setLayoutManager(layoutManager);

        adapter = new CartItemsListAdapter(this, Cart.getInstance().getCartList());
        cartRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void checkOut(View view) {
        Toast.makeText(this, "Total = " + Cart.getInstance().getTotal(), Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, CheckoutActivity.class));

        finish();
    }
}
