package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

// ***************************************************************
// * OVERVIEW                                                    *
// * This activity class represents the Pest Sampler second page,*
// *  where a user selects a field that this pest sample will    *
// * be associated with.                                         *
// ***************************************************************
public class PS_Field_List extends Activity {

    ListView listView;
    Button backButton, cancelButton;

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * This function creates the activity for the field list page  *
    // * of the pest sampler, including setting the layout to the    *
    // * resource layout, adding a text header, and creating the     *
    // * list of fields from the global user's.                      *
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

        View focusView = null;

        setContentView(R.layout.activity_ps_field_list);

        listView = (ListView) findViewById(R.id.ps_field_list);

        TextView header = new TextView(this);
        header.setText("Select A Field To Sample: ");
        listView.addHeaderView(header);

        createListView();
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * This function will add each user field to the created       *
    // * ListView and adds the event handler for a click event.      *
    // ***************************************************************
    private void createListView() {

        backButton = (Button)findViewById(R.id.ps_field_list_back_button);
        cancelButton = (Button)findViewById(R.id.ps_field_list_home_button);

        ArrayList<Field> fieldsList = Globals.currentUser.getFields();
        final String[] selectionList = new String[fieldsList.size()];

        for (int i = 0; i < fieldsList.size(); i++) {
            selectionList[i] = fieldsList.get(i).Name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Globals.sampleToBuild.setFieldID(Globals.currentUser.getFields().get(position - 1).getID());
                Globals.sampleToBuild.setLocation(Globals.currentUser.getFields().get(position - 1).getLocation());

                Intent myIntent = new Intent(PS_Field_List.this, PS_Control_Cost_and_Crop_Value.class);
                PS_Field_List.this.startActivity(myIntent);
            }
        });
        // On back click, return to the previous activity by finishing this one.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.sampleToBuild = null;

                Intent myIntent = new Intent(PS_Field_List.this, SelectionScreen.class);
                PS_Field_List.this.startActivity(myIntent);
            }
        });
    }
}
