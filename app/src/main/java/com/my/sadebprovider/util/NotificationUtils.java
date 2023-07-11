package com.my.sadebprovider.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;


import com.my.sadebprovider.MainActivity;
import com.my.sadebprovider.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();
    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void showNotificationMessage(PushNotificationModel pushNotificationModel) {
        // Check for empty push message
//        if (TextUtils.isEmpty(pushNotificationModel.getJob_id()))
//            return;
//        try {
            Intent intent;
//            if (pushNotificationModel.getType().equals("chat")) {
                intent = new Intent(mContext, MainActivity.class);
//            } else {
//                intent = new Intent(mContext, NotificationActivity.class);
//            }
            intent.putExtra("pushNotificationModel", pushNotificationModel);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            final PendingIntent resultPendingIntent = PendingIntent.getActivity(
                    mContext,
                    0,
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT
            );

            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    mContext);

            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification_tone");
//
////            if (!TextUtils.isEmpty(pushNotificationModel.getImage())) {
//            if(isImageAvailable(pushNotificationModel.getImage())){
//
//                if (pushNotificationModel.getImage() != null && pushNotificationModel.getImage().length() > 4 && Patterns.WEB_URL.matcher(pushNotificationModel.getImage()).matches()) {
//
////                    Bitmap bitmap = getBitmapFromURL(pushNotificationModel.getImage());
////
////                    if (bitmap != null&&!bitmap.equals("")) {
//                        //showBigNotification(/*bitmap,*/ mBuilder, pushNotificationModel, resultPendingIntent, alarmSound);
//                        showSmallNotification(mBuilder, pushNotificationModel, resultPendingIntent, alarmSound);
//
//                    } else {
//                        showSmallNotification(mBuilder, pushNotificationModel, resultPendingIntent, alarmSound);
////                    }
//                }
//            } else {
                showSmallNotification(mBuilder, pushNotificationModel, resultPendingIntent, alarmSound);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, PushNotificationModel pushNotificationModel, PendingIntent resultPendingIntent, Uri alarmSound) {

        String imageUrl ="";// pushNotificationModel.getImage();
//        int icon=R.drawable.ic_launcher_background;
        String title = pushNotificationModel.getProvider_name();//pushNotificationModel.getKey().equals("")?mContext.getString(R.string.app_name):pushNotificationModel.getKey();
        String message = pushNotificationModel.getKey();//pushNotificationModel.getMessage();

//        playNotificationSound();
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        inboxStyle.addLine(message);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(message);
        final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.ic_launcher_background/*icon*/).setTicker(title).setWhen(0)
                .setContentTitle(title)
                .setOnlyAlertOnce(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
//                .setStyle(inboxStyle)
                .setStyle(bigTextStyle)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setWhen(getTimeMilliSec(pushNotificationModel.getDate()))//timeStamp))
                .setContentIntent(resultPendingIntent)
//                .addAction(android.R.drawable.ic_delete, "Ignore", resultPendingIntent)
//                .addAction(android.R.drawable.ic_media_next, "Agree", resultPendingIntent)
                .setLargeIcon(getBitmapFromURL(imageUrl))//BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(message);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    private void showBigNotification(/*Bitmap bitmap,*/ NotificationCompat.Builder mBuilder, PushNotificationModel pushNotificationModel, PendingIntent resultPendingIntent, Uri alarmSound) {
        String imageUrl = "";//pushNotificationModel.getImage();
//        int icon=R.drawable.ic_launcher_background;
        String title = pushNotificationModel.getKey().equals("")?mContext.getString(R.string.app_name):pushNotificationModel.getKey();
        String message = pushNotificationModel.getMessage();

//        playNotificationSound();
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(imageUrl));//bitmap);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.drawable.ic_launcher_background).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSound(alarmSound)
                .setWhen(getTimeMilliSec(pushNotificationModel.getDate()))//timeStamp))
                .setStyle(bigPictureStyle)
//                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher_background))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification);
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isImageAvailable(String image){
        if(image.equals("null")){
            return false;
        }else if(image.equals("")){
            return false;
        }else {
            return true;
        }
    }
}
