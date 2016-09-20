package com.intellisoftplus.grape.db.contracts;

import android.provider.BaseColumns;

/**
 * Created by cndet on 23/08/2016.
 * EventDB object and columns
 *
 */
public class AppointmentContract {
    private String _title, _description, _dtStart, _dtEnd, _location;
    private Boolean _allDay;
    private int _id;

    public AppointmentContract(int id, String title, String description, String dtStart, String dtEnd, String location, int allDay){
        this._title = title;
        this._description=description;
        this._dtStart=dtStart;
        this._dtEnd=dtEnd;
        this._location=location;
        this._allDay=(allDay==1);
        this._id=id;
    }
    public static abstract class AppointmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "Appointments";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DTSTART = "dtStart";
        public static final String COLUMN_DTEND = "dtEnd";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_ALLDAY = "allDay";
    }

    public Boolean getAllDay() {
        return _allDay;
    }

    public String getDescription() {
        return _description;
    }

    public String getDtStart() {
        return _dtStart;
    }

    public String getDtEnd() {
        return _dtEnd;
    }

    public String getTitle() {
        return _title;
    }

    public String getLocation() {
        return _location;
    }

    public int getId() {
        return _id;
    }

    @Override
    public String toString() {
        return "AppointmentContract("+
                "title="+_title+", description="+_description+
                ", location="+_location+", dtStart="+_dtStart+
                ", dtEnd="+_dtEnd+", allDay="+_allDay+")";
    }
}
