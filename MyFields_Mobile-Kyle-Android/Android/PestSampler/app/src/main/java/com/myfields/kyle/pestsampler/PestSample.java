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
    protected int fieldID;
    protected double ControlCost;
    protected double CropValue;
    protected String Notes;
    protected String[] OtherPests;

    public PestSample(int ID, GPSLocation loc, int field, double control, double crop, String notes,
                      String[] otherPests)
    {
        this.ID = ID;
        this.fieldID = field;
        this.location = loc;
        this.ControlCost = control;
        this.CropValue = crop;
        this.Notes = notes;
        this.OtherPests = otherPests;
    }

    public static PestSample jsonRead(JSONObject sample) throws JSONException
    {
        int id = sample.getInt("ID");
        GPSLocation loc = GPSLocation.jsonRead(sample.getJSONObject("Location"));
        double control = sample.getDouble("ControlCost");
		int fieldID = sample.getInt("Field");
        double crop = sample.getDouble("CropValue");
        String notes = sample.getString("Notes");
        ArrayList<String> otherpests = new ArrayList<String>();

        for(int i = 0; i < sample.getJSONArray("OtherPests").length(); i++)
        {
            otherpests.add(sample.getJSONArray("OtherPests").getString(i));
        }

        return new PestSample(id, loc, fieldID, control, crop, notes,
                otherpests.toArray(new String[otherpests.size()]));

    }

    public JSONObject jsonSerialize() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("ID", this.ID);
        json.put("Location", location.jsonSerialize());
        json.put("Field", this.fieldID);
        json.put("ControlCost", this.ControlCost);
        json.put("CropValue", this.CropValue);
        json.put("CropValue", this.CropValue);
        json.put("Notes", this.Notes);
        json.put("OtherPests", new JSONArray(this.OtherPests));

        return json;
    }
	
	@Override
	public boolean equals(Object obj)
	{
		if (!((obj instanceof PestSample) || (obj instanceof GreenbugSample)))
            return false;
        if (obj == this)
            return true;
			
		PestSample p = (PestSample) obj;
		
		boolean returnValue = true;
		
		returnValue = returnValue && p.ID == this.ID;
		returnValue = returnValue && p.location.equals(this.location);
		returnValue = returnValue && p.fieldID == this.fieldID;
		returnValue = returnValue && p.ControlCost == this.ControlCost;
		returnValue = returnValue && p.CropValue == this.CropValue;
		returnValue = returnValue && p.Notes == this.Notes;
		
		
		if(p.OtherPests.length == this.OtherPests.length)
		{
			for(int i = 0; i < p.OtherPests.length; i++)
			{
				returnValue = returnValue && p.OtherPests[i].equals(this.OtherPests[i]);
			}
		}
		else
		{
			returnValue = returnValue && false;
		}
	
		return returnValue;	
	}
}
