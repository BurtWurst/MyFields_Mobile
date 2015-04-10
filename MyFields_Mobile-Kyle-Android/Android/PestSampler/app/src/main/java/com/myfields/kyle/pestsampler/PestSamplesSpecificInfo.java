package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Kyle on 4/9/2015.
 */
public class PestSamplesSpecificInfo extends Activity{
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View focusView = null;

        setContentView(R.layout.activity_fields_list);

        listView = (ListView) findViewById(R.id.pest_samples_specific_info_list);

        createListView();
    }
    private void createListView()
    {

    }
}
