//This is the Java class to represent a dialogue in which the user can
//enter DSN connection details.

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;	//need?

public class Dialog1 extends JDialog implements ActionListener
{
	JPanel DSNPanel = new JPanel();
	JPanel userPanel = new JPanel();
	JPanel passwordPanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	
	JLabel DSNLabel = new JLabel();
	JLabel userLabel = new JLabel();
	JLabel passwordLabel = new JLabel();
	
	JTextField DSN = new JTextField();
	JTextField user = new JTextField();
	JPasswordField password = new JPasswordField();
	//JTextField password = new JTextField();
	
	JButton nextButton = new JButton();
	JButton cancelButton = new JButton();
	
	//private Frame1 parent;	//so we can launch the next dialogue
	private MyFrame parent;
	
	private Database database;
	private Webform webform;
	
	//constructor method
	public Dialog1(/*Frame*/ MyFrame frame, String title, boolean modal, Database db, Webform wf)
	{
		super(frame, title, modal);
		
		//parent = (Frame1)frame;
		parent = (MyFrame)frame;
		
		this.getContentPane().setLayout(new GridLayout(4, 1));
		
		database = db;
		webform = wf;
		
		try
		{
			myInit();
			pack();	//size according to layouts
			centre();
		}
		
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	void myInit() throws Exception
	{
		DSNLabel.setText("DSN:");
		userLabel.setText("user:");
		passwordLabel.setText("password:");
		
		//configure 'next' button
		nextButton.setText("Next >");
		nextButton.addActionListener(this);
		
		//configure 'cancel' button
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(this);
		
		//not needed with a decent layout manager?
		DSN.setColumns(10);
		user.setColumns(10);
		password.setColumns(10);
		
		//add widgets to panel
		DSNPanel.add(DSNLabel);
		DSNPanel.add(DSN);
		
		userPanel.add(userLabel);
		userPanel.add(user);
		
		passwordPanel.add(passwordLabel);
		passwordPanel.add(password);
		
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(nextButton);
		
		//add panels to dialogue
		this.getContentPane().add(DSNPanel);
		this.getContentPane().add(userPanel);
		this.getContentPane().add(passwordPanel);
		this.getContentPane().add(buttonsPanel);
	}
	
	
	
	
	
	void centre()
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
			//set frame's database DSN to whatever the user typed
			webform.setDSN(DSN.getText());
			database.setUser(user.getText());
			//database.setPassword(password.getText());
			database.setPassword(new String(password.getPassword()));
			database.setDSN(DSN.getText());
			
			
			
			database.connect();
			
			
			if(database.isConnected())
			{
				this.dispose();	//free resources and hide
				parent.launchDialog2();
			}
			
			//could not connect to database so focus and highlight the DSN
			//textfield (but what if error was invalid user/password)
			else
			{
				DSN.requestFocus();
				
				//this forms a selection
				DSN.setCaretPosition(0);
				DSN.moveCaretPosition(DSN.getText().length());
			}
		}
		
		else if(command.equals("Cancel"))
		{
			this.dispose();	//free resources and hide
		}
		
		
	}
}	