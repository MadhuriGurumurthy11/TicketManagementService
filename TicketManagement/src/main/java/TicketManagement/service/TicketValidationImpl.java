package TicketManagement.service;

import java.util.Optional;

import org.apache.commons.validator.EmailValidator;

import TicketManagement.dao.SeatHold;

/**
 * 
 */

/**
 * @author Madhuri Gurumurthy
 *
 *         <p>
 *         This is a validation class that validates the user input.
 *         </p>
 */

public class TicketValidationImpl
{

	/**
	 * This method validates the entered level of the venue.
	 * 
	 * @param venueLevel
	 */
	public void isLevelIdValid(int venueLevel) throws IllegalArgumentException
	{
		if (venueLevel < 1 || venueLevel > 4)
		{
			throw new IllegalArgumentException(
					"The level entered is not a valid number.");
		}

	}

	/**
	 * This method validates the number of seats to be booked.
	 * 
	 * @param numSeats
	 */
	public void isNumSeatsValid(int numSeats) throws IllegalArgumentException
	{
		if (numSeats < 1)
		{
			throw new IllegalArgumentException(
					"The number of seats to be booked is not a valid number.");
		}

	}

	/**
	 * This method is used to validate emails.
	 * 
	 * @param customerEmail
	 * @throws IllegalArgumentException
	 */
	public void validateEmail(String customerEmail)
			throws TicketManagementException
	{
		EmailValidator e = EmailValidator.getInstance();
		if (!e.isValid(customerEmail))
		{
			throw new TicketManagementException("Email isn't valid.");
		}

	}

}
