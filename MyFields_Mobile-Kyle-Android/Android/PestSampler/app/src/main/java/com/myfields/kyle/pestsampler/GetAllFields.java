package com.myfields.kyle.pestsampler;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import android.os.AsyncTask;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.util.ArrayList;

public class GetAllFields extends AsyncTask<Void, Void, Boolean> {

    private final String mUser;
    private final String mPassword;
    private final ArrayList<Field> FieldList;

    GetAllFields(ArrayList<Field> fields, String username, String pass) {
        mUser = username;
        mPassword = pass;
        FieldList = fields;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        Boolean returnValue = false;

        try {
            HttpClient getRequest = new DefaultHttpClient();

            HttpGet data = new HttpGet("http://people.cis.ksu.edu/~dgk2010/API.php?user=" + mUser + "&pass=" + mPassword);
            HttpResponse response = getRequest.execute(data);
            StatusLine status = response.getStatusLine();

            if (status.getStatusCode() == HttpStatus.SC_OK) {
                InputStream jsonIn = response.getEntity().getContent();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jsonIn));
                String line = "";
                StringBuilder result = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null)
                    result.append(line);

                jsonIn.close();

                JSONArray parsedJson = new JSONArray(result.toString());

                for (int i = 0; i < parsedJson.length(); i++) {
                    FieldList.add(Field.jsonRead(parsedJson.getJSONObject(i)));
                }
            } else if (status.getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                throw new Exception("Username or password was incorrect!");
            }
        } catch (Exception fail) {
            // Handle connection failed?
            System.out.println("Exception: " + fail.getMessage());
            returnValue = false;
        }

        return returnValue;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
    }

    @Override
    protected void onCancelled() {
    }
}
