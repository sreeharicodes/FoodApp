package com.wordpress.sreeharilive.foodapp.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.adapter.HistoryListAdapter;
import com.wordpress.sreeharilive.foodapp.model.FoodItem;
import com.wordpress.sreeharilive.foodapp.model.HistoryItem;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.historyList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching pending orders...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference().child("orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final ArrayList<HistoryItem> items = new ArrayList<>();
                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            if (child.child("userId").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                String mode = child.child("mode_of_payment").getValue().toString();
                                long timeOfOrder = Long.parseLong(
                                        child.child("timestamp").getValue().toString()
                                );
                                double total = Double.parseDouble(
                                        child.child("order").child("total").getValue().toString()
                                );
                                ArrayList<FoodItem> foodItems = new ArrayList<>();
                                for (DataSnapshot orderItems : child.child("order").getChildren()){
                                    if (!orderItems.getKey().equals("total")){
                                        String category = orderItems.child("category").getValue().toString();
                                        String fid = orderItems.child("fid").getValue().toString();
                                        String name = orderItems.child("item").getValue().toString();
                                        int qty = Integer.parseInt(
                                                orderItems.child("quantity").getValue().toString()
                                        );
                                        foodItems.add(new FoodItem(fid,name,category,qty));
                                    }
                                }
                                items.add(new HistoryItem(timeOfOrder,0,mode,total,foodItems));
                            }
                        }
                        progressDialog.setMessage("Fetching delivered orders...");
                        FirebaseDatabase.getInstance().getReference().child("processed_orders")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            for (DataSnapshot child : dataSnapshot.getChildren()){
                                                if (child.child("userId").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                                    String mode = child.child("mode_of_payment").getValue().toString();
                                                    long timeOfOrder = Long.parseLong(
                                                            child.child("ordered_at").getValue().toString()
                                                    );
                                                    long timeOfDelivery = Long.parseLong(
                                                            child.child("delivered_at").getValue().toString()
                                                    );
                                                    double total = Double.parseDouble(
                                                            child.child("order").child("total").getValue().toString()
                                                    );
                                                    ArrayList<FoodItem> foodItems = new ArrayList<>();
                                                    for (DataSnapshot orderItems : child.child("order").getChildren()){
                                                        if (!orderItems.getKey().equals("total")){
                                                            String category = orderItems.child("category").getValue().toString();
                                                            String fid = orderItems.child("fid").getValue().toString();
                                                            String name = orderItems.child("item").getValue().toString();
                                                            int qty = Integer.parseInt(
                                                                    orderItems.child("quantity").getValue().toString()
                                                            );
                                                            foodItems.add(new FoodItem(fid,name,category,qty));
                                                        }
                                                    }
                                                    items.add(new HistoryItem(timeOfOrder,timeOfDelivery,mode,total,foodItems));
                                                }
                                            }
                                        }catch (NullPointerException ignored){}
                                        HistoryListAdapter adapter = new HistoryListAdapter(HistoryActivity.this,items);
                                        recyclerView.swapAdapter(adapter,true);
                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(HistoryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(HistoryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });

    }
}
