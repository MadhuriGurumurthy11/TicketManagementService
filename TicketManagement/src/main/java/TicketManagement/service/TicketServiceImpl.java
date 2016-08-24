package TicketManagement.service;

import java.util.Optional;

import TicketManagement.dao.SeatHold;

/**
 * @author Madhuri Gurumurthy
 * 
 *         <p>
 *         This class is the implementation class of the TicketService
 *         interface.
 * 
 *         </p>
 *
 */
public class TicketServiceImpl implements TicketService
{
	private TicketValidationImpl ticketValid;

	private TicketQueryImpl ticketQuery;

	/**
	 * This method is an intermittent method which returns the number of
	 * available seats for the given level id. If no level id is provided then,
	 * it returns the total available seats in the whole venue.
	 * 
	 * @param Optional
	 *            <Integer> venueLevel level number
	 * @return int number of available seats
	 */
	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel)
	{
		ticketValid = new TicketValidationImpl();
		ticketQuery = new TicketQueryImpl();
		int noOfSeats = 0;
		if (!venueLevel.isPresent())
		{
			noOfSeats = ticketQuery.getTotalAvailableSeats();
		} else
		{
			ticketValid.isLevelIdValid(venueLevel.get().intValue());
			noOfSeats = ticketQuery.getAvailSeatsOfALevel(venueLevel.get()
					.intValue());
		}
		return noOfSeats;
	}

	/**
	 * This method is an intermittent method which find and holds the seat for
	 * the user as per their requirements.
	 * 
	 * @param int numSeats
	 * @param Optional
	 *            <Integer> minLevel
	 * @param Optional
	 *            <Integer> maxLevel
	 * @param String
	 *            customerEmail
	 * 
	 * @return SeatHold
	 * @throws TicketManagementException
	 */
	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
			Optional<Integer> maxLevel, String customerEmail)
			throws TicketManagementException
	{
		SeatHold seat;
		try
		{
			ticketValid = new TicketValidationImpl();
			ticketQuery = new TicketQueryImpl();
			// validate numSeats, minLevel, maxLevel, customerEmail
			int min = 0;
			int max = 0;
			ticketValid.isNumSeatsValid(numSeats);
			if (minLevel.isPresent())
			{
				min = minLevel.get().intValue();
				if (min == 0 || min > 4 || min < 1)
				{
					min = 1;
				}
				ticketValid.isLevelIdValid(min);

			} else
			{
				min = 0;
			}
			if (maxLevel.isPresent())
			{
				max = maxLevel.get().intValue();
				if (max == 0 || max > 4 || max < 1)
				{
					max = 4;
				}
				ticketValid.isLevelIdValid(max);
			} else
			{
				max = 0;
			}

			if (min > max)
			{
				int temp = min;
				min = max;
				max = temp;
			}
			ticketValid.validateEmail(customerEmail);
			seat = ticketQuery.findAndHoldSeats(numSeats, min, max,
					customerEmail);
		} catch (TicketManagementException e)
		{
			throw new TicketManagementException(e.getMessage());
		} catch (Exception e)
		{
			throw new TicketManagementException(e.getMessage());
		}

		return seat;
	}

	/**
	 * This method is an intermittent method which reserves the held seats and
	 * returns a confirmation code.
	 * 
	 * @param seatHoldId
	 * @param customerEmail
	 * @return String confirmationCode
	 * @throws TicketManagementException
	 */
	@Override
	public String reserveSeats(int seatHoldId, String customerEmail)
			throws TicketManagementException
	{

		try
		{
			ticketValid = new TicketValidationImpl();
			ticketQuery = new TicketQueryImpl();

			ticketValid.validateEmail(customerEmail);
			return ticketQuery.reserveSeats(seatHoldId, customerEmail);
		} catch (TicketManagementException e)
		{
			throw new TicketManagementException(e.getMessage());
		} catch (Exception e)
		{
			throw new TicketManagementException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param seatHoldId
	 *            the seat hold object
	 * @throws TicketManagementException
	 */
	public void cancelHeldSeats(SeatHold seatHold)
			throws TicketManagementException
	{
		ticketQuery = new TicketQueryImpl();

		try
		{
			ticketQuery.cancelReserveSeats(seatHold);
		} catch (TicketManagementException e)
		{
			throw new TicketManagementException(e.getMessage());
		} catch (Exception e)
		{
			throw new TicketManagementException(e.getMessage());
		}
	}

}
