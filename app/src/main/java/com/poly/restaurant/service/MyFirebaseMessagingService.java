package com.poly.restaurant.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.poly.restaurant.R;
import com.poly.restaurant.ui.activities.splash.SplashActivity;
import com.poly.restaurant.utils.Constants;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {

        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (message.getNotification() != null) {
            String title = message.getNotification().getTitle();
            String idBill = message.getData().get("idBill");
            String idStaff = message.getData().get("idStaff");
            sendNotification(title, message.getNotification().getBody());
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getBaseContext());
            Intent intent = new Intent(Constants.REQUEST_TO_ACTIVITY);
            intent.putExtra(Constants.EXTRA_ID_BILL_TO_TABLE_DETAIL, idBill);
            intent.putExtra(Constants.EXTRA_ID_STAFF_TO_TABLE_DETAIL, idStaff);
            broadcastManager.sendBroadcast(intent);
        }
        super.onMessageReceived(message);
    }

    public void sendNotification(String title, String message) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.logo_app)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setSound(Uri.parse(R.raw.notification + ""))
                .build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(111, notification);
    }
}
