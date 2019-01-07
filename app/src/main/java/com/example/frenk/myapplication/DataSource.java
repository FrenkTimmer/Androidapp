package com.example.frenk.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Frenk on 27-3-2016.
 */
public class DataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] assignmentAllColumns = { MySQLiteHelper.COLUMN_LISTITEM_ID, MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_WEBSITE };

    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    // Opens the database to use it
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Closes the database when you no longer need it
    public void close() {
        dbHelper.close();
    }

    public long createAssignment(String name, String website) {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_WEBSITE, website);
        long insertId = database.insert(MySQLiteHelper.TABLE_LISTITEM, null, values);

        // If the database is open, close it
        if (database.isOpen())
            close();

        return insertId;
    }

}


