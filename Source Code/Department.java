package tasklist;


import java.sql.ResultSet;



import java.util.Scanner;

public class Department extends Thing 
{
	private Tasks tasks;
	
	/**
	 * Sends department to super class
	 * to set inherited variables
	 * @param sc  file line
	 */
	Department(Scanner sc)
	{
		super(sc);
	}
	
	Department(ResultSet rs)
	{
		super(rs);
	}
	
	/**
	 * Sets tasks object
	 * @param task   tasks object
	 */
	public void setTask(Tasks task)
	{
		if(task != null)
		{
			tasks = task;
		}
	}
		
	
	/**
	 * gets Task data
	 * @return String of task data
	 */
	public String getTask()
	{
		return tasks.toString();
	}
	
	/**
	 * Returns Task object
	 * @return task 
	 */
	public Tasks getTaskObject()
	{
		return tasks;
	}

	/**
	 * @return data of department in string
	 */
	public String toString()
	{
		return "Department: " + getName() +  " " + getIndex();  
	}	
	
	/**
	 * @return gets dock information with ship
	 * info
	 */
	public String getInfo()
	{
		return "Department: " + getName() +  " " + getIndex() + "\n" 
				+ getTask();  
	}
}
