package com.wordpress.sreeharilive.foodapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.wordpress.sreeharilive.foodapp.Cart;
import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.util.Constants;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView profileImageView;
    TextView emailIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        profileImageView = (ImageView) findViewById(R.id.avatarImageView);
        emailIdTextView = (TextView) findViewById(R.id.emailIdTextView);
        emailIdTextView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        setAvatar();

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChangeAvatarActivity.class));
            }
        });


    }

    private void setAvatar() {
        switch (getSelectedAvatar()){
            case 1:
                profileImageView.setImageResource(R.drawable.avatar_1);
                break;
            case 2:
                profileImageView.setImageResource(R.drawable.avatar_2);
                break;
            case 3:
                profileImageView.setImageResource(R.drawable.avatar_3);
                break;
            case 4:
                profileImageView.setImageResource(R.drawable.avatar_4);
                break;
            case 5:
                profileImageView.setImageResource(R.drawable.avatar_5);
                break;
            case 6:
                profileImageView.setImageResource(R.drawable.avatar_6);
                break;
            case 7:
                profileImageView.setImageResource(R.drawable.avatar_7);
                break;
            case 8:
                profileImageView.setImageResource(R.drawable.avatar_8);
                break;
            case 9:
                profileImageView.setImageResource(R.drawable.avatar_9);
                break;
            case 10:
                profileImageView.setImageResource(R.drawable.avatar_10);
                break;
            case 11:
                profileImageView.setImageResource(R.drawable.avatar_11);
                break;
        }
    }

    private int getSelectedAvatar() {
        return getSharedPreferences("prefs",MODE_PRIVATE).getInt("AVATAR",new Random().nextInt(11));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAvatar();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
                return true;
            default:
                return false;
        }
    }

    public void launchMenu(View view) {
        Intent intent = new Intent(this,ItemsListActivity.class);
        switch (view.getId()){
            case R.id.vegImageView:
                intent.putExtra(Constants.CATEGORY_INTENT_KEY, Constants.VEG);
                break;
            case R.id.nonVegImageView:
                intent.putExtra(Constants.CATEGORY_INTENT_KEY,Constants.NON_VEG);
                break;
            case R.id.chineseImageView:
                intent.putExtra(Constants.CATEGORY_INTENT_KEY,Constants.CHINESE);
                break;
            case R.id.dessertsImageView:
                intent.putExtra(Constants.CATEGORY_INTENT_KEY,Constants.DESSERT);
                break;
        }
        startActivity(intent);

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
                if (!Cart.getInstance().isEmpty())
                    startActivity(new Intent(this,CartActivity.class));
                else
                    Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    public void launchHistory(View view) {
        startActivity(new Intent(this,HistoryActivity.class));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
