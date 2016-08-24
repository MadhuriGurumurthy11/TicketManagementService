package TicketManagement.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Scanner;

import TicketManagement.dao.SeatHold;

/**
 * 
 */

/**
 * @author Madhuri Gurumurthy
 * 
 *         <p>
 *         This is the main class from where execution begins.
 * 
 *         User is given choices of input:
 * 
 *         1. To Find the total number of seats / To find the total number of
 *         seats of a particular level.
 * 
 *         2. To find and hold the seats.
 * 
 *         3. To reserve the held seats.
 * 
 *         4.To cancel the held reservation.
 * 
 *         5. To quit.
 *         </p>
 *
 */
public class TicketManagement
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		TicketService tktService = new TicketServiceImpl();
		Optional<Integer> minLevel = null;
		Optional<Integer> maxLevel = null;
		int option = 0;
		Scanner scan1, scan2;

		// --------------------------------------------------------------

		while (option != 4)
		{
			System.out.println("\n Welcome to reservation system.");
			System.out
					.println("Press 1 to find the total number of seats available.");
			System.out.println("Press 2 to find and hold the seats.");
			System.out.println("Press 5 to quit.");

			scan1 = new Scanner(System.in);
			switch (scan1.nextInt())
			{
			case 1:
				try
				{
					System.out
							.println("Do you wish to find out the number of available seats at a particular level?");
					System.out.println("Yes or no ?");
					scan2 = new Scanner(System.in);
					if (scan2.next().equalsIgnoreCase("yes"))
					{
						System.out
								.println("1 for Orchestra \n 2 for Main \n 3 for Balcony1 \n 4 for Balcony2");
						scan2 = new Scanner(System.in);
						Integer levelId = new Integer(scan2.nextInt());
						Optional<Integer> venueLevel = Optional.of(levelId);
						int seats = tktService.numSeatsAvailable(venueLevel);
						System.out
								.println(" Available number of seats in level "
										+ levelId.intValue() + " is " + seats
										+ ". \n");
					} else
					{
						Optional<Integer> noLevel = Optional.empty();
						int seats = tktService.numSeatsAvailable(noLevel);
						System.out.println("Total available seats is " + seats);
					}
				} catch (Exception e)
				{
					System.err.println(e.getMessage());
				}
				break;
			case 2:
				try
				{
					int numSeats = 0;
					String customerEmail;
					System.out.println("How many seats you want to book?");
					scan2 = new Scanner(System.in);
					numSeats = scan2.nextInt();
					System.out.println("Enter the email id.");
					scan2 = new Scanner(System.in);
					customerEmail = scan2.next();
					System.out
							.println("Do you have the preference of the level? yes or no?");

					if (scan2.next().equalsIgnoreCase("yes"))
					{
						System.out
								.println("Minimum level can be 1 and Maximum level can be 4");
						System.out
								.println("What is the minimum level you prefer?");
						scan2 = new Scanner(System.in);
						Integer minVal = new Integer(scan2.nextInt());
						minLevel = Optional.of(minVal);
						System.out
								.println("What is the maximum level you prefer?");
						scan2 = new Scanner(System.in);
						Integer maxVal = new Integer(scan2.nextInt());
						maxLevel = Optional.of(maxVal);

					} else
					{
						minLevel = Optional.empty();
						maxLevel = Optional.empty();
					}
					SeatHold seatHeld = tktService.findAndHoldSeats(numSeats,
							minLevel, maxLevel, customerEmail);

					System.out.println("Level is " + seatHeld.getLevelId()
							+ "\n" + "Row is " + seatHeld.getRowNumber()
							+ "\n Seat Nos are " + seatHeld.getSeatNumbers());
					System.out
							.println("If you are happy with the reservations, "
									+ "please press 3 within 20 seconds to reserve the seats.\n "
									+ "Otherwise please press 4 to cancel.");

					int x = 20;
					BufferedReader in = new BufferedReader(
							new InputStreamReader(System.in));

					try
					{

						long startTime = System.currentTimeMillis();
						while ((System.currentTimeMillis() - startTime) < x * 1000
								&& !in.ready())
						{
						}

						if (in.ready())
						{
							int choice = Integer.parseInt(in.readLine()
									.toString());
							if (choice == 3)
							{
								String confirmation = tktService.reserveSeats(
										seatHeld.getSeatHoldId(),
										seatHeld.getEmailId());
								System.out.println("Your confirmation id is "
										+ confirmation);
							} else
							{
								tktService.cancelHeldSeats(seatHeld);
								System.out
										.println("Your held seats are cancelled. Try again!");
							}
						} else
						{
							System.out.println("Time out!");
							tktService.cancelHeldSeats(seatHeld);
							System.out
									.println("Your held seats are cancelled. Try again!");
						}
					} catch (IOException e)
					{
						e.printStackTrace();
					}

				} catch (TicketManagementException e)
				{
					System.err.println(e.getMessage());
				}
				// ---------------------------------------------------------------------

				break;

			case 5:
				System.exit(0);
				break;

			default:
				System.out.println("Entered option is invalid");
				break;
			}
		}

	}
}
