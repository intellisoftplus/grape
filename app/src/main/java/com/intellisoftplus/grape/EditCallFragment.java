package com.intellisoftplus.grape;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.intellisoftplus.grape.db.contracts.CallContract.CallEntry;
import com.intellisoftplus.grape.db.operations.SingleCall;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditCallFragment extends Fragment {
    private EditText title,description,association;
    private TextView purpose, time, reminder;
    private long callId;
    private Bundle extras;
    private View view;


    public EditCallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_create_call, container, false);


        Button addCall = (Button)view.findViewById(R.id.submit_new_call);
        TextView timePicker = (TextView)view.findViewById(R.id.generateCallTimePicker);
        TextView reminderPicker = (TextView)view.findViewById(R.id.generateCallReminderPicker);
        TextView purposeHint = (TextView)view.findViewById(R.id.generateCallPurpose);
        purposeHint.setOnClickListener(clickHandler);
        addCall.setOnClickListener(clickHandler);
        timePicker.setOnClickListener(clickHandler);
        reminderPicker.setOnClickListener(clickHandler);

        this.extras = getArguments();

        this.callId = extras.getLong("eventId", 0);

        this.title = (EditText) view.findViewById(R.id.new_call_title);
        this.description = (EditText) view.findViewById(R.id.new_call_description);
        this.purpose = (TextView) view.findViewById(R.id.new_call_purpose);
        this.association = (EditText) view.findViewById(R.id.new_call_association);
        this.time = (TextView) view.findViewById(R.id.new_call_time);
        this.reminder = (TextView) view.findViewById(R.id.new_call_reminder);

        title.setText(extras.getString("title"));
        description.setText(extras.getString("description"));
        purpose.setText(extras.getString("purpose"));
        association.setText(extras.getString("association"));
        time.setText(extras.getString("time"));
        reminder.setText(extras.getString("reminder"));

        return view;
    }
    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.submit_new_call:
                    if (title.getText().toString().equals("")) {
                        title.setError("Please fill in the title");
                        return;
                    }
                    if (description.getText().toString().equals("")) {
                        description.setError("Please fill in the description");
                        return;
                    }
                    if (purpose.getText().toString().equals("")) {
                        purpose.setError("Please set in the purpose");
                        return;
                    }
                    if (association.getText().toString().equals("")) {
                        association.setError("Please fill in the association");
                        return;
                    }
                    if (time.getText().toString().equals("")) {
                        time.setError("Please set in the time");
                        return;
                    }
                    if (reminder.getText().toString().equals("")) {
                        reminder.setError("Please set in the reminder");
                        return;
                    }

                    ContentValues values = new ContentValues();
                    values.put(CallEntry.COLUMN_TITLE, title.getText().toString());
                    values.put(CallEntry.COLUMN_DESCRIPTION, description.getText().toString());
                    values.put(CallEntry.COLUMN_ASSOCIATION, association.getText().toString());
                    values.put(CallEntry.COLUMN_PURPOSE, purpose.getText().toString());
                    values.put(CallEntry.COLUMN_TIME, time.getText().toString());
                    values.put(CallEntry.COLUMN_REMINDER, reminder.getText().toString());

                    SingleCall task = new SingleCall(
                            getActivity(), callId,
                            "update", values
                    );
                    task.execute();

                    FragmentManager fManager = getFragmentManager();
                    fManager.popBackStackImmediate();
                    break;

                case R.id.generateCallTimePicker:
                    new CustomCalendarDialog(getActivity(), "Time", view, R.id.new_call_time);
                    break;
                case R.id.generateCallPurpose:
                    PopupMenu popupMenu = new PopupMenu(getActivity(),v);
                    popupMenu.setOnMenuItemClickListener(popupClickHandler);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.purposes_menu, popupMenu.getMenu());
                    popupMenu.show();
                    break;
                case R.id.generateCallReminderPicker:
                    new CustomCalendarDialog(getActivity(), "Reminder", view, R.id.new_call_reminder);
                    break;
            }
        }
    };

    PopupMenu.OnMenuItemClickListener popupClickHandler = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            TextView purpose = (TextView) view.findViewById(R.id.new_call_purpose);
            purpose.setText(menuItem.getTitle());
            return true;
        }
    };
}
