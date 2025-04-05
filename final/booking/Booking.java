package booking;

import java.io.FileWriter;
import java.io.IOException;
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

        	String[] assignedDriver = Driver.assignDriver(pickupLoc);
        	String driverName = (assignedDriver != null) ? assignedDriver[1] : "No driver available";
        	String driverID = (assignedDriver != null) ? assignedDriver[0] : null;

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
}
