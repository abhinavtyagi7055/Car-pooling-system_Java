package drivers;

import java.io.*;
import java.util.*;
import database.DatabaseConnection;
import java.sql.*;

public class Driver 
{
    	static String[] locations = {"Bidholi", "Prem Nagar", "Clock Tower", "Bus Stop", "Train Station"};

    	public static void registerDriver(String name, String location) 
	{
        	try (Connection conn = DatabaseConnection.getConnection()) 
		{
            		String sql = "INSERT INTO drivers (name, location) VALUES (?, ?)";
            		PreparedStatement stmt = conn.prepareStatement(sql);
            		stmt.setString(1, name);
            		stmt.setString(2, location);

            		int rowsAffected = stmt.executeUpdate();
            		if (rowsAffected > 0) 
			{
                		System.out.println("Driver registered successfully!");
            		}
        	} 
		catch (SQLException e) 
		{
            		e.printStackTrace();
        	}
    	}

    	public static List<String> assignDriver(String pickupLocation) 
	{
        	List<String> driverData = new ArrayList<>();

        	try (Connection conn = DatabaseConnection.getConnection()) 
		{
            		String sql = "SELECT id, name, location FROM drivers WHERE location = ?";
            		PreparedStatement stmt = conn.prepareStatement(sql);
            		stmt.setString(1, pickupLocation);

            		ResultSet rs = stmt.executeQuery();
            		if (rs.next()) 
			{
                		driverData.add(rs.getString("id"));
                		driverData.add(rs.getString("name"));
                		driverData.add(rs.getString("location"));
            		}
        	} 
		catch (SQLException e) 
		{
            		e.printStackTrace();
        	}

        	return driverData;
    	}

    	public static void updateDriverLocation(String driverID, String newLocation) {
        	try 
		{
            		File file = new File("drivers.txt");
            		File tempFile = new File("drivers_temp.txt");

            		BufferedReader reader = new BufferedReader(new FileReader(file));
            		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            		String line;
            		while ((line = reader.readLine()) != null) 
			{
                		String[] parts = line.split(",");
                		if (parts.length >= 3 && parts[0].equals(driverID)) 
				{
                    			writer.write(parts[0] + "," + parts[1] + "," + newLocation + "\n");
                		} 
				else 
				{
                    			writer.write(line + "\n");
                		}
            		}

            		writer.close();
            		reader.close();

            		file.delete();
            		tempFile.renameTo(file);
        	} 
		catch (IOException e) 
		{
            		System.out.println("Error updating driver location.");
        	}
    	}

    	private static int getLocationIndex(String locName) 
	{
        	for (int i = 0; i < locations.length; i++) 
		{
           		if (locations[i].equalsIgnoreCase(locName)) 
			{
                		return i;
            		}
        	}
        	return -1;
    	}
}
