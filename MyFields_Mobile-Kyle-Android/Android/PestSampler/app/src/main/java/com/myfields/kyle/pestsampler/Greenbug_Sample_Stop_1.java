package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 First page on the greenbug sample stop
 */
public class Greenbug_Sample_Stop_1 extends Activity{

    TextView textShown;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_greenbug_sample_stop1);

        CreateLayout();

    }

    private void CreateLayout()
    {
        textShown = (TextView)findViewById(R.id.greenbug_sample_stop1_help_shown);
        //hides the text until it's clicked
        //textShown.setVisibility(View.GONE);
    }
    /*private void ToggleContents(View view)
    {
        if(textShown.isShown()){
            //Fx.slide_up(this, textShown); STILL HAVE TO WRITE THE SLIDE UP PART IN CLASS FX AND THIS
            textShown.setVisibility(View.GONE);
        }
        else{
            textShown.setVisibility(View.VISIBLE);
            Fx.slide_down(this, textShown);
        }
    }*/
}
