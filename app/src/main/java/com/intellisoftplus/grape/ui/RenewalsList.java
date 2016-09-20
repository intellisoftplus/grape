package com.intellisoftplus.grape.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.intellisoftplus.grape.R;
import com.intellisoftplus.grape.adapters.RenewalAdapter;
import com.intellisoftplus.grape.model.RenewalData;

public class RenewalsList extends AppCompatActivity {

    private RecyclerView recView;
    private RenewalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renewals_list);

        recView = (RecyclerView)findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RenewalAdapter(RenewalData.getListData(), this);
        recView.setAdapter(adapter);
    }
}
