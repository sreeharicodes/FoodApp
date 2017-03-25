package com.wordpress.sreeharilive.foodapp;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by negibabu on 3/26/17.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh(){
        String fcm_token = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM_TOKEN",fcm_token);

    }

}
