package com.myfields.kyle.pestsampler;

import org.json.JSONException;
import org.json.JSONObject;

// ***************************************************************
// * OVERVIEW                                                    *
// * This provides a Greenbug Pest Sample implementation, with   *
// * the associated properties as defined by MyFields.           *
// ***************************************************************
public class GreenbugSample extends PestSample {

    public static enum Greenbug_Sample_Values
    {
        Indeterminate,
        Do_Not_Treat_Based_On_Mummy_Count,
        Treat_Based_On_Greenbug_Count,
        Do_Not_Treat_Based_On_Greenbug_Count,
    }

    // The database ID associated with this Greenbug Sample
    protected int SpecificID;

    // Whether or not to treat based on this sample
    protected Greenbug_Sample_Values TreatmentRecommendation;

    // The number of greenbugs detected via sampling
    protected int AphidCount;

    // The number of mummies detected via sampling
    protected int MummyCount;

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * An empty constructor used for creating a blank Greenbug     *
    // * sample. This is used when starting a new pest sample.       *
    // ***************************************************************
    public GreenbugSample() { }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A constructor which takes in all necessary parameters to    *
    // * construct a new Greenbug sample, including generic parent   *
    // * sample information.
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * SpecificID                                                  *
    // *    The unique database identifier of this Greenbug sample.  *
    // * GenericID                                                   *
    // *    The unique database identifier of this sample's parent   *
    // *    generic pest sample.                                     *
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
    // * Treatment                                                   *
    // *    A determination of whether or not to treat the field for *
    // *    this type of pest and why.                               *
    // * Aphids                                                      *
    // *    The number of aphids detected in the Field as part of    *
    // *    the pest sampling process.                               *
    // * Mummys                                                      *
    // *    The number of mummies detected in the Field as part of   *
    // *    the pest sampling process.                               *
    // ***************************************************************
    public GreenbugSample(int SpecificID, int GenericID, GPSLocation loc, int field,
                          double control, double crop, String notes, String[] otherPests,
                          Greenbug_Sample_Values Treatment, int Aphids, int Mummys)
    {
        super(GenericID, loc, field, control, crop, notes, otherPests);

        this.SpecificID = SpecificID;
        this.TreatmentRecommendation = Treatment;
        this.AphidCount = Aphids;
        this.MummyCount = Mummys;
    }

    // A method to return this Greenbug sample's unique database identifier.
    public int getSpecificID() { return this.SpecificID; }
    // A method to set this Greenbug sample's unique database identifier.
    public void setSpecificID(int id) { this.SpecificID = id; }

    // A method to get the recommended treatment for this pest sample.
    public Greenbug_Sample_Values getTreatmentRecommendation() { return this.TreatmentRecommendation; }
    // A method to set the recommended treatment for this pest sample.
    public void setTreatmentRecommendation(Greenbug_Sample_Values recommendation)
    { this.TreatmentRecommendation = recommendation; }

    // A method to get the number of Aphids detected when taking this sample.
    public int getAphidCount() { return this.AphidCount; }
    // A method to add one more aphid to the count so far detected in this sample.
    public void addAphids(int count) { this.AphidCount += count; }

    // A method to get the number of mummies detected when taking this sample.
    public int getMummyCount() { return this.MummyCount; }
    // A method to add one more mummy to the count so far detected in this sample.
    public void addMummys(int count) { this.MummyCount += count; }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse in a new Greenbug Sample object from *
    // * the MyFields website API.                                   *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * sample                                                      *
    // *    The JSON string returned from the MyFields API           *
    // *    representing this particular Greenbug Sample.            *
    // ***************************************************************
    public static GreenbugSample jsonRead(JSONObject sample) throws JSONException
    {
        int specificid = sample.getInt("SpecificID");
        Greenbug_Sample_Values treat = Greenbug_Sample_Values.valueOf
                (sample.getString("TreatmentRecommendation"));
        int aphidcount = sample.getInt("AphidCount");
        int mummycount = sample.getInt("MummyCount");

        PestSample genericsample = PestSample.jsonRead(sample.getJSONObject("GenericSample"));

        return new GreenbugSample(specificid, genericsample.ID, genericsample.location,
                genericsample.fieldID, genericsample.ControlCost, genericsample.CropValue,
                genericsample.Notes, genericsample.OtherPests, treat, aphidcount, mummycount);

    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to parse this Greenbug Sample into a          *
    // * JSONObject, for use in local storage of a Greenbug Sample   *
    // * object.                                                     *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a JSONObject, which contains the key-   *
    // * value pairs representing this Greenbug Sample's data        *
    // * members in JSON format.                                     *
    // ***************************************************************
    public JSONObject jsonSerialize() throws JSONException
    {
        JSONObject json = new JSONObject();

        json.put("SpecificID", this.SpecificID);
        json.put("TreatmentRecommendation",this.TreatmentRecommendation);
        json.put("AphidCount", this.AphidCount);
        json.put("MummyCount", this.MummyCount);
        json.put("GenericSample", super.jsonSerialize());

        return json;
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method used to determine equality between this Greenbug   *
    // * Sample and a passed in Greenbug sample. Done by             *
    // * performing equality checks on each data member.             *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * obj                                                         *
    // *    The Greenbug Sample to compare this one against.         *
    // ***************************************************************
    // * RETURN                                                      *
    // * ----------------------------------------------------------- *
    // * This method returns a boolean which indicates if this       *
    // * Greenbug Sample is equal to the one passed in.              *
    // * True if they are equal.                                     *
    // ***************************************************************
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof GreenbugSample))
            return false;
        if (obj == this)
            return true;
			
		GreenbugSample g = (GreenbugSample) obj;
		
		boolean returnValue = true;
		
		returnValue = returnValue && super.equals(g);
		
		returnValue = returnValue && g.SpecificID == this.SpecificID;
		returnValue = returnValue && g.TreatmentRecommendation == this.TreatmentRecommendation;
		returnValue = returnValue && g.AphidCount == this.AphidCount;
		returnValue = returnValue && g.MummyCount == this.MummyCount;
	
		return returnValue;	
	}

}
