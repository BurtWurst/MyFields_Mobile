package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// ***************************************************************
// * OVERVIEW                                                    *
// * This activity class represents the Notes and Other Pests    *
// * page, which happens after the pest sample main areas        *
// ***************************************************************
public class Notes_and_Other_Pests extends Activity{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

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

        expListView = (ExpandableListView) findViewById(R.id.notes_and_other_pests_expandable_listview); //gets the expandablelistview ID from the layout file

        CreateLayout();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter); //assigns the expandablelistview
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * This function creates the activity, which was modeled to be *
    // * identical to the desktop version of this page               *
    // ***************************************************************
    private void CreateLayout()
    {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        List<String> header_info = new ArrayList<String>(); //used to put the expandablelistview info into
        listDataHeader.add("Help"); //what to make the expandable listview say
        header_info.add(getResources().getString(R.string.notes_and_other_pests_help_shown)); //what the expandable listview says when you click on it
        listDataChild.put(listDataHeader.get(0), header_info); //puts the header and child info from the 2 lines above into the expandable listview
    }
}
