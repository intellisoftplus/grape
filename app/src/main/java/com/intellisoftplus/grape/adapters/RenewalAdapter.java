package com.intellisoftplus.grape.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intellisoftplus.grape.R;
import com.intellisoftplus.grape.model.ListItem;

import java.util.List;

/**
 * Created by user on 9/13/2016.
 */
public class RenewalAdapter extends RecyclerView.Adapter <RenewalAdapter.RenewalHolder> {

    private List<ListItem> listData;
    private LayoutInflater inflater;

    public RenewalAdapter (List<ListItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public RenewalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_items, parent, false);
        return new RenewalHolder(view);
    }

    @Override
    public void onBindViewHolder(RenewalHolder holder, int position) {
        ListItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getImageResId());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class RenewalHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private View container;

        public RenewalHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.lbl_item_text);
            icon = (ImageView)itemView.findViewById(R.id.item_icon);
            container = itemView.findViewById(R.id.cont_item_root);

        }
    }
}
