package tasklist;

import java.io.ObjectOutputStream.PutField;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;


public class Walmart extends Thing 
{
	private HashMap<Integer ,Department> departments;
	public HashMap<Integer, Tasks> que;
	private HashMap<Integer, Tasks> tasks;
	private HashMap<Integer , Personnel> personnels;
	ArrayList<Personnel> personAL;
	ReentrantLock lock;
		
	/**
	 * Uses scanner to set
	 * Walmart instance variables
	 * @param sc   file line
	 */
	Walmart(Scanner sc)
	{
	    super(sc);
		departments = new HashMap<Integer, Department>();
		que = new HashMap<Integer, Tasks>();
		tasks = new HashMap<Integer, Tasks>();
		personnels = new HashMap<Integer, Personnel>();
		personAL = new ArrayList<Personnel>();
	
	}
	
	
	Walmart(ResultSet rs)
	{
	    super(rs);
		departments = new HashMap<Integer, Department>();
		que = new HashMap<Integer, Tasks>();
		tasks = new HashMap<Integer, Tasks>();
		personnels = new HashMap<Integer, Personnel>();
		personAL = new ArrayList<Personnel>();
	
	}

	private void queToDock(Department department)
	{
        for(Integer key : que.keySet())
		{
		    department.setTask( que.get(key) );
			que.remove(key);
			
			for(Job jb : department.getTaskObject().getJobs().values())
			{
			
			}
    	    break;
		}	    
	}
	
	/**
	 * Sends person array list to currently running jobs
	 */
	public void sendResourcesToJob()
	{
	        for( Department department : departments.values() )
	        {
	        	for( Job job : department.getTaskObject().getJobs().values() )
	        	{
	    	    	if(!job.getStatus())
	    	    	{
	    	    	    personAL = job.acquireJobs(personAL);
	    	    	}
	        	}
	        }
	}
	

	public void insertDepartment(Department department)
	{	
		departments.put(department.getIndex(), department);
	}
	
	public void insertTasks(Tasks tasks)
	{
		this.tasks.put(tasks.getIndex(), tasks);
	}
	
	
/**
 * adds person object to persons hashmap
 * @param personnel   object with person data
 */
	public void insertPersonnel(Personnel personnel)
	{
		personnels.put(personnel.getIndex(), personnel);
		personAL.add(personnel);
	}
	

	public void insertQue(Tasks tasks)
	{
		que.put( tasks.getIndex(), tasks );
	}
	
	
	public HashMap<Integer, Department> getDepartments()
	{
		return departments;
	}
	
	public HashMap<Integer , Tasks> getTasks()
	{
		return tasks;
	}
	
	public HashMap<Integer ,Personnel> getPersonnel()
	{
		return personnels;
	}
	
	public HashMap<Integer ,Tasks> getQue()
	{
		return que;
	}

	public String getInfo()
	{	
	    return "Seaport: " + getName() + " Index: " + getIndex(); 
	}

	public String toString()
	{		
		
		String output = "\nSeaport: " + getName() + " " + getIndex() + "\n" + "\n";
		
		//adding docks to string
		output += "\n-----List of all Departments\n";
        for(Department department : departments.values() )
        {
        	output += department.toString() + "\n";
        }
        
        //adding all ship to string 
        output += "\n-----List of all Tasks\n";
        for(Tasks tasks : tasks.values())
        {
        	output +=  tasks.toString() + "\n"; 
        }
        
        //adding all ship in que to string 
        output += "\n-----List of all Tasks in Que\n";
        for(Tasks tasks : que.values())
        {
        	output +=  tasks.toString() + "\n"; 
        }
        
        //adding all persons to string 
        output += "\n-----List of all Persons\n";
        for(Personnel personnel : personnels.values())
        {
        	output +=  personnel.toString() + "\n"; 
        }
        
        //adding all jobs to string 
        output += "\n-----List of all Jobs\n";
        for(Tasks tasks : tasks.values())
        {
        	for(Job job : tasks.getJobs().values())
            {
        	    output +=  job.toString() + "\n"; 
            }
	    }
      
		return output;
	}
}
