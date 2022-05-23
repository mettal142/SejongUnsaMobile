package com.example.dikid.unsa_m;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseIIDService";

    public FirebaseMessagingService() {
    }

    @Override
    public void onNewToken(String s){
        super.onNewToken(s);
        Log.e("New_Token",s);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"From:"+remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            handleNow();
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Msg Notify Body : " + remoteMessage.getNotification().getBody());
        }
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("content");
        sendNotification(title,body);

    }
    private void scheduleJob(){

    }

    public void handleNow(){
        Log.d(TAG,"Short lived task is done.");
    }

    private void sendNotification(String title, String messageBody){

        if(title == null){
            title = "푸시알림";
        }
        Intent it = new Intent(this,MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,it,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"channel_ID")
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[] {1000,1000})
                .setLights(Color.BLUE,1,1)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());


    }

}
