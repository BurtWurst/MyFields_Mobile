package com.myfields.kyle.pestsampler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Overview:
 *  Represents a GPS location in latitude, longitude
 */
public class GPSLocation {

    private double Latitude;
    private double Longitude;

    public GPSLocation(double Lat, double Lon)
    {
        Latitude = Lat;
        Longitude = Lon;
    }

    public double getLat()
    {
        return Latitude;

    }

    public double getLon()
    {
        return Longitude;
    }

    public JSONObject jsonSerialize() throws JSONException
    {
        JSONObject json = new JSONObject();

        json.put("Latitude", Latitude);
        json.put("Longitude", Longitude);

        return json;
    }

}
