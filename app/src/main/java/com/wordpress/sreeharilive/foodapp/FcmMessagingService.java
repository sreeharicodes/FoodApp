package com.wordpress.sreeharilive.foodapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wordpress.sreeharilive.foodapp.activity.MainActivity;

/**
 * Created by negibabu on 3/26/17.
 */

public class FcmMessagingService extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        if(remoteMessage.getData().size()>0){
            String title,message,img_url;

            title=remoteMessage.getData().get("title");
            message=remoteMessage.getData().get("message");
            img_url=remoteMessage.getData().get("img_url");

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle(title);
            builder.setContentText(message);
            builder.setContentIntent(pendingIntent);
            builder.setSound(sounduri);
            

        }



    }

}
