package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

//class that represents the pest samples specific info activity page
public class PestSamplesSpecificInfo extends Activity{
    private ListView listView; //listview that will be set to the listview in the xml
    private Button home_Button; //button that will be set to the button from the xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pest_samples_specific_info);

        home_Button = (Button) findViewById(R.id.pest_samples_specific_info_home_button); //sets the button declared above to the home button from the xml file
        listView = (ListView) findViewById(R.id.pest_samples_specific_info_list); //sets the listview declared above to the listview from the xml file

		//header to show above the listview with the text "complete pest sample info"
        TextView header = new TextView(this);
        header.setText("Complete Pest Sample Info: ");
        listView.addHeaderView(header);

		//calls the method 2 lines below to create the layout
        createListView();
    }
    private void createListView()
    {
        final String[] selectionList;
        int fieldIndex = this.getIntent().getIntExtra("FieldIndex", 0); //retrieves the extended data field index from the intent
        int sampleIndex = this.getIntent().getIntExtra("SampleIndex", 0); //retrieves the extended data plantings index from the intent
        PestSample p = Globals.currentUser.getFields().get(fieldIndex).getPestSamples().get(sampleIndex); //gets current user's fields and the pest samples both based on the indexes

		//On home button click, takes the user back to the selection screen
        home_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PestSamplesSpecificInfo.this, SelectionScreen.class);
                PestSamplesSpecificInfo.this.startActivity(myIntent);
            }
        });

		//if p is an instance of greenbug sample
        if(p instanceof GreenbugSample)
        {
            GreenbugSample specificSample = (GreenbugSample) p;
            selectionList = new String[10]; // Number of data items in a Greenbug Sample + sample type string

            selectionList[0] = "Sample Type: \n\t" + "Greenbug Sample"; //sets the selection list at the first spot 
            selectionList[1] = "Specific Sample ID: \n\t" + specificSample.getSpecificID(); //sets the selection list at the second spot
            selectionList[2] = "Generic Sample ID: \n\t" + specificSample.getID(); //sets the selection list at the third spot
            selectionList[3] = "Treatment Recommendation: \n\t" +
                    specificSample.getTreatmentRecommendation().toString().replace('_', ' '); //sets the selection list at the fourth spot
            selectionList[4] = "Aphid Count: \n\t" + specificSample.getAphidCount(); //sets the selection list at the fifth spot
            selectionList[5] = "Mummy Count: \n\t" + specificSample.getMummyCount(); //sets the selection list at the sixth spot
            selectionList[6] = "Crop Value: \n\t" + specificSample.getCropValue(); //sets the selection list at the seventh spot
            selectionList[7] = "Control Cost: \n\t" + specificSample.getControlCost(); //sets the selection list at the eighth spot
            selectionList[8] = "Notes: \n\t" + specificSample.getNotes(); //sets the selection list at the ninth spot
            selectionList[9] = "Other Pests: \n\t" + TextUtils.join("\n\t", specificSample.getOtherPests()); //sets the selection list at the tenth spot
        }
        else
        {
            selectionList = new String[0]; // No data
        }
		//creates the array adapter with selection list (what will be shown) and then sets it to the listview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        finish(); //finishes the activity safely and goes to the last activity
    }
}
