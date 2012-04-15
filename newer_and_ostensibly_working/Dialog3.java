//This is the Java class to represent a dialogue in which the user selects
//which fields to use from a list of all available fields.

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;	//need?

public class Dialog3 extends JDialog implements ActionListener
{
	JPanel labelsPanel = new JPanel();
	JPanel topPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	JPanel availablePanel = new JPanel();
	JPanel selectedPanel = new JPanel();
	
	JLabel availableLabel = new JLabel();
	JLabel selectedLabel = new JLabel();
	
	//private Frame1 parent;	//so we can launch the next dialogue
	private MyFrame parent;
	
	JList availableFieldsList, selectedFieldsList;
	
	DefaultListModel availableListModel = new DefaultListModel();
	
	DefaultListModel selectedListModel = new DefaultListModel();
	
	JButton next = new JButton();
	JButton back = new JButton();
	JButton copyOne, copyAll, removeOne, removeAll;
	Database database;
	Webform webform;
	
	GridBagLayout gridbag = new GridBagLayout();
	
	//constructor method
	public Dialog3(/*Frame*/MyFrame frame, String title, boolean modal, Database db, Webform wf)
	{
		super(frame, title, modal);
		//parent = (Frame1)frame;
		parent = (MyFrame)frame;
		
		database = db;
		webform = wf;
		
		//we have top and bottom panels
		this.getContentPane().setLayout(gridbag);
		availablePanel.setLayout(new GridLayout(0, 1));
		buttonsPanel.setLayout(new GridLayout(0, 1));
		selectedPanel.setLayout(new GridLayout(2, 1));
		
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
		//use webform.table property to get fields
		//from database.getFields() method. phew!
		availableFieldsList = new JList(database.getFieldsForTable(webform.getTable()));
		
		JScrollPane availablePane = new JScrollPane(availableFieldsList);
		
		//set labels
		availableLabel.setText("Available fields:");
		selectedLabel.setText("Selected fields:");
		
		//selectedFieldsList will be initially empty
		selectedFieldsList = new JList(selectedListModel);
		JScrollPane selectedPane = new JScrollPane(selectedFieldsList);
		
		//configure the 'next' button
		next.setText("Next >");
		next.addActionListener(this);
		
		//configure 'back' button
		back.setText("< Back");
		back.addActionListener(this);
		
		copyOne = new JButton(">");
		copyOne.addActionListener(this);
		
		copyAll = new JButton(">>");
		copyAll.addActionListener(this);
		
		removeOne = new JButton("<");
		removeOne.addActionListener(this);
		
		removeAll = new JButton("<<");
		removeAll.addActionListener(this);
		
		//add widgets (and scrollPanes) to panel
		availablePanel.add(availablePane);
		
		buttonsPanel.add(copyOne);
		buttonsPanel.add(copyAll);
		buttonsPanel.add(removeOne);
		buttonsPanel.add(removeAll);
		
		selectedPanel.add(selectedPane);
		
		topPanel.add(availablePanel);
		topPanel.add(buttonsPanel);
		topPanel.add(selectedPanel);
		
		bottomPanel.add(back);
		bottomPanel.add(next);
		
		//add panels to dialogue
		this.getContentPane().add(topPanel);
		
		this.getContentPane().add(bottomPanel);
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
			//add all fields in 'selected' list to parent's webform
			//(as WebformFields)
			
			int size = selectedListModel.getSize();
			
			if(size == 0)	//only progress if at least 1 field is chosen
			{
				JOptionPane.showMessageDialog(null, "You should choose at least one field!", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			
			else
			{
				for(int loop = 0; loop < size; loop++)
				{
					DatabaseField currentField = (DatabaseField)selectedListModel.getElementAt(loop);
					
					WebformField currentWebformField = new WebformField(currentField.getFieldname(), currentField.getType(), currentField.getPrecision(), currentField.isRequired());
					
					webform.addField(currentWebformField);
				}
				//move on to next dialogue
				this.dispose();
				parent.launchDialog4();
			}
		}
		
		else if(command.equals("< Back"))
		{
			this.dispose();
			parent.launchDialog2();
		}
		
		else if(command.equals(">"))	//currently copies all *selected* fields across
		{
			int selectedFields[] = availableFieldsList.getSelectedIndices();
		
			//remember we didn't set up availableFieldsList
			//with a ListModel
			//(which we need to get its selected items)
			ListModel temp = availableFieldsList.getModel();
			
			for(int loop = 0; loop < selectedFields.length; loop++)
			{
				selectedListModel.addElement((DatabaseField)temp.getElementAt(selectedFields[loop]));
			}
			
			//refresh
			selectedFieldsList.setModel(selectedListModel);
		}
		
		else if(command.equals(">>"))
		{
			//clear selectedFieldsList (eliminate chance for duplicates)
			int size = selectedListModel.getSize();
			
			for(int loop = 0; loop < size; loop++)
			{
				selectedListModel.remove(0);
			}
			
			//add to selectedListModel *all* fields in availableFieldsList
			//(as DatabaseFields!)
			ListModel temp = availableFieldsList.getModel();
			size = temp.getSize();
			
			for(int loop = 0; loop < size; loop++)
			{
				selectedListModel.addElement((DatabaseField)temp.getElementAt(loop));
			}
			
			//refresh
			selectedFieldsList.setModel(selectedListModel);
		}
		
		else if(command.equals("<"))	//currently removes *all* selected fields
		{
			int selectedFields[] = selectedFieldsList.getSelectedIndices();
		
			//clear selectedFieldsList (eliminate chance for duplicates)
			
			for(int loop = 0; loop < selectedFields.length; loop++)
			{
				selectedListModel.removeElementAt(selectedFields[loop]);
			}
			
			//refresh
			selectedFieldsList.setModel(selectedListModel);
		}
		
		else if(command.equals("<<"))
		{
			//clear selectedFieldsList (elimiate chance for duplicates)
			int size = selectedListModel.getSize();
			
			for(int loop = 0; loop < size; loop++)
			{
				selectedListModel.remove(0);
			}
			
			//refresh
			selectedFieldsList.setModel(selectedListModel);
		}
	}
}		