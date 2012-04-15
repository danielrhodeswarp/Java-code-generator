//This is the Java class to represent a dialogue in which the user can
//enter a filename and generate the code for the webform.

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;	//need?

public class Dialog5 extends JDialog implements ActionListener
{
	JPanel filenamePanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	
	JLabel prompt = new JLabel();
	
	JButton nextButton = new JButton();
	JButton cancelButton = new JButton();
	JButton generateButton = new JButton();
	
	//private Frame1 parent;	//so we can launch the next dialogue
	private MyFrame parent;
	
	JTextField userEntryField = new JTextField();
	Webform webform;
	
	//constructor method
	public Dialog5(/*Frame*/MyFrame frame, String title, boolean modal, Webform wf)
	{
		super(frame, title, modal);
		//parent = (Frame1)frame;
		parent = (MyFrame)frame;
		
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
		prompt.setText("Filename (no extension):");
		
		//configure 'next' button
		nextButton.setText("Next >");
		nextButton.addActionListener(this);
		
		//configure 'cancel' button
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(this);
		
		//configure 'generate' button
		generateButton.setText("Generate");
		generateButton.addActionListener(this);
		
		//not needed with decent LayoutManager?
		userEntryField.setColumns(10);
		
		//add widgets to panel
		filenamePanel.add(prompt);
		filenamePanel.add(userEntryField);
		
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(nextButton);
		buttonsPanel.add(generateButton);
		
		//add panel to dialogue
		this.getContentPane().add(filenamePanel);
		this.getContentPane().add(buttonsPanel);
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
			this.dispose();	//free resources and hide
		}
		
		else if(command.equals("Cancel"))
		{
			this.dispose();	//free resources and hide
		}
		
		else if(command.equals("Generate"))
		{
			webform.generate(userEntryField.getText());
			this.dispose();
		}
	}
}