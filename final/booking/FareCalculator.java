package booking;

public class FareCalculator 
{
    	static int COST_PER_KM = 12;
    	static int AVG_SPEED = 40;
	
	// Calculates fare based on distance
    	public static int calculateFare(int dist) 
	{
        	return dist * COST_PER_KM;
    	}

	// Calculates ETA in minutes
    	public static int calculateETA(int dist) 
	{
        	return (int)(((double) dist / AVG_SPEED) * 60);
    	}
}
