package tasklist;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.Collections;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;




//hashmap to arraylist and then sort
public class Global extends Thing
{
	private HashMap<Integer, Walmart> walmarts = new HashMap<Integer , Walmart>(); 
	private ArrayList<Tasks> tasks;
	private ArrayList<Thing> sortedByName;
	private Scanner sc;
	private Vector<String> columnNames;
	Database db;
	
	
	/**
	 * Extract data from file and sends scanner to 
	 * relevant methods
	 * @param file
	 */

		
	public Global( File file )
	{
		super();
		sortedByName = new ArrayList<Thing>();
		db = new Database();
		Scanner ln;
	    try 
	    {
		    sc = new Scanner( file );
		    while( sc.hasNextLine() )
		    {
		    String value = sc.nextLine().trim();
		    if( value.length() == 0 || value.startsWith("//") )
		    	continue;
			ln = new Scanner( value );
			//sents line to method with matching object
			  switch( ln.next() )  
		      {
		          case "port":
		    	      addWalmart(ln);
		    	      break;
		          case "dock":
		      	      addDepartment(ln);
		    	      break;
		          case "pship":
		    	      addIndividualTasks(ln);
		    	      break;
		          case "cship":
		    	      addTeamTasks(ln);
		    	      break;
		          case "person":
		    	      addPersonnel(ln);
		    	      break;
		          case "job":
		    	      addJob(ln);
		          default:
		    	      break;
		      }	
	      }
		   	
	    } 
	    catch (FileNotFoundException e) 
	    {
			
		}
	    catch(NullPointerException e1)
	    {
	    	
	    }
	    columnNames = new Vector<String>();
		columnNames.add("Walmart");
		columnNames.add("Department");
		columnNames.add("Tasks");
		columnNames.add("Personnel");
		columnNames.add("Status (%)");
		columnNames.add("Priority");
	    
	}
	
	/**
	 * Creates new Walmart object and 
	 * places it in walmarts
	 * @param sc    file line
	 */
	public void addWalmart( Scanner sc )
	{
		Walmart temp = new Walmart( sc );
	    walmarts.put( temp.getIndex() , temp ); 
	}
	

	/**
	 * Creates Department object and places
	 * it in department hashmap in ports
	 * @param sc   file line
	 */
	public void addDepartment( Scanner sc )
	{
		Department temp = new Department( sc );	
		if( walmarts.containsKey( temp.getParent() ) )
	        walmarts.get( temp.getParent() ).insertDepartment( temp );
	}
	

	
	/**
	 * Creates Individual Task object and places
	 * it in Task hashmap in ports and que
	 * if its at a department
	 * @param sc   file line
	 */
	public void addIndividualTasks( Scanner sc )
	{
        IndividualTasks temp = new IndividualTasks( sc );
        if( walmarts.containsKey( temp.getParent() ) )
        {
            walmarts.get( temp.getParent() ).insertTasks( temp );
            walmarts.get( temp.getParent() ).insertQue( temp );
        }
        else
        {
            for( Walmart location : walmarts.values() )
            {
               if( location.getDepartments().containsKey( temp.getParent() ) )
               {
        	       location.getDepartments().get( temp.getParent() ).setTask( temp );
        	       location.insertTasks(temp);
               }
            }
        }
	}
	

	
	
