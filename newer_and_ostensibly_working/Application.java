//This is the Java class containing the main() method that will actually run the
//application. It instantiates and displays an object of the MyFrame class.

import javax.swing.UIManager;
import java.awt.*;	//for Dimension and Toolkit classes

public class Application
{
	boolean packFrame = false;
	
	//construct the application
	public Application()
	{
		MyFrame frame = new MyFrame();
		
		//validate frames that have preset sizes
		//pack frames that have useful preferred size info, eg.
		//from their layout
		if(packFrame)
		{
			frame.pack();	//size to layout and layouts of subcomponents
		}
		
		else
		{
			frame.validate();	//refresh subcomponents
		}
		
		//get size of hardware screen and this frame
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		Dimension frameSize = frame.getSize();
		
		//clip if size of frame bigger than hardware screen
		if(frameSize.height > screenSize.height)
		{
			frameSize.height = screenSize.height;
		}
		
		if(frameSize.width > screenSize.width)
		{
			frameSize.width = screenSize.width;
		}
		
		//centre the window
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
		frame.setVisible(true);
	}
	
	//main method
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		catch(Exception e)
		{
		}
		
		new Application();
	}
}

