package com.example.todoapp.DbWrapper;
import android.database.sqlite.SQLiteDatabase;

public class DbWrapperSetup {


    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "TODO_DATABASE";
    public static final String TBL_NAME = "TODO_TABLE";
    public static final String COL_UID = "UID";
    public static final String COL_NAME = "NAME";
    public static final String COL_STATUS = "STATUS";
    public static final String COL_INCREMENTAL = "ID";
    public static final String Q_TBL_TASK_CREATE =
            "CREATE TABLE " + TBL_NAME + " (" +
                    COL_INCREMENTAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_UID + " TEXT, " +
                    COL_NAME + " TEXT, " +
                    COL_STATUS + " INTEGER " +
                    ") ";


    public static void onCreate(SQLiteDatabase database){ database.execSQL(Q_TBL_TASK_CREATE);}

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(database);
    }
}
