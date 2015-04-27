package com.myfields.kyle.pestsampler;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

//class that represents the specific field info, which is show after the fields list, and before the planting specific info and pest samples specific info
public class SpecificFieldInfo extends Activity {

    private int fieldID; //gets the field ID 
    ExpandableListAdapter listAdapter; //used for actually implementing the listview
    ExpandableListView expListView; //expandable listview to be shown
    List<String> listDataHeader; //items to be shown before the listview is expanded
    HashMap<String, List<String>> listDataChild; //used for creating the expandable listview
    private Button home_Button; //home button at the bottom of the page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_field_info);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.specific_field_info_expandable_listview); //gets the expandable listview from the layout xml
        home_Button = (Button) findViewById(R.id.specific_field_info_home_button); //gets the home button from the layout xml

		// the next 3 lines add a header above the expandable listview to show the text "complete field info"
        TextView header = new TextView(this);
        header.setText("Complete Field Info: ");
        expListView.addHeaderView(header);

        // calls the method below to create the view
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild); //sets the expandable listAdapter with the 3 categories and child data in each catagory

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>(); //initializing the header
        listDataChild = new HashMap<String, List<String>>(); //initializing the children

        fieldID = this.getIntent().getIntExtra("FieldIndex", 0);
        Field fieldToShow = Globals.currentUser.getFields().get(fieldID);
        ArrayList<PestSample> userSamples = fieldToShow.getPestSamples(); //gets the pest samples info to show
        ArrayList<Planting> userPlantings = fieldToShow.getPlantingList(); //gets the plantings info to show
        List<String> field_info = new ArrayList<String>();
        List<String> pest_samples = new ArrayList<String>();
        List<String> plantings = new ArrayList<String>();

        // Adding the main categories
        listDataHeader.add("Field Info");
        listDataHeader.add("Plantings");
        listDataHeader.add("Pest Samples");

        // Adding child data for when field info is expanded
        field_info.add("Name: \n\t" + fieldToShow.getName());
        field_info.add("ID: \n\t" + fieldToShow.getID());
        field_info.add("Location: \n\t" + fieldToShow.getLocation());
        field_info.add("Field Size (acres): \n\t" + fieldToShow.getSize());
        field_info.add("Soil Type: \n\t" + fieldToShow.getSoilType());
        field_info.add("Tillage System: \n\t" + fieldToShow.getTillageSystem());
        field_info.add("Irrigation System: \n\t" + fieldToShow.getIrrigationSystem());

		//When plantings is expanded, the info that is display is date of planting, crop type, crop variety, and crop density
        for(Planting p : userPlantings)
        {
            plantings.add(p.getDateOfPlanting().toString() + ": " + p.getCropType() +
                            "\n\tCrop Variety: " + p.getCropVariety() +
                            "\n\tDensity: " + p.getCropDensity() );
        }

		//When pest samples is expanded, it shows the treatment recommendation, the aphid count, and the mummy count
        for(PestSample p : userSamples)
        {
            if(p instanceof GreenbugSample)
            {
                pest_samples.add("Greenbug Sample\n\tTreat: " +
                        ((GreenbugSample) p).getTreatmentRecommendation().toString().replace('_', ' ') +
                        "\n\tAphid Count: " + ((GreenbugSample) p).getAphidCount() +
                        "\n\tMummy Count: " + ((GreenbugSample) p).getMummyCount());
            }
        }

        listDataChild.put(listDataHeader.get(0), field_info); //puts the field info header first in the expandable listview
        listDataChild.put(listDataHeader.get(1), plantings); //puts the plantings info second
        listDataChild.put(listDataHeader.get(2), pest_samples); //puts pest samples third 

        //When a either plantings of pest samples's childs are clicked on, takes it to a new activitiy based on whether it's a plantings child or pest samples
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                switch((String) parent.getExpandableListAdapter().getGroup(groupPosition))
                {
                    case "Plantings": //case for when a child in plantings is clicked on
                        Intent myPlantingIntent = new Intent(SpecificFieldInfo.this, PlantingsSpecificInfo.class);
                        myPlantingIntent.putExtra("FieldIndex", fieldID);
                        myPlantingIntent.putExtra("PlantingIndex", childPosition);
                        SpecificFieldInfo.this.startActivity(myPlantingIntent); //takes it to the plantings specific info class
                        break;
                    case "Pest Samples": //case for when a child in pest samples is clicked on
                        Intent mySampleIntent = new Intent(SpecificFieldInfo.this, PestSamplesSpecificInfo.class);
                        mySampleIntent.putExtra("FieldIndex", fieldID);
                        mySampleIntent.putExtra("SampleIndex", childPosition);
                        SpecificFieldInfo.this.startActivity(mySampleIntent); //takes it to the pest samples specific info class
                        break;
                }

                return false;
            }
        });
		
		//when the home button is clicked, takes the user to the selection screen
        home_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SpecificFieldInfo.this, SelectionScreen.class);
                SpecificFieldInfo.this.startActivity(myIntent);
            }
        });
    }

}