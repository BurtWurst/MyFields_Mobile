package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
After you are on the field page (that shows a field name, and what type of field it is), you click on one, and it will take you to a screen that shows a picture of that field, and a description.
 This class represents that page.
 */
public class SpecificFieldInfo extends Activity {

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_specific_field_info);

        listView = (ListView) findViewById(R.id.list_of_information);

        TextView header = new TextView(this);
        header.setText("Field Information: ");
        listView.addHeaderView(header);

        CreateListView();
    }
    public void CreateListView()
    {

        int FieldIndex = this.getIntent().getIntExtra("FieldIndex", 0);

        Field fieldToShow = Globals.currentUser.getFields().get(FieldIndex);

        List<String> field_info = new ArrayList<String>();

        field_info.add("Name: " + fieldToShow.getName());
        field_info.add("ID: " + fieldToShow.getID());
        field_info.add("Location: " + fieldToShow.getLocation());
        field_info.add("Field Size: " + fieldToShow.getSize());
        field_info.add("Soil Type: " + fieldToShow.getSoilType());
        field_info.add("Tillage System: " + fieldToShow.getTillageSystem());
        field_info.add("Irrigation System: " + fieldToShow.getIrrigationSystem());
        field_info.add("Plantings: ");
        field_info.add("Pest Samples: ");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, field_info);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself
    }
}
