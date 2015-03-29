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

/**
 Represents the fields & names along with the type of field it is (corn, wheat, etc...)
 */
public class FieldsList extends Activity {
    private ListView listView;
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

        setContentView(R.layout.activity_fields_list);

        listView = (ListView) findViewById(R.id.selection_list);

        // TODO: Add progress display later on
        //mProgressView = findViewById(R.id.progress_circular);

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

        }

        ArrayList<Field> fieldsList = currentUser.getFields();
        final String[] selectionList = new String[fieldsList.size()];

        for (int i = 0; i < fieldsList.size(); i++) {
            selectionList[i] = "Field Name: " + fieldsList.get(i).Name;
        }

        //listView = (ListView) findViewById(R.id.selection_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, selectionList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(FieldsList.this, SpecificFieldInfo.class);
                FieldsList.this.startActivity(myIntent);
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