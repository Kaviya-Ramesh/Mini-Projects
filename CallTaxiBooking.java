import java.util.*;
class Taxi
{
  static int taxiCount = 0;
  boolean booked;
  char currentSpot;
  int freeTime;
  int totalEarnings;
    List < String > trips;
  int id;

  public Taxi ()
  {
	booked = false;
	currentSpot = 'A';
	freeTime = 6;
	totalEarnings = 0;
	taxiCount = taxiCount + 1;
	id = taxiCount;
	trips = new ArrayList < String > ();

  }
  public void setDetails (boolean booked, char currentSpot, int freeTime,
						  int totalEarnings, String tripDetail)
  {
	this.booked = booked;
	this.currentSpot = currentSpot;
	this.freeTime = freeTime;
	this.totalEarnings = totalEarnings;
	this.trips.add (tripDetail);
  }
  public void printTaxiDetails ()
  {
	System.out.println ("Taxi id- " + this.id + " Total earnings- " +
						this.totalEarnings + " Free Time- " + this.freeTime);
  }
  public void printDetails ()
  {
	System.out.println ("Taxi - " + this.id + " Total Earnings - " +
						this.totalEarnings);
	System.out.
	  println
	  ("TaxiID           BookingID    CustomerID    From    To    PickupTime    DropTime    Amount");
  for (String trip:trips)
	  {
		System.out.println (id + "     " + trip);
	  }
  }


}

public class CallTaxiBooking
{
  public static List < Taxi > createTaxi (int n)
  {
	List < Taxi > taxis = new ArrayList < Taxi > ();
	for (int i = 0; i < n; i++)
	  {
		Taxi t = new Taxi ();
		  taxis.add (t);
	  }
	return taxis;
  }
  public static List < Taxi > getFreeTaxis (char pickPoint, int pickTime,
											List < Taxi > Taxis)
  {
	List < Taxi > freeTaxis = new ArrayList < Taxi > ();
  for (Taxi t:Taxis)
	  {
		if (pickTime >= t.freeTime
			&& (Math.abs ((t.currentSpot - '0') - (pickPoint - '0'))) <=
			pickTime - t.freeTime)
		  {
			freeTaxis.add (t);
		  }
	  }
	return freeTaxis;
  }
  public static void bookTaxi (int customerID, List < Taxi > freeTaxis,
							   char pickPoint, char dropPoint, int pickTime)
  {
	int min = 999;
	Taxi bookedTaxi = null;
	int distanceBetweenPickupAndDrop = 0;
	int earnings = 0;

	int nextFreeTime = 0;
	char nextSpot = 'Z';
	String tripDetails = "";
  for (Taxi t:freeTaxis)
	  {
		int distanceBetweenCustomerAndTaxi =
		  Math.abs ((t.currentSpot - '0') - (pickPoint - '0')) * 15;
		if (distanceBetweenCustomerAndTaxi <= min)
		  {
			bookedTaxi = t;
			distanceBetweenPickupAndDrop =
			  Math.abs ((pickPoint - '0') - (dropPoint - '0')) * 15;
			earnings = (distanceBetweenPickupAndDrop - 5) * 10 + 100;
			int dropTime = pickTime - distanceBetweenPickupAndDrop / 15;
			nextFreeTime = dropTime;
			nextSpot = dropPoint;
			tripDetails =
			  "          " + customerID + "                " + customerID +
			  "             " + pickPoint + "     " + dropPoint +
			  "          " + pickTime + "         " + dropTime +
			  "          " + earnings;
			min = distanceBetweenCustomerAndTaxi;



		  }
	  }
	bookedTaxi.setDetails (true, nextSpot, nextFreeTime,
						   bookedTaxi.totalEarnings + earnings, tripDetails);
	System.out.println ("Taxi " + bookedTaxi.id + " booked");


  }
  public static void main (String[]args)
  {

	List < Taxi > taxis = createTaxi (4);

	Scanner sc = new Scanner (System.in);

	while (true)
	  {
		System.out.println ("please enter 1 for Booking Taxi");
		System.out.println ("please enter 2 for taxi Details");

		int choice = sc.nextInt ();

		switch (choice)
		  {
		  case 1:
			{
			  System.out.println ("Enter the PickUp Point:");
			  char pickPoint = sc.next ().charAt (0);
			  System.out.println ("Enter the Drop Point:");
			  char dropPoint = sc.next ().charAt (0);
			  System.out.println ("Enter the Pickup time:");
			  int pickTime = sc.nextInt ();
			  int id = 1;

			  if (pickPoint < 'A' || dropPoint > 'F' || pickPoint > 'F'
				  || dropPoint < 'A')
				{
				  System.out.
					println ("please Enter Valid points A, B, C, D, E, F");
				  return;
				}

			  List < Taxi > freeTaxis =
				getFreeTaxis (pickPoint, pickTime, taxis);
			  if (freeTaxis.size () == 0)
				{
				  System.out.println ("No free Taxi to be alloted");
				  return;

				}
			  Collections.sort (freeTaxis,
								(a, b)->a.totalEarnings - b.totalEarnings);
			  bookTaxi (id, freeTaxis, pickPoint, dropPoint, pickTime);

			  id++;
			  break;
			}
		  case 2:
		  for (Taxi t:taxis)
			  {
				t.printTaxiDetails ();
			  }
		  for (Taxi t:taxis)
			  {
				t.printDetails ();
			  }






		  }
	  }


  }
}
