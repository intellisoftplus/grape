package com.intellisoftplus.grape;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private Fragment currentFrag;

    public static CreateLeadFragment newInstance() {
        return new CreateLeadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_create_lead, container, false);
        this.currentFrag = this;

        Button Save = (Button) view.findViewById(R.id.add_lead_button);
        ImageButton btnLeft = (ImageButton) view.findViewById(R.id.btnLeft);
        Save.setOnClickListener(submissionHandler);
        btnLeft.setOnClickListener(clickHandler);

        //Set app bar text
        TextView bar_text = (TextView) view.findViewById(R.id.app_bar_text);

        bar_text.setText("Add Prospect");

        return view;
    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnLeft:
                    FragmentManager fManager = getFragmentManager();
                    fManager.popBackStackImmediate();
                default:
                    break;
            }
        }
    };


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
                        phone.getText().toString(),
                        email.getText().toString(),
                        website.getText().toString(),
                        status.getText().toString(),
                        source.getText().toString(),
                        industry.getText().toString(),
                        description.getText().toString()

                );
                task.execute();
                // Hide keyboard after saving appointment
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                FragmentManager fManager = getFragmentManager();
                fManager.popBackStackImmediate();

            }

        };
    }
