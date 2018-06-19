package tasklist;


import java.sql.ResultSet;



import java.util.HashMap;
import java.util.Scanner;


public class Tasks extends Thing
{
    private TaskTime arrivalTime, dockTime;
    private double draft, length, weight, width;
    private HashMap<Integer, Job> jobs;
    
   
    Tasks( Scanner sc )
    {
    	super( sc );
    	weight = sc.nextDouble();
    	length = sc.nextDouble();
    	width =  sc.nextDouble();
    	draft = sc.nextDouble();	
    	jobs = new HashMap<Integer,Job>();
    }
    
    Tasks(ResultSet rs)
    {
    	super(rs);
    	weight = 0;
    	length = 0;
    	width =  0;
    	draft = 0;
    	jobs = new HashMap<Integer,Job>();
    }
     
    /**
     * @return jobs arraylist
     */
    public HashMap< Integer, Job > getJobs()
    {
    	return jobs;
    }
    
    /**
     * adds new Job to 
     * jobs data structure
     */
    public void insertJob( Job job )
    {
        jobs.put( job.getIndex() , job );
    }
		
    /**
     * @return task name and index
     */
    public String getInfo()
    {
    	return "Name: " + name + " Index:" + index;
    }
    
    /**
     * @return name 
     */
    public String getTaskName()
    {
    	return name;
    }
    
    /**
     * @return draft
     */
    public double getDraft()
    {
    	return draft;
    }
    
    /**
     * @return length
     */
    public double getLength()
    {
    	return length;
    }
    
    /**
     * @return weight
     */
    public double getWeight()
    {
    	return weight;
    }
    
    /**
     * @return width
     */
    public double getWidth()
    {
    	return width;
    }
    
}
