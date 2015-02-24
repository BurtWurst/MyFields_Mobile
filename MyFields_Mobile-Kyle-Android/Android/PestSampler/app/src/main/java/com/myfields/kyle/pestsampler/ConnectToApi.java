package com.myfields.kyle.pestsampler;


public class ConnectToApi {

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
