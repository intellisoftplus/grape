package com.intellisoftplus.grape;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by cndet on 25/08/2016.
 */
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
           Notification notification =
                new Notification(
                        context,LeadsActivity.class,intent.getStringExtra("title"),
                        intent.getStringExtra("message"),intent.getIntExtra("notificationId",0),
                        android.R.drawable.ic_input_add
                   );
            notification.init();
    }
}
