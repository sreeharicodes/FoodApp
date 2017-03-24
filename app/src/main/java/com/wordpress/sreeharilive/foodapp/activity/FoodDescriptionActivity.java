package com.wordpress.sreeharilive.foodapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wordpress.sreeharilive.foodapp.Cart;
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
        itemNameTextView = (TextView) findViewById(R.id.itemNameTextView);
        itemDescTextView = (TextView) findViewById(R.id.foodItemDescriptionTextView);
        itemImageView = (ImageView) findViewById(R.id.foodItemImageView);

        itemNameTextView.setText(item.getName());
        itemDescTextView.setText(item.getDescription());

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,new Integer[]{1,2,3,4,5,6,7,8,9,10});
        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        qtySpinner.setAdapter(arrayAdapter);

        Picasso.with(this).load(item.getImageUrl()).into(itemImageView);



    }

    public void addItemToCart(View view) {
        int qty = (int) qtySpinner.getSelectedItem();
        item.setQuantity(qty);
        Cart.getInstance().addToCart(item);
        finish();
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
