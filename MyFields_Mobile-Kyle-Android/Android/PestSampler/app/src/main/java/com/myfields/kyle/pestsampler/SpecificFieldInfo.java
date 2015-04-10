package com.myfields.kyle.pestsampler;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class SpecificFieldInfo extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_field_info);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        int FieldIndex = this.getIntent().getIntExtra("FieldIndex", 0);
        Field fieldToShow = Globals.currentUser.getFields().get(FieldIndex);
        ArrayList<PestSample> userSamples = fieldToShow.getPestSamples();
        ArrayList<Planting> userPlantings = fieldToShow.getPlantingList();
        List<String> pest_samples = new ArrayList<String>();
        List<String> field_info = new ArrayList<String>();
        List<String> plantings = new ArrayList<String>();

        // Adding child data
        listDataHeader.add("Field Info");
        listDataHeader.add("Plantings");
        listDataHeader.add("Pest Samples");

        // Adding child data
        field_info.add("Name: \n\t" + fieldToShow.getName());
        field_info.add("ID: \n\t" + fieldToShow.getID());
        field_info.add("Location: \n\t" + fieldToShow.getLocation());
        field_info.add("Field Size (acres): \n\t" + fieldToShow.getSize());
        field_info.add("Soil Type: \n\t" + fieldToShow.getSoilType());
        field_info.add("Tillage System: \n\t" + fieldToShow.getTillageSystem());
        field_info.add("Irrigation System: \n\t" + fieldToShow.getIrrigationSystem());

        for(Planting p : userPlantings)
        {
            plantings.add(p.getDateOfPlanting().toString() + ": " + p.getCropType() +
                            "\n\tCrop Variety: " + p.getCropVariety() +
                            "\n\tDensity: " + p.getCropDensity() );
        }

        for(PestSample p : userSamples)
        {
            if(p instanceof GreenbugSample)
            {
                pest_samples.add("Greenbug Sample\n\tTreat: " +
                        ((GreenbugSample) p).getTreatmentRecommendation().toString() +
                        "\n\tAphid Count: " + ((GreenbugSample) p).getAphidCount() +
                        "\n\tMummy Count: " + ((GreenbugSample) p).getMummyCount());
            }
        }

        listDataChild.put(listDataHeader.get(0), field_info); // Header, Child data
        listDataChild.put(listDataHeader.get(1), plantings);
        listDataChild.put(listDataHeader.get(2), pest_samples);

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                if (groupPosition == 1)
                {
                    if (childPosition == 0)
                    {
                        //shit be wrong
                    }
                }

                return false;
            }
        });
    }

}