package com.my.sadebprovider;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.my.sadebprovider.receiver.NotifyUserReceiver;
import com.my.sadebprovider.util.Config;
import com.my.sadebprovider.util.NotificationUtils;
import com.my.sadebprovider.util.PushNotificationModel;
import com.my.sadebprovider.util.Utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    @Override
    public void onCreate() {
        LocalBroadcastManager.getInstance(this).registerReceiver(new NotifyUserReceiver(),
                new IntentFilter(Config.GET_DATA_NOTIFICATION));
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());

            PushNotificationModel pushNotificationModel =new PushNotificationModel();
            pushNotificationModel.setKey(remoteMessage.getNotification().getTitle());
            pushNotificationModel.setMessage(remoteMessage.getNotification().getBody());
            pushNotificationModel.setDate(getCurrentDate());
            handleNotification(pushNotificationModel);
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject jsonObject=new JSONObject(remoteMessage.getData());
                jsonObject.put("date", getCurrentDate());

                PushNotificationModel pushNotificationModel = new Gson().fromJson(String.valueOf(jsonObject), PushNotificationModel.class);

                Intent intent = new Intent(Config.GET_DATA_NOTIFICATION);
                intent.putExtra("pushNotificationModel",pushNotificationModel);

                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                handleDataMessage(pushNotificationModel);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }
    public static String getCurrentDate() {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(calendar.getTime());
        return date;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void handleNotification(PushNotificationModel pushNotificationModel){
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("pushNotificationModel",pushNotificationModel);

            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            // play notification sound
            showNotificationMessage(getApplicationContext(),pushNotificationModel);
        } else {
            showNotificationMessage(getApplicationContext(),pushNotificationModel);
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(PushNotificationModel pushNotificationModel){
        try {
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("pushNotificationModel",pushNotificationModel);

                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                showNotificationMessage(getApplicationContext(),pushNotificationModel);

            } else {
                showNotificationMessage(getApplicationContext(),pushNotificationModel);

            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessage(Context context,PushNotificationModel pushNotificationModel){
        notificationUtils = new NotificationUtils(context);
        notificationUtils.showNotificationMessage(pushNotificationModel);
    }

}

