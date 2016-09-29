package com.intellisoftplus.grape;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intellisoftplus.grape.db.contracts.TaskContract;
import com.intellisoftplus.grape.db.operations.SingleTask;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link TaskInfoFragment#} factory method to
 * create an instance of this fragment.
 */
public class TaskInfoFragment extends Fragment {

    private View view;
    private TaskContract taskinfo;

    public TaskInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_task_info, container, false);


        TextView title = (TextView) view.findViewById(R.id.single_task_title);
        TextView description = (TextView) view.findViewById(R.id.single_task_description);
        TextView association = (TextView) view.findViewById(R.id.single_task_association);
        TextView starttime = (TextView) view.findViewById(R.id.single_task_starttime);
        TextView endtime = (TextView) view.findViewById(R.id.single_task_endtime);

        SingleTask task = new SingleTask(
                getActivity(),getActivity().getIntent().getIntExtra("taskinfoId",0),
                "read", null
        );
        try {
            this.taskinfo = task.execute().get();
            if(taskinfo!=null){
                title.setText(taskinfo.getTitle());
                description.setText(taskinfo.getDescription());
                association.setText(taskinfo.getAssociation());
                starttime.setText(taskinfo.getStarttime());
                endtime.setText(taskinfo.getEndtime());
                setHasOptionsMenu(true);
            } else {
                String nulltaskinfo = "No such task has been created";
                title.setText(nulltaskinfo);
            }
        } catch (ExecutionException|InterruptedException e){
            e.printStackTrace();
        }
        //Set app bar text
        TextView bar_text = (TextView) view.findViewById(R.id.app_bar_text);

        bar_text.setText(taskinfo.getTitle());


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_del_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.del_action_icon:
                deletetaskinfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deletetaskinfo(){
        SingleTask task = new SingleTask(
                getActivity(), taskinfo.getId(),
                "delete", null
        );
        task.execute();
        getActivity().finish();
    }

}

