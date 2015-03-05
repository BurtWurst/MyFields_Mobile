package com.myfields.kyle.pestsampler;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Overview:
 *  This provides an implementation of a Field object, with the associated properties as
 *  defined by MyFields.
 */
public class Field extends Activity{

    protected int ID;
    protected String Name;
    protected GPSLocation Location;
    protected double Size;
    protected String SoilType;
    protected String TillageSystem;
    protected  String IrrigationSystem;

    protected ArrayList<Planting> PlantingList;
    protected ArrayList<PestSample> PestSamples;


    public Field ()
    {

    }
    public Field(int FieldID, String Name, GPSLocation loc, double Acres,
                 String SoilType, String MethodOfTill, String Irrigation)
    {
        this.ID = FieldID;
        this.Name = Name;
        this.Location = loc;
        this.Size = Acres;
        this.SoilType = SoilType;
        this.TillageSystem = MethodOfTill;
        this.IrrigationSystem = Irrigation;

        this.PlantingList = new ArrayList<Planting>();
        this.PestSamples = new ArrayList<PestSample>();
    }

    public void addSample(PestSample sample)
    {
        this.PestSamples.add(sample);
    }

    public void addPlanting(Planting planting)
    {
        this.PlantingList.add(planting);
    }

    public int getID()
    {
        return this.ID;
    }

    public static Field jsonRead(JSONObject jsonField) throws JSONException, ParseException
    {
        int id = jsonField.getInt("ID");
        String name = jsonField.getString("FieldName");
        GPSLocation loc = GPSLocation.jsonRead(jsonField.getJSONObject("Location"));
        double size = jsonField.getDouble(("Size"));
        String soil = jsonField.getString("TypeOfSoil");
        String tillage = jsonField.getString("TillageSystem");
        String irrigation = jsonField.getString("IrrigationSystem");

        Field f = new Field(id, name, loc, size, soil, tillage, irrigation);

        JSONArray plantings = jsonField.getJSONArray("PlantingList");
        JSONArray samples = jsonField.getJSONArray("PestSamples");

        for(int i = 0; i < plantings.length(); i++)
        {
            f.addPlanting(Planting.jsonRead(plantings.getJSONObject(i)));
        }

        for(int i = 0; i < samples.length(); i++)
        {
            //This is a GreenbugSample
            if(samples.getJSONObject(i).has("MummyCount"))
            {
                f.addSample(GreenbugSample.jsonRead(samples.getJSONObject(i), f));
            }
        }

        return f;

    }

    public JSONObject jsonSerialize() throws JSONException
    {
        JSONObject json = new JSONObject();

        json.put("ID", this.ID);
        json.put("FieldName", this.Name);
        json.put("Location", this.Location.jsonSerialize());
        json.put("Size", this.Size);
        json.put("TypeOfSoil", this.SoilType);
        json.put("TillageSystem", this.TillageSystem);
        json.put("IrrigationSystem", this.IrrigationSystem);
        json.put("PlantingList", new JSONArray(this.PlantingList));
        json.put("PestSamples", new JSONArray(PestSamples));

        return json;
    }

}
