package com.myfields.kyle.pestsampler;

import java.util.ArrayList;

/**
 * Created by Danny on 3/4/2015.
 */
public class User {

    protected String user;
    protected String pass;

    protected ArrayList<Field> Fields;

    public User(String user, String pass, ArrayList<Field> userFields)
    {
        this.user = user;
        this.pass = pass;

        Fields = userFields;
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


}
