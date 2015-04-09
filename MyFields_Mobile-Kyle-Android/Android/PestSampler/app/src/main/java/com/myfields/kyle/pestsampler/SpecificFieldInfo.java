package com.myfields.kyle.pestsampler;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
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
        PestSample pestToSample = Globals.sampleToBuild; //
        List<String> pest_samples = new ArrayList<String>();
        List<String> field_info = new ArrayList<String>();
        List<String> plantings = new ArrayList<String>();

        // Adding child data
        listDataHeader.add("Field Info");
        listDataHeader.add("Plantings");
        listDataHeader.add("Pest Samples");

        // Adding child data
        field_info.add("Name: " + fieldToShow.getName());
        field_info.add("ID: " + fieldToShow.getID());
        field_info.add("Location: " + fieldToShow.getLocation());
        field_info.add("Field Size: " + fieldToShow.getSize());
        field_info.add("Soil Type: " + fieldToShow.getSoilType());
        field_info.add("Tillage System: " + fieldToShow.getTillageSystem());
        field_info.add("Irrigation System: " + fieldToShow.getIrrigationSystem());

        plantings.add("The Conjuring");
        plantings.add("Despicable Me 2");
        plantings.add("Turbo");
        plantings.add("Grown Ups 2");
        plantings.add("Red 2");
        plantings.add("The Wolverine");

        pest_samples.add("ID: ");
        pest_samples.add("The Smurfs 2");
        pest_samples.add("The Spectacular Now");
        pest_samples.add("The Canyons");
        pest_samples.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), field_info); // Header, Child data
        listDataChild.put(listDataHeader.get(1), plantings);
        listDataChild.put(listDataHeader.get(2), pest_samples);
    }
}