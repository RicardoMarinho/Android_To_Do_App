package com.example.todoapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todoapp.DbWrapper.DbWrapperSetup;
import com.example.todoapp.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DbWrapperSetup.DB_NAME, null, DbWrapperSetup.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DbWrapperSetup.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DbWrapperSetup.onUpgrade(db, oldVersion, newVersion);
    }

    private void openDatabase() { database = this.getWritableDatabase(); }

    public boolean Add(Task task){
        if(database == null) openDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbWrapperSetup.COL_UID, task.get_uid());
        contentValues.put(DbWrapperSetup.COL_NAME, task.get_name());
        contentValues.put(DbWrapperSetup.COL_STATUS, task.get_status());
        long autoNumber = database.insert(DbWrapperSetup.TBL_NAME, null, contentValues);
        return (autoNumber > 0) ? true : false;
    }

    public boolean Update(Task task){
        if(database == null) openDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbWrapperSetup.COL_UID, task.get_uid());
        contentValues.put(DbWrapperSetup.COL_NAME, task.get_name());
        contentValues.put(DbWrapperSetup.COL_STATUS, task.get_status());
        long numRowsAffected = database.update(DbWrapperSetup.TBL_NAME, contentValues, DbWrapperSetup.COL_UID + "= ?",
                new String[]{
                        String.valueOf(task.get_uid())
                });

        return (numRowsAffected > 0) ? true : false;
    }

    public boolean updateStatus(Task task, int status){
        if(database == null) openDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbWrapperSetup.COL_STATUS, status);
        long numRowsAffected = database.update(DbWrapperSetup.TBL_NAME, contentValues, DbWrapperSetup.COL_UID + "= ?",
                new String[]{
                        String.valueOf(task.get_uid())
                });

        return (numRowsAffected > 0) ? true : false;
    }

    public boolean Delete(Task task) {
        if(database == null) openDatabase();

        long numRowsAffected = database.delete(DbWrapperSetup.TBL_NAME,DbWrapperSetup.COL_UID + "= ?",
                new String[]{
                        String.valueOf(task.get_uid())
                });

        return (numRowsAffected > 0) ? true : false;
    }

    public List<Task> List() {

        if(database == null) openDatabase();
        List<Task> taskList = new ArrayList<Task>();

        String query = "SELECT * FROM " + DbWrapperSetup.TBL_NAME;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {

                String uid = cursor.getString(cursor.getColumnIndex(DbWrapperSetup.COL_UID));
                Task task = new Task(uid);
                task.set_name(cursor.getString(cursor.getColumnIndex(DbWrapperSetup.COL_NAME)));
                task.set_status(cursor.getInt(cursor.getColumnIndex(DbWrapperSetup.COL_STATUS)));

                taskList.add(task);
            } while (cursor.moveToNext());
        }

        return taskList;
    }
}
