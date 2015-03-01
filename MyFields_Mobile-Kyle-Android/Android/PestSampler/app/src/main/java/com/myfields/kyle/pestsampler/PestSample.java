package com.myfields.kyle.pestsampler;

import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Overview:
 *  This class represents a generic, high-level pest sample, which contains
 *  the information common to all samples.
 */
public class PestSample {

    protected int ID;
    protected GPSLocation location;
    protected Field field;
    protected double ControlCost;
    protected double CropValue;
    protected String Notes;
    protected String[] OtherPests;

    public PestSample(int ID, GPSLocation loc, Field field, double control, double crop, String notes,
                      String[] otherPests)
    {
        this.ID = ID;
        this.field = field;
        this.location = loc;
        this.ControlCost = control;
        this.CropValue = crop;
        this.Notes = notes;
        this.OtherPests = otherPests;
    }

    public static PestSample jsonRead(JSONObject sample, Field f) throws JSONException
    {
        int id = sample.getInt("ID");
        GPSLocation loc = GPSLocation.jsonRead(sample.getJSONObject("Location"));
        double control = sample.getDouble("ControlCost");
        double crop = sample.getDouble("CropValue");
        String notes = sample.getString("Notes");
        ArrayList<String> otherpests = new ArrayList<String>();

        for(int i = 0; i < sample.getJSONArray("OtherPests").length(); i++)
        {
            otherpests.add(sample.getJSONArray("OtherPests").getString(i));
        }

        return new PestSample(id, loc, f, control, crop, notes,
                otherpests.toArray(new String[otherpests.size()]));

    }

    public JSONObject jsonSerialize() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("ID", this.ID);
        json.put("Location", location.jsonSerialize());
        json.put("Field", this.field.getID());
        json.put("ControlCost", this.ControlCost);
        json.put("CropValue", this.CropValue);
        json.put("CropValue", this.CropValue);
        json.put("Notes", this.Notes);
        json.put("OtherPests", new JSONArray(this.OtherPests));

        return json;
    }
}
