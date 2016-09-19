package com.intellisoftplus.grape.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intellisoftplus.grape.R;
import com.intellisoftplus.grape.SingleCallActivity;
import com.intellisoftplus.grape.SingleLeadActivity;
import com.intellisoftplus.grape.db.contracts.CallContract;

import java.util.List;

/**
 * Created by cndet on 25/08/2016.
 */
public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.ViewHolder> {

    private List<CallContract> callList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventTitle, eventTime;
        public CardView eventContainer;
        public ViewHolder(View itemView) {
            super(itemView);
            this.eventTitle = (TextView)itemView.findViewById(R.id.eventTitle);
            this.eventTime = (TextView)itemView.findViewById(R.id.eventTime);
            this.eventContainer = (CardView)itemView.findViewById(R.id.eventCard);
        }
    }

    public CallListAdapter(List<CallContract> callList, Context c){
        this.callList = callList;
        this.context=c;
    }

    @Override
    public CallListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.single_event_view, parent, false);
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
        CallContract current = callList.get(position);
        holder.eventTitle.setText(current.getTitle());
        holder.eventTime.setText(current.getTime());
        holder.eventContainer.setTag(current.getId());
    }


    @Override
    public int getItemCount() {
        return callList.size();
    }

}
