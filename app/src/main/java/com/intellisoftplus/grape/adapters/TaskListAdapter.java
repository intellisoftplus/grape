package com.intellisoftplus.grape.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intellisoftplus.grape.R;
import com.intellisoftplus.grape.SingleTaskActivity;
import com.intellisoftplus.grape.db.contracts.TaskContract;

import java.util.List;

/**
 * Created by frank on 9/5/16.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>  {

    private List<TaskContract> taskList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tasktTitle, taskdescription;
        public CardView taskContainer;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tasktTitle = (TextView)itemView.findViewById(R.id.taskTitle);
            this.taskdescription = (TextView)itemView.findViewById(R.id.taskDescription);
            this.taskContainer = (CardView)itemView.findViewById(R.id.taskCard);
        }
    }

    public TaskListAdapter(List<TaskContract> taskList, Context c){
        this.taskList = taskList;
        this.context=c;
    }

    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_task_view, parent, false);
        ViewHolder vH = new ViewHolder(v);
        vH.taskContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SingleTaskActivity.class);
                i.putExtra("taskinfoId", (int)view.getTag());
                context.startActivity(i);
            }
        });
        return vH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskContract current = taskList.get(position);
        holder.tasktTitle.setText(current.getTitle());
        holder.taskdescription.setText(current.getDescription());
        holder.taskContainer.setTag(current.getId());
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
