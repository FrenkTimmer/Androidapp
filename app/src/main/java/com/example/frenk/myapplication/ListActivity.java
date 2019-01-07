package com.example.frenk.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private List<ListItem> items;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        items = new ArrayList<ListItem>();

        adapter = new ItemAdapter(this, R.layout.row_item, items);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        items.add(new ListItem("Google", "www.google.nl"));
        items.add(new ListItem("Facebook", "www.facebook.com"));
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Create an Intent
                Intent intent = new Intent(ListActivity.this, WebviewActivity.class);
                ListItem clickedItem = (ListItem) parent.getItemAtPosition(position);
                intent.putExtra("description", clickedItem.getDescription());
                //Open the new screen by starting the activity
                startActivity(intent);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewItemActivity.class);
                startActivityForResult(intent, 1234);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check if the result code is the right one
        if (resultCode == Activity.RESULT_OK) {
            //Check if the request code is correct
            if (requestCode == 1234) {
                //Everything's fine, get the values;
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");

                //Create a list item from the values
                ListItem item = new ListItem(title, description);

                //Add the new item to the adapter;
                items.add(item);

                //Have the adapter update
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        //Get the clicked item

        //Inflate the context menu from the resource file
        getMenuInflater().inflate(R.menu.context_menu, menu);


        //Let Android do its magic
        super.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Retrieve info about the long pressed list item
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.context_menu_delete_item) {
            //Remove the item from the list
            items.remove(itemInfo.position);

            //Update the adapter to reflect the list change
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
    }
}
