package com.intellisoftplus.grape;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by User on 8/24/2016.
 */

import com.intellisoftplus.grape.db.operations.SaveLead;

import java.util.Locale;

public class CreateLeadFragment extends Fragment {

    private View view;

    public static CreateLeadFragment newInstance() {
        return new CreateLeadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_create_lead, container, false);

        EditText names = (EditText) view.findViewById(R.id.add_lead_names_editText);
        EditText phone = (EditText) view.findViewById(R.id.add_lead_phone_editText);
        EditText email = (EditText) view.findViewById(R.id.add_lead_email_editText);
        EditText website = (EditText) view.findViewById(R.id.add_lead_website_editText);
        EditText status = (EditText) view.findViewById(R.id.add_lead_status_editText);
        EditText source = (EditText) view.findViewById(R.id.add_lead_source_editText);
        EditText industry = (EditText) view.findViewById(R.id.add_lead_indusrty_editText);
        EditText description = (EditText) view.findViewById(R.id.add_lead_description_editText);

        Button Save = (Button) view.findViewById(R.id.add_lead_button);
        Save.setOnClickListener(submissionHandler);

        return view;
    }


    View.OnClickListener submissionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText names = (EditText) view.findViewById(R.id.add_lead_names_editText);
            EditText phone = (EditText) view.findViewById(R.id.add_lead_phone_editText);
            EditText email = (EditText) view.findViewById(R.id.add_lead_email_editText);
            EditText website = (EditText) view.findViewById(R.id.add_lead_website_editText);
            EditText status = (EditText) view.findViewById(R.id.add_lead_status_editText);
            EditText source = (EditText) view.findViewById(R.id.add_lead_source_editText);
            EditText industry = (EditText) view.findViewById(R.id.add_lead_indusrty_editText);
            EditText description = (EditText) view.findViewById(R.id.add_lead_description_editText);


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


            SaveLead task = new SaveLead(
                    getActivity(),
                    names.getText().toString(),
                    description.getText().toString(),
                    phone.getText().toString(),
                    email.getText().toString(),
                    website.getText().toString(),
                    status.getText().toString(),
                    source.getText().toString(),
                    industry.getText().toString()

            );
            task.execute();
            /*
            FragmentManager fManager = getFragmentManager();
            fManager.beginTransaction()
                    .replace(R.id.leadsContainer, LeadsActivity.newInstance())
                    .addToBackStack(null)
                    .commit();
                    */
        }

    };

    private static void displayToast(Context context, CharSequence text, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
