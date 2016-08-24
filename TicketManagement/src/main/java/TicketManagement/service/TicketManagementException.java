/**
 * 
 */
package TicketManagement.service;

/**
 * @author Madhuri Gurumurthy
 * 
 *         This is a generic exception class for Ticket Management.
 *
 */
public class TicketManagementException extends Exception
{

	private static final long serialVersionUID = 1L;

	String message;

	public TicketManagementException(String msg)
	{
		message = msg;
	}

	public String getMessage()
	{
		return message;
	}

}
