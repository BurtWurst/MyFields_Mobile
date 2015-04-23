package com.myfields.kyle.pestsampler;

import org.json.JSONException;
import org.json.JSONObject;

// ***************************************************************
// * OVERVIEW                                                    *
// * This provides an implementation of GPS coordinates.         *
// ***************************************************************
public class GPSLocation {

    // The Latitude of the coordinate pair, as a double
    private double Latitude;

    // The Longitude of the coordinate pair, as a double
    private double Longitude;

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A constructor which takes in all necessary parameters to    *
    // * construct a new set of GPS coordinates.                     *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * Lat                                                         *
    // *    The latitude of the coordinate pair.                     *
    // * Lon                                                         *
    // *    The longitude of the coordinate pair.                    *
    // ***************************************************************
    public GPSLocation(double Lat, double Lon)
    {
        Latitude = Lat;
        Longitude = Lon;
    }

    // A method to return the latitude of the coordinate pair.
    public double getLat() { return Latitude; }

    // A method to return the longitude of the coordinate pair.
    public double getLon()
    {
        return Longitude;
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse in a new GPSLocation object from the *
    // * MyFields website API.                                       *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * loc                                                         *
    // *    The JSON string returned from the MyFields API           *
    // *    representing this particular coordinate pair.            *
    // ***************************************************************
    public static GPSLocation jsonRead(JSONObject loc) throws JSONException
    {
        return new GPSLocation(loc.getDouble("Latitude"), loc.getDouble("Longitude"));
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse this GPSLocation into a JSONObject,  *
    // * for use in local storage of GPSLocation objects.            *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a JSONObject, which contains the key-   *
    // * value pairs representing this coordinate pair's data        *
    // * members in JSON format.                                     *
    // ***************************************************************
    public JSONObject jsonSerialize() throws JSONException
    {
        JSONObject json = new JSONObject();

        json.put("Latitude", Latitude);
        json.put("Longitude", Longitude);

        return json;
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to determine equality between this coordinate *
    // * pair and a passed in coordinate pair. Done by performing    *
    // * equality checks on each data member.                        *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * obj                                                         *
    // *    The GPSLocation to compare this one against.             *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a boolean which indicates if this       *
    // * coordinate pair is equal to the pair passed in.             *
    // * True if they are equal.                                       *
    // ***************************************************************
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

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse this coordinate pair into a String.  *
    // * This method should be used to display the pair to the UI.   *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a String representing the coordinate    *
    // * pair as a pair of comma-separated decimals.                 *
    // ***************************************************************
    @Override
    public String toString()
    {
        return Longitude +", " + Latitude;
    }

}
