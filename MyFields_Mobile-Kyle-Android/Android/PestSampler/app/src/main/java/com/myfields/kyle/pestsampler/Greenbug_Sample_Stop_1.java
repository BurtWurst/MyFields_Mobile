package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 Greenbug sample stop
 */
public class Greenbug_Sample_Stop_1 extends ActionBarActivity{

    private Button helpButton;
    private TextView stop1TextView;
    private GridView gridView;
    private ArrayAdapter gridViewAdapter;
    boolean[] ViewSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_greenbug_sample_stop1);

        helpButton = (Button)findViewById(R.id.greenbug_sample_stop1_help_button);
        stop1TextView = (TextView)findViewById(R.id.greenbug_sample_stop1_stop1_textbox);
        gridView = (GridView)findViewById(R.id.greenbug_sample_stop1_gridview);

        ViewSelected = new boolean[6];

        CreateLayout();
    }
    private void CreateLayout() {
        Button continue_Button = (Button) findViewById(R.id.greenbug_sample_stop1_continue_button);
        Button back_Button = (Button) findViewById(R.id.greenbug_sample_stop1_back_button);
        Button cancel_Button = (Button) findViewById(R.id.greenbug_sample_stop1_cancel_button);


        gridViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getData());
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 6) // Do not do anything if the header cells are clicked
                {
                    if (position <= 8) // Aphid clicked
                    {
                        if (!ViewSelected[position - 6]) {
                            view.setBackgroundColor(Color.GREEN);
                            ViewSelected[position - 6] = true;
                            ((GreenbugSample) Globals.sampleToBuild).addAphids(1);
                        } else {
                            view.setBackgroundColor(Color.TRANSPARENT);
                            ViewSelected[position - 6] = false;
                            ((GreenbugSample) Globals.sampleToBuild).addAphids(-1);
                        }
                    } else // Mummy clicked
                    {
                        if (!ViewSelected[position - 6]) {
                            view.setBackgroundColor(Color.RED);
                            ViewSelected[position - 6] = true;
                            ((GreenbugSample) Globals.sampleToBuild).addMummys(1);
                        } else {
                            view.setBackgroundColor(Color.TRANSPARENT);
                            ViewSelected[position - 6] = false;
                            ((GreenbugSample) Globals.sampleToBuild).addMummys(-1);
                        }
                    }

                    view.invalidate();
                }
            }
        });
        continue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Greenbug_Sample_Stop_1.this, Notes_and_Other_Pests.class);
                Greenbug_Sample_Stop_1.this.startActivity(myIntent);
            }
        });
        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.sampleToBuild = null;

                Intent myIntent = new Intent(Greenbug_Sample_Stop_1.this, SelectionScreen.class);
                Greenbug_Sample_Stop_1.this.startActivity(myIntent);
            }
        });
        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog alert = new AlertDialog.Builder(Greenbug_Sample_Stop_1.this).create();
                alert.setTitle(getResources().getString(R.string.greenbug_sample_stop1_help_shown));
                alert.setMessage(getResources().getString(R.string.greenbug_sample_stop1_help_expanded));
                alert.setButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.cancel(); //make sure it goes to last page
                    }
                });
                alert.show();
            }
        });

    }


    private ArrayList<String> getData() {
        final ArrayList<String> imageItems = new ArrayList<>();
        imageItems.add("Tiller 1");
        imageItems.add("Tiller 2");
        imageItems.add("Tiller 3");

        imageItems.add("--------");
        imageItems.add("--------");
        imageItems.add("--------");

        imageItems.add("Aphid");
        imageItems.add("Aphid");
        imageItems.add("Aphid");

        imageItems.add("Mummy");
        imageItems.add("Mummy");
        imageItems.add("Mummy");
        /*TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        String imageTitle = "";
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));

            if(i <= imgs.length()/2)
            {
                imageTitle = "Tiller " + i;
            }
            else
            {
                imageTitle = "";
            }

            imageItems.add(new ImageItem(bitmap, imageTitle)); //figure out how to do one line only
        }*/
        return imageItems;

    }
}