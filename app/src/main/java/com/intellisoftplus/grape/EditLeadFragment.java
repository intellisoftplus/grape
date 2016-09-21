package com.intellisoftplus.grape;


import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.intellisoftplus.grape.db.contracts.LeadContract.LeadEntry;
import com.intellisoftplus.grape.db.operations.SaveLead;
import com.intellisoftplus.grape.db.operations.SingleLead;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditLeadFragment extends Fragment {

    private View view;
    private EditText names,phone,email,website,status,source,industry,description;
    private int leadId;
    private Toolbar toolbar;


    public EditLeadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_create_lead, container, false);
        Bundle extras = getArguments();

        Toolbar toolbar = ((DashboardActivity) getActivity()).toolbar;

        ((DashboardActivity) getActivity()).toolbar.setTitle("Add Lead");












        names = (EditText) view.findViewById(R.id.add_lead_names_editText);
        phone = (EditText) view.findViewById(R.id.add_lead_phone_editText);
        email = (EditText) view.findViewById(R.id.add_lead_email_editText);
        website = (EditText) view.findViewById(R.id.add_lead_website_editText);
        status = (EditText) view.findViewById(R.id.add_lead_status_editText);
        source = (EditText) view.findViewById(R.id.add_lead_source_editText);
        industry = (EditText) view.findViewById(R.id.add_lead_indusrty_editText);
        description = (EditText) view.findViewById(R.id.add_lead_description_editText);
        Button Save = (Button) view.findViewById(R.id.add_lead_button);
        Save.setOnClickListener(submissionHandler);

        names.setText(extras.getString("names"));
        phone.setText(extras.getString("phone"));
        email.setText(extras.getString("email"));
        website.setText(extras.getString("website"));
        status.setText(extras.getString("status"));
        source.setText(extras.getString("source"));
        industry.setText(extras.getString("industry"));
        description.setText(extras.getString("description"));
        this.leadId = extras.getInt("leadId");

        return view;
    }


    View.OnClickListener submissionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (names.getText().toString().equals("")) {
                names.setError("Please fill in the Name.");
                return;
            }
            if (description.getText().toString().equals("")) {
                description.setError("Please fill in the description.");
                return;
            }
            if (phone.getText().toString().equals("")) {
                phone.setError("Please fill in the Phone Number");
                return;
            }
            if (email.getText().toString().equals("")) {
                email.setError("Please fill in the Email");
                return;
            }
            if (website.getText().toString().equals("")) {
                website.setError("Please fill in the Website");
                return;
            }
            if (status.getText().toString().equals("")) {
                status.setError("Please fill in the Status");
                return;
            }
            if (source.getText().toString().equals("")) {
                source.setError("Please fill in the Source");
                return;
            }
            if (industry.getText().toString().equals("")) {
                industry.setError("Please fill in the Industry");
                return;
            }
            ContentValues values = new ContentValues();
            values.put(LeadEntry.COLUMN_NAMES, names.getText().toString());
            values.put(LeadEntry.COLUMN_DESCRIPTION, description.getText().toString());
            values.put(LeadEntry.COLUMN_PHONE, phone.getText().toString());
            values.put(LeadEntry.COLUMN_EMAIL, email.getText().toString());
            values.put(LeadEntry.COLUMN_WEBSITE, website.getText().toString());
            values.put(LeadEntry.COLUMN_STATUS, status.getText().toString());
            values.put(LeadEntry.COLUMN_SOURCE, source.getText().toString());
            values.put(LeadEntry.COLUMN_INDUSTRY, industry.getText().toString());

            SingleLead task = new SingleLead(
                    getActivity(), leadId,
                    "update", values
            );
            task.execute();

            FragmentManager fManager = getFragmentManager();
            fManager.popBackStackImmediate();
        }

    };
}
