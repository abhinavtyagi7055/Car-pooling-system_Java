package booking;

import java.util.*;

import java.io.*;
import java.sql.*;
import database.DatabaseConnection;
import routes.Pick;
import drivers.Driver;

public class Booking 
{
    	public int[] book() 
	{
        	Pick p = new Pick();
        	int dist = p.choice();

        	String pickupLoc = p.getPickupLocation();
        	String dropLoc = p.getDropLocation();

        	List<String> assignedDriver = Driver.assignDriver(pickupLoc);
		String driverName = "No driver available";
		String driverID = null;

		if (assignedDriver != null && assignedDriver.size() >= 2) 
		{
    			driverID = assignedDriver.get(0);
   			driverName = assignedDriver.get(1);
		} 
		else 
		{
    			System.out.println("No driver assigned. Check if driver exists at pickup location.");
		}


        	if (driverID != null) 
		{
            		Driver.updateDriverLocation(driverID, dropLoc);
        	}

        	int fare = FareCalculator.calculateFare(dist);
        	int eta = FareCalculator.calculateETA(dist);
        	int[] outp = {fare, eta};

        	try 
		{
            		FileWriter writer = new FileWriter("booking_history.txt", true);
            		writer.write("Pickup: " + pickupLoc + ", Drop: " + dropLoc + ", Distance: " + dist + " km, Fare: ₹" + fare + ", ETA: " + eta + " min, Driver: " + driverName + "\n");
            		writer.close();
        	} 
		catch (IOException e) 
		{
            		System.out.println("An error occurred while saving booking history.");
        	}

        	System.out.println("Driver Name : " + driverName);

        	return outp;
    	}
	public static void bookRide(String pickupLocation, String dropLocation, int distance, int fare, int eta, int driverId) 
	{
        	try (Connection conn = DatabaseConnection.getConnection()) 
		{
            		String sql = "INSERT INTO bookings (pickupLocation, dropLocation, distance, fare, eta, driver_id) VALUES (?, ?, ?, ?, ?, ?)";
            		PreparedStatement stmt = conn.prepareStatement(sql);
            		stmt.setString(1, pickupLocation);
            		stmt.setString(2, dropLocation);
            		stmt.setInt(3, distance);
            		stmt.setInt(4, fare);
            		stmt.setInt(5, eta);
            		stmt.setInt(6, driverId);
            		int rowsAffected = stmt.executeUpdate();
            		if (rowsAffected > 0) 
			{
                		System.out.println("Booking successful!");
            		}
        	}
		catch (SQLException e) 
		{
            		e.printStackTrace();
        	}
    	}
}
