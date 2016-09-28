package com.intellisoftplus.grape;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intellisoftplus.grape.adapters.CallListAdapter;
import com.intellisoftplus.grape.db.contracts.CallContract;
import com.intellisoftplus.grape.db.operations.ReadCalls;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallListFragment extends Fragment {


    public CallListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_list, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.callRecycler);
        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.addCallBtn);
        floatingActionButton.setOnClickListener(clickHandler);
        List<CallContract> callList;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ReadCalls task = new ReadCalls(getActivity());
        try{
            callList = task.execute().get();
            RecyclerView.Adapter adapter = new CallListAdapter(callList, getActivity());
            recyclerView.setAdapter(adapter);
        } catch (InterruptedException|ExecutionException e){
            e.printStackTrace();
        }

        //Set app bar text
        TextView bar_text = (TextView) view.findViewById(R.id.app_bar_text);

        bar_text.setText("Calls");

        return view;
    }
    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.callContainer, new CreateCallFragment())
                    .commit();
        }
    };
}
