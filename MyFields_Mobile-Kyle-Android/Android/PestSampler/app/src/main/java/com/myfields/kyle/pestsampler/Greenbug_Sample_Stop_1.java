package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

/**
 First page on the greenbug sample stop
 */
public class Greenbug_Sample_Stop_1 extends Activity{

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_greenbug_sample_stop1);
        listView = (ListView) findViewById(R.id.greenbug_sample_page1);

        CreateLayout();
    }
    private void CreateLayout()
    {
        final String[] items = {"Help", "Step 1 of 5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, items);
        listView.setAdapter(adapter);
    }
}
