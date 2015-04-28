package com.myfields.kyle.pestsampler;

import java.util.ArrayList;

// ***************************************************************
// * OVERVIEW                                                    *
// * This provides an implementation of a User object, with      *
// * the associated properties as needed by the app.             *
// ***************************************************************
public class User {

    // The username of the user
    protected String user;

    // The hashed password of the user
    protected String pass;

    // This user's list of fields
    protected ArrayList<Field> Fields;

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A constructor which takes in all necessary parameters to    *
    // * construct a new User.                                       *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * User                                                        *
    // *    The database username of this user.                      *
    // * Pass                                                        *
    // *    The hashed password of this user.                        *
    // ***************************************************************
    public User(String user, String pass)
    {
        this.user = user;
        this.pass = pass;

        Fields = new ArrayList<Field>();
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method for synchronizing a user's fields with an array of *
    // * fields passed in. This will add any fields the user does    *
    // * not currently have, and update any that they do.            *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * Updates                                                     *
    // *    The list of fields to compare against this User's        *
    // *    fields.                                                  *
    // ***************************************************************
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

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method for getting a Field by its database identifier.    *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * ID                                                          *
    // *    The database identifier of the Field to return.          *
    // ***************************************************************
    public Field getFieldByID(int id)
    {
        return Fields.get(hasField(id));
    }

    // ***************************************************************
    // * OVERVIEW                                                    *
    // * ----------------------------------------------------------- *
    // * A method for checking if this user has a Field with a       *
    // * particular database identifier.                             *
    // ***************************************************************
    // * PARAMETERS                                                  *
    // * ----------------------------------------------------------- *
    // * ID                                                          *
    // *    The database identifier of the Field to check if this    *
    // *    user currently has.                                      *
    // ***************************************************************
	public int hasField(int ID)
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