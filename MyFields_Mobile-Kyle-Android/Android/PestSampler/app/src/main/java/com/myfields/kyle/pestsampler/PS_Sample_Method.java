package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
// * This activity class represents the Pest Sampler first page, *
// * the sample method page where a user selects what pest they  *
// * will be sampling for.                                       *
// ***************************************************************
public class PS_Sample_Method extends Activity{

    ListView listView;
    Button cancelButton;

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * This function creates the activity for the sample method    *
    // * page, including setting the layout to the resource layout,  *
    // * adding a text header, and creating the list of methods.     *
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

        setContentView(R.layout.activity_ps_sample_method);

        listView = (ListView) findViewById(R.id.ps_sample_method);

        TextView header = new TextView(this);
        header.setText("Select A Sample Method: ");
        listView.addHeaderView(header);

        createListView();
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * This function will add each sample method to the created    *
    // * ListView and adds the event handler for a click event.      *
    // ***************************************************************
    public void createListView()
    {

        cancelButton = (Button)findViewById(R.id.ps_sample_method_home_button);

        final Resources res = getResources();
        final String[] sampleMethods = res.getStringArray(R.array.pest_sampler_methods);

        ArrayList<Field> fieldsList = Globals.currentUser.getFields();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, sampleMethods );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position - 1; // Position is 1-indexed

                // Show sample description with option to Continue or Go Back
                new AlertDialog.Builder(parent.getContext())
                        .setTitle("Sample Method Description")
                        .setMessage(res.getStringArray(R.array.pest_sampler_descriptions)[pos]) // Position is 1-indexed
                        .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // If continue is clicked, construct the global sample
                                if (Globals.sampleToBuild != null) {
                                    Globals.sampleToBuild = null;
                                }

                                // To add samples, add another case here
                                switch (sampleMethods[pos]) {
                                    case ("Glance N\' Go (Greenbug)"):
                                        Globals.sampleToBuild = new GreenbugSample();
                                        Globals.sampleToBuild.setID(0); // Mark as 0 so database can auto-generate later
                                        ((GreenbugSample) Globals.sampleToBuild).setSpecificID(0);
                                        Intent myIntent = new Intent(PS_Sample_Method.this, PS_Field_List.class);
                                        PS_Sample_Method.this.startActivity(myIntent);
                                        break;
                                }
                            }
                        })
                        .setNegativeButton(R.string.Back, new DialogInterface.OnClickListener() {
                            // If back is clicked, return to the list of samples.
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.sampleToBuild = null;

                Intent myIntent = new Intent(PS_Sample_Method.this, SelectionScreen.class);
                PS_Sample_Method.this.startActivity(myIntent);
            }
        });
    }
}
