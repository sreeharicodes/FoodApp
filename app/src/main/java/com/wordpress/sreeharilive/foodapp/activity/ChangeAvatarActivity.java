package com.wordpress.sreeharilive.foodapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wordpress.sreeharilive.foodapp.R;

public class ChangeAvatarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);
        getSupportActionBar().setTitle("Choose Your Avatar");
    }

    public void chooseAvatar(View view) {
        switch (view.getId()){
            case R.id.avatarOne:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",1).apply();
                break;
            case R.id.avatarTwo:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",2).apply();
                break;
            case R.id.avatarThree:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",3).apply();
                break;
            case R.id.avatarFour:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",4).apply();
                break;
            case R.id.avatarFive:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",5).apply();
                break;
            case R.id.avatarSix:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",6).apply();
                break;
            case R.id.avatarSeven:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",7).apply();
                break;
            case R.id.avatarEight:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",8).apply();
                break;
            case R.id.avatarNine:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",9).apply();
                break;
            case R.id.avatarTen:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",10).apply();
                break;
            case R.id.avatarEleven:
                getSharedPreferences("prefs",MODE_PRIVATE).edit().putInt("AVATAR",11).apply();
                break;
        }
        finish();
    }
}
