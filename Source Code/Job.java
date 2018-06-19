package tasklist;


import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import javax.swing.*;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.util.concurrent.locks.ReentrantLock;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.GridLayout;



public class Job extends Thing implements Runnable
{
	static Random rn = new Random();
	Personnel worker = null;
	private int priority;
	
    
	//GUI elements
	private JProgressBar pm = new JProgressBar();
    private JPanel parent;
    private JPanel vert;
    private JLabel jobNameLbl;
    private JLabel parentLbl;
    private JButton stopBtn = new JButton("Stop");
    private JButton cancelBtn = new JButton("Cancel");
    
    public boolean goflag = false;
    private boolean taskComplete = false;
    private boolean pause = false;
    private boolean visited = false;
   
    Status status = Status.SUSPENDED;
    enum Status{RUNNING, SUSPENDED, WAITING, DONE};
     
    private JPanel req;
    
    ReentrantLock lock;
    private double duration;
    private ArrayList<String> requirements;
    private ArrayList<Personnel> acquired;
    
    /**
     * Sets super class variables 
     * and instance variables
     * @param sc
     */
    Job(Scanner sc)
    {
    	super(sc);
    	duration = sc.nextDouble();
    }
    
    Job(ResultSet rs)
    {
    	super(rs);
    	
    }

    
    
    /**
     * Shows current status of thread and
     * updates GUI elements
     * @param st
     */
    private void showStatus(Status st)  // check status according to st
    {
    	switch(st)
    	{
    	    case RUNNING:
    	        stopBtn.setBackground(Color.green);
    	        stopBtn.setText("Running");
    	        break;
    	    case SUSPENDED:
    	    	stopBtn.setBackground(Color.yellow);
    	    	stopBtn.setText("Suspended");
    	    	break;
    	    case WAITING:
    	    	stopBtn.setBackground(Color.orange);
    	    	stopBtn.setText("Waiting turn");
    	    	break;
    	    case DONE:
    	    	stopBtn.setBackground(Color.red);
    	        stopBtn.setText("Done");
                break;
    	}	
    }
    
    /**
     * Runnable method that progresses job
     * in relation to the duration 
     * 
     */
    public void run()  //run the program 
    {

    }
    
    /**
     * adds jobs to acquired array list and 
     * deletes from person list in seaport
     * @param res person arraylist
     * @return arraylist
     */
   public ArrayList<Personnel> acquireJobs(ArrayList<Personnel> res)
   { 
	  
   return null;
   }
   
   /**
    * returns resources stored in acquired array list to person arraylist in 
    * seaport
    * @param res  person array list
    * @return   res person arraay list
    */
   public ArrayList<Personnel> returnResources(ArrayList<Personnel> res)
   {
	   try
	   {
           lock.lock();
	       for(Personnel p : acquired)
	       {
	    	   p.setStatus(false);
	    	   res.add(p);
	       }
	   }
	   catch(Exception e1)
	   {
		   
	   }
	   finally
	   {
	       lock.unlock();
	   }
	   
	   return res;
	  
   }
    
   /**
    * checks if acquired has all jobs 
    * @return  boolean
    */
   public boolean resourceCount()
   {
	   if(acquired.size() == requirements.size())
	   {
		   return true;
	   }
	   return false;
   }
    
    
    /**
     * 
     * @return taskComplete job completion
     */
    public boolean getStatus()
    {
    	return taskComplete;
    }
    
    public boolean getVisited()
    {
    	return visited;
    }
    
    public void setVisited(boolean v)
    {
    	visited = v;
    }
    
    
	/**
	 * @return duration job time
	 */
    public double getDuration()
    {
    	return duration;
    }
    
    public int getPriority()
    {
    	return priority;
    }
    
    public String getPerson()
    {
    	return worker.toString();
    }
    
    public JProgressBar getProgress()
    {
    	return pm;
    }
    
    /**
     * @return String object Job information
     */
    public String toString()
    {
    	return "Name: " + getName() + " Index: " 
            + getIndex() + " Duration: " + duration;
    } 
}
