package com.wordpress.sreeharilive.foodapp.activity;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wordpress.sreeharilive.foodapp.Cart;
import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.model.FoodItem;
import com.wordpress.sreeharilive.foodapp.util.Constants;

import java.util.ArrayList;

public class FoodDescriptionActivity extends AppCompatActivity implements Cart.OnCartUpdateListener {

    FoodItem item;
    Spinner qtySpinner;
    TextView itemNameTextView, itemDescTextView;
    ImageView itemImageView;

    Button addButton,removeButton,updateButton;

    boolean comingFromCart = false;

    ArrayList<FoodItem> items;
    int postion;

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_description);

        item = (FoodItem) getIntent().getSerializableExtra(Constants.SELECTED_ITEM_KEY);

        comingFromCart = getIntent().getBooleanExtra(Constants.FROM_CART_EXTRA_KEY,false);

        Cart.getInstance().setOnCartUpdateListener(this);

        qtySpinner = (Spinner) findViewById(R.id.foodQuantitySpinner);
        itemNameTextView = (TextView) findViewById(R.id.itemNameTextView);
        itemDescTextView = (TextView) findViewById(R.id.foodItemDescriptionTextView);
        itemImageView = (ImageView) findViewById(R.id.foodItemImageView);

        addButton = (Button) findViewById(R.id.addItemButton);
        removeButton = (Button) findViewById(R.id.removeItemButton);
        updateButton = (Button) findViewById(R.id.updateCartButton);

        itemNameTextView.setText(item.getName());
        itemDescTextView.setText(item.getDescription());

        Integer qtySpinnerArr[];
        if (item.getCount() > 10) {
            qtySpinnerArr = new Integer[10];
            for (int i = 0; i < 10; i++){
                qtySpinnerArr[i]=i+1;
            }
        }else {
            qtySpinnerArr = new Integer[item.getCount()];
            for (int i = 0; i < item.getCount(); i++){
                qtySpinnerArr[i]=i+1;
            }
        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,qtySpinnerArr);
        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        qtySpinner.setAdapter(arrayAdapter);

        Picasso.with(this).load(item.getImageUrl()).into(itemImageView);

        if (comingFromCart){
            addButton.setVisibility(View.GONE);
            removeButton.setVisibility(View.VISIBLE);
            qtySpinner.setSelection(item.getQuantity()-1);
            updateButton.setVisibility(View.VISIBLE);
            items = Cart.getInstance().getCartList();
            postion = getIntent().getIntExtra(Constants.SELECTED_ITEM_INDEX,-1);
        }else {
            removeButton.setVisibility(View.GONE);
            addButton.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.GONE);
        }



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

    public void removeItemFromCart(View view) {
        items.remove(postion);
        Cart.getInstance().updateCartList(items);
        finish();
    }

    public void updateCartItem(View view) {
        item.setQuantity((int)qtySpinner.getSelectedItem());
        items.remove(postion);
        items.add(postion,item);
        Cart.getInstance().updateCartList(items);
        finish();
    }

    @Override
    public void onUpdate(int count) {

        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        Cart.setBadgeCount(this, icon, count);
    }
}
