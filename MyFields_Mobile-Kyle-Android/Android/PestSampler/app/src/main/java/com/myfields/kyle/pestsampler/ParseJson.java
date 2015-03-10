package com.myfields.kyle.pestsampler;

import android.app.Activity;
import android.os.Environment;

/// Parses the json and puts it into the field list

public class ParseJson extends Activity{

   /* public void putsIntoList(String string)
    {
        try {
            JSONObject jsonObject = new JSONObject(string);
            String aJsonString = jsonObject.getString("ID");
            System.out.println(aJsonString);

        } catch (JSONException exception)
        {
            System.out.println("Couldn't find item");
        }
    }*/

       public void Save()
       {

           try
           {
               String root = Environment.getExternalStorageDirectory().toString() ;
               System.out.println( root ) ;
               // Look here for the saved_images directory :  /storage/emulated/0
           } catch (Exception e) {
               e.printStackTrace();
           }
       }


}