package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kyle on 4/18/2015.
 */
public class Notes_and_Other_Pests extends Activity{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_and_other_pests);

        expListView = (ExpandableListView) findViewById(R.id.notes_and_other_pests_expandable_listview);

        CreateLayout();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    private void CreateLayout()
    {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        List<String> header_info = new ArrayList<String>();
        listDataHeader.add("Help"); // Adding child data
        header_info.add(getResources().getString(R.string.notes_and_other_pests_help_shown)); // Adding child data
        listDataChild.put(listDataHeader.get(0), header_info); // Header, Child data
    }
}
