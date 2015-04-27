package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
    String[] arrayOfPests;
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

        arrayOfPests = getResources().getStringArray(R.array.show_pests_array);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayOfPests);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(spinnerAdapter);

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

            }
        });


    }
}