package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Notes_and_Other_Pests_Show_Notes extends Activity{
    EditText editText;
    Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_and_other_pests_show_notes);
        editText = (EditText)findViewById(R.id.notes_and_other_pests_editable_notes_textbox);
        button = (Button)findViewById(R.id.notes_and_other_pests_show_notes_done_button);

        CreateLayout();
    }

    private void CreateLayout()
    {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
