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
import com.intellisoftplus.grape.SingleLeadActivity;
import com.intellisoftplus.grape.db.contracts.LeadContract;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cndet on 25/08/2016.
 */
public class LeadListAdapter extends RecyclerView.Adapter<LeadListAdapter.ViewHolder> {

    private List<LeadContract> leadList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView leadText;
        public CardView leadContainer;
        public ViewHolder(View itemView) {
            super(itemView);
            this.leadText = (TextView)itemView.findViewById(R.id.lead_text);
            this.leadContainer = (CardView)itemView.findViewById(R.id.leadCard);
        }
    }

    public LeadListAdapter(List<LeadContract> leadList, Context c){
        this.leadList = leadList;
        this.context=c;
    }

    @Override
    public LeadListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.single_lead_view, parent, false);
        ViewHolder vH = new ViewHolder(v);
        vH.leadContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SingleLeadActivity.class);
                i.putExtra("leadId", (int)view.getTag());
                context.startActivity(i);
            }
        });
        return vH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LeadContract current = leadList.get(position);
        holder.leadText.setText(current.getNames());
        holder.leadContainer.setTag(current.getId());
    }


    @Override
    public int getItemCount() {
        return leadList.size();
    }

}
