package com.wordpress.sreeharilive.foodapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wordpress.sreeharilive.foodapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchMenu(View view) {
        Intent intent = new Intent(this,ItemsListActivity.class);
        switch (view.getId()){
            case R.id.vegImageView:
                intent.putExtra("CATEGORY","VEG");
                break;
            case R.id.nonVegImageView:
                intent.putExtra("CATEGORY","NON_VEG");
                break;
            case R.id.chineseImageView:
                intent.putExtra("CATEGORY","CHINESE");
                break;
            case R.id.dessertsImageView:
                intent.putExtra("CATEGORY","DESSERT");
                break;
        }
        startActivity(intent);

    }
}
