package com.example.frenk.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public long addListItem(ListItem item) {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, item.getTitle());
        values.put(MySQLiteHelper.COLUMN_WEBSITE, item.getDescription());
        long insertId = database.insert(MySQLiteHelper.TABLE_LISTITEM, null, values);

        // If the database is open, close it
        if (database.isOpen())
            close();

        return insertId;
    }

    public boolean removeListItem(String name) {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();


        int deleted = database.delete(MySQLiteHelper.TABLE_LISTITEM, MySQLiteHelper.COLUMN_NAME + "=?", new String[]{name});

        // If the database is open, close it
        if (database.isOpen())
            close();

        return deleted > 0;
    }


    public List<ListItem> getList() {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        List<ListItem> list = new ArrayList<>();

        String[] columns = new String[]{MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_WEBSITE};

        Cursor cursor = database.query(false, MySQLiteHelper.TABLE_LISTITEM, columns, "", null, "", "", "", "");

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                String name = cursor.getString(0);
                String url = cursor.getString(1);

                list.add(new ListItem(name, url));

            } while (cursor.moveToNext());

        }
        // If the database is open, close it
        if (database.isOpen())
            close();

        return list;
    }

    public ListItem get(String name) {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ListItem item;

        String[] columns = this.assignmentAllColumns;

        Cursor cursor = database.query(true, MySQLiteHelper.TABLE_LISTITEM, columns, MySQLiteHelper.COLUMN_NAME + "=?", new String[]{name}, "", "", "", "");

        cursor.moveToFirst();

        do {
            String nameColumn = cursor.getString(0);
            String urlColumn = cursor.getString(1);

            item = new ListItem(nameColumn, urlColumn);

        } while (cursor.moveToNext());

        // If the database is open, close it
        if (database.isOpen())
            close();

        return item;
    }

    public boolean update(String oldTitle, ListItem item) {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, item.getTitle());
        values.put(MySQLiteHelper.COLUMN_WEBSITE, item.getDescription());

        int updated = database.update(MySQLiteHelper.TABLE_LISTITEM, values, MySQLiteHelper.COLUMN_NAME+"=?", new String[]{oldTitle});

        // If the database is open, close it
        if (database.isOpen())
            close();

        return updated > 0;
    }
}


