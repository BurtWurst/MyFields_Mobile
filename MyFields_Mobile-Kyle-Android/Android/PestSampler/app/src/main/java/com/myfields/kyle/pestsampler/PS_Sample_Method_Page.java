package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PS_Sample_Method_Page extends Activity {
    ListView listView;
    private GetAllFields api;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(currentUser == null)
        {
            SharedPreferences myCredentials = getSharedPreferences("Credentials", Context.MODE_PRIVATE);

            currentUser = new User(myCredentials.getString("user", null),
                    myCredentials.getString("pass", null));
        }

        boolean cancel = false;
        View focusView = null;

        setContentView(R.layout.activity_ps_sample_method_page);

        listView = (ListView) findViewById(R.id.ps_sample_method_page_list);

        if (cancel) {
            // There was an error; don't attempt field retrieval and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            api = new GetAllFields(currentUser.getFields(), currentUser.getUserName(),
                    currentUser.getUserPassword());
            api.execute((Void) null);
        }

        createListView();
    }
    private void createListView() {
        try
        {
            api.get(10000, TimeUnit.MILLISECONDS);
        }
        catch(Exception e)
        {
           System.out.print(e.toString());
        }

        ArrayList<Field> fieldsList = currentUser.getFields();
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
                Intent myIntent = new Intent(PS_Sample_Method_Page.this, SpecificFieldInfo.class);
                PS_Sample_Method_Page.this.startActivity(myIntent);
            }
        });
    }
}
