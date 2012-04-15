//This is the Java class to encapsulate an ODBC database connection.

import java.sql.*;
import java.util.*;	//for Vector class
import javax.swing.*;	//for JOptionPane

public class Database
{
	private String DBMS;
	private String DSN;
	private String user;
	private String password;
	private int numberOfTables;
	private Vector tables;
	private Connection DBConnection;
	private boolean connected = false;
	
	public Database(String DSN)
	{
		DSN = DSN;
		tables = new Vector();
		numberOfTables = 0;
		
		connect();	//we have the DSN so attempt a connect
		getTableInfo();
	}
	
	public Database()
	{
		tables = new Vector();
		numberOfTables = 0;
	}
	
	public void setDSN(String newDSN)
	{
		DSN = newDSN;
		
		connect();	//DSN has changed so attempt to reconnect
		getTableInfo();
	}
	
	public String getDSN()
	{
		return DSN;
	}
	
	public void setUser(String newUser)
	{
		user = newUser;
	}
	
	public String getUser()
	{
		return user;
	}
	
	public void setPassword(String newPassword)
	{
		password = newPassword;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public int getNumberOfTables()	//not no *set*!
	{
		return numberOfTables;
	}
	
	public Vector getTables()
	{
		getTableInfo();
		return tables;
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	//return Vector of DatabaseFields
	public Vector getFieldsForTable(String tablename)
	{
		if(!connected)
		{
			connect();
		}
		
		Vector fields = new Vector();
		
		try
		{
			//try to get the fields of the table!
			String query = "SELECT * FROM " + tablename;
			
			Statement stmt = DBConnection.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			
			for(int loop = 1; loop <= numberOfColumns; loop++)
			{
				String colName = rsmd.getColumnName(loop);
				
				int colType = rsmd.getColumnType(loop);
				int colPrecision = rsmd.getPrecision(loop);
				
				boolean required;
				
				if(rsmd.isNullable(loop) == ResultSetMetaData.columnNullable)
				{
					required = false;
				}
				
				//group ResultSetMetaData.columnNoNulls and ResultSetMetaData.columnNullableUnknown
				//together as 'required'
				else
				{
					required = true;
				}
				
				fields.addElement(new DatabaseField(colName, colType, colPrecision, required));
			}
			
			stmt.close();
			return fields;
		}
		
		catch(SQLException exy)
		{
			System.err.println("SQLException: " + exy.getMessage());
			
			return null;
		}
	}
	
	public void connect()
	{
		String URL = "jdbc:odbc:" + DSN;
		
		try
		{
			//assumes we have already registered the driver class
			DBConnection = DriverManager.getConnection(URL, user, password);
		}
		
		catch(SQLException ex)
		{
			//ex.toString() includes the Java class name of the exception,
			//ex.getMessage() don't so we use that
			JOptionPane.showMessageDialog(null, "Connection failed!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
			//default yes, but what about changing DSN!
			connected = false;
			
			return;
		}
		
		connected = true;
	}
	
	private boolean getTableInfo()
	{
		if(!connected)
		{
			connect();
		}
		
		//record details of tables
		try
		{
			//get the metadata regarding this connection
			DatabaseMetaData dbmd = DBConnection.getMetaData();
			
			ResultSet rs = dbmd.getTables(null, null, null, null);
			
			//loop through all returned rows
			//first call to next() actually moves us to
			//*first* row!
			while(rs.next())
			{
				String name = rs.getString("TABLE_NAME");
				String type = rs.getString("TABLE_TYPE");
				
				//add *user* tables to our vector of tables
				if(type.equals("TABLE"))	//*not* a system table
				{
					tables.addElement(name);
					numberOfTables++;
				}
			}
		}
		
		catch(SQLException ex)
		{
			System.err.println("SQLException: " + ex.getMessage());
			
			return false;
		}
		
		return true;
	}
}
			

