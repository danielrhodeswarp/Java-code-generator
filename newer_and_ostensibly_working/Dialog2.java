//This is the Java class to represent a dialogue in which the user can choose from
//a list of tables.

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;	//need?

public class Dialog2 extends JDialog implements ActionListener
{
	JPanel tablesPanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	
	JLabel tableLabel = new JLabel();
	JComboBox tables;
	JButton next = new JButton();
	JButton back = new JButton();
	//Frame1 parent;	//so we can launch the next dialogue
	MyFrame parent;
	
	Database database;
	Webform webform;
	
	//constructor method
	public Dialog2(/*Frame*/MyFrame frame, String title, boolean modal, Database db, Webform wf)
	{
		super(frame, title, modal);
		//parent = (Frame1)frame;
		parent = (MyFrame)frame;
		
		database = db;
		webform = wf;
		
		this.getContentPane().setLayout(new GridLayout(2, 1));
		
		try
		{
			myInit();
			pack();
			centre();
		}
		
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	void myInit() throws Exception
	{
		//fill combobox based on values in parent frame's database
		tables = new JComboBox(database.getTables());
		
		tableLabel.setText("Choose a table:");
		
		//configure 'next' button
		next.setText("Next >");
		next.addActionListener(this);
		
		//configure 'back' button
		back.setText("< Back");
		back.addActionListener(this);
		
		//add widgets to panel
		tablesPanel.add(tableLabel);
		tablesPanel.add(tables);
		
		buttonsPanel.add(back);
		buttonsPanel.add(next);
		
		//add panel to dialogue
		this.getContentPane().add(tablesPanel);
		this.getContentPane().add(buttonsPanel);
	}
	
	//define what happens when 'next' clicked
	void nextMouseClicked(MouseEvent e)
	{
		
	}
	
	//define what happens when 'back' clicked
	void backMouseClicked(MouseEvent e)
	{
		
	}
	
	private void centre()
	{
		//get size of hardware screen and this dialogue
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		Dimension dialogueSize = this.getSize();
		
		//clip if size of frame bigger than hardware screen
		if(dialogueSize.height > screenSize.height)
		{
			dialogueSize.height = screenSize.height;
		}
		
		if(dialogueSize.width > screenSize.width)
		{
			dialogueSize.width = screenSize.width;
		}
		
		//centre the dialogue
		this.setLocation((screenSize.width - dialogueSize.width) / 2, (screenSize.height - dialogueSize.height) / 2);
	}
	
	//
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		
		if(command.equals("Next >"))
		{
			//set frame's webform to have user's chosen table
			webform.setTable((String)tables.getSelectedItem());
			
			this.dispose();	//free resources and hide
			
			parent.launchDialog3();
		}
		
		else if(command.equals("< Back"))
		{
			this.dispose();
			parent.launchDialog1();
		}
		
		
	}
}