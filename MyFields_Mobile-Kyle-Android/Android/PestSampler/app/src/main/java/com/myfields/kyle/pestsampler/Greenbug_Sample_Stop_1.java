package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 Greenbug sample stop
 */
public class Greenbug_Sample_Stop_1 extends ActionBarActivity{

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    ListView listView;

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_greenbug_sample_stop1);


        expListView = (ExpandableListView) findViewById(R.id.greenbug_sample_page1);
        listView = (ListView) findViewById(R.id.greenbug_sample_page1_listview);
        gridView = (GridView)findViewById(R.id.greenbug_sample_page1_gridview);

        CreateLayout();
    }
    private void CreateLayout() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        List<String> header_info = new ArrayList<String>();
        listDataHeader.add("Help"); // Adding child data
        header_info.add(getResources().getString(R.string.greenbug_sample_stop1_help_expanded)); // Adding child data
        listDataChild.put(listDataHeader.get(0), header_info); // Header, Child data;

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        final String[] selectionList = new String[1];
        selectionList[0] = "Stop 1 of 5";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter);

        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridViewAdapter);

        Button continue_Button = (Button) findViewById(R.id.greenbug_sample_stop1_continue_button);
        Button back_Button = (Button) findViewById(R.id.greenbug_sample_stop1_back_button);
        Button cancel_Button = (Button) findViewById(R.id.greenbug_sample_stop1_cancel_button);

        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.sampleToBuild = null;

                Intent myIntent = new Intent(Greenbug_Sample_Stop_1.this, SelectionScreen.class);
                Greenbug_Sample_Stop_1.this.startActivity(myIntent);
            }
        });

    }


    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "")); //figure out how to do one line only
        }
        return imageItems;
    }
}