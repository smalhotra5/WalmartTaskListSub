package tasklist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Thing implements Comparable<Thing>
{
    protected int index;
    protected String name;
    protected int parent;
    
    Thing()
    {
    	index = 0;
    	name = "";
    	parent = 0;
    }
    
    /**
     * Sets instance variables with
     * file data
     * @param sc   file data
     */
     Thing(Scanner sc)
     {
    	 name = sc.next();
    	 index = sc.nextInt();
    	 parent = sc.nextInt();
     }
     
     Thing(ResultSet rs)
     {
    	 try 
    	 {
			name = rs.getString("Name");
			index = Integer.parseInt(rs.getString("Index"));
	    	parent = Integer.parseInt(rs.getString("Parent"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
     }
    
    
    /**
     * Compares objects by name
     * @o  object to compare with this
     */
	@Override
	public int compareTo( Thing o ) 
	{
	    return this.getName().compareTo( o.getName() );	
	}
	
    /**
     * gets index instance
     * @return  index
     */
	public int getIndex()
	{
		return index;
	}
	
    /**
     * gets name instance
     * @return  name
     */
	public String getName()
	{
		return name;
	}
	
    /**
     * gets parent instance
     * @return  parent
     */
	public int getParent()
	{
		return parent;
	}
}

