package com.myfields.kyle.pestsampler;

import org.json.JSONException;
import org.json.JSONObject;
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

}
