package com.myfields.kyle.pestsampler;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.util.ArrayList;


public class ConnectToApi {

    public ArrayList<Field> GetAllFields(String username, String password)
    {
        ArrayList<Field> items = new ArrayList<Field>();

        try
        {
            HttpClient getRequest = new DefaultHttpClient();

            HttpGet data = new HttpGet("http://people.cis.ksu.edu/~dgk2010/API.php?user=myFieldsTester&pass=1cdd42b6d34675537dd103024892d858280d7c23");
            HttpResponse response = getRequest.execute(data);
            StatusLine status = response.getStatusLine();

            if(status.getStatusCode() == HttpStatus.SC_OK)
            {
                InputStream jsonIn = response.getEntity().getContent();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jsonIn));
                String line = "";
                StringBuilder result = new StringBuilder();
                while((line = bufferedReader.readLine()) != null)
                    result.append(line);

                jsonIn.close();

                JSONArray parsedJson = new JSONArray(result);

                for(int i = 0; i < parsedJson.length(); i++)
                {
                    items.add(Field.jsonRead(parsedJson.getJSONObject(i)));
                }
            }
            else if(status.getStatusCode() == HttpStatus.SC_UNAUTHORIZED)
            {
                throw new Exception("Username or password was incorrect!");
            }
        }
        catch (Exception fail)
        {
            // Handle connection failed?
        }

        return items;

    }

    public void AttemptConnection()
    {
        ParseJson parses = new ParseJson();
        parses.Save();
        /*
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost("http://people.cis.ksu.edu/~dgk2010/API.php?user=myFieldsTester&pass=1cdd42b6d34675537dd103024892d858280d7c23");
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
            JSONObject jsonObject = new JSONObject(result);
            String rando = jsonObject.getString("ID");
            System.out.println(rando);

        } catch (Exception e) {
            System.out.println("Couldn't find item");
            System.out.println(e.toString());
        }
        finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        }*/
    }
}
