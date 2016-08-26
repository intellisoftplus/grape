package com.intellisoftplus.grape;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellisoftplus.grape.adapters.LeadListAdapter;
import com.intellisoftplus.grape.db.operations.ReadLeads;

import java.util.concurrent.ExecutionException;

/**
 * Created by cndet on 25/08/2016.
 */
public class LeadsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leads_list, container, false);


        FloatingActionButton createLead = (FloatingActionButton)view.findViewById(R.id.addLeadBtn);
        //unused will activate later
        createLead.setOnClickListener(clickHandler);

        recyclerView = (RecyclerView)view.findViewById(R.id.leadsRecycler);
        recyclerView.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(rLayoutManager);
        ReadLeads readLeads = new ReadLeads(getActivity());
        try {
            rAdapter = new LeadListAdapter(readLeads.execute().get());
            recyclerView.setAdapter(rAdapter);
        }catch (InterruptedException|ExecutionException e){
            e.printStackTrace();
        }

        return view;
    }

    View.OnClickListener clickHandler  = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.addLeadBtn:
                    //bring up fragment
                    FragmentManager fManager = getFragmentManager();
                    fManager.beginTransaction()
                            .replace(R.id.leadsContainer, CreateLeadFragment.newInstance())
                            .addToBackStack(null)
                            .commit();

                    break;

            }


        }
    };
}