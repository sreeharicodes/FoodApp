package com.wordpress.sreeharilive.foodapp;

import android.app.Application;

import com.onesignal.OneSignal;

public class FoodApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).init();
    }
}
