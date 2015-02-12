package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Intent;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;

/**
 Represents the fields & names along with the type of field it is (corn, wheat, etc...)
 */
public class FieldsList extends Activity {
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);

        listView = (ListView) findViewById(R.id.selection_list);

        final String[] selectionList = new String[]
                {
                        "Field 1                               Corn",
                        "Field 2                               Wheat",
                        "Field 3                               Sorghum"
                };

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
