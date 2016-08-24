package com.intellisoftplus.grape.db.contracts;

import android.provider.BaseColumns;

/**
 * Created by cndet on 23/08/2016.
 */
public class EventContract {
    public EventContract(){}
    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "Events";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DTSTART = "dtStart";
        public static final String COLUMN_DTEND = "dtEnd";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_ALLDAY = "allDay";
    }
}
