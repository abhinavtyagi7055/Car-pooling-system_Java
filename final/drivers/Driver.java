package drivers;

import java.io.*;
import java.util.*;

public class Driver 
{
    	static String[] locations = {"Bidholi", "Prem Nagar", "Clock Tower", "Bus Stop", "Train Station"};

    	public static void registerDriver() 
	{
        	Scanner s = new Scanner(System.in);

        	System.out.println("Enter your name:");
        	String name = s.nextLine();

        	System.out.println("Select your current location:");
        	for (int i = 0; i < locations.length; i++) 
		{
            		System.out.println((i + 1) + ". " + locations[i]);
        	}

        	int locIndex = s.nextInt() - 1;
        	String location = locations[locIndex];

        	int driverID = getNextDriverID();

        	try 
		{
            	FileWriter fw = new FileWriter("drivers.txt", true);
            	fw.write(driverID + "," + name + "," + location + "\n");
            	fw.close();
            	System.out.println("Driver registered successfully. Your Driver ID is: " + driverID);
		} 
		catch (IOException e) 
		{
           		 System.out.println("Error saving driver info.");
       	 	}
   	}

    	private static int getNextDriverID() 
	{
        	int id = 1;
        	try 
		{
            		BufferedReader br = new BufferedReader(new FileReader("drivers.txt"));
            		String line;
            		while ((line = br.readLine()) != null) 
			{
                		String[] parts = line.split(",");
                		if (parts.length > 0) 
				{
                    			int lastID = Integer.parseInt(parts[0]);
                    			if (lastID >= id) 
					{
                        			id = lastID + 1;
                    			}
               			}
            		}
            		br.close();
        	} 
		catch (FileNotFoundException e) 
		{
        	} 
		catch (IOException e) 
		{
            		e.printStackTrace();
        	}
        	return id;
    	}

	public static String[] assignDriver(String pickupLocation) 
	{
    		String[] closestDriverData = null;
    		int minDistance = Integer.MAX_VALUE;

    		try 
		{
        		BufferedReader br = new BufferedReader(new FileReader("drivers.txt"));
        		String line;
        		while ((line = br.readLine()) != null) 
			{
            			String[] parts = line.split(",");
            			if (parts.length >= 3) 
				{
                			String driverID = parts[0];
                			String name = parts[1];
                			String location = parts[2];

                			int dist = Math.abs(getLocationIndex(location) - getLocationIndex(pickupLocation));
                			if (dist < minDistance) 
					{
                    				minDistance = dist;
                    				closestDriverData = parts;                  
					}
            			}
        		}
        		br.close();

        		if (closestDriverData != null) 
			{
            			System.out.println("Driver Found!");
        		}

    		} 
		catch (IOException e) 
		{
        		System.out.println("Error finding drivers.");
    		}
    		return closestDriverData;
	}


    	public static void updateDriverLocation(String driverID, String newLocation) 
	{
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