	/**
	 * Creates TeamTask object and places
	 * it in Task hashmap in walmarts and que
	 * if its at a department
	 * @param sc   file line
	 */
	public void addTeamTasks( Scanner sc )
	{
        TeamTasks temp = new TeamTasks( sc );
        if( walmarts.containsKey( temp.getParent() ) )
        {
            walmarts.get( temp.getParent() ).insertTasks( temp );
            walmarts.get( temp.getParent() ).insertQue( temp );
        }
        else
        {
            for( Walmart location : walmarts.values() )
            {
               if( location.getDepartments().containsKey( temp.getParent() ) )
               {
        	       location.getDepartments().get( temp.getParent() ).setTask( temp );
        	       location.insertTasks(temp);
               }
            }
        }
	}
	

   
	/**
	 * Creates Personnel object and places
	 * it in person hashmap in ports 
	 * if its at a department
	 * @param sc   file line
	 */
	public void addPersonnel( Scanner sc )
	{
		Personnel temp = new Personnel( sc );
		if( walmarts.containsKey( temp.getParent() ) )
	        walmarts.get(temp.getParent()).insertPersonnel( temp );
	}
	

	
	/**
	 * Creates Job object and places
	 * it in the parent task in the job
	 * data structure
	 * if its at a department, starts thread for department
	 * ships
	 * @param sc   file line
	 * @param p  JPanel object from walmarts Class
	 */
	public void addJob( Scanner sc )
	{	
		Job  temp =  new Job( sc );
		for( Walmart location: walmarts.values() )
		{
			for(Department department : location.getDepartments().values())
			{
					if(department.getTaskObject().getIndex() == temp.getParent() )
					{
						department.getTaskObject().insertJob( temp );
						
					}
					else if( location.getTasks().containsKey( temp.getParent() ) )
					{
			            location.getTasks().get(temp.getParent()).insertJob(temp);
			            
					}
				}
			}
		}
	
	
	
	
	/**
	 * searches walmart data structure by index
	 * @param indx   index of object
	 * @return  String of object data
	 */
	public String searchByIndex( int indx )
	{
	     if( walmarts.containsKey( indx ) )
	     {
	    	 return walmarts.get( indx ).getInfo();
	     }
	     else
	     {
	         for( Walmart location : walmarts.values() ) //fix
	         {
	             if( location.getDepartments().containsKey( indx ) )
	        	     return location.getDepartments().get( indx ).getInfo();
	             else if( location.getTasks().containsKey( indx ) )
	        	     return location.getTasks().get( indx ).getInfo();
	             else if( location.getPersonnel().containsKey( indx ) )
	        	     return location.getPersonnel().get( indx ).toString();
	             else if( location.getQue().containsKey( indx ) )
	        	     return location.getQue().get( indx ).getInfo(); 
	             else
	             {
	            	 for(Tasks tasks : location.getTasks().values())
	            	 {
	            		 if(tasks.getJobs().containsKey( indx ))
	            		 {
	            			 return tasks.getJobs().get(indx).toString();
	            		 }
	            	 }
	             }
	         }
	         
	     }
		 return "not found";
	}
	
	/**
	 * searches walmart data structure for name
	 * @param nme   target string
	 * @return  String of object data
	 */
	public String searchByName( String nme )
	{
		String output = "";
		for( Walmart location : walmarts.values() ) //fix
		{
			if( location.getName().equals( nme ) )
			{
				output =  location.getInfo();
				return output;
			}
			else if( !(output = searchTasks( location.getTasks(), nme )).equals(""))
			{
				return output;
			}
		   else if( !(output = searchPerson( location.getPersonnel(), nme )).equals(""))
		   {
				return output;
		   }
		   else if(!(output = searchDepartments( location.getDepartments(), nme )).equals(""))
		   {
		        return output;
		    // output = searchJob( port.getShips(), nme )
	       } 
		   else if( !(output = searchJobs( location.getTasks(), nme )).equals("") )
		   {
			  return output;
		   }
		}
		return "";
	}
	
	/**
	 * Searches tasks hashmap for name value
	 * @param tasks stores tasks
	 * @param name  target string
	 * @return  tasks object as string
	 */
	public String searchTasks( HashMap<Integer, Tasks> tasks, String name )
	{
		for( Tasks task : tasks.values() )
		{
			if( task.getName().equals( name ) )
			{
				return task.getInfo();
			}
		}
		return "";
	}
	
	/**
	 * Searches person hashmap for name value
	 * @param personnels stores persons
	 * @param name target string
	 * @return person with name as string
	 */
	public String searchPerson( HashMap<Integer, Personnel> personnels, String name )
	{
		for( Personnel personnel : personnels.values() )
		{
			if( personnel.getName().equals( name ) )
			{
				return personnel.toString();
			}
		}
		return "";
	}
	
	/**
	 * Searches department hashmap for name value
	 * @param departments stores docks
	 * @param name  target string
	 * @return departments with name as string
	 */
	public String searchDepartments( HashMap<Integer, Department> departments, String name )
	{
		for( Department department : departments.values() )
		{
			if( department.getName().equals( name ) )
			{
				return department.getInfo();
			}
		}
		return "";
	}

	/**
	 * Searches jobs hashmap within task object
	 * for name value
	 * @param tasks stores tasks
	 * @param name target string
	 * @return  job with name as string
	 */
	public String searchJobs( HashMap<Integer, Tasks> tasks, String name )
	{
		for( Tasks task : tasks.values() )
		{
			for( Job job : task.getJobs().values() )
			{
			    if( job.getName().equals( name ) )
			    {
				    return job.toString();
			    }
			}
		}
		return "";
	}
	
