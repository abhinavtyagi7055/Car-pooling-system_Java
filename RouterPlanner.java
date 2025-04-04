package com.rideoptimizer.routing;
public class routeplan
{
	public static void main(String[] args)
	{
		int[][] distance = {{0,10,0,30,100},{10, 0, 50, 0, 0},{0, 50, 0, 20, 10},{30, 0, 20, 0, 60},{100, 0, 10, 60, 0}};
		
		DistGraph graph = new DistGraph(distances);
		int source = 0;
		int destination = 4;
		double avgSpeed = 40.0;
		
		routeRes result = graph.getShortestRoute(source, destination, averageSpeed);
		System.out.println(result);
	}

}