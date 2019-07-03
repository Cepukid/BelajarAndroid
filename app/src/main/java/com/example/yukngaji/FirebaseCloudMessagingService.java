package com.example.yukngaji;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseCloudMessagingService extends FirebaseMessagingService {
    public static String CHANNEL_ID ;
    public static CharSequence CHANNEL_NAME = "Sahabat mengaji";
    public static final int NOTIFICATION_ID = 1;

    public String TAG = "FIREBASE MESSAGING";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        notif(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                CHANNEL_ID=remoteMessage.getNotification().getChannelId();
                notif(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
                //startActivity(new Intent(this, NotifikasiActivity.class));
            } else {
                // Handle message within 10 seconds
                notif(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
                //startActivity(new Intent(this, NotifikasiActivity.class));
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            notif(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
            //startActivity(new Intent(this, NotifikasiActivity.class));
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        startActivity(new Intent(this, NotifikasiActivity.class));
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            startActivity(new Intent(this, NotifikasiActivity.class));
//
//        }
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
//
//    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
    public void notif(String tittle,String Body){
        Intent intent = new Intent(getApplicationContext(), MenuUtama.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setContentTitle(tittle)
                .setContentText(Body)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
