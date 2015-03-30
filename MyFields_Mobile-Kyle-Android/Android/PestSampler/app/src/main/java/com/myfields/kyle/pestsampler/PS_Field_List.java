package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PS_Field_List extends Activity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View focusView = null;

        setContentView(R.layout.activity_ps_field_list);

        listView = (ListView) findViewById(R.id.ps_field_list);

        TextView header = new TextView(this);
        header.setText("Select A Field To Sample: ");
        listView.addHeaderView(header);

        createListView();
    }
    private void createListView() {

        ArrayList<Field> fieldsList = Globals.currentUser.getFields();
        final String[] selectionList = new String[fieldsList.size()];

        for (int i = 0; i < fieldsList.size(); i++) {
            selectionList[i] = "Field Name: " + fieldsList.get(i).Name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Globals.sampleToBuild.setFieldID(Globals.currentUser.getFields().get(position - 1).getID());
                Globals.sampleToBuild.setLocation(Globals.currentUser.getFields().get(position - 1).getLocation());

                //Intent myIntent = new Intent(PS_Field_List.this, PS_Sample_Method.class);
                //PS_Field_List.this.startActivity(myIntent);
            }
        });
    }
}
