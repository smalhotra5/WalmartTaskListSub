package tasklist;


import java.sql.ResultSet;

/**
 * PassengerShip.java
 * @author Sumit Malhotra
 * 09/02/2017
 * 
 * Creates a PassengerShip object with
 * methods to return and set data
 * 
 */

import java.util.Scanner;

public class IndividualTasks extends Tasks
{
    int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;
    
    
    /**
     * Sets instance variables for
     * object and super class
     * @param sc   file line
     */
    IndividualTasks(Scanner sc)
    {
    	super(sc);
    	numberOfPassengers = sc.nextInt();
    	numberOfRooms = sc.nextInt();
    	numberOfOccupiedRooms = sc.nextInt();
    }
    
    
    IndividualTasks(ResultSet rs)
    {
    	super(rs);

    }
    
    
    /**
     * @return numberOfRooms
     */
    public int getNumberOfRooms()
    {
    	return numberOfRooms;
    }
    
    /**
     * @return numberOfOccupiedRooms
     */
    public int getNumberOfOccupiedRooms()
    {
        return numberOfOccupiedRooms;
    }
    
    /**
     * @return numberOfPassengers
     */
    public int getNumberOfPassengers()
    {
    	return numberOfPassengers;
    }
    
    /**
     * @return object information from super class
     */
    public String toString()
    {
    	return "Individual Tasks  " + getName() + " " + getIndex();
    }
    
    /**
     * @return passenger ship information with
     * object data
     */
    public String getInfo()
    {
    	return "Individual Tasks  " + getName() + " " + getIndex() + "\n" 
    			+"Number of Rooms: " + numberOfRooms + "\n" + "Number of Passengers: " 
    			+ numberOfPassengers + "\n" + "Number of Occupied Rooms: " + numberOfOccupiedRooms
    			+ "\nDraft: " + getDraft() + "\n" + "Length: " + getLength()
    			+ "\nWeight: " + getWeight() + "\nWidth: " + getWidth();
    }
}
