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
    // The unique identifier of this planting.
    protected int ID;

    // The type of crop associated with this planting.
    protected String CropType;

    // The variety of crop associated with this planting.
    protected String CropVariety;

    // The density of crop planted.
    protected double CropDensity;

    // Any user inputted notes.
    protected String Notes;

    // The date this planting occured.
    protected Date DateOfPlanting;

    public static final SimpleDateFormat dateStringFormat = new SimpleDateFormat("yyyy-MM-dd");

    // A constructor to build a new planting object
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

    public int getID() { return this.ID; }

    public String getCropType() { return this.CropType; }

    public String getCropVariety() { return this.CropVariety; }

    public double getCropDensity() { return this.CropDensity; }

    public String getNotes() { return this.Notes; }

    public String getDateOfPlanting() { return dateStringFormat.format(this.DateOfPlanting); }

    // A method to read in a new planting from a JSON object
    public static Planting jsonRead(JSONObject plant) throws JSONException, ParseException
    {
        int id = plant.getInt("ID");
        String crop = plant.getString("CropType");
        String variety = plant.getString("CropVariety");
        double density = plant.getDouble("CropDensity");
        String notes = plant.getString("Notes");
        Date date = dateStringFormat.parse(plant.getString("Date"));

        return new Planting(id, crop, variety, density, notes, date);
    }

    // A method to serialize this object to JSON
    public JSONObject jsonSerialize() throws JSONException
    {
        JSONObject json = new JSONObject();

        json.put("ID", this.ID);
        json.put("CropType", this.CropType);
        json.put("CropVariety", this.CropVariety);
        json.put("CropDensity", this.CropDensity);
        json.put("Notes", this.Notes);
        json.put("Date", dateStringFormat.format(this.DateOfPlanting));

        return json;
    }

    // A method to check if this planting is equal to another.
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
		returnValue = returnValue && p.CropType.equals(this.CropType);
		returnValue = returnValue && p.CropVariety.equals(this.CropVariety);
		returnValue = returnValue && p.CropDensity == this.CropDensity;
		returnValue = returnValue && p.Notes.equals(this.Notes);
		returnValue = returnValue && p.DateOfPlanting.equals(this.DateOfPlanting);

		return returnValue;
	}

}
