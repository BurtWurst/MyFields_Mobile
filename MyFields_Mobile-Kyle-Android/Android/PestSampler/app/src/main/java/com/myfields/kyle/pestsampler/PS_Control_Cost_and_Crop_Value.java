package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 Control cost/crop value page for pest sampler
 */
public class PS_Control_Cost_and_Crop_Value extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ps_control_cost_crop_value);

        CreateLayout();
    }
    private void CreateLayout()
    {
        Spinner control_cost_spinner = (Spinner)findViewById(R.id.control_cost_spinner);
        String[] control_cost_array = getResources().getStringArray(R.array.pest_sampler_control_costs);

        Spinner crop_value_spinner = (Spinner)findViewById(R.id.crop_value_spinner);
        String[] crop_value_array = getResources().getStringArray(R.array.pest_sampler_crop_values);

        Button continue_Button = (Button)findViewById(R.id.ps_control_cost_crop_value_continue_button);
        Button back_Button = (Button)findViewById(R.id.ps_control_cost_crop_value_back_button);
        Button cancel_Button = (Button)findViewById(R.id.ps_control_cost_crop_value_cancel_button);


        ArrayAdapter<String> control_cost_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, control_cost_array);
        control_cost_spinner.setAdapter(control_cost_adapter);

        ArrayAdapter<String> crop_value_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, crop_value_array);
        crop_value_spinner.setAdapter(crop_value_adapter);

        continue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PS_Control_Cost_and_Crop_Value.this, Greenbug_Sample_Stop_1.class);
                PS_Control_Cost_and_Crop_Value.this.startActivity(myIntent);
            }
        });

        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PS_Control_Cost_and_Crop_Value.this, PS_Field_List.class);
                PS_Control_Cost_and_Crop_Value.this.startActivity(myIntent);
            }
        });

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PS_Control_Cost_and_Crop_Value.this, SelectionScreen.class);
                PS_Control_Cost_and_Crop_Value.this.startActivity(myIntent);
            }
        });
    }
}
