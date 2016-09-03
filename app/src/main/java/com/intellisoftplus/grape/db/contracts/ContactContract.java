package com.intellisoftplus.grape.db.contracts;

import android.provider.BaseColumns;

/**
 * Created by frank on 9/3/16.
 */
public class ContactContract {
    private String _name, _number;
    private int _id;

    public ContactContract(int id, String name, String number){
        this._id=id;
        this._name = name;
        this._number=number;
    }
    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "Contact";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NUMBER = "number";
    }

    public String get_name() {
        return _name;
    }

    public String get_number() {
        return _number;
    }

    public int getId() {
        return _id;
    }

    @Override
    public String toString() {
        return "ContactContract("+
                "name="+_name+", number="+_number+")";
    }
}
