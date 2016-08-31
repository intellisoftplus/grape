package com.intellisoftplus.grape;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellisoftplus.grape.db.contracts.CallContract;
import com.intellisoftplus.grape.db.operations.SingleCall;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallInfoFragment extends Fragment {

    private CallContract event;


    public CallInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_info, container, false);
        SingleCall task = new SingleCall(
                getActivity(),
                getActivity().getIntent().getIntExtra("eventId",0),
                "read",
                null
        );
        try{
            this.event = task.execute().get();
            if(event!=null) {
                Log.v("Exists","Exists");
            } else {
                Log.v("Null","Null");
            }
        } catch (InterruptedException|ExecutionException e){
            e.printStackTrace();
        }
        return view;
    }

}
