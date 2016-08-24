package com.intellisoftplus.grape;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AppointmentListFragment extends Fragment {

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

        Button addAppointment = (Button)view.findViewById(R.id.add_appointment);
        addAppointment.setOnClickListener(clickHandler);

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
}
