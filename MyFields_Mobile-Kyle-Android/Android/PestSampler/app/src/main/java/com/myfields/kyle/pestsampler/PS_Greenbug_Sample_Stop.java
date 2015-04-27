package com.myfields.kyle.pestsampler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

/**
 Greenbug sample stop
 */
public class PS_Greenbug_Sample_Stop extends ActionBarActivity{

    private Button helpButton;
    private TextView stop1TextView;
    private GridView gridView;
    private ArrayAdapter gridViewAdapter;
    boolean[] ViewSelected;
    private int StopNumber;
    private int MaxStops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_greenbug_sample_stop1);

        helpButton = (Button)findViewById(R.id.greenbug_sample_stop1_help_button);
        stop1TextView = (TextView)findViewById(R.id.greenbug_sample_stop1_stop1_textbox);
        gridView = (GridView)findViewById(R.id.greenbug_sample_stop1_gridview);

        ViewSelected = new boolean[6];

        CreateLayout();
    }
    private void CreateLayout() {
        Button continue_Button = (Button) findViewById(R.id.greenbug_sample_stop1_continue_button);
        Button back_Button = (Button) findViewById(R.id.greenbug_sample_stop1_back_button);
        Button cancel_Button = (Button) findViewById(R.id.greenbug_sample_stop1_cancel_button);

        StopNumber = this.getIntent().getIntExtra("Stop", 1);
        MaxStops = this.getIntent().getIntExtra("MaxStop", 5);

        stop1TextView.setText("Stop " + StopNumber + " of " + MaxStops);
        stop1TextView.invalidate();


        gridViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getData());
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 6) // Do not do anything if the header cells are clicked
                {
                    if (position <= 8) // Aphid clicked
                    {
                        if (!ViewSelected[position - 6]) {
                            // If view is not selected, set it selected, color it green
                            // and add 1 to the number of aphids in the sample
                            view.setBackgroundColor(Color.GREEN);
                            ViewSelected[position - 6] = true;
                            ((GreenbugSample) Globals.sampleToBuild).addAphids(1);
                        } else {
                            // Else view must be selected, so set it transparent,
                            // mark it not selected, and subtract one from the aphids
                            view.setBackgroundColor(Color.TRANSPARENT);
                            ViewSelected[position - 6] = false;
                            ((GreenbugSample) Globals.sampleToBuild).addAphids(-1);
                        }
                    } else // Mummy clicked
                    {
                        if (!ViewSelected[position - 6]) {
                            // If view is not selected, set it selected, color it red
                            // and add 1 to the number of mummies in the sample
                            view.setBackgroundColor(Color.RED);
                            ViewSelected[position - 6] = true;
                            ((GreenbugSample) Globals.sampleToBuild).addMummys(1);
                        } else {
                            // Else view must be selected, so set it transparent,
                            // mark it not selected, and subtract one from the mummies
                            view.setBackgroundColor(Color.TRANSPARENT);
                            ViewSelected[position - 6] = false;
                            ((GreenbugSample) Globals.sampleToBuild).addMummys(-1);
                        }
                    }

                    view.invalidate();
                }
            }
        });
        continue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(StopNumber == MaxStops) // Have formed a unit
                {
                    // Try to determine the treatment recommendation
                    GreenbugSample sample = (GreenbugSample) Globals.sampleToBuild;
                    try
                    {
                        sample.determineTreatment(StopNumber, v.getContext());
                    }
                    catch(JSONException j)
                    {
                    }

                    // If the recommendation was indeterminate, either stop or continue sampling
                    // based on the number of stops performed.
                    if(sample.TreatmentRecommendation == GreenbugSample.Greenbug_Sample_Values.Indeterminate)
                    {
                        // If at the max number of stops, we cannot determine a recommendation based
                        // on the input samples, so go on to Notes page.
                        if(MaxStops == 30)
                        {
                            // Is it possible to not be able to determine a recommendation at
                            // the max number of stops?
                            new AlertDialog.Builder(v.getContext())
                                    .setTitle("Treatment Recommendation")
                                    .setMessage("Cannot determine a Treatment Recommendation with the maximum number of samples. " +
                                            "Press continue to go to the Notes pages.")
                                    .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent myIntent = new Intent(PS_Greenbug_Sample_Stop.this, PS_Notes_and_Other_Pests.class);
                                            PS_Greenbug_Sample_Stop.this.startActivity(myIntent);

                                        }
                                    })
                                    .setNegativeButton(R.string.Back, new DialogInterface.OnClickListener() {
                                        // If back is clicked, return to the page
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        // Else we continue with another unit of samples
                        else
                        {
                            // Display an alert that we can't determine the sample recommendation
                            new AlertDialog.Builder(v.getContext())
                                    .setTitle("Treatment Recommendation")
                                    .setMessage("Cannot determine a Treatment Recommendation. Press continue to keep sampling.")
                                    .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        // Build a new Sample Stop with stops + 1 and max stops + 5
                                        Intent myIntent = new Intent(PS_Greenbug_Sample_Stop.this, PS_Greenbug_Sample_Stop.class);
                                        myIntent.putExtra("Stop", StopNumber + 1);
                                        myIntent.putExtra("MaxStop", MaxStops + 5);
                                        PS_Greenbug_Sample_Stop.this.startActivity(myIntent);

                                        }
                                    })
                                    .setNegativeButton(R.string.Back, new DialogInterface.OnClickListener() {
                                        // If back is clicked, return to the page
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }
                    // If the recommendation was not indeterminate, go on to the notes page.
                    else
                    {
                        // Display the recommendation to the user
                        new AlertDialog.Builder(v.getContext())
                                .setTitle("Treatment Recommendation")
                                .setMessage(sample.TreatmentRecommendation.toString().replace('_', ' '))
                                .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        // If continue is pressed, continue to the notes page
                                        Intent myIntent = new Intent(PS_Greenbug_Sample_Stop.this, PS_Notes_and_Other_Pests.class);
                                        PS_Greenbug_Sample_Stop.this.startActivity(myIntent);

                                    }
                                })
                                .setNegativeButton(R.string.Back, new DialogInterface.OnClickListener() {
                                    // If back is clicked, return to the page
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }

                }
                else // Continue sampling
                {
                    // Build a new Sample Stop activity, with + 1 to the stop count and the same
                    // max stops
                    Intent myIntent = new Intent(PS_Greenbug_Sample_Stop.this, PS_Greenbug_Sample_Stop.class);
                    myIntent.putExtra("Stop", StopNumber + 1);
                    myIntent.putExtra("MaxStop", MaxStops);
                    PS_Greenbug_Sample_Stop.this.startActivity(myIntent);
                }
            }
        });

        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Loop through the gridview selectable items, subtract any selected sample values
                for(int i = 0; i < ViewSelected.length; i++)
                {
                    if(ViewSelected[i])
                    {
                        if(i < 3)
                        {
                            ((GreenbugSample) Globals.sampleToBuild).addAphids(-1);
                        }
                        else
                        {
                            ((GreenbugSample) Globals.sampleToBuild).addMummys(-1);
                        }
                    }
                }

                // Finish this activity to display the previous page
                finish();
            }
        });

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the building of the current sample by null-ing it.
                Globals.sampleToBuild = null;

                // Return to the main page.
                Intent myIntent = new Intent(PS_Greenbug_Sample_Stop.this, SelectionScreen.class);
                PS_Greenbug_Sample_Stop.this.startActivity(myIntent);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Display an alert dialog with the help text as defined in the Resource values
                new AlertDialog.Builder(PS_Greenbug_Sample_Stop.this)
                    .setTitle(getResources().getString(R.string.greenbug_sample_stop1_help_shown))
                    .setMessage(getResources().getString(R.string.greenbug_sample_stop1_help_expanded))
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            }
        });

    }


    private ArrayList<String> getData() {
        // Build the gridview items array, 3 columns per row.
        final ArrayList<String> imageItems = new ArrayList<>();
        imageItems.add("Tiller 1");
        imageItems.add("Tiller 2");
        imageItems.add("Tiller 3");

        imageItems.add("--------");
        imageItems.add("--------");
        imageItems.add("--------");

        imageItems.add("Aphid");
        imageItems.add("Aphid");
        imageItems.add("Aphid");

        imageItems.add("Mummy");
        imageItems.add("Mummy");
        imageItems.add("Mummy");

        return imageItems;

    }
}