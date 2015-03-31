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

    public double getLat() { return Latitude; }

    public double getLon()
    {
        return Longitude;
    }

    public static GPSLocation jsonRead(JSONObject loc) throws JSONException
    {
        return new GPSLocation(loc.getDouble("Latitude"), loc.getDouble("Longitude"));
    }

    public JSONObject jsonSerialize() throws JSONException
    {
        JSONObject json = new JSONObject();

        json.put("Latitude", Latitude);
        json.put("Longitude", Longitude);

        return json;
    }

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof GPSLocation))
            return false;
        if (obj == this)
            return true;

		GPSLocation g = (GPSLocation) obj;

		return g.Latitude == this.Latitude && g.Longitude == this.Longitude;
	}

    @Override
    public String toString()
    {
        return Longitude +", " + Latitude;
    }

}
