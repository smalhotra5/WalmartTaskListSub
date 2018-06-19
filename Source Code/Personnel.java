package tasklist;


/**
 * Person.java
 * @author Sumit Malhotra
 * 09/02/2017
 * 
 * Creates a person object with methods
 * to get and set data
 */

import java.util.Scanner;
import java.awt.Color;
import java.sql.ResultSet;

import javax.swing.JPanel;

public class Personnel extends Thing
{
    private String skill;
    Color status;
    JPanel resourcePanel;
    /**
     * sets instance variables of
     * person and super class
     * @param sc  file line
     */
    public Personnel(Scanner sc)
    {
    	super(sc);
    	skill = sc.next();
    	setStatus(false);
    }
    
    public Personnel(ResultSet rs)
    {
    	super(rs);
    }
    
  
    
    
    public void setStatus( boolean st )
    {
    	if(st == true)
    	{
    	    status = Color.RED;
    	}
    	else 
    	{
    		status = Color.GREEN;
    	}
    }
    
    public Color getStatus()
    {
    	return status;
    }
    
    /**
     * @return object information
     */
    public String toString()
    {
    	return "Person: " + getName() + " " + getIndex() + " " + skill;
    }
   
}
