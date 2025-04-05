package routes;

import java.util.Scanner;

public class Pick {
	static String[] locations = {"Bidholi", "Prem Nagar", "Clock Tower", "Bus Stop", "Train Station"};
	Scanner s = new Scanner(System.in);
    	int pickup;
   	int drop;
	public int choice() 
	{
       		System.out.println("The routes are listed below : \n\tBidholi - Prem Nagar\n\tPrem Nagar - Clock Tower\n\tClock Tower - Bus Stop\n\tBus Stop - Train Station");
        	System.out.println("The spots are numbered as following :\n\t\t1 Bidholi\n\t\t2 Prem Nagar\n\t\t3 Clock Tower\n\t\t4 Bus Stop\n\t\t5 Train Station");

        	int[] dist = {9, 11, 7, 13};

        	System.out.println("Enter the pickup point : ");
       		pickup = s.nextInt();

        	System.out.println("Enter the drop point : ");
        	drop = s.nextInt();

        	if (pickup < 1 || pickup > 5 || drop < 1 || drop > 5) 
		{
           	System.out.println("Invalid pickup or drop point!");
            	return 0;
        	}

        	if (pickup == drop) 
		{
            		System.out.println("You can walk");
            		return 0;
        	}

        	if (pickup > drop) 
		{
            		int temp = pickup;
            		pickup = drop;
            		drop = temp;
       		}

        	int check = 0;
        	for (int i = pickup - 1; i < drop - 1; i++) 
		{
            		check += dist[i];
        	}

        	return check;
    	}

    	public int getPickup() 
	{
        	return pickup;
    	}

    	public int getDrop() 
	{
        	return drop;
    	}
	public String getPickupLocation() 
	{
    		return locations[pickup - 1];
	}

	public String getDropLocation() 
	{
    		return locations[drop - 1];
	}

}
