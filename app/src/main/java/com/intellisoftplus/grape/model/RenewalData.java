package com.intellisoftplus.grape.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 9/13/2016.
 */
public class RenewalData {
    private static final String[] titles = {"Nothingness cannot be defined", "The softest thing cannot be snapped"};

    private static final int[] icons = {android.R.drawable.ic_popup_reminder, android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_delete};

    public static List<ListItem> getListData() {
        List<ListItem> data = new ArrayList<>();

        //Repeat process 4 times, so that we have enough data to demonstrate a scrollable RecycleView
        for (int x = 0; x < 4; x++) {
            //create ListItem with dummy, then add them to our List
            for (int i = 0; i < titles.length && i < icons.length;) {
                ListItem item = new ListItem();
                item.setImageResId((icons[i]));
                item.setTitle(titles[i]);
                data.add(item);
            }

        }
        return data;
    }
}
