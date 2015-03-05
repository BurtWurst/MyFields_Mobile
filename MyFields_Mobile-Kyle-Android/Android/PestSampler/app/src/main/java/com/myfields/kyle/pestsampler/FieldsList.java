package com.myfields.kyle.pestsampler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 Represents the fields & names along with the type of field it is (corn, wheat, etc...)
 */
public class FieldsList extends Field {
    ListView listView;
    ConnectToApi api = new ConnectToApi();
    ArrayList<Field> fieldsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);
        //api.AttemptConnection();
        createListView();
    }

    private void createListView() {
        listView = (ListView) findViewById(R.id.selection_list);
        final String[] selectionList = new String[fieldsList.size()];

        for (int i = 0; i < fieldsList.size(); i++) {
            selectionList[i] = "ID: " + fieldsList.get(i).ID;
            selectionList[i].concat("Field Name: " + fieldsList.get(i).Name + "\n");
            selectionList[i].concat("Location: " + fieldsList.get(i).Location + "\n");
            selectionList[i].concat("Field Size: " + fieldsList.get(i).Size + "\n");
            selectionList[i].concat("Soil Type: " + fieldsList.get(i).SoilType + "\n");
            selectionList[i].concat("Tillage System: " + fieldsList.get(i).TillageSystem + "\n");
            selectionList[i].concat("Irrigation System: " + fieldsList.get(i).IrrigationSystem + "\n");
        }
    }
}

        /*listView = (ListView) findViewById(R.id.selection_list);
        final String[] selectionList = new String[]
                {
                        "ID:                                        1",
                        "Field Name:                                Field1",
                        "Location                                   Latitude: 39 ",
                        "                                           Longitude: -99"
                };
>>>>>>> origin/DanielPersonal
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(FieldsList.this, SpecificFieldInfo.class);
                FieldsList.this.startActivity(myIntent);
            }
        });
    }
}