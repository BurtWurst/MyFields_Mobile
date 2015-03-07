package com.myfields.kyle.pestsampler;

import java.util.ArrayList;

/**
 * Created by Danny on 3/4/2015.
 */
public class User {

    protected String user;
    protected String pass;

    protected ArrayList<Field> Fields;

    public User(String user, String pass)
    {
        this.user = user;
        this.pass = pass;

        Fields = new ArrayList<Field>();
    }
	
	public void UpdateFields(ArrayList<Field> updates)
	{
		int hasField;
		for(Field f : updates)
		{
			hasField = this.hasField(f.getID());
			if(hasField == -1)
			{
				Fields.add(f);
			}
			else
			{
				if(!f.equals(Fields.get(hasField)))
				{
                    Fields.set(hasField, f);
				}
			}
		}
	}

    public String getUserName()
    {
        return this.user;
    }

    public String getUserPassword()
    {
        return this.pass;
    }

    public ArrayList<Field> getFields()
    {
        return this.Fields;
    }
	
	private int hasField(int ID)
	{
		int returnValue = -1;
		
		for(int i = 0; i < Fields.size(); i++)
		{
			if(Fields.get(i).getID() == ID)
			{
				returnValue = i;
                break;
			}
				
		}
		
		return returnValue;
	}


}