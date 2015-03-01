package com.myfields.kyle.pestsampler;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Overview:
 *  This provides an implementation of a Field object, with the associated properties as
 *  defined by MyFields.
 */
public class Field {

    protected int ID;
    protected String Name;
    protected GPSLocation location;
    protected double Size;
    protected String SoilType;
    protected String TillageSystem;
    protected  String IrrigationSystem;

    protected ArrayList<Planting> PlantingList;
    protected ArrayList<PestSample> PestSamples;


    public Field(int FieldID, String Name, double Latitude, double Longitude, double Acres,
                 String SoilType, String MethodOfTill, String Irrigation)
    {
        this.ID = FieldID;
        this.Name = Name;
        this.location = new GPSLocation(Latitude, Longitude);
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

    public static Field jsonRead(JSONObject jsonField)
    {

    }

    public JSONObject jsonSerialize() throws JSONException
    {
        JSONObject json = new JSONObject();

        json.put("ID", this.ID);
        json.put("FieldName", this.Name);
        json.put("Location", this.location.jsonSerialize());
        json.put("Size", this.Size);
        json.put("TypeOfSoil", this.SoilType);
        json.put("TillageSystem", this.TillageSystem);
        json.put("IrrigationSystem", this.IrrigationSystem);
        json.put("PlantingList", new JSONArray(this.PlantingList));
        json.put("PestSamples", new JSONArray(PestSamples));

        return json;
    }

}
