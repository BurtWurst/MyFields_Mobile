package com.myfields.kyle.pestsampler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Overview:
 *  This class represents a Planting for a field.
 */
public class Planting {

    protected int ID;
    protected String CropType;
    protected String CropVariety;
    protected double CropDensity;
    protected String Notes;
    protected Date DateOfPlanting;

    public Planting(int ID, String Crop, String Variety, double Density, String Notes,
                    Date DateOfPlant)
    {
        this.ID = ID;
        this.CropType = Crop;
        this.CropVariety = Variety;
        this.CropDensity = Density;
        this.Notes = Notes;
        this.DateOfPlanting = DateOfPlant;
    }

    public static Planting jsonRead(JSONObject plant) throws JSONException, ParseException
    {
        int id = plant.getInt("ID");
        String crop = plant.getString("CropType");
        String variety = plant.getString("CropVariety");
        double density = plant.getDouble("CropDensity");
        String notes = plant.getString("Notes");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(plant.getString("Date"));

        return new Planting(id, crop, variety, density, notes, date);
    }

    public JSONObject jsonSerialize() throws JSONException
    {
        JSONObject json = new JSONObject();

        json.put("ID", this.ID);
        json.put("CropType", this.CropType);
        json.put("CropVariety", this.CropVariety);
        json.put("CropDensity", this.CropDensity);
        json.put("Notes", this.Notes);
        json.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(this.DateOfPlanting));

        return json;
    }
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Planting))
            return false;
        if (obj == this)
            return true;
			
		Planting p = (Planting) obj;
		
		boolean returnValue = true;
		
		returnValue = returnValue && p.ID == this.ID;
		returnValue = returnValue && p.CropType == this.CropType;
		returnValue = returnValue && p.CropVariety == this.CropVariety;
		returnValue = returnValue && p.CropDensity == this.CropDensity;
		returnValue = returnValue && p.Notes == this.Notes;
		returnValue = returnValue && p.DateOfPlanting.equals(this.DateOfPlanting);

		return returnValue;
	}

}
