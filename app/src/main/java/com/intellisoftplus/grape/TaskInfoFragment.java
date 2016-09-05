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
    private TaskContract event;


    public TaskInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_info, container, false);
        SingleTask task = new SingleTask(
                getActivity(),
                getActivity().getIntent().getLongExtra("eventId",0),
                "read",
                null
        );
        TextView title = (TextView) view.findViewById(R.id.single_task_title);
        TextView description = (TextView) view.findViewById(R.id.single_task_description);
        TextView association = (TextView) view.findViewById(R.id.single_task_association);
        TextView starttime = (TextView) view.findViewById(R.id.single_task_starttime);
        TextView endtime = (TextView) view.findViewById(R.id.single_task_endtime);
        try{
            this.event = task.execute().get();
            Log.v("Event", event.toString());
            if(event!=null) {
                title.setText(event.getTitle());
                description.setText(event.getDescription());
                starttime.setText(event.getStarttime());
                association.setText(event.getAssociation());
                endtime.setText(event.getEndtime());
                setHasOptionsMenu(true);
            } else {
                title.setText(R.string.null_event);
            }
        } catch (InterruptedException|ExecutionException e){
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
                deleteCall();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openEditFrag() {
        EditCallFragment editFrag = new EditCallFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("eventId", event.getId());
        bundle.putString("title", event.getTitle());
        bundle.putString("description", event.getDescription());
        bundle.putString("association", event.getAssociation());
        bundle.putString("starttime", event.getStarttime());
        bundle.putString("endtime", event.getEndtime());
        editFrag.setArguments(bundle);
        FragmentManager fManager = getFragmentManager();
        fManager.beginTransaction()
                .replace(R.id.singleTaskContainer, editFrag)
                .addToBackStack(null)
                .commit();

    }

    public void deleteCall(){
        SingleTask task = new SingleTask(
                getActivity(), event.getId(),
                "delete", null
        );
        task.execute();
        getActivity().finish();
    }
}
