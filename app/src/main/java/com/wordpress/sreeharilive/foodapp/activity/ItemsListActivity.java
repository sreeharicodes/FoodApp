package com.wordpress.sreeharilive.foodapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wordpress.sreeharilive.foodapp.Cart;
import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.adapter.FoodItemsListAdapter;
import com.wordpress.sreeharilive.foodapp.model.FoodItem;
import com.wordpress.sreeharilive.foodapp.util.Constants;

import java.util.ArrayList;

public class ItemsListActivity extends AppCompatActivity implements Cart.OnCartUpdateListener{

    String selectedCategory;

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        selectedCategory = getIntent().getStringExtra(Constants.CATEGORY_INTENT_KEY);

        getSupportActionBar().setTitle(selectedCategory);

        Cart.getInstance().setOnCartUpdateListener(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("items").child(selectedCategory).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FoodItem> foodItems = new ArrayList<>();
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for(DataSnapshot child : iterable){
                    foodItems.add(
                            new FoodItem(
                                    child.getKey(),
                                    child.child("name").getValue().toString(),
                                    selectedCategory,
                                    Double.parseDouble(child.child("price").getValue().toString()),
                                    child.child("imageurl").getValue().toString(),
                                    child.child("description").getValue().toString(),
                                    Integer.parseInt(child.child("count").getValue().toString())
                            )
                    );
                    Log.d(child.getKey(),child.getValue().toString());
                }
                recyclerView.setAdapter(new FoodItemsListAdapter(ItemsListActivity.this,foodItems));
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            onUpdate(Cart.getInstance().getCartList().size());
        }catch (NullPointerException ignored){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu,menu);
        this.menu = menu;
        onUpdate(Cart.getInstance().getCartList().size());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_cart:
                if (!Cart.getInstance().isEmpty())
                    startActivity(new Intent(this,CartActivity.class));
                else
                    Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    @Override
    public void onUpdate(int count) {

        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        Cart.setBadgeCount(this, icon, count);
    }
}
