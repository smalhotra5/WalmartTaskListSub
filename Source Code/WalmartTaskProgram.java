package tasklist;

/***
 * WalmartTAskProgram.java
 * @author Sumit Malhotra
 * 06/18/2017
 * This program creates a user interface for reading files
 * and displaying information from the file
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTree;
import javax.swing.JTabbedPane;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class WalmartTaskProgram extends JFrame 
{	
	private JTextArea jta;
	private JScrollPane jsp;
	Global global;
	
	//Buttons for data on text area
	private JButton readBtn;
	private JButton searchBtn;
	private JButton displayBtn;
	private JLabel priorityLbl;
	private JTextField priorityTF;
	private JComboBox<String> employeeCB;
	private JScrollPane jTableScrollPane;
	
	//Sort Elements by name
	private JButton sortByNameBtn;
	private JLabel sortNameLbl; 
	private JLabel sortingObjectLbl;
	private JComboBox<String> sortObjectNameCB;
	static int count = 0;
	
	//creating main panel which is the bottom panel where all the controls and tree views are
	JPanel mainPanel;
	
	//Sort ship elements by attribute
	private JButton sortByAttBtn;
	private JLabel sortShipAttLbl;
	private JComboBox<String> sortShipAttCB;
	JPanel sortPanel; // a button panel for sorting within the main paenl
	JPanel ctrlPnl; // a button panel for reading, displaying, searching withing the main panel
	JPanel progressPanel; 
	JPanel upperPanel;
	JScrollPane side;
	private JTree tree;
	private JTabbedPane tb;
    ArrayList<File> files;
    private JPanel resourcePanel;
    private JScrollPane resource;
    private JTable table;
    private JScrollPane jsp2;
    private Vector<Object> rows;
    

	
	//instantiates all variables being placed into the
	//GUI and creates action listeners 
	//for buttons to perform functions
    WalmartTaskProgram()
    {
    	//title for the frame
    	setTitle("Walmart Task Program");
    	this.setSize(1100, 700);
    	//a text area held inside a scroll pane
    	jta = new JTextArea();
    	jta.setEditable(true);
    	jta.setFont(new java.awt.Font("Monospaced", 0 , 12));
    	jsp = new JScrollPane(jta);
    	jsp2 = new JScrollPane();
    	rows = new Vector<Object>();
    	
    	tb = new JTabbedPane();
    	
    	files = new ArrayList<File>();
    	mainPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
    	//add(jsp, BorderLayout.CENTER);
    
    	side = new JScrollPane();
    	
    	//Buttons for data on text area
    	readBtn = new JButton("Read");
    	searchBtn = new JButton("Search");
    	displayBtn = new JButton("Display");
        
    	//keyword searching
    	priorityLbl = new JLabel("Priority");
    	priorityTF = new JTextField(10);
    	
    	//search choices
    	employeeCB = new JComboBox <String> ();
    	employeeCB.addItem("Index");
    	employeeCB.addItem("Name");
    	employeeCB.addItem("Type");
    
    	//adds the elements to the top of the page
    	//with flowlayout
    	ctrlPnl = new JPanel();
    	ctrlPnl.add(readBtn);
    	ctrlPnl.add(displayBtn);
    	ctrlPnl.add(priorityLbl);
    	ctrlPnl.add(priorityTF);
    	ctrlPnl.add(employeeCB);
    	ctrlPnl.add(searchBtn);
        	
    	//sort elements 
        sortByNameBtn = new JButton("Sort By Name");
        
        //sort object combo box elements
        sortObjectNameCB = new JComboBox<String>();
        sortObjectNameCB.addItem("Walmart");
        sortObjectNameCB.addItem("Department");
        sortObjectNameCB.addItem("Task");
        sortObjectNameCB.addItem("Personnel");
        sortObjectNameCB.addItem("Job");
        
        sortPanel = new JPanel();
        //sort by name elements on GUI
        sortNameLbl = new JLabel("Sort By Name");
        sortPanel.add( sortNameLbl );
        sortPanel.add( sortObjectNameCB );
        sortPanel.add( sortByNameBtn );
        
        //sort by ship attributes 
    	sortByAttBtn = new JButton("Sort Tasks in Que");
    	sortShipAttLbl = new JLabel("Sort Tasks in Que");
        
    	sortShipAttCB = new JComboBox<String>();
        sortShipAttCB.addItem("Weight");
        sortShipAttCB.addItem("Length");
        sortShipAttCB.addItem("Width");
    	sortShipAttCB.addItem("Draft");
    	
    	
    	//add ship attribute element to GUI
        sortPanel.add( sortShipAttLbl );
        sortPanel.add( sortShipAttCB );
        sortPanel.add( sortByAttBtn );
        
        
        jsp2.setPreferredSize(new Dimension(900, 200));
        progressPanel = new JPanel();
       
               
        JPanel notesPnl = new JPanel();
        notesPnl.setLayout(new BoxLayout(notesPnl, BoxLayout.Y_AXIS));
        notesPnl.add(new JLabel("Additional Notes:"));
        notesPnl.add(new JScrollPane());
        
       mainPanel.add(ctrlPnl , BorderLayout.PAGE_START );
       mainPanel.add(jsp, BorderLayout.CENTER);
       mainPanel.add( sortPanel, BorderLayout.PAGE_END ); 
       mainPanel.add(notesPnl, BorderLayout.EAST);
       JPanel p = new JPanel(new GridLayout(2,1));
      
       p.add(jsp2);
       tb.addTab("Add Task", mainPanel);
       
       p.add(tb);
       add(p, BorderLayout.CENTER);
       
 
      
       sortByAttBtn.addActionListener(new ActionListener()
        		{
        		     public void actionPerformed(ActionEvent e)
                     {
                        try
                     	{
                     		jta.setText(" ");
                     		global.sortByShipAttribute(
                     				(String)sortShipAttCB.getSelectedItem());
                     		jta.append(global.shipsToString());
                     	}
                     	catch(NullPointerException e1)
                     	{
                     		jta.append("No File Selected");
                     	}
                     }
        		});
        
        
        sortByNameBtn.addActionListener(new ActionListener()
        		{
        	         public void actionPerformed(ActionEvent e)
        	         {
        	         	try
        	        	{
        	        		jta.setText(" ");
        	        		global.sortByName(
        	        				(String)sortObjectNameCB.getSelectedItem());
        	        		jta.append(global.thingsToString());
        	        	}
        	        	catch(NullPointerException e1)
        	        	{
        	        		jta.append("No File Selected");
        	        	}
        	         }
        		});
        
        //readbtn actionlistener calls readFile method
        readBtn.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				readFile();
			}
        });
        
        //searchBtn actionlistener calls search method
        searchBtn.addActionListener(new ActionListener()
        {
     			@Override
     			public void actionPerformed(ActionEvent e) 
     			{
     			    if(priorityTF.getText() != null)
     				    search((String)(employeeCB.getSelectedItem()), 
     			    	    	priorityTF.getText());     		
     			}
         });
        
        //actionlistener for dipslay button, gets world data 
        //from toString
        displayBtn.addActionListener(new ActionListener(){
     			@Override
     			public void actionPerformed(ActionEvent e) {
     				try
     				{
     					jta.setText("");
     					jta.append(global.toString());
     				}
     				catch(NullPointerException e1)
     				{
     			        jta.append("No File Selected");
     				}
     			}
          }); 
        
        Database db = new Database();
    	ResultSet dbResultSet = db.selectAll();
    	 //add Prexisting tasks in table
         try   
         {      
    		    while( dbResultSet.next() )
    		    {
    		       Vector<Object> temp = new Vector<Object>();
    		       
    		    
    		     	temp.add(dbResultSet.getString("Walmart") );
    		    	temp.add(dbResultSet.getString("Department") );
    		    	temp.add( dbResultSet.getString("Tasks") );
    		    	temp.add( dbResultSet.getString("Personnel") );
    		    	temp.add(dbResultSet.getString("Status") );
    		    	temp.add(dbResultSet.getString("Priority") );
    		    	rows.add(temp); 
    		    }
    		    
    		    Vector<String> columnNames = new Vector<String>();
    			columnNames.add("Walmart");
    			columnNames.add("Department");
    			columnNames.add("Tasks");
    			columnNames.add("Personnel");
    			columnNames.add("Status (%)");
    			columnNames.add("Priority");
    		    
                table = new JTable(new DefaultTableModel(rows, columnNames));
    	        jsp2.getViewport().add(table);
    	} 
         catch (SQLException e2) 
         {
    		// TODO Auto-generated catch block
    		e2.printStackTrace();
    	}
        catch(Exception e)
        {
            e.printStackTrace();	
        }
     
    
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setVisible(true);
    	this.setLocationRelativeTo(null);   
    }
       
    /**
     * Calls JFileChooser to select file
     * and then creates world object with file,
     * also calls method to create JTree and adds
     * to JFrame
     */
    public void readFile() 
    {
    	    JFileChooser chooser = new JFileChooser(".");
    	    chooser.showOpenDialog(null);
            jta.append ( "Read File button pressed\n\n" );
           
            global = new Global( chooser.getSelectedFile() );
           
            side = new JScrollPane(global.createJTree());
            table.setModel(global.createTableOfTasks(rows));
            
            jsp2.getViewport().add(table);
            
            if( !files.contains( chooser.getSelectedFile()  ) && chooser.getSelectedFile() != null )
            {
            	files.add( chooser.getSelectedFile() );
            	tb.addTab( chooser.getName( chooser.getSelectedFile() ) , side );
                this.revalidate();
                this.repaint();
            }       
    } // end method readFile
    
   
   /**
    * Method calls world method to search
    * by given parameters
    * @param type    combox box selection
    * @param target    search keyword
    */
    public void search(String type, String target) 
    {
    	try
    	{
        	jta.setText(" ");
        	if(type.equals("Index"))
        	{
         		jta.append(global.searchByIndex(Integer.valueOf(target)));
    	    }
    	    else if(type.equals("Name"))
        	{
        		jta.append (global.searchByName(target));
        	}
    	    else if(type.equals("Type"))
    	    {
           		jta.append(global.searchByType(target));
    	    }
    	}
    	catch(NullPointerException e1)
    	{
    		jta.append("No entry");
    	}
    	catch(NumberFormatException e2)
    	{
    		jta.append("No entry");
    	}
    } // end method readFile
    
 	public static void main(String[] args) 
	{
        WalmartTaskProgram prg = new WalmartTaskProgram();
	}
 	
}