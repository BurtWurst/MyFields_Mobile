package com.example.kyle.fieldslist;

import android.os.Bundle;
import android.app.Activity;

public class FieldInfoList extends Activity{
    String[] info = {
            "Irrigation Type: Ditch",
            "Size (acres): 1000",
            "Tillage System: Flow"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_list);
        FieldGrid grid = new FieldGrid();
    }
}