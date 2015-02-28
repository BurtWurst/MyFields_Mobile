package com.myfields.kyle.pestsampler;

import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

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

    public PestSample(int ID, double Lat, double Lon, Field field, double control, double crop, String notes, String[] otherPests)
    {
        this.ID = ID;
        this.field = field;
        this.location = new GPSLocation(Lat, Lon);
        this.ControlCost = control;
        this.CropValue = crop;
        this.Notes = notes;
        this.OtherPests = otherPests;
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
