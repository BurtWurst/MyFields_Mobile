package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
After you are on the field page (that shows a field name, and what type of field it is), you click on one, and it will take you to a screen that shows a picture of that field, and a description.
 This class represents that page.
 */
public class SpecificFieldInfo extends Activity {

    private User currentUser;
    private GetAllFields api;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(currentUser == null)
        {
            SharedPreferences myCredentials = getSharedPreferences("Credentials", Context.MODE_PRIVATE);

            currentUser = new User(myCredentials.getString("user", null),
                    myCredentials.getString("pass", null));
        }

        boolean cancel = false;

        View focusView = null;
        setContentView(R.layout.activity_specific_field_info);

        listView = (ListView) findViewById(R.id.list_of_information);

        if (cancel) {
            // There was an error; don't attempt field retrieval and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            api = new GetAllFields(currentUser.getFields(), currentUser.getUserName(),
                    currentUser.getUserPassword());
            api.execute((Void) null);
        }

        CreateListView();
    }
    public void CreateListView()
    {

        try
        {
            api.get(10000, TimeUnit.MILLISECONDS);
        }
        catch(Exception e)
        {

        }
        ArrayList<Field> fieldsList = currentUser.getFields();
        final String[] selectionList = new String[fieldsList.size()];

        for (int i = 0; i < fieldsList.size(); i++) {
            selectionList[i] = "Field Name: " + fieldsList.get(i).Name;
            selectionList[i].concat("ID: " + fieldsList.get(i).ID + "\n");
            selectionList[i].concat("Location: " + fieldsList.get(i).Location + "\n");
            selectionList[i].concat("Field Size: " + fieldsList.get(i).Size + "\n");
            selectionList[i].concat("Soil Type: " + fieldsList.get(i).SoilType + "\n");
            selectionList[i].concat("Tillage System: " + fieldsList.get(i).TillageSystem + "\n");
            selectionList[i].concat("Irrigation System: " + fieldsList.get(i).IrrigationSystem + "\n");
        }

        //listView = (ListView) findViewById(R.id.selection_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter);
    }
}
