package com.intellisoftplus.grape;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intellisoftplus.grape.db.contracts.LeadContract;
import com.intellisoftplus.grape.db.operations.SingleLead;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeadInfoFragment extends Fragment {
    
    private View view;
    private LeadContract lead;

    public LeadInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_lead_info, container, false);


        TextView names = (TextView) view.findViewById(R.id.single_lead_names);
        TextView phone = (TextView) view.findViewById(R.id.single_lead_phone);
        TextView email = (TextView) view.findViewById(R.id.single_lead_email);
        TextView website = (TextView) view.findViewById(R.id.single_lead_website);
        TextView status = (TextView) view.findViewById(R.id.single_lead_status);
        TextView source = (TextView) view.findViewById(R.id.single_lead_source);
        TextView industry = (TextView) view.findViewById(R.id.single_lead_industry);
        TextView description = (TextView) view.findViewById(R.id.single_lead_description);

        SingleLead task = new SingleLead(
                getActivity(),getActivity().getIntent().getIntExtra("leadId",0),
                "read", null
        );
        try {
            this.lead = task.execute().get();
            if(lead!=null){
                names.setText(lead.getNames());
                phone.setText(lead.getPhone());
                email.setText(lead.getEmail());
                website.setText(lead.getWebsite());
                status.setText(lead.getStatus());
                source.setText(lead.getSource());
                industry.setText(lead.getIndustry());
                description.setText(lead.getDescription());
                setHasOptionsMenu(true);
            } else {
                String nullLead = "No such lead as been created";
                names.setText(nullLead);
            }
        } catch (ExecutionException|InterruptedException e){
            e.printStackTrace();
        }
        return view;
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
                deleteLead();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void openEditFrag() {
        EditLeadFragment editFrag = new EditLeadFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("leadId", lead.getId());
        bundle.putString("names", lead.getNames());
        bundle.putString("phone", lead.getPhone());
        bundle.putString("email", lead.getEmail());
        bundle.putString("website", lead.getWebsite());
        bundle.putString("status", lead.getStatus());
        bundle.putString("source", lead.getSource());
        bundle.putString("industry", lead.getIndustry());
        bundle.putString("description", lead.getDescription());
        editFrag.setArguments(bundle);
        FragmentManager fManager = getFragmentManager();
        fManager.beginTransaction()
                .replace(R.id.singleLeadContainer, editFrag)
                .addToBackStack(null)
                .commit();

    }

    public void deleteLead(){
        SingleLead task = new SingleLead(
                getActivity(), lead.getId(),
                "delete", null
        );
        task.execute();
        getActivity().finish();
    }

}
