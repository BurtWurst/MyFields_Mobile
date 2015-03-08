package com.myfields.kyle.pestsampler;

import java.util.ArrayList;

/**
 * This class represents a particular user, with their associated information.
 */
public class User {

    // The username of the user
    protected String user;

    // The hashed password of the user
    protected String pass;

    // This user's list of fields
    protected ArrayList<Field> Fields;

    // A constructor for building a new user
    public User(String user, String pass)
    {
        this.user = user;
        this.pass = pass;

        Fields = new ArrayList<Field>();
    }

    // A method to add or update fields for this user
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

    // A method to get this user's screen name
    public String getUserName()
    {
        return this.user;
    }

    // A method to get this user's hashed password
    public String getUserPassword()
    {
        return this.pass;
    }

    // A method to return this user's list of fields
    public ArrayList<Field> getFields()
    {
        return this.Fields;
    }

    // A method for checking if this user has a particular field
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