package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Kyle on 4/9/2015.
 */
public class PestSamplesSpecificInfo extends Activity{
    private ListView listView;
    private Button home_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pest_samples_specific_info);

        home_Button = (Button) findViewById(R.id.pest_samples_specific_info_home_button);
        listView = (ListView) findViewById(R.id.pest_samples_specific_info_list);

        createListView();
    }
    private void createListView()
    {
        final String[] selectionList;
        int fieldIndex = this.getIntent().getIntExtra("FieldIndex", 0);
        int sampleIndex = this.getIntent().getIntExtra("SampleIndex", 0);
        PestSample p = Globals.currentUser.getFields().get(fieldIndex).getPestSamples().get(sampleIndex);

        home_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PestSamplesSpecificInfo.this, SelectionScreen.class);
                PestSamplesSpecificInfo.this.startActivity(myIntent);
            }
        });

        if(p instanceof GreenbugSample)
        {
            GreenbugSample specificSample = (GreenbugSample) p;
            selectionList = new String[10]; // Number of data items in a Greenbug Sample + sample type string

            selectionList[0] = "Sample Type: \n\t" + "Greenbug Sample";
            selectionList[1] = "Specific Sample ID: \n\t" + specificSample.getSpecificID();
            selectionList[2] = "Generic Sample ID: \n\t" + specificSample.getID();
            selectionList[3] = "Treatment Recommendation: \n\t" +
                    (specificSample.getTreatmentRecommendation() ?
                            "Treat Field for Greenbugs" :
                            "Do Not Treat Field for Greenbugs");
            selectionList[4] = "Aphid Count: \n\t" + specificSample.getAphidCount();
            selectionList[5] = "Mummy Count: \n\t" + specificSample.getMummyCount();
            selectionList[6] = "Crop Value: \n\t" + specificSample.getCropValue();
            selectionList[7] = "Control Cost: \n\t" + specificSample.getControlCost();
            selectionList[8] = "Notes: \n\t" + specificSample.getNotes();
            selectionList[9] = "Other Pests: \n\t" + TextUtils.join("\n\t", specificSample.getOtherPests());
        }
        else
        {
            selectionList = new String[0]; // No data
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        finish();
    }
}
