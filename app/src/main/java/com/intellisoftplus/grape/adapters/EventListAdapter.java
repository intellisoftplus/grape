package com.intellisoftplus.grape.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.intellisoftplus.grape.db.contracts.AppointmentContract;
import com.intellisoftplus.grape.db.operations.ReadAppointments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by cndet on 24/08/2016.
 */
public class EventListAdapter extends AsyncTask<Object, Void, List<CalendarEvent>> {
    private List<AppointmentContract> eventList = new ArrayList<>();
    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);


    public  EventListAdapter(Context c){
        // Get events from DB
        ReadAppointments task = new ReadAppointments(c);
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
        for (AppointmentContract current: eventList) {
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
