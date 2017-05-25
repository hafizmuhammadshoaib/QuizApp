package com.example.shoaibsilat.quizapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Shoaib Silat on 4/22/2017.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }
    public List<String> getOptions(int id){
        int column=0;
        List <String> list =new ArrayList<>();

        Cursor cursor=database.rawQuery("select optA,optB,optC,optD from "+SelectionActivity.tableName+" where tid="+id,null);
        cursor.moveToFirst();

        while(column<=3){
            list.add(cursor.getString(column));
            column++;
        }
        Collections.shuffle(list);
        return list;
    }
    public String getQues(int id){
        Cursor cursor=database.rawQuery("select ques from "+SelectionActivity.tableName+" where tid="+id ,null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }
    public String getAns(int id){
        Cursor cursor=database.rawQuery("select ans from "+SelectionActivity.tableName+" where tid="+id,null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

}