	/**
	 * searches walmarts data structure by type
	 * @param type   type of object
	 * @return  String of object data
	 */
	public String searchByType(String type)
	{
		String output = "";	
		type = type.toLowerCase();
	    switch( type )
	    {
	        case "dock":
	        	output = getAllDepartments();
	        	break;
	        case "ship":
	        	output = getAllTasks();
	        	break;
	        case "person":
	        	output = getAllPersons();
	        	break;
	        case "port":
	         	output = getAllWalmarts();
	         	break;
	        case "job":
	        	output = getAllJobs();
	        	break;
	        default:
	            output = "not found";
	        	break;
	    }
		return output;
	}
	
	/**
	 * @return all department to string
	 */
	public String getAllDepartments()
	{
		String output = "";
		for( Walmart location : walmarts.values() )
		{
	        for( Department department : location.getDepartments().values() )
	        {
	            output += department.getInfo() + "\n\n";
	        }
		}
		return output;
	}
	
	/**
	 * @return all tasks to string
	 */
	public String getAllTasks()
	{
	    String output = "";
	    for( Walmart location : walmarts.values() )
		{
	        for( Tasks tasks : location.getTasks().values() )
	        {
	            output += tasks.getInfo() + "\n\n";
	        }
		}
	    return output; 
	}
	
	/**
	 * @return all persons to string
	 */
	public String getAllPersons()
	{
	    String output = "";
	    for( Walmart location : walmarts.values() )
		{
	        for( Personnel personnel : location.getPersonnel().values() )
	        {
	            output += personnel.toString() + "\n\n";
	        }
		}
	    return output; 
	}
	
	
	/**
	 * @return all walmarts to string
	 */
	public String getAllWalmarts()
	{
	    String output = "";
	    for( Walmart location : walmarts.values() )
		{
	        output += location.getInfo() + "\n";
		}
	    return output; 
	}
	
	
	/**
	 * @return Returns all jobs in data structure
	 * to string
	 */
	public String getAllJobs()
	{
		String output = "";
		for( Walmart location : walmarts.values() )
		{
			for( Tasks tasks : location.getTasks().values() )
			{
				for( Job job: tasks.getJobs().values() )
				{
					output += job.toString() + "\n";
				}
			}
		}
		return output;
	}
	
	
	/**
	 * adds elements to sortedByName arraylist based on
	 * sortObject value and then sorts sortedByName 
	 * by name
	 * @param sortObject object type to sort 
	 */
	public void sortByName(String sortObject)
	{
	    switch(sortObject)
	    {
	        case "Port":
	    	     createThingArrayList("walmart");
	        	 break;
	        case "Dock":
	        	 createThingArrayList("department");
	        	 break;
	        case "Ship":
	    	     createThingArrayList("task");
	        	 break;
	        case "Person":
	        	 createThingArrayList("personnel");
	        	 break;
	        case "Job":
	    	     createThingArrayList("job");
	    	     break;
	        default:
	    	     break;
	    }
	    Collections.sort(sortedByName);
	}
	

   /**
    * Sorts ships arraylist based on the sortObject
    * paramter
    * @param sortObject attribute of ship to sort by
    */
	public void sortByShipAttribute(String sortObject)
	{
	    createShipArrayLists();
	    switch(sortObject)
	    {
	        case "Weight":
	        	Collections.sort(tasks, new SortByWeight());
	        	break;
	        case "Length":
	        	Collections.sort(tasks, new SortByLength());
	        	break;
	        case "Width":
	        	 Collections.sort(tasks, new SortByWidth());
	        	 break;
	        case "Draft":
	        	 Collections.sort(tasks, new SortByDraft());
	        	 break;
	        default:
	    	     break;
	    }
	}
	
	/**
	 *   method to create arraylist for 
	 *   sorting the ship elements	
	 */
	public void createShipArrayLists()
	{
	    tasks = new ArrayList<Tasks>();
		for( Walmart location : walmarts.values() )
		{
			for( Tasks tasks : location.getQue().values() )
			{
				this.tasks.add(tasks);
			}
		}	
	}
	
