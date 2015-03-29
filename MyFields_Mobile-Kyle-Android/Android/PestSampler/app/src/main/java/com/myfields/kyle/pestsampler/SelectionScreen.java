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

import java.util.concurrent.TimeUnit;

/**
  Represents the activity that lets you choose either My Fields or Pest Sampler
 */
public class SelectionScreen extends Activity {
    ListView listView;
    private GetAllFields api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);

        listView = (ListView) findViewById(R.id.selection_list);

        String[] selectionList = getResources().getStringArray(R.array.selection_info_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);

        listView.setAdapter(adapter);
        // TODO: Add progress display later on
        //mProgressView = findViewById(R.id.progress_circular);

        SharedPreferences myCredentials = getSharedPreferences("Credentials", Context.MODE_PRIVATE);

        if(Globals.currentUser == null)
        {
            if(!(myCredentials.contains("user")))
            {
                Intent myIntent = new Intent(SelectionScreen.this, LoginActivity.class);
                SelectionScreen.this.startActivity(myIntent);
            }

            Globals.currentUser = new User(myCredentials.getString("user", null),
                                           myCredentials.getString("pass", null));

                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                //showProgress(true);
                api = new GetAllFields(Globals.currentUser.getFields(),
                                       Globals.currentUser.getUserName(),
                                       Globals.currentUser.getUserPassword());
                api.execute((Void) null);

                try {
                    api.get(10000, TimeUnit.MILLISECONDS);
                }
                catch(Exception ex) {

                }
        }

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //goes to fields list
                if (position == 0) {
                    Intent myIntent = new Intent(SelectionScreen.this, FieldsList.class);
                    SelectionScreen.this.startActivity(myIntent);
                }
                //goes to pest sampler
                if (position == 1)
                {
                    Intent myIntent = new Intent(SelectionScreen.this, PS_Field_List.class);
                    SelectionScreen.this.startActivity(myIntent);
                }
            }
        });
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    /*@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            listView.setVisibility(show ? View.GONE : View.VISIBLE);
            listView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    listView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            listView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/
}