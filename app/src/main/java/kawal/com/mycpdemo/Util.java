package kawal.com.mycpdemo;

import android.net.Uri;

/**
 * Created by kawaldeep on 4/11/2017.
 */

public class Util {
    public static final int DB_VERSION =1;
    public static final String DB_Name = "Students.db";

    public static final String TAB_NAME = "Student";
    public static final String COL_ID = "_ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_PHONE= "PHONE";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_GENDER = "GENDER";
    public static final String COL_CITY = "CITY";

    public static final String CREATE_TAB_QUERY = "create table Student("+
            "_ID integer primary key autoincrement,"+
            "NAME varchar(256),"+
            "PHONE varchar(20),"+
            "EMAIL varchar(256),"+
            "GENDER varchar(10),"+
            "CITY varchar(256)"+
            ")";

    public static final Uri STUDENT_URI = Uri.parse("content://kawal.com.mycpdemo.studentprovider/"+TAB_NAME);

}
