package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

// ***************************************************************
// * OVERVIEW                                                    *
// * This activity class represents the Notes and Other Pests    *
// * page, which happens after the pest sample main areas        *
// ***************************************************************
public class PS_Notes_and_Other_Pests extends Activity{
    Button helpButton;
    EditText editText;
    Spinner spinner;
    Button cancelButton;
    Button backButton;
    Button finishButton;

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * This function creates the activity for the Notes and Other  *
    // * Pests page. This includes the show notes, show pests,       *
    // * and the textbox, lists, and images associated with them.    *
    // * Keep in mind it doesn't actually create, it CALLS the other *
    // * method that actually creates everything                     *
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
        setContentView(R.layout.activity_notes_and_other_pests); // sets the look to the layout file

        editText = (EditText)findViewById(R.id.notes_and_other_pests_editable_notes_textbox);
        spinner = (Spinner)findViewById(R.id.notes_and_other_pests_spinner);

        helpButton = (Button)findViewById(R.id.notes_and_other_pests_help); //sets the textview from the xml file
        cancelButton = (Button)findViewById(R.id.notes_and_other_pests_cancel_button);
        backButton = (Button) findViewById(R.id.notes_and_other_pests_back_button);
        finishButton = (Button) findViewById(R.id.notes_and_other_pests_finish_button);

        CreateLayout();
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * This function creates the activity, which was modeled to be *
    // * identical to the desktop version of this page               *
    // ***************************************************************
    private void CreateLayout()
    {

        String[] arrayOfPests = getResources().getStringArray(R.array.show_pests_array);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayOfPests);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedPest = parent.getItemAtPosition(position).toString();

                if(!selectedPest.equals("Select a pest"))
                {
                    if(!Globals.sampleToBuild.checkForOtherPest(selectedPest))
                    {
                        Globals.sampleToBuild.addOtherPest(selectedPest);
                    }
                    else
                    {
                        Globals.sampleToBuild.removeOtherPest(selectedPest);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Display an alert dialog with the help text as defined in the Resource values
                new AlertDialog.Builder(PS_Notes_and_Other_Pests.this)
                        .setTitle(getResources().getString(R.string.notes_and_other_pests_help))
                        .setMessage(getResources().getString(R.string.notes_and_other_pests_help_shown))
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
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

                // Cancelling the sample, nullify the one being built
                Globals.sampleToBuild = null;

                // Return to the main menu page
                Intent myIntent = new Intent(PS_Notes_and_Other_Pests.this, SelectionScreen.class);
                PS_Notes_and_Other_Pests.this.startActivity(myIntent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Reset Notes and Other Pests to defaults
            Globals.sampleToBuild.setNotes("");
            Globals.sampleToBuild.setOtherPests(new ArrayList<String>());

            // Return to the main menu page
            finish();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editString = editText.getText().toString();

                if(!(editString == null) && !editString.equals("Show Notes"))
                {
                    Globals.sampleToBuild.setNotes(editText.getText().toString());
                }
                int FieldID = Globals.sampleToBuild.getFieldID();
                Field fieldToAdd = Globals.currentUser.getFieldByID(FieldID);
                int FieldIndex = Globals.currentUser.hasField(FieldID);

                fieldToAdd.addSample(Globals.sampleToBuild);
                int SampleIndex = fieldToAdd.hasSample(Globals.sampleToBuild);

                Intent mySampleIntent = new Intent(PS_Notes_and_Other_Pests.this, PestSamplesSpecificInfo.class);
                mySampleIntent.putExtra("FieldIndex", FieldIndex);
                mySampleIntent.putExtra("SampleIndex", SampleIndex);

                Globals.sampleToBuild = null;

                PS_Notes_and_Other_Pests.this.startActivity(mySampleIntent);
            }
        });


    }
}
