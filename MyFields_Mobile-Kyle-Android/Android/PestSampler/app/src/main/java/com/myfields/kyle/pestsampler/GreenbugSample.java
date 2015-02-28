package com.myfields.kyle.pestsampler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Overview:
 *  This represents a specific Pest Sample instance, in this case a Greenbug sample.
 */
public class GreenbugSample extends PestSample {

    protected int SpecificID;
    protected Boolean TreatmentRecommendation;
    protected int AphidCount;
    protected int MummyCount;

    public GreenbugSample(int SpecificID, int GenericID, double Lat, double Lon, Field field,
                          double control, double crop, String notes, String[] otherPests,
                          Boolean Treatment, int Aphids, int Mummys)
    {
        super(GenericID, Lat, Lon, field, control, crop, notes, otherPests);

        this.SpecificID = SpecificID;
        this.TreatmentRecommendation = Treatment;
        this.AphidCount = Aphids;
        this.MummyCount = Mummys;
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

}
