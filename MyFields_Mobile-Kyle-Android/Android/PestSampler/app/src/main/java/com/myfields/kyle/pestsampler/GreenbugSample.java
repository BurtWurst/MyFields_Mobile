package com.myfields.kyle.pestsampler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Overview:
 *  This represents a specific Pest Sample instance, in this case a Greenbug sample.
 */
public class GreenbugSample extends PestSample {

    // The database ID associated with this Greenbug Sample
    protected int SpecificID;

    // Whether or not to treat based on this sample
    protected Boolean TreatmentRecommendation;

    // The number of greenbugs detected via sampling
    protected int AphidCount;

    // The number of mummies detected via sampling
    protected int MummyCount;

    public GreenbugSample() { }

    public GreenbugSample(int SpecificID, int GenericID, GPSLocation loc, int field,
                          double control, double crop, String notes, String[] otherPests,
                          Boolean Treatment, int Aphids, int Mummys)
    {
        super(GenericID, loc, field, control, crop, notes, otherPests);

        this.SpecificID = SpecificID;
        this.TreatmentRecommendation = Treatment;
        this.AphidCount = Aphids;
        this.MummyCount = Mummys;
    }

    public int getSpecificID() { return this.SpecificID; }
    public void setSpecificID(int id) { this.SpecificID = id; }

    public Boolean getTreatmentRecommendation() { return this.TreatmentRecommendation; }
    public void setTreatmentRecommendation(Boolean recommendation)
    { this.TreatmentRecommendation = recommendation; }

    public int getAphidCount() { return this.AphidCount; }
    public void setAphidCount(int count) { this.AphidCount = count; }

    public int getMummyCount() { return this.MummyCount; }
    public void setMummyCount(int count) { this.MummyCount = count; }

    public static GreenbugSample jsonRead(JSONObject sample) throws JSONException
    {
        int specificid = sample.getInt("SpecificID");
        boolean treat = sample.getInt("TreatmentRecommendation") == 1;
        int aphidcount = sample.getInt("AphidCount");
        int mummycount = sample.getInt("MummyCount");

        PestSample genericsample = PestSample.jsonRead(sample.getJSONObject("GenericSample"));

        return new GreenbugSample(specificid, genericsample.ID, genericsample.location,
                genericsample.fieldID, genericsample.ControlCost, genericsample.CropValue,
                genericsample.Notes, genericsample.OtherPests, treat, aphidcount, mummycount);

    }

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
