package com.intellisoftplus.grape.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
public class EventListAdapter extends AsyncTask<Object, Void, List<CalendarEvent>> {
    private List<HashMap<String,String>> eventList = new ArrayList<>();
    private Context context;

    public  EventListAdapter(Context c){
        ReadEvents task = new ReadEvents(c);
        this.context = c;
        try{
            this.eventList = task.execute().get();
        } catch (InterruptedException|ExecutionException e){
            e.printStackTrace();
        }
    }

    @Override
    protected List<CalendarEvent> doInBackground(Object[] objects) {
        List<CalendarEvent> calEvents = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        try {
            for (HashMap<String,String> current: eventList) {
                Calendar startTime = GregorianCalendar.getInstance();
                Calendar endTime = GregorianCalendar.getInstance();
                startTime.setTime(sdf.parse(current.get("DTSTART")));
                endTime.setTime(sdf.parse(current.get("DTEND")));

                BaseCalendarEvent currentEvent = new BaseCalendarEvent(
                        current.get("TITLE"), current.get("DESCRIPTION"),
                        current.get("LOCATION"), ContextCompat.getColor(context, android.R.color.black),
                        startTime, endTime,
                        Boolean.parseBoolean(current.get("ALLDAY"))
                );
                calEvents.add(currentEvent);
            }

        }catch (ParseException e){
            e.printStackTrace();
        }


        return calEvents;
    }
}
