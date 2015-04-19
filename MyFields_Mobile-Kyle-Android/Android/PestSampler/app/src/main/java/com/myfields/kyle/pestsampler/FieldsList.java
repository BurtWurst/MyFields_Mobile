package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


// ***************************************************************
// * OVERVIEW                                                    *
// * This activity class represents the Fields List page, which  *
// * shows the fields & names along with what type of field it is*
// ***************************************************************

public class FieldsList extends Activity {
    private ListView listView; //listview used to create the layout

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * This function creates the activity for the Fields List page.*
    // * It calls the createListView method which does the work      *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * savedInstanceState                                          *
    // *    This parameter specifies any data that was saved by a    *
    // *    previous run of the activity; i.e. the last time that    *
    // *    onSaveInstanceState was called; otherwise null.          *
    // ***************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fields_list);

        listView = (ListView) findViewById(R.id.selection_list);

        createListView();
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * This function creates the activity, which is a list view    *
    // * that loads in field info from the api then puts it into     *
    // * the api                                                     *
    // ***************************************************************
    private void createListView() {

        ArrayList<Field> fieldsList = Globals.currentUser.getFields(); //gets the fieldsList from the current user
        final String[] selectionList = new String[fieldsList.size()]; //gets the size of the field list

        //puts the fields list info into selectionList
        for (int i = 0; i < fieldsList.size(); i++) {
            selectionList[i] = fieldsList.get(i).Name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent myIntent = new Intent(FieldsList.this, SpecificFieldInfo.class);
                myIntent.putExtra("FieldIndex", position);
                FieldsList.this.startActivity(myIntent);
            }
        });
    }
}