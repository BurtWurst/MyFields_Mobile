package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.TextView;
/**
After you are on the field page (that shows a field name, and what type of field it is), you click on one, and it will take you to a screen that shows a picture of that field, and a description.
 This class represents that page.
 */
public class SpecificFieldInfo extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_field_info);
    }
}
