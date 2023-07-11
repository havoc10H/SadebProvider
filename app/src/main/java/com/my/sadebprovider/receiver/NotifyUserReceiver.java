package com.my.sadebprovider.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.my.sadebprovider.util.Config;
import com.my.sadebprovider.util.PushNotificationModel;


public class NotifyUserReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Config.GET_DATA_NOTIFICATION)) {
            PushNotificationModel pushNotificationModel = (PushNotificationModel) intent.getSerializableExtra("pushNotificationModel");
        }
    }
}
