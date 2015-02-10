package com.example.kyle.fieldslist;

import android.os.Bundle;
import android.app.Activity;
import android.widget.GridView;
import android.widget.ListView;

public class MainActivity extends Activity {
    GridView grid;
    ListView list;
    String[] web = {
            "Corn Field",
            "Wheat Field",
            "Wheat Field 2",
            "Corn Field 2"

    } ;

    int[] imageId = {
           R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomGrid adapter = new CustomGrid(MainActivity.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
    }
}