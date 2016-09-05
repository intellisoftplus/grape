package com.intellisoftplus.grape.db.contracts;

import android.provider.BaseColumns;

/**
 * Created by frank on 9/3/16.
 */
public class TaskContract {
    private String _title, _description, _association, _starttime, _endtime;
    private int _id;

    public TaskContract(int id, String string, String title, String description, String association, String starttime, String endtime){
        this._title = title;
        this._description=description;
        this._association =association;
        this._starttime =starttime;
        this._endtime =endtime;
        this._id=id;

    }
    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "Task";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_ASSOCIATION = "association";
        public static final String COLUMN_STARTTIME = "starttime";
        public static final String COLUMN_ENDTIME = "endtime";
    }

    public String getStarttime() {
        return _starttime;
    }

    public String getDescription() {
        return _description;
    }

    public String getAssociation() {
        return _association;
    }

    public String getEndtime() {
        return _endtime;
    }

    public String getTitle() {
        return _title;
    }

    public int getId() {
        return _id;
    }

    @Override
    public String toString() {
        return "TaskContract("+
                "title="+_title+", description="+_description+
                ", date="+ _starttime +", reminder="+_endtime+
                ", who="+ _association +")";
    }
}
