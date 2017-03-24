package com.wordpress.sreeharilive.foodapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.adapter.FoodItemsListAdapter;
import com.wordpress.sreeharilive.foodapp.model.FoodItem;

import java.util.ArrayList;

public class ItemsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("items").child("NonVeg").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FoodItem> foodItems = new ArrayList<>();
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for(DataSnapshot child : iterable){
                    foodItems.add(
                            new FoodItem(
                                    child.child("name").getValue().toString(),
                                    "Non Veg",
                                    Double.parseDouble(child.child("price").getValue().toString())
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
}
