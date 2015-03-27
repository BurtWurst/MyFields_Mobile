package com.myfields.kyle.pestsampler;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        String[] selectionList = getResources().getStringArray(R.array.selection_info_list);

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
                //goes to fields list
                if (position == 0) {
                    Intent myIntent = new Intent(getApplicationContext(), FieldsList.class);
                    SelectionScreen.this.startActivity(myIntent);
                }
                //goes to pest sampler
                if (position == 1)
                {
                    Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    SelectionScreen.this.startActivity(myIntent);
                }
            }
        });
    }
}