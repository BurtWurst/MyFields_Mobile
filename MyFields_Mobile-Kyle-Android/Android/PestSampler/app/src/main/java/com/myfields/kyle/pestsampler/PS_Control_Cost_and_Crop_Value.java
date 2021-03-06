package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

// ***************************************************************
// * OVERVIEW                                                    *
// * This activity class represents the Pest Sampler control     *
// * cost and crop value page, where a user selects what the     *
// * field's crop is worth (per bushel?) and how much it costs   *
// * to treat for the selected pest.                             *
// ***************************************************************
public class PS_Control_Cost_and_Crop_Value extends Activity{

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * This function creates the activity for the costs page of    *
    // * the pest sampler, including setting the layout to the       *
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

    Button helpButton;
    Spinner control_cost_spinner;
    Spinner crop_value_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ps_control_cost_crop_value);

        CreateLayout();
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * This function will add the list of control costs and crop   *
    // * values specified in the resources to the dropdowns, add the *
    // * buttons, and add event handlers for each button.            *
    // ***************************************************************
    private void CreateLayout()
    {
        helpButton = (Button)findViewById(R.id.ps_control_cost_crop_value_help_button);

        control_cost_spinner = (Spinner)findViewById(R.id.control_cost_spinner);
        String[] control_cost_array = getResources().getStringArray(R.array.pest_sampler_control_costs);

        crop_value_spinner = (Spinner)findViewById(R.id.crop_value_spinner);
        String[] crop_value_array = getResources().getStringArray(R.array.pest_sampler_crop_values);

        Button continue_Button = (Button)findViewById(R.id.ps_control_cost_crop_value_continue_button);
        Button back_Button = (Button)findViewById(R.id.ps_control_cost_crop_value_back_button);
        Button cancel_Button = (Button)findViewById(R.id.ps_control_cost_crop_value_cancel_button);


        final ArrayAdapter<String> control_cost_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, control_cost_array);
        control_cost_spinner.setAdapter(control_cost_adapter);

        ArrayAdapter<String> crop_value_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, crop_value_array);
        crop_value_spinner.setAdapter(crop_value_adapter);

        continue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If user has not selected values, display error
                if(control_cost_spinner.getSelectedItem().toString().equals("- None -") ||
                   crop_value_spinner.getSelectedItem().toString().equals("- None -"))
                {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Error - Bad Values!")
                            .setMessage("Please select values for both Crop Value and Control Cost!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Simply return to the costs page
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                // Else add the values to the current sample and move to the samples page.
                else
                {
                    Globals.sampleToBuild.CropValue = Double.parseDouble(crop_value_spinner.getSelectedItem().toString());
                    Globals.sampleToBuild.ControlCost = Double.parseDouble(control_cost_spinner.getSelectedItem().toString());

                    Intent myIntent = new Intent(PS_Control_Cost_and_Crop_Value.this, PS_Greenbug_Sample_Stop.class);
                    PS_Control_Cost_and_Crop_Value.this.startActivity(myIntent);
                }
            }
        });

        // On back click, return to the previous activity by finishing this one.
        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Reset values to defaults
                Globals.sampleToBuild.setControlCost(0.0);
                Globals.sampleToBuild.setCropValue(0.0);

                finish();
            }
        });

        // On cancel click, void the current sample and return to the SelectionScreen page.
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.sampleToBuild = null;

                Intent myIntent = new Intent(PS_Control_Cost_and_Crop_Value.this, SelectionScreen.class);
                PS_Control_Cost_and_Crop_Value.this.startActivity(myIntent);
            }
        });
        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog alert = new AlertDialog.Builder(PS_Control_Cost_and_Crop_Value.this).create();
                alert.setTitle(getResources().getString(R.string.ps_control_cost_crop_value_help_button));
                alert.setMessage(getResources().getString(R.string.ps_control_cost_crop_value_help_shown_text));
                alert.setButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.cancel(); //make sure it goes to last page
                    }
                });
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.show();
            }
        });
    }
}
