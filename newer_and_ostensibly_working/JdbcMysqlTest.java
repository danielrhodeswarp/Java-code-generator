import java.sql.*;

public class JdbcMysqlTest
{
	public static void main(String[] args)
	{
		System.out.println("MySQL Connect Example.");
		Connection conn = null;
		String url = "jdbc:mysql://yourserver:3306/";
		String dbName = "javaCodeGen";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root"; 
		String password = "yourpassword";
		String table = "aTable";
		
		try
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println(url + dbName);
			System.out.println("Connected to the database");
			
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
			
			while(rs.next())
			{
				//dbtime = rs.getString(1);
				System.out.println(rs.getString(1));
			} //end while

			
			
			conn.close();
			System.out.println("Disconnected from database");
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}