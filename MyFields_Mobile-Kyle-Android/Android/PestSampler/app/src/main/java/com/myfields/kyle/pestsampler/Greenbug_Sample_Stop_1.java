package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 Greenbug sample stop
 */
public class Greenbug_Sample_Stop_1 extends ActionBarActivity{

    private ListView listView;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_greenbug_sample_stop1);

        listView = (ListView) findViewById(R.id.greenbug_sample_page1);
        gridView = (GridView)findViewById(R.id.greenbug_sample_stop1_gridview);
        TextView header = new TextView(this);
        header.setText("Sample for Pest: ");
        listView.addHeaderView(header);

        CreateLayout();
    }
    private void CreateLayout()
    {
        final String[] items = {"Help", "Step 1 of 5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, items);
        listView.setAdapter(adapter);

        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridViewAdapter);
    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Tiller: " + i)); //figure out how to do one line only
        }
        return imageItems;
    }
}