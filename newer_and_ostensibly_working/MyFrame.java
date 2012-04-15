//This is the Java class that represents the main frame of the application. It can launch all
//of the other wizard dialogues.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class MyFrame extends JFrame implements ActionListener
{
	JMenuBar menuBar1 = new JMenuBar();
	JMenu menuFile = new JMenu();
	JMenuItem menuFileStart = new JMenuItem();
	JMenuItem menuFileExit = new JMenuItem();
	JMenu menuHelp = new JMenu();
	JMenuItem menuHelpAbout = new JMenuItem();
	BorderLayout borderLayout1 = new BorderLayout();
	
	//webform and database the user acts upon
	private Database database = new Database();
	private Webform webform = new Webform();
	
	//construct the frame
	public MyFrame()
	{
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		
		try
		{
			myInit();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//component initialisation
	private void myInit() throws Exception
	{
		//try to register the JDBC-ODBC driver
		try
		{
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			Class.forName("com.mysql.jdbc.Driver");
		}
		
		catch(ClassNotFoundException e)
		{
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		//setup the widgets
		this.getContentPane().setLayout(borderLayout1);
		this.setSize(new Dimension(400, 300));
		this.setTitle("Code Generator Wizard");
		
		menuFile.setText("File");
		menuFileStart.setText("Start");
		menuFileStart.addActionListener(this);
		menuFileExit.setText("Exit");
		menuFileExit.addActionListener(this);
		
		menuHelp.setText("Help");
		menuHelpAbout.setText("About");
		menuHelpAbout.addActionListener(this);
		
		//add the options to the menubar
		//and then add the menubar to the frame
		menuFile.add(menuFileStart);
		menuFile.add(menuFileExit);
		menuHelp.add(menuHelpAbout);
		menuBar1.add(menuFile);
		menuBar1.add(menuHelp);
		this.setJMenuBar(menuBar1);
	}
	
	
	
	
	public void launchDialog1()
	{
		Dialog1 one = new Dialog1(this, "Step 1 - Enter DB details", true, database, webform);
		
		one.setVisible(true);
	}
	
	public void launchDialog2()
	{
		Dialog2 two = new Dialog2(this, "Step 2 - Choose table", true, database, webform);
		
		two.setVisible(true);
	}
	
	public void launchDialog3()
	{
		Dialog3 three = new Dialog3(this, "Step 3 - Choose fields", true, database, webform);
		
		three.setVisible(true);
	}
	
	public void launchDialog4()
	{
		Dialog4 four = new Dialog4(this, "Step 4 - Enter field labels", true, webform);
		
		four.setVisible(true);
	}
	
	public void launchDialog5()
	{
		Dialog5 five = new Dialog5(this, "Step 5 - Enter filename and generate!", true, webform);
		
		five.setVisible(true);
	}
	
	//overridden so we can exit on System Close
	protected void processWindowEvent(WindowEvent e)
	{
		super.processWindowEvent(e);
		
		if(e.getID() == WindowEvent.WINDOW_CLOSING)
		{
			System.exit(0);
		}
	}
	
	//
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		
		if(command.equals("About"))	//help | about action performed
		{
			
		}
		
		else if(command.equals("Exit"))	//file | exit action performed
		{
			System.exit(0);
		}
		
		else if(command.equals("Start"))	//file | start
		{
			this.launchDialog1();
		}
		
	}
}		