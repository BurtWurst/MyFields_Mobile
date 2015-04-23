package com.myfields.kyle.pestsampler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

// ***************************************************************
// * OVERVIEW                                                    *
// * This provides an implementation of a Planting object, with  *
// * the associated properties as defined by MyFields.           *
// ***************************************************************
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

    // The Date format used in any date-related processing in a Planting.
    public static final SimpleDateFormat dateStringFormat = new SimpleDateFormat("yyyy-MM-dd");

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A constructor which takes in all necessary parameters to    *
    // * construct a new Planting.                                   *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * ID                                                          *
    // *    The database identifier of the new Planting.             *
    // * Crop                                                        *
    // *    The type of crop planted.                                *
    // * Variety                                                     *
    // *    The variety of the crop planted.                         *
    // * Density                                                     *
    // *    The density, in thousand seeds per acre, of the          *
    // *    planted crop.                                            *
    // * Notes                                                       *
    // *    Any notes recorded during this planting.                 *
    // * Date                                                        *
    // *    The date, in year-month-day, the planting was done.      *
    // ***************************************************************
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

    // A method used to get the database identifier of this Planting.
    public int getID() { return this.ID; }

    // A method to retrieve the crop type of this Planting.
    public String getCropType() { return this.CropType; }

    // A method to retrieve the crop variety of this Planting.
    public String getCropVariety() { return this.CropVariety; }

    // A method to retrieve the crop density, in thousand seeds per acre, of this Planting.
    public double getCropDensity() { return this.CropDensity; }

    // A method to retrieve the notes stored as part of this Planting.
    public String getNotes() { return this.Notes; }

    // A method to return the Date of this Planting in String format.
    public String getDateOfPlanting() { return dateStringFormat.format(this.DateOfPlanting); }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse in a new Planting object from the    *
    // * MyFields website API.                                       *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * jsonField                                                   *
    // *    The JSON string returned from the MyFields API           *
    // *    representing this particular Planting.                   *
    // ***************************************************************
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

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse this Planting into a JSONObject, for *
    // * use in local storage of Planting objects.                   *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a JSONObject, which contains the key-   *
    // * value pairs representing this Planting's data members in    *
    // * JSON format.                                                *
    // ***************************************************************
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

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to determine equality between this Planting   *
    // * and a passed in field. Done by performing equality checks   *
    // * on each data member.                                        *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * obj                                                         *
    // *    The Planting to compare this one against.                *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a boolean which indicates if this       *
    // * Planting is equal to the Planting passed in.                *
    // * True if they are equal.                                     *
    // ***************************************************************
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
