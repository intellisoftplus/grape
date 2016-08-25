package com.intellisoftplus.grape;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.intellisoftplus.grape.adapters.EventListAdapter;
import com.intellisoftplus.grape.db.operations.DeleteEvents;
import com.intellisoftplus.grape.db.operations.ReadEvents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * This fragment lists all appointments a user has created.
 */

public class AppointmentListFragment extends Fragment implements CalendarPickerController {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment CreateAppointmentFragment.
     */
    public static AppointmentListFragment newInstance() {
        return new AppointmentListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointments_list, container, false);

        FloatingActionButton addAppointment = (FloatingActionButton)view.findViewById(R.id.add_appointment);
        addAppointment.setOnClickListener(clickHandler);

        // Get the calendar view in the layout and populate it with events
        AgendaCalendarView agendaCalendarView = (AgendaCalendarView)view.findViewById(R.id.agenda_calendar_view);
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();


        minDate.add(Calendar.MONTH, -12);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);
        EventListAdapter eventList = new EventListAdapter(getActivity());
        List<CalendarEvent> calEvents = new ArrayList<>();
        try{
            calEvents = eventList.execute().get();
        } catch (InterruptedException|ExecutionException e){
            e.printStackTrace();
        }


        agendaCalendarView.init(calEvents, minDate, maxDate, Locale.getDefault(), this);

        return view;
    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Switch fragment when to creating an appointment after clicking the button
            FragmentManager fManager = getFragmentManager();
            fManager.beginTransaction()
                    .replace(R.id.appointmentContainer, CreateAppointmentFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
    };

    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {

    }

    @Override
    public void onScrollToDate(Calendar calendar) {

    }

}
