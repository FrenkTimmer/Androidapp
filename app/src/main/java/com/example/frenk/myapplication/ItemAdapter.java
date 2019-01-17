package com.example.frenk.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Frenk on 12-1-2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

    // Items from database
    private List<ListItem> items;

    // Count used for getting item from item list
    private int count;

    // On click listener for items
    private CustomItemClickListener listener;

    // On long click listener for items
    private CustomItemLongClickListener longClickListener;


    public ItemAdapter(List<ListItem> items, CustomItemClickListener listener, CustomItemLongClickListener longClickListener) {

        // Add items from db to private list
        this.items = items;

        // Add click listener
        this.listener = listener;

        // Add long click listener
        this.longClickListener = longClickListener;

        // Set count to 0 to start at the first item in the array
        this.count = 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Get parent context
        Context context = parent.getContext();

        // Get id for current list item
        int layoutIdForListItem = R.layout.row_item;

        // Create layout inflater based on parent context
        LayoutInflater inflater = LayoutInflater.from(context);

        // Get current view
        View row = inflater.inflate(layoutIdForListItem, parent, false);

        // Create new view holder for current row (view)
        final ViewHolder viewHolder = new ViewHolder(row);
        this.bindViewHolder(viewHolder, count);

        // Set on click listener for current row (view)
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = (TextView)v.findViewById(R.id.title);
                for (int i = 0; i < items.size(); i++) {
                    if(items.get(i).getTitle() == t.getText()) {
                        listener.onItemClick(v, i);
                        return;
                    }
                }
            }
        });

        row.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                TextView t = (TextView)v.findViewById(R.id.title);
                for (int i = 0; i < items.size(); i++) {
                    if(items.get(i).getTitle() == t.getText()) {
                        longClickListener.onItemLongClick(v, i);
                        return true;
                    }
                }

            return false;
            }
        });

        // Populate view
        viewHolder.populateRow(this.items.get(count));

        // Update list count, used for positions in array
        count++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Populate row(view)
        holder.populateRow(this.items.get(position));
    }

    @Override
    public int getItemCount() {
        // Get count of items in db
        return this.items.size();
    }

    public ListItem getItemAt(int position) {
        // Get item at position in list
        return this.items.get(position);
    }

    public void setItems(List<ListItem> items) {
        this.items = items;
    }



    public interface CustomItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface CustomItemLongClickListener {
        void onItemLongClick(View v, int position);
    }
}





