package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Kyle on 4/9/2015.
 */
public class PlantingsSpecificInfo extends Activity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.plantings_specific_info);

        listView = (ListView) findViewById(R.id.plantings_specific_info_list);

        createListView();
    }
    private void createListView()
    {
        final String[] selectionList = new String[6]; // Number of data items in a Planting
        int fieldIndex = this.getIntent().getIntExtra("FieldIndex", 0);
        int plantingIndex = this.getIntent().getIntExtra("PlantingIndex", 0);
        Planting p = Globals.currentUser.getFields().get(fieldIndex).getPlantingList().get(plantingIndex);

        selectionList[0] = "ID: \n\t" + p.getID();
        selectionList[1] = "Crop Type: \n\t" + p.getCropType();
        selectionList[2] = "Crop Variety: \n\t" + p.getCropVariety();
        selectionList[3] = "Crop Density (thousand seeds/acre): \n\t" + p.getCropDensity();
        selectionList[4] = "Date Of Planting: \n\t" + p.getDateOfPlanting();
        selectionList[5] = "Notes: \n\t" + p.getNotes();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter);

    }

    @Override
    public void onStop()
    {
        super.onStop();

        finish();
    }
}
