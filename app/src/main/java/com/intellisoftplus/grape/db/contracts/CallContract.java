package com.intellisoftplus.grape.db.contracts;

import android.provider.BaseColumns;

/**
 * Created by cndet on 23/08/2016.
 * EventDB object and columns
 *
 */
public class CallContract {
    private String _title, _description, _association, _purpose, _time, _reminder;
    private int _id;

    public CallContract(int id, String title, String description, String association, String purpose, String time, String reminder){
        this._title = title;
        this._description=description;
        this._association =association;
        this._purpose =purpose;
        this._time =time;
        this._id=id;
        this._reminder=reminder;
    }
    public static abstract class CallEntry implements BaseColumns {
        public static final String TABLE_NAME = "Calls";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_ASSOCIATION = "association";
        public static final String COLUMN_PURPOSE = "purpose";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_REMINDER = "reminder";
    }

    public String getReminder() {
        return _reminder;
    }

    public String getDescription() {
        return _description;
    }

    public String getAssociation() {
        return _association;
    }

    public String getPurpose() {
        return _purpose;
    }

    public String getTitle() {
        return _title;
    }

    public String getTime() {
        return _time;
    }

    public int getId() {
        return _id;
    }

    @Override
    public String toString() {
        return "AppointmentContract("+
                "title="+_title+", description="+_description+
                ", date="+ _time +", reminder="+_reminder+
                ", who="+ _association +", why="+ _purpose +")";
    }
}
