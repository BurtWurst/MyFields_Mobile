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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
Sample Method Page for Pest Sampler
 */
public class PS_Sample_Method extends Activity{

    ListView listView;

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
    public void createListView()
    {
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
                        if(Globals.sampleToBuild != null)
                        {
                            Globals.sampleToBuild = null;
                        }

                        switch(sampleMethods[pos])
                        {
                            case("Glance N\' Go (Greenbug)"):
                                Globals.sampleToBuild = new GreenbugSample();
                                Globals.sampleToBuild.setID(5); //TODO: Fix this to dynamically determine next index
                                Intent myIntent = new Intent(PS_Sample_Method.this, PS_Field_List.class);
                                PS_Sample_Method.this.startActivity(myIntent);
                                break;
                        }


                    }
                })
                        .setNegativeButton(R.string.Back, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });
    }
}
