//This is the Java class that represents the main frame of the application. It can launch all
//of the other wizard dialogues.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class MyFrame extends JFrame
{
	JMenuBar menuBar1 = new JMenuBar();
	JMenu menuFile = new JMenu();
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
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		
		catch(ClassNotFoundException e)
		{
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		//setup the widgets
		this.getContentPane().setLayout(borderLayout1);
		this.setSize(new Dimension(400, 300));
		this.setTitle("Frame Title");
		
		menuFile.setText("File");
		menuFileExit.setText("Exit");
		menuFileExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fileExit_actionPerformed(e);
			}
		});
		
		menuHelp.setText("Help");
		menuHelpAbout.setText("About");
		menuHelpAbout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				helpAbout_actionPerformed(e);
			}
		});
		
		//add the options to the menubar
		//and then add the menubar to the frame
		menuFile.add(menuFileExit);
		menuHelp.add(menuHelpAbout);
		menuBar1.add(menuFile);
		menuBar1.add(menuHelp);
		this.setJMenuBar(menuBar1);
	}
	
	//file | exit action performed
	public void fileExit_actionPerformed(ActionEvent e)
	{
		System.exit(0);
	}
	
	//help | about action performed
	public void helpAbout_actionPerformed(ActionEvent e)
	{
	}
	
	public void launchDialogue1()
	{
		Dialog1 one = new Dialog1(this, "Dialogue 1 - Enter DB details", true, database, webform);
		
		one.setVisible(true);
	}
	
	public void launchDialogue2()
	{
		Dialog2 two = new Dialog2(this, "Dialogue 2 - Choose table", true, database, webform);
		
		two.setVisible(true);
	}
	
	public void launchDialogue3()
	{
		Dialog3 three = new Dialog3(this, "Dialogue 3 - Choose fields", true, database, webform);
		
		three.setVisible(true);
	}
	
	public void launchDialogue4()
	{
		Dialog4 four = new Dialog4(this, "Dialogue 4 - Enter field labels", true, webform);
		
		four.setVisible(true);
	}
	
	public void launchDialogue5()
	{
		Dialog5 five = new Dialog5(this, "Dialogue 5 - Enter filename and generate!", true, webform);
		
		five.setVisible(true);
	}
	
	//overridden so we can exit on System Close
	protected void processWindowEvent(WindowEvent e)
	{
		super.processWindowEvent(e);
		
		if(e.getID() == WindowEvent.WINDOW_CLOSING)
		{
			fileExit_actionPerformed(null);
		}
	}
}
	
	
		
		
		
		