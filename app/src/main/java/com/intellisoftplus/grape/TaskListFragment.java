package com.intellisoftplus.grape;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intellisoftplus.grape.adapters.TaskListAdapter;
import com.intellisoftplus.grape.db.contracts.TaskContract;
import com.intellisoftplus.grape.db.operations.ReadTask;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TaskListFragment extends Fragment {

    public TaskListFragment() {
        // Required empty public constructor
    }
    private List<TaskContract> taskList;
    private ReadTask readTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.taskRecycler);
        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.addtaskBtn);
        floatingActionButton.setOnClickListener(clickHandler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        readTask = new ReadTask( getActivity());
        try{
            this.taskList = readTask.execute().get();
            RecyclerView.Adapter adapter = new TaskListAdapter(taskList, getActivity());
            recyclerView.setAdapter(adapter);
        } catch (InterruptedException|ExecutionException e){
            e.printStackTrace();
        }

        //Set app bar text
        TextView bar_text = (TextView) view.findViewById(R.id.app_bar_text);

        bar_text.setText("Tasks");

        return view;
    }
    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.taskContainer, new CreateTaskFragment())
                    .commit();
        }
    };
}
