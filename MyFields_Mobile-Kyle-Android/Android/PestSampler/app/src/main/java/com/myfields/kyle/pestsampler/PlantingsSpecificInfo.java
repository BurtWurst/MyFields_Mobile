package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

//class that represents the plantings specific info activity page
public class PlantingsSpecificInfo extends Activity {
    private ListView listView;
    private Button home_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.plantings_specific_info);

        home_Button = (Button) findViewById(R.id.plantings_specific_info_home_button); //sets the button declared earlier to the button from the xml file
        listView = (ListView) findViewById(R.id.plantings_specific_info_list); //sets the listview to the one created in the xml

		//header to show above the listview with the text "complete plantings info"
        TextView header = new TextView(this);
        header.setText("Complete Plantings Info: ");
        listView.addHeaderView(header);

        createListView(); //calls the method 2 lines below that creates the layout
    }
    private void createListView()
    {
        final String[] selectionList = new String[6]; // Number of data items in a Planting
        int fieldIndex = this.getIntent().getIntExtra("FieldIndex", 0); //retrieves the extended data field index from the intent
        int plantingIndex = this.getIntent().getIntExtra("PlantingIndex", 0); //retrieves the extended data plantings index from the intent
        Planting p = Globals.currentUser.getFields().get(fieldIndex).getPlantingList().get(plantingIndex);
		//above line gets the fields and the plantings list based on the two indexes

		//on the home button's click, takes the user back to the selection screen
        home_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PlantingsSpecificInfo.this, SelectionScreen.class);
                PlantingsSpecificInfo.this.startActivity(myIntent);
            }
        });

        selectionList[0] = "ID: \n\t" + p.getID(); //first item in the listview is the planting id
        selectionList[1] = "Crop Type: \n\t" + p.getCropType(); //second item in the listview is the crop type
        selectionList[2] = "Crop Variety: \n\t" + p.getCropVariety(); //third item in the listview is the crop variety
        selectionList[3] = "Crop Density (thousand seeds/acre): \n\t" + p.getCropDensity(); //fourth item in the listview is the crop density
        selectionList[4] = "Date Of Planting: \n\t" + p.getDateOfPlanting(); //fifth item in the listview is the date of the planting
        selectionList[5] = "Notes: \n\t" + p.getNotes(); //sixth item in the listview are the notes about a planting

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter); //creates the adapter and sets it based on the info above

    }

    @Override
    public void onStop()
    {
        super.onStop();

        finish(); //goes back to the last activity safely
    }
}
