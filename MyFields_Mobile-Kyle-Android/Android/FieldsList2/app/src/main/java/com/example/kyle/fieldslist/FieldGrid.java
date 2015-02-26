package com.example.kyle.fieldslist;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.view.View;
import android.app.ListActivity;

public class FieldGrid extends ListActivity{
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = new String[] { "Irrigation Type: Ditch",
                "Size (acres): 1000",
                "Tillage System: Flow",
                "Sample Taken 2 weeks ago",
                "View Full Report",
                "Do not treat based on mummy count",
                "Edit"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }
}