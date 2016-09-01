package com.intellisoftplus.grape;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intellisoftplus.grape.db.contracts.AppointmentContract;
import com.intellisoftplus.grape.db.operations.SingleAppointment;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentInfoFragment extends Fragment {


    private AppointmentContract event;

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
        SingleAppointment task = new SingleAppointment(
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
                setHasOptionsMenu(true);
            } else {
                title.setText(R.string.null_event);
            }
        } catch (InterruptedException|ExecutionException e){
            e.printStackTrace();
        }
        
        return view;
    }

    public void openEditFrag() {
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

    public void deleteAppointment(){
        SingleAppointment task = new SingleAppointment(
                getActivity(), event.getId(),
                "delete", null
        );
        task.execute();
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_del_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_action_icon:
                openEditFrag();
                return true;
            case R.id.del_action_icon:
                deleteAppointment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