	/**
	 * This method adds elements to 
	 * sortedByName arraylist based on String
	 * type 
	 * @param type of of elements to add
	 */
	public void createThingArrayList(String type)
	{
		sortedByName.clear();
		if( type.equals("port") )
		{
			for(Walmart location : walmarts.values())
			{
			    sortedByName.add(location);
			}
		}
		else if( type.equals("ship") )
		{
			for(Walmart location : walmarts.values())
			{
				for(Tasks tasks : location.getTasks().values())
				{
					sortedByName.add(tasks);
				}
			}
		}
		else if( type.equals("dock") )
		{
			for(Walmart port : walmarts.values())
			{
				for(Department department : port.getDepartments().values())
				{
					sortedByName.add(department);
				}
			}
		}
		
		else if(type.equals("person"))
		{
			for(Walmart port : walmarts.values())
			{
				for(Personnel personnel : port.getPersonnel().values())
				{
					sortedByName.add(personnel);
				}
			}
		}
		else if(type.equals("job"))
		{
			for(Walmart port : walmarts.values())
			{
				for(Tasks tasks : port.getTasks().values())
				{
					for(Job job : tasks.getJobs().values())
					{
					    sortedByName.add(job);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @return tasks arraylist to custom
	 * string
	 */
	public String shipsToString()
	{
		String output = "";
		for(Tasks tasks : tasks)
		{
		    output += tasks.getInfo() + " \n\n";
		}
		return output;
	}
	
	
	/**
	 * 
	 * @return sortedByName arraylist to custom
	 * string
	 */
	public String thingsToString()
	{
		String output = "";
		for(Thing thing : sortedByName )
		{
		    output += thing.toString() + " \n\n";
		}
		return output;
	}
	
	
	//creates table model for tasks ordered by status and rank
	public DefaultTableModel createTableOfTasks(Vector<Object> rows)
	{
		
		String walmartLab = "";
		String departmentName = "";
		String taskInfo = "";
		String taskName = "";
		double progress = 0;
		int priority = 0;
			
        for(Walmart walmart : walmarts.values())
        {
        	for(Department department : walmart.getDepartments().values())
        	{
        		for(Job task : department.getTaskObject().getJobs().values())
        		{
        			Vector<Object> rowValues = new Vector<Object>();
        			rowValues.add(walmartLab = walmart.getName());
        			rowValues.add(departmentName = department.getName());
        			rowValues.add(taskInfo = department.getTask());
        			rowValues.add(taskName = task.getName());
        			rowValues.add(progress = task.getProgress().getValue());
        			rowValues.add(priority = task.getPriority());
        			rowValues.add("");
        			rows.add(rowValues);
        		
        			db.userGeneratedQuery("INSERT INTO WalmartTasks VALUES("+ null + ", " + null + ", " +
        			null + ", " + null +", " + progress + ", " + priority + ");");
        			
        		}
        	}
        }
		return new DefaultTableModel( rows , columnNames);
	}
	
	/**
	 * Creates Jtree object from walmarts data structure
	 * @return JTree of walmarts
	 */
	public JTree createJTree()
	{
		DefaultMutableTreeNode wrld = new DefaultMutableTreeNode("Global");
		DefaultMutableTreeNode prt;
		DefaultMutableTreeNode d;
		DefaultMutableTreeNode shp;
		DefaultMutableTreeNode jb;
		DefaultMutableTreeNode pn;
		
		for( Walmart port : walmarts.values() )
		{
			prt = new DefaultMutableTreeNode( port.getName() );
		    wrld.add( prt );	
		   
		   for( Department dk : port.getDepartments().values() )
		   {
			   d = new DefaultMutableTreeNode(dk.getName());
			   prt.add(d);
			   shp = new DefaultMutableTreeNode(dk.getTaskObject().getName());
			   d.add(shp);
			   
			   for(Job j : dk.getTaskObject().getJobs().values())
			   {
				   shp.add(new DefaultMutableTreeNode(j.getName()));
			   }   
		   }
		   
		   pn = new DefaultMutableTreeNode("Persons");
		   prt.add(pn);
		   for(Personnel p : port.getPersonnel().values())
		   {
			  pn.add(new DefaultMutableTreeNode(p.getName()));
		   }
		}	
	    return new JTree(wrld);
	}
	
	
	
	/**
	 * returns ports data structure
	 * @return String object data
	 */
	public String toString()
	{
		String output = ">>>>>>>All Walmart Labs\n\n";
		for( Walmart port : walmarts.values())
		{
		    output += port.toString();
		}
		return output;
	}	
}
