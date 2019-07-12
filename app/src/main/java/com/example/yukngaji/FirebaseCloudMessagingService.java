package com.example.yukngaji;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.yukngaji.setting.UserPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseCloudMessagingService extends FirebaseMessagingService {
    public static String CHANNEL_ID ;
    public static CharSequence CHANNEL_NAME = "Sahabat mengaji";
    public static final int NOTIFICATION_ID = 1;
    UserPreference preference;
    public String TAG = "FIREBASE MESSAGING";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
                CHANNEL_ID=remoteMessage.getNotification().getChannelId();
                Intent intent = new Intent(getApplicationContext(), MenuUtama.class);
                notif(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"),intent);
                if (remoteMessage.getData().get("bayar")!=null) {
                    if (remoteMessage.getData().get("bayar").equals("true")) {
                        preference=new UserPreference(this);
                        preference.setCekBayar(true);
                        preference.setTunggu(true);
                        preference.setUidGuru(remoteMessage.getData().get("uidguru"));
                    }
                    else if (remoteMessage.getData().get("bayar").equals("false")) {
                        preference=new UserPreference(this);
                        preference.setTunggu(false);
                        preference.setCekBayar(false);
                    }
                    else {

                    }
                }
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Intent intent = new Intent(getApplicationContext(), NotifikasiActivity.class);
            notif(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),intent);
        }
    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference= database.getReference();
        if(preference.getGuru()){
            getReference.child("Guru").child(preference.getUid()).child("token").setValue(s);
        }
        else {
            getReference.child("Murid").child(preference.getUid()).child("token").setValue(s);
        }
    }
    public void notif(String tittle,String Body,Intent intent){
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
