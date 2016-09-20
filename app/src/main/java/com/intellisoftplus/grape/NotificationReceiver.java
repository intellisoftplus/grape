package com.intellisoftplus.grape;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by cndet on 25/08/2016.
 */
public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver(){}
    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification =
            new Notification(
                    context,(Class)intent.getExtras().get("class"),intent.getStringExtra("title"),
                    intent.getStringExtra("message"), intent.getStringExtra("eventType"),
                    intent.getLongExtra("notificationId",0),android.R.drawable.ic_input_add
               );
        notification.init();
    }
}
