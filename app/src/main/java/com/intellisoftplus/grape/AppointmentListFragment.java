package com.intellisoftplus.grape;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.intellisoftplus.grape.db.operations.ReadEvents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


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

        AgendaCalendarView agendaCalendarView = (AgendaCalendarView)view.findViewById(R.id.agenda_calendar_view);
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);


        agendaCalendarView.init(new EventListAdapter(getActivity()).getEvents(), minDate, maxDate, Locale.getDefault(), this);

        return view;
    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fManager = getFragmentManager();
            fManager.beginTransaction()
                    .replace(R.id.appointmentContainer, CreateAppointmentFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
    };

    @Override
    public void onDaySelected(DayItem dayItem) {
//        displayToast(getActivity(), dayItem.getDate().toString(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
//        displayToast(getActivity(), event.getTitle(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
//        displayToast(getActivity(), calendar.toString(), Toast.LENGTH_SHORT);
    }

    private static void displayToast(Context context, CharSequence text, int duration){
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
