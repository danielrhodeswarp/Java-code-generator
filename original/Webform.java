//This is the Java class to represent the webform definition as chosen by the user. It can
//generate itself based on its attributes.

import java.util.Vector;	//dynamic array class
import java.io.*;	//file IO classes
import java.sql.Types;	//for SQL type checking when writing ASP query strings

public class Webform
{
	private String middlewareType;
	private String table;
	private Vector fields;
	private String DSN;
	
	public Webform(String middleware)
	{
		//constructor
		middlewareType = middleware;
		fields = new Vector();
	}
	
	public Webform()
	{
		fields = new Vector();
	}
	
	//individual "write" functions add appropriate extension
	public void generate(String filenameMinusExtension)
	{
		writeHTMLForm(filenameMinusExtension);
		writeASPAddPage(filenameMinusExtension);
	}
	
	private void writeHTMLForm(String filename)
	{
		PrintWriter outputFile;
		BufferedReader topTemplate, bottomTemplate;
		boolean done = false;
		String temp = "";
		
		//try to write the HTML form
		try
		{
			outputFile = new PrintWriter(new FileOutputStream(filename + ".html"));
			
			topTemplate = new BufferedReader(new FileReader("top.txt"));
			
			//write top.txt to output file line-by-line
			while(!done)
			{
				temp = topTemplate.readLine();
				
				//readLine() returns null for end-of-stream
				if(temp == null)
				{
					done = true;
				}
				
				else
				{
					outputFile.println(temp);
				}
			}
			
			//write form tags to output file
			outputFile.println("<form method=post action=" + filename + ".asp>");
			
			for(int loop = 0; loop < fields.size(); loop++)
			{
				WebformField tempy = (WebformField)fields.elementAt(loop);
				
				//if type is boolean
				if(tempy.getType() == Types.BIT)
				{
					outputFile.println(tempy.getLabel() + "<input type=checkbox name=" + tempy.getFieldname() + "><br>");
				}
				
				else
				{
					//flag as required or not string
					String flag = "";
					
					if(tempy.isRequired())
					{
						flag = "*";
					}
					
					outputFile.println(tempy.getLabel() + "<input type=text name=" + tempy.getFieldname() + " maxlength=" + tempy.getPrecision() + ">" + flag + "<br>");
				}
			}
			
			outputFile.println("<input type=submit>");
			outputFile.println("<input type=reset>");
			outputFile.println("</form>");
			
			//write bottom.txt to output file line-by-line
			done = false;
			bottomTemplate = new BufferedReader(new FileReader("bottom.txt"));
			
			while(!done)
			{
				temp = bottomTemplate.readLine();
				
				//temp.equals(null) or temp.equals("") not work
				//because for equals(), argument needs to be not
				//null!
				if(temp == null)
				{
					done = true;
				}
				
				else
				{
					outputFile.println(temp);
				}
			}
			
			outputFile.close();
		}
		
		catch(IOException ioExcptnn)
		{
			System.out.print("Error with file " + filename + ": ");
			System.out.println(ioExcptnn.getMessage());
		}
	}
	
	private void writeASPAddPage(String filename)
	{
		PrintWriter outputFile;
		
		String insertStatement = "insertQuery = \"INSERT INTO " + this.table + "(";
		
		String valuesString = "VALUES(";
		
		//try to write the ASP "add" page
		try
		{
			String quote = "";	//or not!
			
			outputFile = new PrintWriter(new FileOutputStream(filename + ".asp"));
			
			//write DSN connection details to output file
			outputFile.println("<%");
			outputFile.println("\tset connection = server.createobject(\"ADODB.connection\")");
			outputFile.println("\tconnection.open \"" + DSN + "\"");
			outputFile.println();
			
			for(int loop = 0; loop < fields.size(); loop++)
			{
				WebformField tempy = (WebformField)fields.elementAt(loop);
				
				outputFile.println("\t" + tempy.getFieldname() + " = request.form(\"" + tempy.getFieldname() + "\")");
				
				//string in SQL queries need quotes
				quote = "";
				
				if(tempy.getType() == Types.VARCHAR)
				{
					quote = "'";
					
					//enables string arguments to have single quotes
					outputFile.println("\t" + tempy.getFieldname() + " = replace(" + tempy.getFieldname() + ", \"'\", \"''\")");
				}
				
				//add to insertStatement here (saves a second loop)
				insertStatement += tempy.getFieldname();
				
				if(loop != (fields.size() - 1))
				{
					insertStatement += ", ";
				}
				
				valuesString += quote + "\" + " + tempy.getFieldname();
				
				if(loop != (fields.size() - 1))
				{
					valuesString += " + \"" + quote + ", ";
				}
			}
			
			insertStatement += ") ";
			valuesString += " + \"" + quote + ")\"";
			outputFile.println();
			outputFile.println("\t" + insertStatement + valuesString);
			
			outputFile.println("\tconnection.execute(insertQuery)");
			
			outputFile.println("%>");
			
			outputFile.println("this.table = " + table);
			
			outputFile.println("insertStatement = " + insertStatement);
			
			outputFile.println("valuesString = " + valuesString);
			
			outputFile.println("<html>");
			outputFile.println("</html>");
			
			outputFile.close();
		}
		
		catch(IOException ioExcptn)
		{
			System.out.print("Error with file " + filename + ": ");
			System.out.println(ioExcptn.getMessage());
		}
	}
	
	public void setTable(String tablename)
	{
		table = tablename;
	}
	
	public String getTable()
	{
		return table;
	}
	
	public void setDSN(String newDSN)
	{
		DSN = newDSN;
	}
	
	public String getDSN()
	{
		return DSN;
	}
	
	public Vector getFields()
	{
		return fields;
	}
	
	//this method is maybe not ideal?
	public void setFields(Vector replacement)
	{
		fields = replacement;
	}
	
	public void addField(WebformField addMe)
	{
		fields.addElement(addMe);
	}
	
	public int getNumberOfFields()
	{
		return fields.size();
	}
}
			
			
					