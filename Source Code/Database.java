
package tasklist;

import java.sql.*;
import java.io.*;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class Database 
{
	private Connection conn = null;
	
	public Database()
	{
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		    conn = DriverManager.getConnection(
		    		"jdbc:sqlserver://analysis.cp08bufezu5p.us-east-1.rds.amazonaws.com:1433;database=WalmartTasks;user=master;password=Passw1rd");	

		    conn.setAutoCommit(false);
		    System.out.println("Connection was made");
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
		}
		
	}
	

	
	public ResultSet selectAll()
	{
		ResultSet rs = null;
		PreparedStatement s = null;
		try 
		{
			s = conn.prepareStatement("SELECT * FROM WalmartTasks ORDER BY Status, Priority;");
			rs = s.executeQuery(); 
			return rs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return rs;
	}
	
	public ResultSet userGeneratedQuery(String sql)
	{
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try
		{
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			conn.commit();
			return rs;
		
		}
		catch(SQLException e)
		{
			
			e.printStackTrace();
		}
		
		
		return rs;
	}
	
	public ArrayList<ResultSet> batchQuery(ArrayList<String> sql)
	{
		ArrayList<ResultSet> rs = new ArrayList<ResultSet>();
		PreparedStatement stmt = null;
		
		try
		{
			for( String q : sql )
			{
				stmt = conn.prepareStatement(q,  ResultSet.TYPE_SCROLL_INSENSITIVE,
					    ResultSet.CONCUR_READ_ONLY);
				rs.add(stmt.executeQuery());
				conn.commit();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
		return rs;
	}
	
    public void deleteTable( String table ) 
    {
    	String sql = "DROP TABLE " + table + ";";
    	PreparedStatement stmt = null;
    	try
    	{
    	    stmt = conn.prepareStatement(sql);
    	    stmt.executeUpdate();
    	    conn.commit();
    	}
    	catch(SQLException e1)
    	{
    		e1.printStackTrace();
    	}
    }
    
}
