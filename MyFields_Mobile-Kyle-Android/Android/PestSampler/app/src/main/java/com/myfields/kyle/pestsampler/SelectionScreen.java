package com.myfields.kyle.pestsampler;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

//class that represents the selection screen that is show after login
public class SelectionScreen extends Activity {
    ListView listView; //listview that will be implemented
    private GetAllFields api; //information to pull in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);

        listView = (ListView) findViewById(R.id.selection_list); //gets the listview from the layout xml

		//header to be shown above the list items
        TextView header = new TextView(this);
        header.setText("Select an option: ");
        listView.addHeaderView(header);

        String[] selectionList = getResources().getStringArray(R.array.selection_info_list); //gets the selection list from the string array in the selection info list string resource file

		//sets the adapter with the selection list (this sets the listview with the selectionList)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);

        listView.setAdapter(adapter); //sets the adapter

		//when an item on the listview is clicked, it takes it to another activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //goes to fields list
                if (position == 1) {
                    Intent myIntent = new Intent(SelectionScreen.this, FieldsList.class);
                    SelectionScreen.this.startActivity(myIntent);
                }
                //goes to pest sampler
                if (position == 2)
                {
                    Intent myIntent = new Intent(SelectionScreen.this, PS_Sample_Method.class);
                    SelectionScreen.this.startActivity(myIntent);
                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferences myCredentials = getSharedPreferences("Credentials", Context.MODE_PRIVATE);

        if(!(myCredentials.contains("user"))) //if the user isn't valid, takes you to the login page
        {
            Intent myIntent = new Intent(SelectionScreen.this, LoginActivity.class);
            SelectionScreen.this.startActivity(myIntent);
        }
        else if(Globals.currentUser == null) //if the current user is now, it sets the user and password to null
        {

            Globals.currentUser = new User(myCredentials.getString("user", null),
                    myCredentials.getString("pass", null));

            // Show a progress spinner, and kick off a background task to
            // perform the field retrieval attempt.
            ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Loading...");
            progress.setMessage("Please wait while we load your information.");
            progress.show();

            api = new GetAllFields(Globals.currentUser.getFields(),
                    Globals.currentUser.getUserName(),
                    Globals.currentUser.getUserPassword(),
                    progress);
            api.execute((Void) null);
        }
    }
}