package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// ***************************************************************
// * OVERVIEW                                                    *
// * This activity class represents the Notes and Other Pests    *
// * page, which happens after the pest sample main areas        *
// ***************************************************************
public class Notes_and_Other_Pests extends Activity{
    Button helpButton;
    EditText editText;
    Spinner spinner;
    String[] arrayOfPests;
    Button cancelButton;

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
        helpButton = (Button)findViewById(R.id.notes_and_other_pests_help); //sets the textview from the xml file
        editText = (EditText)findViewById(R.id.notes_and_other_pests_editable_notes_textbox);
        spinner = (Spinner)findViewById(R.id.notes_and_other_pests_spinner);
        cancelButton = (Button)findViewById(R.id.notes_and_other_pests_cancel_button);
        CreateLayout();
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * This function creates the activity, which was modeled to be *
    // * identical to the desktop version of this page               *
    // ***************************************************************
    private void CreateLayout()
    {
        //Start of onclick listener that shows the help dialog for the help button
        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog alert = new AlertDialog.Builder(Notes_and_Other_Pests.this).create();
                alert.setTitle(getResources().getString(R.string.notes_and_other_pests_help));
                alert.setMessage(getResources().getString(R.string.notes_and_other_pests_help_shown));
                alert.setButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.cancel(); //make sure it goes to last page
                    }
                });
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.show();
            }
        });
        arrayOfPests = getResources().getStringArray(R.array.show_pests_array);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayOfPests);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(spinnerAdapter);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Notes_and_Other_Pests.this, SelectionScreen.class);
                Notes_and_Other_Pests.this.startActivity(myIntent);
            }
        });
    }
}
