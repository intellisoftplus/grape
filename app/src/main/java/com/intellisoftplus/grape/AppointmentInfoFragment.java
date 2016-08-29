package com.intellisoftplus.grape;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intellisoftplus.grape.db.contracts.EventContract;
import com.intellisoftplus.grape.db.operations.ReadSingleEvent;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentInfoFragment extends Fragment {


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
        ReadSingleEvent task = new ReadSingleEvent(getActivity(),getActivity().getIntent().getLongExtra("eventId",0));
        try{
            EventContract event = task.execute().get();
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

        }
    };
}
