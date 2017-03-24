package com.wordpress.sreeharilive.foodapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.model.FoodItem;
import com.wordpress.sreeharilive.foodapp.util.Constants;

public class FoodDescriptionActivity extends AppCompatActivity {

    FoodItem item;
    Spinner qtySpinner;
    TextView itemNameTextView, itemDescTextView;
    ImageView itemImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_description);

        item = (FoodItem) getIntent().getSerializableExtra(Constants.SELECTED_ITEM_KEY);

        qtySpinner = (Spinner) findViewById(R.id.foodQuantitySpinner);


    }

    public void addItemToCart(View view) {

    }
}
