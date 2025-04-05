import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
class pick 
{
	Scanner s = new Scanner(System.in);
	int pickup;
	int drop;
	int choice()
	{
	
		System.out.println("The routes are listed below : \n\tBidholi - Prem Nagar\n\tPrem Nagar - Clock Towe\n\tClock Tower - Bus Stop\n\t Bus Stop - Train Station");
		System.out.println("The spots are numbered as following :\n\t\t1 Bidholi\n\t\t2 Prem Nagar\n\t\t3 Clock Tower\n\t\t4 Bus Stop\n\t\t5 Train Station");
		
		int[] dist = {9,11,7,13};

		System.out.println("Enter the pickup point : ");
		pickup = s.nextInt();
	
		System.out.println("Enter the drop point : ");
		drop = s.nextInt();
		
		
		if (pickup < 1 || pickup > 5 || drop < 1 || drop > 5) 
		{
    			System.out.println("Invalid pickup or drop point!");
    			return 0;
		}

	
		int check = pickup - drop;
		
		if (check == 0)
		{
			System.out.println("You can walk");
		}
		
		if (pickup > drop) 
		{
    			int temp = pickup;
   			pickup = drop;
    			drop = temp;
		}

		check = 0;
		
		for (int i = pickup - 1;i < drop - 1; i ++)
		{
			check += dist[i];
			/*System.out.println(dist[i]+":"+check);*/
		}
		
		return check;
	}
			
	int getPickup()
	{
		return pickup;
	}

	int getDrop()
	{
		return drop;
	}
}
class FareCalculator 
{
	static int COST_PER_KM = 12;
	static int AVG_SPEED = 40;
	static int calculateFare(int dist) 
	{
        	return dist * COST_PER_KM;
    	}
	static int calculateETA(int dist) 
	{
		return (int)(((double) dist / AVG_SPEED) * 60);
	}
}


class booking
{
	static int cost = 12;
	static int avg_speed = 40;
	int[] book()
	{
		pick p = new pick();
		int dist = p.choice();
		int fare = FareCalculator.calculateFare(dist);
		int eta = FareCalculator.calculateETA(dist);
		int [] outp = new int[2];
		outp[0] = fare;
		outp[1] = eta;
		try 
		{
            		FileWriter writer = new FileWriter("booking_history.txt", true); // true = append mode

            		// Get readable pickup and drop names
            		String[] loc = {"Bidholi", "Prem Nagar", "Clock Tower", "Bus Stop", "Train Station"};
            		int pickup = p.getPickup();
            		int drop = p.getDrop();

            		writer.write("Pickup: " + loc[pickup - 1] + ", Drop: " + loc[drop - 1] +", Distance: " + dist + " km, Fare: ₹" + fare + ", ETA: " + eta + " min\n");
			writer.close();
        	} 
		catch (IOException e) 
		{
            		System.out.println("An error occurred while saving booking history.");
        	}
		return outp;
	}
}

class ques1
{
	public static void main (String[] args)
	{
		booking b = new booking();
		int [] info = new int[2];
		info = b.book();
		System.out.println("Your ride is booked. Rider will arrive soon.");
		System.out.println("Estimated time of arrival (ETA): " + info[1] + " minutes");
		System.out.println("Total fare: ₹" + info[0]);
	}
}