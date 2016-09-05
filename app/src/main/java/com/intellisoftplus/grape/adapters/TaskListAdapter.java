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
import com.intellisoftplus.grape.SingleCallActivity;
import com.intellisoftplus.grape.db.contracts.TaskContract;

import java.util.List;

/**
 * Created by frank on 9/5/16.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>  {

    private List<TaskContract> taskList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventTitle, eventTime;
        public CardView eventContainer;
        public ViewHolder(View itemView) {
            super(itemView);
            this.eventTitle = (TextView)itemView.findViewById(R.id.taskTitle);
            this.eventTime = (TextView)itemView.findViewById(R.id.taskDescription);
            this.eventContainer = (CardView)itemView.findViewById(R.id.taskCard);
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
        vH.eventContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SingleCallActivity.class);
                i.putExtra("eventId", (long)(int)view.getTag());
                context.startActivity(i);
            }
        });
        return vH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskContract current = taskList.get(position);
        holder.eventTitle.setText(current.getTitle());
        holder.eventTime.setText(current.getDescription());
        holder.eventContainer.setTag(current.getId());
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
