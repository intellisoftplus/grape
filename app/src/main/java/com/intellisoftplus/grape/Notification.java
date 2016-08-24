package com.intellisoftplus.grape;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by cndet on 24/08/2016.
 * 
 * Usage example
 *
 *   Notification notification =
 *        new Notification(
 *          context,LeadsActivity.class,"Deal with it",
 *           "Because... Damn it!!",notificationId,android.R.drawable.btn_minus
 *   );
 *   notification.init();
 */
public class Notification {
    private Class activity;
    private Context context;
    private int drawable, notificationId;
    private String title, message;
    public Notification(Context context, Class activity, String title, String message, int notificationId, int drawable){
        this.context = context;
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.drawable = drawable;
        this.notificationId = notificationId;
    }

    public void init(){
        // Builds notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(drawable)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true);
        // Creates intent
        Intent intent = new Intent(context, activity);
        // Creates artificial task back stack. Navigating back takes one to the ome screen
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder
                .addParentStack(activity)
                .addNextIntent(intent);
        // Set intent to occur on notification click
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationBuilder.build());

    }
}
