package com.example.frenk.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {


    private Button addButton;
    private EditText titleEditText, descriptionEditText, oldTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_item);
        //Initialize layout items
        addButton = (Button) findViewById(R.id.edit_item_button);
        titleEditText = (EditText) findViewById(R.id.edit_item_title);
        descriptionEditText = (EditText) findViewById(R.id.edit_item_description);
        oldTitle = (EditText) findViewById(R.id.edit_item_old_title);


        // Set texts to db values
        Bundle bundle = this.getIntent().getExtras();

        titleEditText.setText(bundle.getString("title"));
        descriptionEditText.setText(bundle.getString("website"));
        oldTitle.setText(bundle.getString("title"));

        //Handle the button click
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if the title and descriptions have text
                if (!TextUtils.isEmpty(titleEditText.getText()) && !TextUtils.isEmpty(descriptionEditText.getText())) {

                    //Create a new intent with the entered data
                                    Intent data = new Intent();
                    data.putExtra("title", titleEditText.getText().toString());
                    data.putExtra("oldTitle", oldTitle.getText().toString());
                    data.putExtra("description", descriptionEditText.getText().toString());

                    //Send the result back to the activity
                    setResult(Activity.RESULT_OK, data);

                    //Finish this activity
                    finish();
                } else {
                    //Show a message to the user
                    Toast.makeText(EditItemActivity.this, "Please fill in a name and url", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
