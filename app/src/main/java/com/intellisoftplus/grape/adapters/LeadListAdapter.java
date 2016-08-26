package com.intellisoftplus.grape.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intellisoftplus.grape.R;
import com.intellisoftplus.grape.db.contracts.LeadContract;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cndet on 25/08/2016.
 */
public class LeadListAdapter extends RecyclerView.Adapter<LeadListAdapter.ViewHolder> {

    private List<LeadContract> leadList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView leadText;
        public ViewHolder(View itemView) {
            super(itemView);
            this.leadText = (TextView)itemView.findViewById(R.id.lead_text);
        }
    }

    public LeadListAdapter(List<LeadContract> leadList){
        this.leadList = leadList;
    }

    @Override
    public LeadListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.single_lead_view, parent, false);
        ViewHolder vH = new ViewHolder(v);
        return vH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LeadContract current = leadList.get(position);
        holder.leadText.setText(current.getNames());
    }


    @Override
    public int getItemCount() {
        return leadList.size();
    }
}
