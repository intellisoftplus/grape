package com.intellisoftplus.grape.db.contracts;

import android.provider.BaseColumns;

/**
 * Created by User on 8/24/2016.
 */
public class LeadContract {
    private String _names,_phone,_email,_website,_status,_source,_industry,_description;
    private int _id;

    public LeadContract(int id, String names,String phone,String email,String website,
                        String status,String source,String industry,String description){
        this._id=id;
        this._names=names;
        this._phone=phone;
        this._email=email;
        this._website=website;
        this._status=status;
        this._source=source;
        this._industry=industry;
        this._description=description;
    }
    public static abstract class LeadEntry implements BaseColumns {
        public static final String TABLE_NAME = "Leads";
        public static final String COLUMN_NAMES = "names";
        public static final String COLUMN_PHONE= "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_WEBSITE = "website";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_SOURCE = "source";
        public static final String COLUMN_INDUSTRY = "industry";
        public static final String COLUMN_DESCRIPTION = "description";

    }

    public String getNames() {
        return _names;
    }

    public String getDescription() {
        return _description;
    }

    public String getEmail() {
        return _email;
    }

    public String getIndustry() {
        return _industry;
    }

    public String getPhone() {
        return _phone;
    }

    public String getSource() {
        return _source;
    }

    public String getStatus() {
        return _status;
    }

    public String getWebsite() {
        return _website;
    }

    public int getId() {
        return _id;
    }

    @Override
    public String toString() {
        return "LeadContract(names="+_names+
                ", phone="+_phone+
                ", email="+_email+
                ", website="+_website+
                ", status="+_status+
                ", source="+_source+
                ", industry="+_industry+
                ", description="+_description+")";
    }
}
