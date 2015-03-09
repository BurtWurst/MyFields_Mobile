package com.myfields.kyle.pestsampler;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;

/**
  Represents the activity that lets you choose either My Fields or Pest Sampler
 */
public class SelectionScreen extends Activity {
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);

        listView = (ListView) findViewById(R.id.selection_list);

        String[] selectionList = new String[]
                {
                "My Fields",
                "Pest Sampler",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);

        listView.setAdapter(adapter);

        SharedPreferences myCredentials = getSharedPreferences("Credentials", Context.MODE_PRIVATE);

        if(!(myCredentials.contains("user")))
        {
            Intent myIntent = new Intent(SelectionScreen.this, LoginActivity.class);
            SelectionScreen.this.startActivity(myIntent);
        }

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(SelectionScreen.this, FieldsList.class);
                SelectionScreen.this.startActivity(myIntent);
            }
        });


    }
}