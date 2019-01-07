package com.example.frenk.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Frenk on 29-2-2016.
 */

public class ItemAdapter extends ArrayAdapter<ListItem> {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        //Check if the row is new
        if (row == null) {
            //Inflate the layout if it didn't exist yet
            row = inflater.inflate(R.layout.row_item, parent, false);
            //Create a new view holder instance
            holder = new ViewHolder(row);
            //set the holder as a tag so we can get it back later
            row.setTag(holder);
        } else {
            //The row isn't new so we can reuse the view holder
            holder = (ViewHolder) row.getTag();
        }
        //Populate the row
        holder.populateRow(getItem(position));
        return row;
    }

    class ViewHolder {
        private TextView title;
        private TextView description;
        //Initialize the variables
        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
        }
        //Method to set the values in a row
        public void populateRow(ListItem item) {
            //Set the title for this row
            title.setText(item.getTitle());
            //Set the description for this row
            description.setText(item.getDescription());
        }
    }


    private LayoutInflater inflater;

    //Constructor
    public ItemAdapter(Context context, int resource, List<ListItem> objects) {
        super(context, resource, objects);

        //Initialize the layout inflater
        inflater = LayoutInflater.from(getContext());
    }
}

