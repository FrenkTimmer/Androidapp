package com.example.frenk.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewItemActivity extends AppCompatActivity {


    private Button addButton;
    private EditText titleEditText, descriptionEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_item);
        //Initialize layout items
        addButton = (Button) findViewById(R.id.new_item_add);
        titleEditText = (EditText) findViewById(R.id.new_item_title);
        descriptionEditText = (EditText) findViewById(R.id.new_item_description);


        //Create the spinner items
        String[] spinnerItems = {"Game1", "Game2", "Game3"};

        //Create the spinner adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerItems);

        //Handle the button click
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if the title and descriptions have text
                if (!TextUtils.isEmpty(titleEditText.getText()) && !TextUtils.isEmpty(descriptionEditText.getText())) {

                    //Create a new intent with the entered data
                    Intent data = new Intent();
                    data.putExtra("title", titleEditText.getText().toString());
                    data.putExtra("description", descriptionEditText.getText().toString());

                    //Send the result back to the activity
                    setResult(Activity.RESULT_OK, data);

                    //Finish this activity
                    finish();
                } else {
                    //Show a message to the user
                    Toast.makeText(NewItemActivity.this, "Please fill in a name and url", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
