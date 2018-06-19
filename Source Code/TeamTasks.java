package tasklist;


import java.sql.ResultSet;




import java.util.Scanner;

public class TeamTasks extends Tasks
{
	double cargoValue, cargoVolume, cargoWeight;
	

	public TeamTasks(Scanner sc)
	{
		super(sc);
		cargoWeight = sc.nextDouble();
		cargoVolume = sc.nextDouble();
		cargoValue = sc.nextDouble();	
	}
	
	public TeamTasks(ResultSet rs)
	{
		super(rs);
	}
	

	/**
	 * @return cargoValue
	 */
	public double getCargoValue()
	{
		return cargoValue;
	}
	
	/**
	 * @return  cargoVolume
	 */
	public double getCargoVolume()
	{
		return cargoVolume;
	}
	
	/**
	 * @return cargoWeight
	 */
	public double getCargoWeight()
	{
		return cargoWeight;
	}
	
	/**
	 *@return CargoShip inherited data
	 */
	public String toString()
	{
		return "CargoShip: " + getName() + " " + getIndex();
	}
	
	/**
	 * @return  instance and super class data
	 */
	 public String getInfo()
	    {
	    	return "Cargo Ship  " + getName() + " " + getIndex() + "\n" 
	    			+"Cargo Value: " + cargoValue + "\n" + "Cargo Volume: " 
	    			+ cargoVolume + "\n" + "Cargo Weight: " + cargoWeight +"\n"
	    			+ "Draft: " + getDraft() + "\n" + "Length: " + getLength()
	    			+ "\nWeight: " + getWeight() + "\nWidth: " + getWidth();	
	    }
}