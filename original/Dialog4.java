//This is the Java class to represent a dialogue in which the
//user can enter labels for the chosen fields.

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

public class Dialog4 extends JDialog
{
	JPanel fieldPanels[];
	JPanel buttonsPanel;
	JPanel onAScrolly = new JPanel(new GridLayout(0, 1));
	
	//private Frame1 parent;	//so we can launch the next dialogue
	private MyFrame parent;
	
	JList availableFieldsList, selectedFieldsList;
	
	DefaultListModel availableListModel = new DefaultListModel();
	DefaultListModel selectedListModel = new DefaultListModel();
	
	JButton next = new JButton();
	JButton back = new JButton();
	JButton copyOne, copyAll, removeOne, removeAll;
	JLabel fields[];
	JTextField prompts[];
	Vector fieldsVector = new Vector();
	Webform webform;
	
	//constructor method
	public Dialog4(/*Frame*/MyFrame frame, String title, boolean modal, Webform wf)
	{
		super(frame, title, modal);
		//parent = (Frame1)frame;
		parent = (MyFrame)frame;
		
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
		//get number of user selected fields from passed Webform
		int size = webform.getFields().size();
		
		this.getContentPane().setLayout(new GridLayout(0, 1));
		
		fields = new JLabel[size];
		prompts = new JTextFields[size];
		fieldPanels = new JPanel[size];
		
		//get fields from passed Webform
		fieldsVector = webform.getFields();
		
		WebformField temp;
		
		for(int loop = 0; loop < size; loop++)
		{
			//instantiate each field and prompt array element and the JPanel
			//to contain them
			fields[loop] = new JLabel();
			prompts[loop] = new JTextField();
			fieldPanels[loop] = new JPanel();
			
			//set text of current field label (from current selectedField)
			temp = (WebformField)fieldsVector.elementAt(loop);
			
			fields[loop].setText(temp.getFieldname());
			
			prompts[loop].setText(temp.getFieldname());
			
			//add label and textfield to panel
			fieldPanels[loop].add(fields[loop]);
			fieldPanels[loop].add(prompts[loop]);
		}
		
		//configure 'next' button
		next.setText("Next >");
		next.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				nextMouseClicked(e);
			}
		});
		
		//configure 'back' button
		back.setText("< Back");
		back.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				backMouseClicked(e);
			}
		});
		
		//configure panel
		buttonsPanel = new JPanel();
		
		//add field labels and prompt textFields to appropriate panels
		for(int loop = 0; loop < fields.length; loop++)
		{
			onAScrolly.add(fieldPanels[loop]);
		}
		
		JScrollPane scrolly = new JScrollPane(onAScrolly);
		
		//add buttons to buttonsPanel
		buttonsPanel.add(back);
		buttonsPanel.add(next);
		
		//add panels to dialogue
		this.getContentPane().add(scrolly);
		this.getContentPane().add(buttonsPanel);
	}
	
	void nextMouseClicked(MouseEvent e)
	{
		//add user's entered label to all fields in 'selected' list
		
		int size = webform.getFields().size();
		boolean allPresent = true;
		
		//check for empty fields, we *must* have labels
		for(int loop = 0; loop < size; loop++)
		{
			if(prompts[loop].getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, fields[loop].getText() + " is null!", "Warning", JOptionPane.WARNING_MESSAGE);
				
				allPresent = false;
				
				//could break out of the loop here actually...
			}
		}
		
		if(allPresent)
		{
			Vector fieldsList = webform.getFields();
			
			//record entered prompts (but where?)
			//no need to replace the vectors?
			for(int loop = 0; loop < size; loop++)
			{
				String currentLabel = prompts[loop].getText();
				
				WebformField temp = (WebformField)fieldsList.elementAt(loop);
				
				temp.setLabel(currentLabel);
				fieldsList.setElementAt(temp, loop);
				webform.setFields(fieldsList);
			}
			
			//move on to next dialogue
			this.dispose();
			parent.launchDialogue5();
		}
	}
	
	void backMouseClicked(MouseEvent e)
	{
		this.dispose();
		parent.launchDialogue3();
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
}