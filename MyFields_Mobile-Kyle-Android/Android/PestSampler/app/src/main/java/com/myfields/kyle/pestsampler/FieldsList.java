package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 Represents the fields & names along with the type of field it is (corn, wheat, etc...)
 */
public class FieldsList extends Activity {
    ListView listView ;
    ConnectToApi api = new ConnectToApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);
        api.AttemptConnection();
        /*listView = (ListView) findViewById(R.id.selection_list);
        final String[] selectionList = new String[]
                {
                        "ID:                                        1",
                        "Field Name:                                Field1",
                        "Location                                   Latitude: 39 ",
                        "                                           Longitude: -99"
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
        });*/
    }
}