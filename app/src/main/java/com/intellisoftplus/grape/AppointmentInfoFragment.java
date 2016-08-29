package com.intellisoftplus.grape;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intellisoftplus.grape.db.contracts.EventContract;
import com.intellisoftplus.grape.db.operations.SingleEvent;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentInfoFragment extends Fragment {
    private EventContract event;

    public AppointmentInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment_info, container, false);

        TextView title = (TextView)view.findViewById(R.id.single_event_title);
        TextView description = (TextView)view.findViewById(R.id.single_event_description);
        TextView location = (TextView)view.findViewById(R.id.single_event_location);
        TextView timing = (TextView)view.findViewById(R.id.single_event_timing);
        TextView allDay = (TextView)view.findViewById(R.id.single_event_all_day);
        FloatingActionButton edit = (FloatingActionButton)view.findViewById(R.id.edit_single_event_button);
        SingleEvent task = new SingleEvent(
                getActivity(),
                getActivity().getIntent().getLongExtra("eventId",0),
                "read",
                null
        );
        try{
            this.event = task.execute().get();
            if(event!=null) {
                title.setText(event.getTitle());
                description.setText(event.getDescription());
                location.setText(event.getLocation());
                timing.setText(event.getDtStart());
                allDay.setText(event.getAllDay().toString());
                edit.setVisibility(View.VISIBLE);
                edit.setOnClickListener(editEvent);
            } else {
                title.setText(R.string.null_event);
            }
        } catch (InterruptedException|ExecutionException e){
            e.printStackTrace();
        }
        
        return view;
    }
    View.OnClickListener editEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditAppointmentFragment editFrag = new EditAppointmentFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("eventId", event.getId());
            bundle.putString("title", event.getTitle());
            bundle.putString("description", event.getDescription());
            bundle.putString("dtStart", event.getDtStart());
            bundle.putString("dtEnd", event.getDtEnd());
            bundle.putString("location", event.getLocation());
            bundle.putBoolean("allDay", event.getAllDay());
            editFrag.setArguments(bundle);
            FragmentManager fManager = getFragmentManager();
            fManager.beginTransaction()
                    .replace(R.id.singleAppointmentContainer, editFrag)
                    .addToBackStack(null)
                    .commit();

        }
    };
}
