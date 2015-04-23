package com.myfields.kyle.pestsampler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;


// ***************************************************************
// * OVERVIEW                                                    *
// * This provides an implementation of a Field object, with the *
// * associated properties as defined by MyFields.               *
// ***************************************************************
public class Field {

    // The database ID associated with this Field. Necessary, as name may change.
    protected int ID;

    // The name of the field.
    protected String Name;

    // Latitude and Longitude of the field
    protected GPSLocation Location;

    // The size, in acres, of the field
    protected double Size;

    // The type of soil associated with this field
    protected String SoilType;

    // The type of tillage being used for this field
    protected String TillageSystem;

    // The irrigation system associated with this field
    protected  String IrrigationSystem;

    // The history of plantings planted in the field
    protected ArrayList<Planting> PlantingList;

    // The history of pest samples taken in this field
    protected ArrayList<PestSample> PestSamples;

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A constructor which takes in all necessary parameters to    *
    // * construct a new field.                                      *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * FieldID                                                     *
    // *    The database identifier of the new field.                *
    // * Name                                                        *
    // *    The name of the field.                                   *
    // * Loc                                                         *
    // *    The GPS coordinates of the field.                        *
    // * Acres                                                       *
    // *    The size, in acres, of the field.                        *
    // * SoilType                                                    *
    // *    The type of soil this field contains, as a string.       *
    // * MethodOfTill                                                *
    // *    The tillage system used in this field.                   *
    // * Irrigation                                                  *
    // *    The method of irrigation used in this field.             *
    // ***************************************************************
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

    // A method for adding a new pest sample to this field's history
    public void addSample(PestSample sample)
    {
        this.PestSamples.add(sample);
    }

    // A method for adding a new planting to this field's history
    public void addPlanting(Planting planting)
    {
        this.PlantingList.add(planting);
    }

    // A method to get the database ID of this field
    public int getID()
    {
        return this.ID;
    }
    // A method to set the database ID of this field.
    public void setID(int id) { this.ID = id; }

    // A method for getting the GPS coordinates of this field.
    public GPSLocation getLocation() { return this.Location; }
    // A method used to set the GPS coordinates of this field.
    public void setLocation(GPSLocation loc) { this.Location = loc; }

    // A method to return the name of the field.
    public String getName() { return this.Name; }
    // A method to set the name of the field.
    public void setName(String name) { this.Name = name; }

    // A method to return the size in acres of the field.
    public double getSize() { return this.Size; }
    // A method to set the size in acres of the field.
    public void setSize(double size) { this.Size = size; }

    // A method returning the type of soil associated with this field.
    public String getSoilType() { return this.SoilType; }
    // A method to set the type of soil associated with this field.
    public void setSoilType(String soilType) { this.SoilType = soilType; }

    // A method returning the type of tillage associated with this field.
    public String getTillageSystem() { return this.TillageSystem; }
    // A method to set the type of tillage associated with this field.
    public void setTillageSystem(String tillageSystem) { this.TillageSystem = tillageSystem; }

    // A method returning the type of irrigation associated with this field.
    public String getIrrigationSystem() { return this.IrrigationSystem; }
    // A method to set the type of irrigation associated with this field.
    public void setIrrigationSystem(String irrigationSystem) { this.IrrigationSystem = irrigationSystem; }

    // A method to retrieve the planting history of this field.
    public ArrayList<Planting> getPlantingList() { return  this.PlantingList; }

    // A method to retrieve the pest sampling history of this field.
    public ArrayList<PestSample> getPestSamples() { return this.PestSamples; }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse in a new Field object from the       *
    // * MyFields website API.                                       *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * jsonField                                                   *
    // *    The JSON string returned from the MyFields API           *
    // *    representing this particular field.                      *
    // ***************************************************************
    public static Field jsonRead(JSONObject jsonField) throws JSONException, ParseException
    {
        int id = jsonField.getInt("ID");
        String name = jsonField.getString("FieldName");
        GPSLocation loc = GPSLocation.jsonRead(jsonField.getJSONObject("Location"));
        double size = jsonField.getDouble(("Size"));
        String soil = jsonField.getString("TypeOfSoil").replace('_', ' ');
        String tillage = jsonField.getString("TillageSystem").replace('-', ' ');
        String irrigation = jsonField.getString("IrrigationSystem").replace('_', ' ');

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
                f.addSample(GreenbugSample.jsonRead(samples.getJSONObject(i)));
            }
        }

        return f;

    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse this Field into a JSONObject, for    *
    // * use in local storage of Field objects.                      *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a JSONObject, which contains the key-   *
    // * value pairs representing this field's data members in       *
    // * JSON format.                                                *
    // ***************************************************************
    public JSONObject jsonSerialize() throws JSONException
    {
        JSONObject json = new JSONObject();

        json.put("ID", this.ID);
        json.put("FieldName", this.Name);
        json.put("Location", this.Location.jsonSerialize());
        json.put("Size", this.Size);
        json.put("TypeOfSoil", this.SoilType.replace(' ', '_'));
        json.put("TillageSystem", this.TillageSystem.replace(' ', '-'));
        json.put("IrrigationSystem", this.IrrigationSystem.replace(' ', '_'));
        json.put("PlantingList", new JSONArray(this.PlantingList));
        json.put("PestSamples", new JSONArray(this.PestSamples));

        return json;
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to determine equality between this field and  *
    // * a passed in field. Done by performing equality checks on    *
    // * each data member and all items of the list members.         *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * obj                                                         *
    // *    The field to compare this one against.                   *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a boolean which indicates if this field *
    // * is equal to the field passed in. True if they are equal.    *
    // ***************************************************************
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Field))
            return false;
        if (obj == this)
            return true;

        Field f = (Field) obj;

        boolean returnValue = true;

        returnValue = returnValue && f.ID == this.ID;
        returnValue = returnValue && f.Name.equals(this.Name);
        returnValue = returnValue && f.Location.equals(this.Location);
        returnValue = returnValue && f.Size == this.Size;
        returnValue = returnValue && f.SoilType.equals(this.SoilType);
        returnValue = returnValue && f.TillageSystem.equals(this.TillageSystem);
        returnValue = returnValue && f.IrrigationSystem.equals(this.IrrigationSystem);

        if(f.PlantingList.size() == this.PlantingList.size())
        {
            for(int i = 0; i < f.PlantingList.size(); i++)
            {
                returnValue = returnValue && f.PlantingList.get(i).equals(this.PlantingList.get(i));
            }
        }
        else
        {
            returnValue = returnValue && false;
        }

        if(f.PestSamples.size() == this.PestSamples.size())
        {
            for(int i = 0; i < f.PestSamples.size(); i++)
            {
                returnValue = returnValue && f.PestSamples.get(i).equals(this.PestSamples.get(i));
            }
        }
        else
        {
            returnValue = returnValue && false;
        }

        return returnValue;
    }

}
