package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection 
{
	private static final String URL = "jdbc:mysql://localhost:3306/cpsdb";
    	private static final String USER = "root"; 
    	private static final String PASSWORD = "myroot1234";

	// connection to database
    	public static Connection getConnection() throws SQLException 
	{
        	try 
		{
            		Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver

            		return DriverManager.getConnection(URL, USER, PASSWORD);
        	} 
		catch (ClassNotFoundException e) 
		{
            		e.printStackTrace();
            		throw new SQLException("Database driver not found");
        	}
    	}
}
