package com.intellisoftplus.grape.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Adapter;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.intellisoftplus.grape.db.contracts.EventContract;
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
    private List<EventContract> eventList = new ArrayList<>();
    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);


    public  EventListAdapter(Context c){
        // Get events from DB
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
        // Create list of CalendarEvent objects which are rendered in the CalendarView
        List<CalendarEvent> calEvents = new ArrayList<>();
        for (EventContract current: eventList) {
            BaseCalendarEvent currentEvent = new BaseCalendarEvent(
                    current.getTitle(), current.getDescription(),
                    current.getLocation(), ContextCompat.getColor(context, android.R.color.black),
                    getAsCalendarObject(current.getDtStart()), getAsCalendarObject(current.getDtEnd()),
                    current.getAllDay()
            );
            currentEvent.setId(current.getId());
            calEvents.add(currentEvent);
        }


        return calEvents;
    }


    public Calendar getAsCalendarObject(String dtString) {
        Calendar calendar = GregorianCalendar.getInstance();
        try {
            calendar.setTime(sdf.parse(dtString));
        } catch (ParseException e){
            e.printStackTrace();
        }
        return calendar;
    }
}
