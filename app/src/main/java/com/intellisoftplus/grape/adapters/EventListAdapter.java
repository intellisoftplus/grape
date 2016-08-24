package com.intellisoftplus.grape.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.Adapter;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.intellisoftplus.grape.db.contracts.EventContract.EventEntry;
import com.intellisoftplus.grape.db.operations.ReadEvents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by cndet on 24/08/2016.
 */
public class EventListAdapter {

    private List<HashMap<String,String>> eventList = new ArrayList<>();
    private Context context;
    private  ReadEvents task;

    public  EventListAdapter(Context c){
        this.task = new ReadEvents(c);
        this.context = c;
        try{
            this.eventList = task.execute().get();
        } catch (InterruptedException|ExecutionException e){
            e.printStackTrace();
        }
    }
    
    public List<CalendarEvent> getEvents(){
        List<CalendarEvent> calEvents = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        Calendar startTime = GregorianCalendar.getInstance();
        Calendar endTime = (Calendar)startTime.clone();
        for (HashMap<String,String> current: eventList) {
            try {
                startTime.setTime(sdf.parse(current.get("DTSTART")));
                endTime.setTime(sdf.parse(current.get("DTEND")));


                calEvents.add(new BaseCalendarEvent(
                        current.get("TITLE"), current.get("DESCRIPTION"),
                        current.get("LOCATION"), ContextCompat.getColor(context, android.R.color.black),
                        startTime, endTime,
                        Boolean.parseBoolean(current.get("ALLDAY"))
                    )
                );
            } catch (ParseException e){
                e.printStackTrace();
            }

        }

        return calEvents;
    }

//    public EventListAdapter(long id, int color, String title, String description, String location, long dateStart, long dateEnd, int allDay, String duration) {
//        super(id, color, title, description, location, dateStart, dateEnd, allDay, duration);

//        Calendar calendar = GregorianCalendar.getInstance();
//        calendar.set(year,month,day,hour,min);
//        Calendar start = (Calendar)calendar.clone();
//        start.set(Calendar.YEAR, 2016);
//        Calendar end = Calendar.getInstance();
//
//        BaseCalendarEvent event = new BaseCalendarEvent(
//                cursor.getString(0), cursor.getString(1),
//                cursor.getString(4), android.R.color.black,
//                start,
//                end,
//                (cursor.getInt(5) == 1)
//        );

//    }
}
