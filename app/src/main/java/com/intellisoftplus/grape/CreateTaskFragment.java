package com.intellisoftplus.grape;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.intellisoftplus.grape.db.operations.SaveCall;
import com.intellisoftplus.grape.db.operations.SaveTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateTaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTaskFragment extends Fragment {
    private View view;


    public CreateTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_create_task, container, false);
        Button addCall = (Button)view.findViewById(R.id.submit_new_task);
        TextView timePicker = (TextView)view.findViewById(R.id.generateCallTimePicker);
        TextView reminderPicker = (TextView)view.findViewById(R.id.generateCallReminderPicker);
        addCall.setOnClickListener(clickHandler);
        timePicker.setOnClickListener(clickHandler);
        reminderPicker.setOnClickListener(clickHandler);
        return view;
    }
    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.submit_new_task:
                    EditText title = (EditText) view.findViewById(R.id.new_task_title);
                    EditText description = (EditText) view.findViewById(R.id.new_task_description);
                    EditText association = (EditText) view.findViewById(R.id.new_task_association);
                    TextView starttime = (TextView) view.findViewById(R.id.new_start_time);
                    TextView endtime = (TextView) view.findViewById(R.id.new_end_time);

                    if (title.getText().toString().equals("")) {
                        title.setError("Please fill in the title");
                        return;
                    }
                    if (description.getText().toString().equals("")) {
                        description.setError("Please fill in the description");
                        return;
                    }
                    if (association.getText().toString().equals("")) {
                        association.setError("Please fill in the association");
                        return;
                    }
                    if (starttime.getText().toString().equals("")) {
                        starttime.setError("Please set in the time");
                        return;
                    }
                    if (endtime.getText().toString().equals("")) {
                        endtime.setError("Please set in the reminder");
                        return;
                    }

                    SaveTask task = new SaveTask(
                            getActivity(), title.getText().toString(),
                            description.getText().toString(), association.getText().toString(),
                            starttime.getText().toString(),
                            endtime.getText().toString()
                    );
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
                    task.execute();

                    Log.v("title", endtime.getText().toString());

                    // Hide keyboard after saving appointment
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    getFragmentManager()
                            .popBackStackImmediate();
                    break;
                case R.id.generateCallTimePicker:
                    new CustomCalendarDialog(getActivity(), "Start Time ", view, R.id.new_start_time);
                    break;
                case R.id.generateCallReminderPicker:
                    new CustomCalendarDialog(getActivity(), "Endtime", view, R.id.new_end_time);
                    break;
            }
        }
    };

//    PopupMenu.OnMenuItemClickListener popupClickHandler = new PopupMenu.OnMenuItemClickListener() {
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            TextView purpose = (TextView) view.findViewById(R.id.new_call_purpose);
//            purpose.setText(menuItem.getTitle());
//            return true;
//        }
//    };
}
