package com.intellisoftplus.grape;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.intellisoftplus.grape.adapters.LeadListAdapter;
import com.intellisoftplus.grape.db.contracts.LeadContract;
import com.intellisoftplus.grape.db.operations.ReadLeads;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by cndet on 25/08/2016.
 */
public class LeadsListFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private List<LeadContract> leads;
    private ReadLeads readLeads;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_leads_list, container, false);


        FloatingActionButton createLead = (FloatingActionButton)view.findViewById(R.id.addLeadBtn);
        ImageButton btnLeft = (ImageButton) view.findViewById(R.id.btnLeft);

        createLead.setOnClickListener(clickHandler);
        btnLeft.setOnClickListener(clickHandler);

        recyclerView = (RecyclerView)view.findViewById(R.id.leadsRecycler);
        recyclerView.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(rLayoutManager);
        readLeads = new ReadLeads(getActivity());
        try {
            this.leads = readLeads.execute().get();
            rAdapter = new LeadListAdapter(leads, getActivity());
            recyclerView.setAdapter(rAdapter);
        }catch (InterruptedException|ExecutionException e){
            e.printStackTrace();
        }

        //Set app bar text
        TextView bar_text = (TextView) view.findViewById(R.id.app_bar_text);

        bar_text.setText("Prospects");

        return view;
    }


    View.OnClickListener clickHandler  = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.addLeadBtn:
                    //bring up fragment
                    FragmentManager fManager = getFragmentManager();
                    fManager.beginTransaction()
                            .replace(R.id.leadsContainer, CreateLeadFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    break;
                case R.id.btnLeft:
                    getActivity().finish();
                    break;
                default:
                    break;
            }
        }
    };
}
