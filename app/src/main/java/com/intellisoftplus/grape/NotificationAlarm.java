package com.intellisoftplus.grape;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by cndet on 31/08/2016.
 */
public class NotificationAlarm {
    private Context context;
    private String title, description, eventType;
    private long time;
    private long eventId;
    private Class aClass;
    public NotificationAlarm(
            Context c, long time,
            String title, String description,
            String eventType, long eventId,
            Class aClass){
        this.context=c;
        this.title=title;
        this.description=description;
        this.eventType=eventType;
        this.eventId=eventId;
        this.time=time;
        this.aClass=aClass;
    }

    public void init(){
        AlarmManager alarm  = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("com.intellisoftplus.ALARM_ACTION");
        intent.putExtra("title", title);
        intent.putExtra("message", description);
        intent.putExtra("notificationId", eventId);
        intent.putExtra("class", aClass);
        intent.putExtra("eventType", eventType);
        String intentID;
        switch (eventType){
            case "appointment":
                intentID = "1"+eventId;
                break;
            case "call":
                intentID = "2"+eventId;
                break;
            default:
                intentID = "0";
                break;
        }
        // Context, Unique intent ID, Intent, Flags
        PendingIntent operation = PendingIntent.getBroadcast(context, Integer.parseInt(intentID), intent, 0);
        alarm.set(
                AlarmManager.RTC_WAKEUP,
                time,
                operation
        );
    }
}
