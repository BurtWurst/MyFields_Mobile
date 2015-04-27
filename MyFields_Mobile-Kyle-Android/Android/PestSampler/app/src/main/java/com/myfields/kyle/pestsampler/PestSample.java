package com.myfields.kyle.pestsampler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;

// ***************************************************************
// * OVERVIEW                                                    *
// * This provides a Generic Pest Sample implementation, with    *
// * the associated properties as defined by MyFields.           *
// ***************************************************************
public class PestSample {

    // The unique database identifier associated with this generic sample.
    protected int ID;

    // The GPS coordinates of this pest sample.
    // WARNING: To be removed in the future -- stored with the associated Field.
    protected GPSLocation location;

    // The associated field's unique database identifier. Used to trace this sample
    // to its field.
    protected int fieldID;

    // The control cost to treat this sample's pest.
    protected double ControlCost;

    // The crop value in the associated field at the time of this sampling.
    protected double CropValue;

    // Any notes taken as part of this pest sample.
    protected String Notes;

    // Any other pests detected in the field at the time of this pest sample.
    protected ArrayList<String> OtherPests;

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * An empty constructor used for creating a blank pest         *
    // * sample. This is used when starting a new pest sample.       *
    // ***************************************************************
    public PestSample()
    {
        ID = 0;
        location = new GPSLocation(0.0, 0.0);
        fieldID = 0;
        ControlCost = 0.0;
        CropValue = 0.0;
        Notes = "";
        OtherPests = new ArrayList<String>();
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A constructor which takes in all necessary parameters to    *
    // * construct a new generic sample.                             *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * ID                                                          *
    // *    The unique database identifier of this generic sample.   *
    // * Loc                                                         *
    // *    The GPS coordinates associated with this pest sample.    *
    // *    WARNINIG: These should be removed in the future --       *
    // *    they are already stored in the associated Field.         *
    // * Field                                                       *
    // *    The unique database identifier of this sample's          *
    // *    associated Field.                                        *
    // * Control                                                     *
    // *    The control cost of the pesticide used to treat for this *
    // *    pest.                                                    *
    // * Crop                                                        *
    // *    The value of the crop, per bushel, in this Field at the  *
    // *    time of the sample.                                      *
    // * Notes                                                       *
    // *    Any notes taken as part of the sampling process.         *
    // * OtherPests                                                  *
    // *    A String array representing any other pests detected in  *
    // *    the field at the time of sampling.                       *
    // ***************************************************************
    public PestSample(int ID, GPSLocation loc, int field, double control, double crop, String notes,
                      String[] otherPests)
    {
        this.ID = ID;
        this.fieldID = field;
        this.location = loc;
        this.ControlCost = control;
        this.CropValue = crop;
        this.Notes = notes;
        this.OtherPests = new ArrayList<String>();

        for(int i = 0; i < otherPests.length; i++)
        {
            this.OtherPests.add(otherPests[i]);
        }
    }

    // A method to return this sample's unique database identifier.
    public int getID() { return ID; }
    // A method to set this sample's unique database identifier.
    public void setID(int id) { this.ID = id; }

    // WARNINIG -- To be removed in the future. Use Field's location instead.
    public GPSLocation getLocation() { return location; }
    // WARNINIG -- To be removed in the future. Use Field's location instead.
    public void setLocation(GPSLocation loc) { this.location = loc; }

    // A method to return the associated Field's database identifier.
    public int getFieldID() { return fieldID; }
    // A method to set the identifier of the associated Field.
    public void setFieldID(int FieldId) { this.fieldID = FieldId; }

    // A method to retrieve the Control Cost of this sample's pest.
    public double getControlCost() { return ControlCost; }
    // A method to set the control cost of this sample's pest.
    public void setControlCost(double cost) { this.ControlCost = cost; }

    // A method to retrieve the crop value, per bushel, of the field at the time of sampling.
    public double getCropValue() { return CropValue; }
    // A method to set the crop value, per bushel, of the field at the time of sampling.
    public void setCropValue(double Value) { this.CropValue = Value; }

    // A method to retrieve any notes taken as part of the sampling process.
    public String getNotes() { return Notes; }
    // A method to add notes to this sample.
    public void setNotes(String notes) { this.Notes = notes; }

    // A method to return a string array representing other pests detected
    // in the field at the time of sampling.
    public String[] getOtherPests() { return (String[]) OtherPests.toArray(); }
    // A method to set Other Pests detected at the time of sampling.
    public void setOtherPests(ArrayList<String> pests) { this.OtherPests = pests; }
    // A method to add a pest to the list of Other Pests
    public void addOtherPest(String pest) { this.OtherPests.add(pest); }
    // A method to remove a pest from the list of other Pests
    public void removeOtherPest(String pest)
    {
        if(this.OtherPests.contains(pest))
        {
            this.OtherPests.remove(pest);
        }
    }
    // A method to see if the list of Other Pests already contains a pest
    public boolean checkForOtherPest(String pest) { return this.OtherPests.contains(pest); }


    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse in a new generic Sample object from  *
    // * the MyFields website API.                                   *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * sample                                                      *
    // *    The JSON string returned from the MyFields API           *
    // *    representing this generic Sample.                        *
    // ***************************************************************
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

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse this generic Sample into a           *
    // * JSONObject, for use in local storage of a generic Sample    *
    // * object.                                                     *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a JSONObject, which contains the key-   *
    // * value pairs representing this generic Sample's data         *
    // * members in JSON format.                                     *
    // ***************************************************************
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

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to determine equality between this generic    *
    // * Sample and a passed in generic sample. Done by              *
    // * performing equality checks on each data member.             *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * obj                                                         *
    // *    The generic Sample to compare this one against.          *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a boolean which indicates if this       *
    // * generic Sample is equal to the one passed in.               *
    // * True if they are equal.                                     *
    // ***************************************************************
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
		returnValue = returnValue && p.Notes.equals(this.Notes);
		
		
		if(p.OtherPests.size() == this.OtherPests.size())
		{
			for(int i = 0; i < p.OtherPests.size(); i++)
			{
				returnValue = returnValue && p.OtherPests.get(i).equals(this.OtherPests.get(i));
			}
		}
		else
		{
			returnValue = returnValue && false;
		}
	
		return returnValue;	
	}
}
