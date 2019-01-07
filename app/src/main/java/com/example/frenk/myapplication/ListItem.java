package com.example.frenk.myapplication;

/**
 * Created by Frenk on 29-2-2016.
 */
public class ListItem {
    private String title;
    private String description;

    //Constructor
    public ListItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
