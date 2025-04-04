package roueRes;
import java.util.List;

public class routeRes
{
	private final List<Integer> path;
	private final int totalDist;
	private final double etaHours;
	
	publix routeRes(List<Integer> path, int totalDist, double etaHours)
	{
		this.path = path;
		this.totalDist = totalDist;
		this.etaHours = etaHours;
	}
	
	public List<Integer> getPath()
	{
		return path;
	}
	
	public int getTotalDist() 
	{
		return totalDist;
	}
	
	public double getEtaHours()
	{
		return etaHours;
	}
	
	public String toString()
	{
		return "Route: " + path + "\nTotal Distance: " + toatlDist + " km\nETA: " + String.format("%.2f",etaHours) + " hours";
	}
}