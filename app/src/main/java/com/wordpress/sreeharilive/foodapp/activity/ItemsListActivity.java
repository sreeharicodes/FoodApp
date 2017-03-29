package com.wordpress.sreeharilive.foodapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.adapter.FoodItemsListAdapter;
import com.wordpress.sreeharilive.foodapp.model.FoodItem;
import com.wordpress.sreeharilive.foodapp.util.Constants;

import java.util.ArrayList;

public class ItemsListActivity extends AppCompatActivity {

    String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        selectedCategory = getIntent().getStringExtra(Constants.CATEGORY_INTENT_KEY);



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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_cart:
                startActivity(new Intent(this,CartActivity.class));
                break;
        }

        return true;
    }
}
