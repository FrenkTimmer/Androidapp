package com.example.frenk.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView description;

    //Initialize the variables
    public ViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
    }

    //Method to set the values in a row
    public void populateRow(ListItem item) {

        //Set the title for this row
        title.setText(item.getTitle());

        //Set the description for this row
        description.setText(item.getDescription());
    }
}