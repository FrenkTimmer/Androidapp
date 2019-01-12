package com.example.frenk.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Frenk on 27-3-2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DATABASE_NAME = "wishlist.db";
    private static final int DATABASE_VERSION = 1;

    // Assignments
    public static final String TABLE_LISTITEM = "listitem";
    public static final String COLUMN_LISTITEM_ID = "listitem_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_WEBSITE = "website";

    // Creating the table
    private static final String DATABASE_CREATE_LISTITEM =
            "CREATE TABLE " + TABLE_LISTITEM +
                    "(" +
                    COLUMN_LISTITEM_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text not null, " +
                    COLUMN_WEBSITE + " text not null" +
                    ");";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Execute the sql to create the table assignments
        database.execSQL(DATABASE_CREATE_LISTITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	/*
     	* When the database gets upgraded you should handle the update to make sure there is no data loss.
     	* This is the default code you put in the upgrade method, to delete the table and call the oncreate again.
     	*/
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTITEM);
        onCreate(db);
    }
}
