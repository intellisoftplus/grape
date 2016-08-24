package com.intellisoftplus.grape.db.contracts;

import android.provider.BaseColumns;

/**
 * Created by User on 8/24/2016.
 */
public class LeadContract {
    public LeadContract(){}
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
}
