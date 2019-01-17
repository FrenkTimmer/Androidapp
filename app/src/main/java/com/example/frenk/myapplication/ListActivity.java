package com.example.frenk.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private List<ListItem> items;
    private RecyclerView recyclerView;
    private DataSource source;
    private ListItem currentListItem;
    private int currentPosition;
    private View currentView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this.source = new DataSource(this);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get recycler view
        recyclerView = (RecyclerView) findViewById(R.id.listView);

        // Get items from database
        items = this.source.getList();

        // Create new linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // Set recycler view layout manager to the above manager
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



        // Initialize ItemAdapter (RecycleViewAdapter)
        adapter = new ItemAdapter(items, new ItemAdapter.CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                //Create an Intent
                Intent intent = new Intent(ListActivity.this, WebviewActivity.class);

                // Get list item
                ListItem clickedItem = adapter.getItemAt(position);

                // Set intent description
                intent.putExtra("description", clickedItem.getDescription());

                //Open the new screen by starting the activity
                startActivity(intent);
            }
        }, new ItemAdapter.CustomItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                ViewHolder viewHolder = (ViewHolder) recyclerView.findViewHolderForItemId(v.getId());
                //int position = viewHolder.getLayoutPosition();
                currentListItem = items.get(position);
                currentPosition = position;

                openContextMenu(v);
            }
        });

        // Set adapter
        recyclerView.setAdapter(adapter);

        registerForContextMenu(recyclerView);

        // + action button to add
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get intent
                Intent intent = new Intent(view.getContext(), NewItemActivity.class);

                // Start new item activity
                startActivityForResult(intent, 1234);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check if the result code is the right one
        if (resultCode == Activity.RESULT_OK) {
            // Check if the request code is correct
            if (requestCode == 1234) {
                // Everything's fine, get the values;
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");

                // Create a list item from the values
                ListItem item = new ListItem(title, description);

                // Add item to db
                this.source.addListItem(item);

                // Add the new item to the adapter;
                items.add(item);

                // Update adapter item list
                adapter.setItems(this.items);

                // Have the adapter update
                adapter.notifyDataSetChanged();


                // Set recycler view visibility to visible if necessary
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            } else if (requestCode == 4321){
                // Everything's fine, get the values;
                String title = data.getStringExtra("title");
                String oldTitle = data.getStringExtra("oldTitle");
                String website = data.getStringExtra("description");

                // Update in db
                this.source.update(oldTitle, new ListItem(title, website));

                // Update to updated list
                this.items = this.source.getList();

                // Update adapter item list
                adapter.setItems(this.items);

                adapter.notifyItemRemoved(currentPosition - 1);
                adapter.notifyItemRangeChanged(0, this.items.size());

                // Have the adapter update
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * MENU
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                // edit stuff here

                // TODO edit in db
                // Get intent
                Intent intent = new Intent(this.recyclerView.getContext(), EditItemActivity.class);

                intent.putExtra("title", this.currentListItem.getTitle());
                intent.putExtra("website", this.currentListItem.getDescription());

                // Start new item activity
                startActivityForResult(intent, 4321);
                return true;
            case R.id.delete:

                if (!this.source.removeListItem(currentListItem.getTitle())) return false;

                // Change list to items from db (to remove the item from the in-memory list)
                this.items = this.source.getList();

                // Set adapter items
                adapter.setItems(items);

                // Notify change so the item actually gets removed from the screen
                adapter.notifyDataSetChanged();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
