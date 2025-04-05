package main;

import drivers.Driver;
import booking.Booking;

import java.util.Scanner;

public class Ques1 
{
    	public static void main(String[] args) 
	{
        	Scanner s = new Scanner(System.in);
        	System.out.println("Welcome to the Car Pooling System!");
        	System.out.println("Are you a:\n1. Customer\n2. Driver");
        	int userType = s.nextInt();

        	if (userType == 2) 
		{
            		Driver.registerDriver();
        	} 
		else 
		{
            		Booking b = new Booking();
            		int[] info = b.book();
            		System.out.println("Your ride is booked. Rider will arrive soon.");
            		System.out.println("Estimated time of arrival (ETA): " + info[1] + " minutes");
            		System.out.println("Total fare: ₹" + info[0]);
        	}
    	}
}
